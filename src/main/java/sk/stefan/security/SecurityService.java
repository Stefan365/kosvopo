package sk.stefan.security;

import com.vaadin.server.VaadinSession;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sk.stefan.MVP.model.entity.dao.todo.User_log;


/**
 * Bezpečnostní Informační Služba.
 *
 * @author lukas
 */
public class SecurityService {

    private MessageDigest md;

    public SecurityService() {
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
    public void login(User_log user) {
        VaadinSession.getCurrent().setAttribute(User_log.class, user);
    }

    /**
     * Odstraní uživatele ze session.
     */
    public void logout() {
        VaadinSession.getCurrent().setAttribute(User_log.class, null);
    }

    /**
     * Vrátí aktuálně přihlášeného uživatele, uloženého v session.
     *
     * @return aktuálně přihlášeného uživatele
     */
    public User_log getCurrentUser() {
        return VaadinSession.getCurrent().getAttribute(User_log.class);
    }

    /**
     * Porovná hesla.
     *
     * @param rawPassword heslo v podobě plain text
     * @param hashPassword hash hesla
     * @return TRUE, pokud jsou hesla stejná
     */
    public boolean matchPassword(String rawPassword, byte[] hashPassword) {
        if (hashPassword == null || rawPassword == null) {
            return false;
        }
        return MessageDigest.isEqual(hashPassword, getEncryptedPassword(rawPassword));
    }

    /**
     * Vrátí hash hesla.
     *
     * @param password heslo, ze kterého bude vytvořen hash.
     * @return hash hesla
     */
    public byte[] getEncryptedPassword(String password) {
        try {
            String saltedPassword = password.toUpperCase() + password;
            md.update(saltedPassword.getBytes("UTF-8"));
            return md.digest();
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }
}
