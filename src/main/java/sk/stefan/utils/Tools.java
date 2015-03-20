package sk.stefan.utils;

import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.dao.Kraj;
import sk.stefan.MVP.model.entity.dao.Location;
import sk.stefan.MVP.model.entity.dao.Okres;
import sk.stefan.MVP.model.entity.dao.PublicBody;
import sk.stefan.MVP.model.entity.dao.PublicPerson;
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.entity.dao.Subject;
import sk.stefan.MVP.model.entity.dao.Tenure;
import sk.stefan.MVP.model.entity.dao.Theme;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.MVP.model.repo.dao.UniRepo;
//import sk.stefan.MVP.view.components.InputFormLayout;

/**
 * Trida obsahujici pomocne metody pro vytvareni GUI
 *
 * @author Marek Svarc
 */
public class Tools {

    private static final Logger log = Logger.getLogger(Tools.class);

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

    // 3. stefan
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

        List<String> zozPar = Tools.getClassProperties(cls, false);

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

    private static String getHtmlLabelText(String caption, String text) {
        return String.format("<div class=v-label-%s><strong>%s</strong></div><i>%s</i>",
                ValoTheme.LABEL_BOLD, caption, text);
    }

    /**
     * Method for getting the Properies
     *
     * @param tabName
     * @return
     * @throws java.io.FileNotFoundException
     */
    public static Properties getDepictParams(String tabName) throws FileNotFoundException, IOException {
        String fileN;

        fileN = "C:\\Users\\stefan\\Desktop\\kosvopo6\\src\\main\\resources\\depictNames\\"
                + tabName.toLowerCase() + ".properties";

        Properties prop = new Properties();
        InputStream input = new FileInputStream(fileN);

        prop.load(input);
        return prop;
    }

    /**
     * Method for getting the Properies
     *
     * @param tabName
     * @return
     * @throws java.io.FileNotFoundException
     */
    public static Properties getPoradieParams(String tabName) throws FileNotFoundException, IOException {
        InputStream input;
        String fileN;
        fileN = "C:\\Users\\stefan\\Desktop\\kosvopo6\\src\\main\\resources\\poradieParametrov\\"
                + tabName.toLowerCase() + "_p.properties";
        Properties prop = new Properties();
        input = new FileInputStream(fileN);
        prop.load(input);
        input.close();
        return prop;
    }

    public static String getTableDepictNames(String tabName) throws FileNotFoundException, IOException {
        InputStream input;
        String fileN;
        fileN = "C:\\Users\\stefan\\Desktop\\kosvopo6\\src\\main\\resources\\nazvyTabuliek\\"
                + tabName.toLowerCase() + ".properties";
        Properties prop = new Properties();
        input = new FileInputStream(fileN);
        prop.load(input);
        input.close();

        return prop.getProperty(tabName);
    }

    /**
     * Vrati vhodnu triedu podla nazvu FK entity.
     *
     *
     * @param pn
     * @return 
     */
    public static Class<?> getClassFromName(String pn) {

        switch (pn) {

            case "kraj_id":
                return Kraj.class;
            case "okres_id":
                return Okres.class;
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
     * @param cls
     * @return 
     */
    @SuppressWarnings({"unchecked"})
    public static Map<String, Integer> findAllByClass(Class<?> cls) {

        Map<String, Integer> map = new HashMap<>();
        String repN;
        Integer id;

        try {
            Class<?> repoCls = Class.forName("sk.stefan.MVP.model.repo.dao.UniRepo");
            Constructor<UniRepo<? extends Object>> repoCtor;
            repoCtor = (Constructor<UniRepo<? extends Object>>) repoCls.getConstructor(Class.class);
            List<? extends Object> listObj;
            listObj = repoCtor.newInstance(cls).findAll();
//            log.info("KARAMAZOV: " + (listObj == null));
            for (Object o : listObj) {
                Method getRepNameMethod = cls.getDeclaredMethod("getPresentationName");
                repN = (String) getRepNameMethod.invoke(o);
                Method getIdMethod = cls.getDeclaredMethod("getId");
                id = (Integer) getIdMethod.invoke(o);
                map.put(repN, id);
            }
            return map;
        } catch (NoSuchMethodException | InvocationTargetException |
                IllegalArgumentException | IllegalAccessException |
                InstantiationException | SecurityException | ClassNotFoundException ex) {
            log.error(ex.getMessage());
        }
        return null;
    }

    /**
     * dekapits the tablename
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
    
    /**
     * Kontroluje, ci je volebne obdobie aktualne.
     * 
     * @param tenure_id
     * @return 
     */
    public static Boolean isActual(Integer tenure_id) {
   
        UniRepo<Tenure> tenRepo = new UniRepo<>(Tenure.class);
        Tenure ten = tenRepo.findOne(tenure_id);
        
        if (ten != null){
            
            Long today = (new Date()).getTime();
            Long since = ten.getSince().getTime();
            
            if(since <= today && (ten.getTill() == null || ten.getTill().getTime() >= today)){
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        }
        
        return null;
    }
    
    /**
     * Vrati verejne role, ktore aktualne patria do verejneho organu.
     * 
     * @param pubB
     * @return 
     */
    public static List<PublicRole> getActualPublicRoles(PublicBody pubB){

        List<PublicRole> prActual = new ArrayList<>();
        
        UniRepo<PublicRole> prRepo = new UniRepo<>(PublicRole.class);
        
        List<PublicRole> pubRoles = prRepo.findByParam("public_body_id", "" + pubB.getId());
        
        Integer tid;
        
        for (PublicRole pr : pubRoles){
            tid = pr.getTenure_id(); 
            if (Tools.isActual(tid)){
                prActual.add(pr);
            }
        }
        
        return prActual;
        
    }



}
