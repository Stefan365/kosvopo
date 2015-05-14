package sk.stefan.MVP.model.serviceImpl;

import com.vaadin.server.VaadinSession;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.A_User;
import sk.stefan.MVP.model.entity.A_UserRole;
import sk.stefan.MVP.model.repo.UniRepo;
import sk.stefan.MVP.model.service.SecurityService;

/**
 * SLuzi na zabezpecenie aplikacie pred nepovolanymi vstupmi.
 *
 * @author stefan
 */
public class SecurityServiceImpl implements SecurityService {

    private static final Logger log = Logger.getLogger(SecurityServiceImpl.class);

    
    private UniRepo<A_User> userRepo;
    
    private UniRepo<A_UserRole> userRoleRepo;
    
    private MessageDigest md;

    //0.konstruktor:
    /**
     * 
     */
    public SecurityServiceImpl() {
        
        this.userRepo = new UniRepo<>(A_User.class);
        this.userRoleRepo = new UniRepo<>(A_UserRole.class);
        
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
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
     * @return TRUE, pokud jsou hesla stejná
     */
    @Override
    public Boolean checkPassword(String rawPassword, byte[] hashPassword) {

        if (hashPassword == null || rawPassword == null) {
            return Boolean.FALSE;
        }
        //return true;
        byte[] passByte = encryptPassword(rawPassword);

        return MessageDigest.isEqual(hashPassword, passByte);

    }

    /**
     * Vrátí hash hesla.
     *
     * @param password heslo, ze kterého bude vytvořen hash.
     * @return hash hesla
     */
    @Override
    public byte[] encryptPassword(String password) {
        try {
            byte[] bytes = password.getBytes();
            byte[] bytesa;
            //String saltedPassword = password.toUpperCase() + "KAROLKO";
            String saltedPassword = password;//.toUpperCase() + "KAROLKO";
            md.update(saltedPassword.getBytes("UTF-8"));
//            md.update(saltedPassword.getBytes());

            StringBuilder sb = new StringBuilder();
            StringBuilder sba = new StringBuilder();

            for (byte b : bytes) {
                sb.append(b);
                sba.append(Integer.toHexString(b));

            }
            log.info("3. PASS FORM LINE:" + sb.toString());
            log.info("4. PASS FORM LINE:" + sba.toString());

            bytesa = md.digest();

            StringBuilder sbc = new StringBuilder();
            StringBuilder sbd = new StringBuilder();

            for (byte b : bytesa) {
                sbc.append(b);
                sbd.append(Integer.toHexString(b));

            }
            log.info("5. PASS FORM LINE:" + sbc.toString());
            log.info("6. PASS FORM LINE:" + sbd.toString());

            return bytesa;

        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void initAdmin() {
        
        byte[] encPassword;
        
        A_User admin = new A_User();
        
        admin.setFirst_name("admin");
        admin.setLast_name("adminovic");
        admin.setLogin("admin");
        admin.setE_mail("admin@admin.sk");
        admin.setVisible(Boolean.TRUE);
        
        encPassword = encryptPassword("admin");
        admin.setPassword(encPassword);
        
        admin = userRepo.save(admin);
        
        //user Role:
        A_UserRole urole = new A_UserRole();
        
        urole.setUser_id(admin.getId());
        urole.setRole_id(2);
        urole.setSince((java.sql.Date) new Date());
        urole.setTill(null);
        urole.setVisible(Boolean.TRUE);
        urole.setActual(Boolean.TRUE);
        
        userRoleRepo.save(urole);
        
        String sql = "SELECT * FROM"; 
        
    }
}
