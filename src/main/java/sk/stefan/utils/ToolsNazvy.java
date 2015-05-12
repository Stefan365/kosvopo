package sk.stefan.utils;

import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import static javax.servlet.SessionTrackingMode.URL;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.A_Role;
import sk.stefan.MVP.model.entity.A_User;
import sk.stefan.MVP.model.entity.A_UserRole;
import sk.stefan.MVP.model.entity.District;
import sk.stefan.MVP.model.entity.Location;
import sk.stefan.MVP.model.entity.PublicBody;
import sk.stefan.MVP.model.entity.PublicPerson;
import sk.stefan.MVP.model.entity.PublicRole;
import sk.stefan.MVP.model.entity.Region;
import sk.stefan.MVP.model.entity.Subject;
import sk.stefan.MVP.model.entity.Tenure;
import sk.stefan.MVP.model.entity.Theme;
import sk.stefan.MVP.model.entity.Vote;
import sk.stefan.enums.UserType;

/**
 * Trida obsahujici pomocne metody pro vytvareni GUI
 *
 */
public abstract class ToolsNazvy {

    private static final Logger log = Logger.getLogger(ToolsNazvy.class);

    /**
     * Vytvori textove pole.
     *
     * @param caption
     * @param required
     * @return
     */
    public static TextField createFormTextField(String caption, boolean required) {
        TextField tf = new TextField(caption);
        tf.setStyleName(ValoTheme.TEXTFIELD_SMALL);
        tf.setSizeFull();
        tf.setValidationVisible(false);

        return tf;
    }

    /**
     * Vytvori textove pole pro zadanie hesla
     *
     * @param caption
     * @param required
     * @return
     */
    public static PasswordField createFormPasswordField(String caption,
            boolean required) {
        PasswordField pf = new PasswordField(caption);
        pf.setStyleName(ValoTheme.TEXTFIELD_SMALL);
        pf.setSizeFull();
        pf.setValidationVisible(false);
        return pf;
    }

    /**
     * Vytvori hlavni nadpis
     *
     * @param caption
     * @return
     */
    public static Label createPanelCaption(String caption) {
        Label la = new Label(caption);
        la.addStyleName(ValoTheme.LABEL_BOLD);
        la.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        la.addStyleName(ValoTheme.LABEL_COLORED);
        la.addStyleName(ValoTheme.LABEL_LARGE);
        la.addStyleName("appCaption");

        return la;
    }

    /**
     * Ziska mapu nazvov 'parameter : jeho typ' danej triedy ako string.
     *
     * @param cls Class dana tridy.
     * @return
     * @throws java.lang.NoSuchFieldException
     */
    public static Map<String, Class<?>> getTypParametrov(Class<?> cls)
            throws NoSuchFieldException, SecurityException {

        Map<String, Class<?>> typy = new HashMap<>();

        List<String> zozPar = ToolsNazvy.getClassProperties(cls, false);

        for (String p : zozPar) {
            Type typ = cls.getDeclaredField(p).getType();
            typy.put(p, (Class<?>) typ);
        }
        return typy;
    }

    // 4. stefan
    /**
     * Ziska nazvy parametrov danej triedy ako zoznam typu String
     *
     * @param cls Class dane tridy
     * @param keepId Ziska nazvy parametrov danej triedy ako zoznam typu String
     * @return
     * @throws java.lang.NoSuchFieldException
     */
    public static List<String> getClassProperties(Class<?> cls, boolean keepId)
            throws NoSuchFieldException, SecurityException {

        //Set<String> properties= new HashSet<>();
        List<String> properties = new ArrayList<>();

        for (Method method : cls.getDeclaredMethods()) {

            String methodName = method.getName();
            if (methodName.startsWith("set")) {
                properties.add(Character.toLowerCase(methodName.charAt(3))
                        + methodName.substring(4));
            }
        }

        if (!keepId) {
            if (properties.contains("id")) {
                properties.remove("id");
            }
        }
        return properties;
    }

    /**
     * Vrati mapu nazvov uzovatelov vhodnu pre ucely logout.
     *
     * @return
     */
    public static Map<String, Integer> getUserTypes() {

        try {
            Class<?> cls = UserType.class;
            Method getNm = cls.getDeclaredMethod("getNames");
            Method getOm = cls.getDeclaredMethod("getOrdinals");
            List<String> names = (List<String>) getNm.invoke(null);
            List<Integer> ordinals = (List<Integer>) getOm.invoke(null);

            Map<String, Integer> map = ToolsNazvy.makeEnumMap(names, ordinals);
            return map;
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException |
                NoSuchMethodException | SecurityException ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }

    }

    //5.
    /**
     * Makes map of String / Enum for list of enums.
     *
     * @param listS seznam Stringu
     * @param listI zodpovidajici seznam integeru.
     * @return
     */
    public static Map<String, Integer> makeEnumMap(List<String> listS, List<Integer> listI) {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < listI.size(); i++) {
            map.put(listS.get(i), listI.get(i));
        }
        return map;
    }

