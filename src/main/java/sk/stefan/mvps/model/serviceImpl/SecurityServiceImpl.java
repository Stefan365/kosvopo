package sk.stefan.mvps.model.serviceImpl;

import com.vaadin.server.VaadinSession;
import java.security.MessageDigest;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.mvps.model.repo.GeneralRepo;
import sk.stefan.mvps.model.service.SecurityService;

/**
 * SLuzi na zabezpecenie aplikacie pred nepovolanymi vstupmi.
 *
 * @author stefan
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    private static final Logger log = Logger.getLogger(SecurityServiceImpl.class);

    private final GeneralRepo genRepo;
    

    //0.konstruktor:
    /**
     * 
     */
    public SecurityServiceImpl() {
        
        this.genRepo = new GeneralRepo();
        
    }

    /**
     * Přidá přihlášeného uživatele do session.
     *
     * @param user uživatel
     *
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
     * @param rawPassword heslo v podobe plain text
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
    public void updatePassword(String newPwd, String uid) throws SQLException  {
        
        genRepo.updatePassword(newPwd, uid);
        
    }

    @Override
    public byte[] encryptPassword(String rawPassword) {
        return genRepo.encryptPassword(rawPassword);
    }


}
