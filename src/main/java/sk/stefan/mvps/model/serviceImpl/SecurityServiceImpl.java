package sk.stefan.mvps.model.serviceImpl;

import com.vaadin.server.VaadinSession;

import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stefan.enums.UserType;
import sk.stefan.mvps.model.entity.A_Role;
import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.mvps.model.entity.A_UserRole;
import sk.stefan.mvps.model.repo.GeneralRepo;
import sk.stefan.mvps.model.repo.UniRepo;
import sk.stefan.mvps.model.service.SecurityService;
import sk.stefan.mvps.model.service.UserService;

/**
 * SLuzi na zabezpecenie aplikacie pred nepovolanymi vstupmi.
 *
 * @author stefan
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    private static final Logger log = Logger.getLogger(SecurityServiceImpl.class);

    private final GeneralRepo genRepo;

    private final UniRepo<A_UserRole> userRoleRepo;

    private final UniRepo<A_Role> roleRepo;

    @Autowired
    private UserService userService;

    public SecurityServiceImpl() {

        this.genRepo = new GeneralRepo();
        this.userRoleRepo = new UniRepo<>(A_UserRole.class);
        this.roleRepo = new UniRepo<>(A_Role.class);

    }

    /**
     * Přidá přihlášeného uživatele do session.
     *
     * @param user uživatel
     */
    @Override
    public void login(A_User user) {

        VaadinSession.getCurrent().setAttribute(A_User.class, user);

    }

    /**
     * Odstraní uživatele ze session.
     */
    @Override
    public void logout() {

        VaadinSession.getCurrent().setAttribute(A_User.class, null);

    }

    /**
     * Vrátí aktuálně přihlášeného uživatele, uloženého v session.
     *
     * @return aktuálně přihlášeného uživatele
     */
    @Override
    public A_User getCurrentUser() {
        return VaadinSession.getCurrent().getAttribute(A_User.class);
    }

    /**
     * Porovná hesla.
     *
     * @param rawPassword  heslo v podobe plain text
     * @param hashPassword hash hesla z databazy
     * @return TRUE, pokial su hesla rovnake.
     */
    @Override
    public Boolean checkPassword(String rawPassword, byte[] hashPassword) {

        if (hashPassword == null || rawPassword == null) {
            return Boolean.FALSE;
        }
        //return true;
        byte[] passByte = genRepo.encryptPassword(rawPassword);

        return MessageDigest.isEqual(hashPassword, passByte);

    }


    @Override
    public byte[] getPassword(Integer id) throws SQLException {

        return genRepo.getPassword(id + "");

    }

    @Override
    public void updatePassword(String newPwd, String uid) throws SQLException {

        genRepo.updatePassword(newPwd, uid);

    }

    @Override
    public byte[] encryptPassword(String rawPassword) {
        return genRepo.encryptPassword(rawPassword);
    }

    @Override
    public A_UserRole getActualUserRoleForUser(A_User user) {
        if (user == null) {
            return null;
        }

        List<A_UserRole> roles = userRoleRepo.findByTwoParams("user_id", String.valueOf(user.getId()), "actual", "true");
        if (roles.size() > 1) {
            throw new RuntimeException("Nalezeny více jak jedna aktuální role uživatele: " + user.getPresentationName());
        } else {
            return roles.isEmpty() ? null : roles.get(0);
        }
    }

    @Override
    public List<A_Role> getAvailableRoles() {
        return roleRepo.findAll();
    }

    @Override
    public boolean currentUserHasRole(UserType userType) {
        A_User user = getCurrentUser();
        if (user != null) {
            UserType currentUserType = userService.getUserType(user);
            if (currentUserType != null && currentUserType == userType) {
                return true;
            }
        }
        return false;
    }


}