//    private static String getHtmlLabelText(String caption, String text) {
//        return String.format("<div class=v-label-%s><strong>%s</strong></div><i>%s</i>",
//                ValoTheme.LABEL_BOLD, caption, text);
//    }
    /**
     * Method for getting the Properies
     *
     * @param tabName
     * @return
     */
    public static Properties getDepictParams(String tabName) {

        String path = "depictNames/" + tabName.toLowerCase();
        ResourceBundle properties = ResourceBundle.getBundle(path);
        
        Properties prop = new Properties();
        
        for (String key : properties.keySet()) {
            String value = properties.getString(key);
//            System.out.println(key + " => " + value);
            prop.put(key, value);
        }
        return prop;
//   
//        String userDir = System.getProperty("user.dir");
//        log.info("USER DIR: " + userDir);
//
//        InputStream input = ClassLoader.class.getResourceAsStream(path + ".properties");
//        log.info("INPU IS NULL?: " + (input == null));
//
//        URL url = ToolsNazvy.class.getResource("");
//        URL url1 = ToolsNazvy.class.getResource("");
//
////        Notification.show("URL: " + url.getPath());
//        Notification.show("URL: is null? " + (url == null));
//
//        log.info("URL: is null? " + (url == null));
//        if (url != null) {
//            log.info("URL: " + (url.getPath()));
//        }
//        if (url1 != null) {
//            log.info("URL1: " + (url1.getPath()));
//        }
//
//        try {
//            String fileN = properties.getString(path+ ".properties");
//            fileN = "C:\\Users\\stefan\\Desktop\\kosvopo6\\src\\main\\resources\\depictNames\\"
//            fileN = "${catalina.home}/webapps/kosvopo/WEB-INF/columnNames/"

//                    + tabName.toLowerCase() + ".properties";
//            Properties prop = new Properties();
//            input = new FileInputStream(path + ".properties");
//            prop.load(input);

//        } catch (IOException ex) {
//            log.error(ex.getMessage(), ex);
//            return null;
//        } finally {
//            try {
//                if (input != null) {
//                    input.close();
//                }
//            } catch (IOException ex) {
//                log.error(ex.getMessage(), ex);
//            }
//        }
    }

    /**
     * Method for getting the Properies
     *
     * @param tabName
     * @return
     */
    public static Properties getPoradieParams(String tabName) {

        String path = "poradieParametrov/" + tabName.toLowerCase() + "_p";
        ResourceBundle properties = ResourceBundle.getBundle(path);
        
        Properties prop = new Properties();
        
        for (String key : properties.keySet()) {
            String value = properties.getString(key);
//            System.out.println(key + " => " + value);
            prop.put(key, value);
        }
        return prop;

//        InputStream input = null;
//        try {
//            String fileN;
//            fileN = "C:\\Users\\stefan\\Desktop\\kosvopo6\\src\\main\\resources\\poradieParametrov\\"
//                    + tabName.toLowerCase() + "_p.properties";
//
//            Properties prop = new Properties();
//            input = new FileInputStream(fileN);
//            prop.load(input);
//            input.close();
//            return prop;
//        } catch (IOException ex) {
//            log.error(ex.getMessage(), ex);
//            return null;
//        } finally {
//            try {
//                if (input != null) {
//                    input.close();
//                }
//            } catch (IOException ex) {
//                log.error(ex.getMessage(), ex);
//            }
//        }
    }

//    neni treba, je to v tabulkach ako parameter PRES_NAME
    public static String getTableDepictNames(String tabName) {

        String path = "depictNames/nazvyTabuliek";
        ResourceBundle properties = ResourceBundle.getBundle(path);
        
        Properties prop = new Properties();
        
        for (String key : properties.keySet()) {
            String value = properties.getString(key);
//            System.out.println(key + " => " + value);
            prop.put(key, value);
        }
        return prop.getProperty(tabName);
        

//        InputStream input = null;
//        try {
//            String fileN;
//            fileN = "C:\\Users\\stefan\\Desktop\\kosvopo6\\src\\main\\resources\\nazvyTabuliek.properties";
//
//            Properties prop = new Properties();
//            input = new FileInputStream(fileN);
//            prop.load(input);
//            input.close();
//
//            return prop.getProperty(tabName);
//        } catch (IOException ex) {
//            log.error(ex.getMessage(), ex);
//            return null;
//        } finally {
//            try {
//                if (input != null) {
//                    input.close();
//                }
//            } catch (IOException ex) {
//                log.error(ex.getMessage(), ex);
//            }
//        }
    }

    /**
     * Vrati vhodnu triedu podla nazvu FK entity.
     *
     *
     * @param pn
     * @return
     */
    public static Class<?> getClassFromName(String pn) {
//        log.info("MARTINKO:" + pn);
        switch (pn) {

            case "user_id":
                return A_User.class;
            case "role_id":
                return A_Role.class;
            case "user_role_id":
                return A_UserRole.class;
            case "region_id":
                return Region.class;
            case "district_id":
                return District.class;
            case "public_person_id":
                return PublicPerson.class;
            case "location_id":
                return Location.class;
            case "public_role_id":
                return PublicRole.class;
            case "public_body_id":
                return PublicBody.class;
            case "vote_id":
                return Vote.class;
            case "subject_id":
                return Subject.class;
            case "theme_id":
                return Theme.class;
            case "tenure_id":
                return Tenure.class;
            default:
                return null;
        }
    }

    /**
     * dekapits the tablename
     *
     * @param tn
     * @return
     */
    public static String decapit(String tn) {

        if (tn.contains("t_")) {
            return tn.replace("t_", "");
        } else if (tn.contains("a_")) {
            return tn.replace("a_", "");
        } else {
            return "kokosko";
        }
    }
}
