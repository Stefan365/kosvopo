/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.serviceImpl;

import com.vaadin.ui.Notification;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.A_Role;
import sk.stefan.MVP.model.entity.A_User;
import sk.stefan.MVP.model.entity.A_UserRole;
import sk.stefan.MVP.model.repo.GeneralRepo;
import sk.stefan.MVP.model.repo.UniRepo;
import sk.stefan.MVP.model.service.UserService;
import sk.stefan.enums.UserType;

/**
 *
 * @author stefan
 */
public class UserServiceImpl implements UserService {

    private static final Logger log = Logger.getLogger(UserServiceImpl.class);
    private final UniRepo<A_User> userRepo = new UniRepo<>(A_User.class);
    private final UniRepo<A_UserRole> userRoleRepo = new UniRepo<>(A_UserRole.class);
    private final UniRepo<A_Role> roleRepo = new UniRepo<>(A_Role.class);
    private final GeneralRepo genRepo = new GeneralRepo();
    private final String tn = "a_user";
    
    
    @Override
    public A_User getUserByLogin(String login) {
        
        List<A_User> usrs = userRepo.findByParam("login", login);
        
        if(usrs == null || usrs.isEmpty()){
            return null;
        } else {
            return usrs.get(0);
        }
    }

    @Override
    public A_User save(A_User user) {
        return userRepo.save(user, true);
    }

    @Override
    public void modifyPassword(String paramName, String paramValue, A_User user) {
        try {
            userRepo.updateParam(paramName, paramValue, "" + user.getId(), true);
        } catch (SQLException ex) {
            log.error(ex);
            Notification.show("Userservice" + ex);
        }
    }

    @Override
    public List<A_User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public void delete(A_User user) {
        
        try {
            genRepo.deactivateWithSlavesTree(tn, user.getId());
            genRepo.doCommit();
//        userRepo.deactivateOneOnly(user);
//        toto sa musi deaktivovat stromovo.
        } catch (SQLException ex) {
            log.error(ex.getMessage(),ex);
        }
    }

    @Override
    public byte[] getEncPasswordByLogin(String login) {
        
        List<A_User> usrs = userRepo.findByParam("login", login);
        if (usrs == null || usrs.isEmpty()){
            return null;
        } else {
            A_User user = usrs.get(0);
            return user.getPassword();
        }
        
    }

    @Override
    public UserType getUserType(A_User user){
        
        A_UserRole aurole = this.getActualRole(user);
//        log.debug("2. UROLE IS NULL?" + (aurole == null));
        return this.getType(aurole);
        
    }    
    
        /**
     * Get actual public roles for public person
     *
     * @param user
     * @return 
     */
    private A_UserRole getActualRole(A_User user) {
//        Tenure ten;
        Date dSince;
        Date dTill;

        Integer id = user.getId();
        List<A_UserRole> rAll = userRoleRepo.findByParam("user_id", "" + id);
        
//        List<A_UserRole> rActual = new ArrayList<>();
        // actual date
        java.util.Date ad = new java.util.Date();
        java.sql.Date sad = new java.sql.Date(ad.getTime()); // actual date in
        // sql mode

        for (A_UserRole ur : rAll) {
            
            dSince = ur.getSince();
            dTill = ur.getTill();
            
            if ((sad.compareTo(dSince) == 1 && ((dTill == null) || dTill
                    .compareTo(sad) == 1))) {
                return ur;
            }
        }

        return null;
    }
    
    /**
     * @param urole
     * @return 
     */
    private UserType getType(A_UserRole urole){
//        log.debug("UROLE IS NULL" + (urole==null));
        
        Integer roleId = urole.getRole_id();
        A_Role role = roleRepo.findOne(roleId);
        if (role!= null){
            return role.getRole();
        } 
        return null;
        
    }

}
