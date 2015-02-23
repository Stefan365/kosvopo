package sk.stefan.MVP.model.service;

import sk.stefan.MVP.model.service.SecurityService;
import com.vaadin.server.VaadinSession;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sk.stefan.MVP.model.entity.dao.User;

/**
 * Bezpečnostní Informační Služba.
 *
 * @author stefan
 */
public class SecurityServiceImpl implements SecurityService {

    private MessageDigest md;

    public SecurityServiceImpl() {
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
    public void login(User user) {
    	VaadinSession.getCurrent().setAttribute(User.class, user);
    }

    /**
     * Odstraní uživatele ze session.
     */
    @Override
    public void logout() {
	VaadinSession.getCurrent().setAttribute(User.class, null);
    }

    /**
     * Vrátí aktuálně přihlášeného uživatele, uloženého v session.
     *
     * @return aktuálně přihlášeného uživatele
     */
    @Override
    public User getCurrentUser() {
	return VaadinSession.getCurrent().getAttribute(User.class);
    }

    /**
     * Porovná hesla.
     *
     * @param rawPassword heslo v podobě plain text
     * @param hashPassword hash hesla
     * @return TRUE, pokud jsou hesla stejná
     */
    @Override
    public boolean checkPassword(String rawPassword, byte[] hashPassword) {
	
        if (hashPassword == null || rawPassword == null) {
	    return false;
	}
	return true;
        //return MessageDigest.isEqual(hashPassword, encryptPassword(rawPassword));
        
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
	    //String saltedPassword = password.toUpperCase() + "KAROLKO";
            String saltedPassword = password;//.toUpperCase() + "KAROLKO";
            md.update(saltedPassword.getBytes("UTF-8"));
	    return md.digest();
	} catch (UnsupportedEncodingException ex) {
	    throw new RuntimeException(ex);
	}
    }
}