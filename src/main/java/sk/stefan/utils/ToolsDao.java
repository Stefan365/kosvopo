package sk.stefan.utils;

import com.vaadin.data.Item;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.MVP.model.repo.dao.UniRepo;

public class ToolsDao {

    private static final Logger log = Logger.getLogger(ToolsDao.class);

    //1. 
    /**
     * Ziska nazvy parametrov danej triedy ako zoznam typu String
     *
     * @param cls
     * @param keepId
     * @return
     * @throws java.lang.NoSuchFieldException
     */
    public static Set<String> getClassProperties(Class<?> cls, boolean keepId)
            throws NoSuchFieldException, SecurityException {

        Set<String> properties = new HashSet<>();

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

    //2. 
    /**
     * gets getter or setter method name for given parameter
     *
     * @param p
     * @param type
     * @return
     */
    public static String getG_SetterName(String p, String type) {

        StringBuilder sb = new StringBuilder();
        sb.append(type);//'get' or 'set'
        sb.append(Character.toUpperCase(p.charAt(0)));
        sb.append(p.substring(1));

        return sb.toString();
    }

    //3. 
    /**
     * Ziska mapu nazvov 'parameter : jeho typ' danej triedy ako string.
     *
     * @param cls
     * @return
     * @throws java.lang.NoSuchFieldException
     */
    public static Map<String, Class<?>> getTypParametrov(Class<?> cls)
            throws NoSuchFieldException, SecurityException {

        Map<String, Class<?>> typy = new HashMap<>();

        Set<String> zozPar = ToolsDao.getClassProperties(cls, true);

        for (String p : zozPar) {
            Type typ = cls.getDeclaredField(p).getType();
            //typy.put(p, getShortTyp(typ.toString()));
            typy.put(p, (Class<?>) typ);

        }
        return typy;
    }

    /**
     * Ziska mapu parameter : typ danej triedy.
     *
     * @param typ
     * @return
     */
    public static String getShortTyp(String typ) {
        String[] parts = typ.split("\\.");
        List<String> str = new ArrayList<>(Arrays.asList(parts));
        return str.get(str.size() - 1);
    }

    /**
     * Ziska getter pre result set.
     *
     * @param typ
     * @return
     */
    public static String getGettersForResultSet(String typ) {
        StringBuilder sb = new StringBuilder();
        sb.append("get");
        if (null != typ) {
            switch (typ) {
                case "java.lang.Integer":
                    sb.append("Int");
                    break;
                case "java.util.Date":
                    sb.append("Timestamp");
                    break;
                case "byte[]":
                case "java.lang.Byte[]":
                    sb.append("Bytes");
                    break;
                case "sk.stefan.enums.VoteResult":
                case "sk.stefan.enums.VoteAction":
                case "sk.stefan.enums.Stability":
                case "sk.stefan.enums.UserType":
                case "sk.stefan.enums.PublicUsefulness":
                case "sk.stefan.enums.FilterType":
                case "sk.stefan.enums.NonEditableFields":

                    sb.append("Short");
                    break;
                default:
                    String fields[] = typ.split("\\.");
                    sb.append(fields[fields.length - 1]);
                    break;
            }
        }

        String s = sb.toString();
        //System.out.println("SKACU: " + s);
        // ResultSet#getTimestamp() you need java.sql.Timestamp
        return s;
    }

    /**
     * Metoda sluzi k prevodu java.util.Date do stringu, ktory je ochotny
     * spolknout typ MYSQL timestamp.
     *
     * @param date
     * @return
     */
    public static String utilDateToString(java.util.Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date.getTime());

    }

    /**
     *
     */
    public static Integer getShortFromEnum(Class<?> cls, Object value) {

        try {
            Method entMethod = cls.getMethod("ordinal");
            Integer i = (Integer) entMethod.invoke(value);
            return i;
        } catch (NoSuchMethodException | SecurityException |
                IllegalArgumentException | InvocationTargetException |
                IllegalAccessException ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }
    }

    /**
     *
     * @param clsEnum
     * @param sh
     * @return 
     */
    public static Object getEnumVal(Class<?> clsEnum, Short sh) {
        
        try {
            
            Method enumMethod = clsEnum.getDeclaredMethod("values");
            Object[] enumVals = (Object[]) (enumMethod.invoke(null));
            Object enumVal = enumVals[sh];
            
            return enumVal;
        } catch (NoSuchMethodException | SecurityException |
                IllegalArgumentException | InvocationTargetException |
                IllegalAccessException ex) {
            log.error(ex.getMessage(), ex);
            return null;
        } 
    }
    
    
    /**
     * Neuniverzalna metoda len pre potreby hlasovania. 
     * zuniverzalnit v buducnosti.
     * 
     * @param item
     * @param vot
     * @return 
     */
    public static void updateVoteItem(Item item, Vote vot){
        
//        item.getItemProperty("id").setValue(vot.getId()); //it is read only.
        item.getItemProperty("vote_date").setValue(vot.getVote_date());
        item.getItemProperty("subject_id").setValue(vot.getSubject_id());
        item.getItemProperty("internal_nr").setValue(vot.getInternal_nr());
        item.getItemProperty("result_vote").setValue(vot.getResult_vote().ordinal());
        item.getItemProperty("for_vote").setValue(vot.getFor_vote());
        item.getItemProperty("against_vote").setValue(vot.getAgainst_vote());
        item.getItemProperty("refrain_vote").setValue(vot.getRefrain_vote());
        item.getItemProperty("absent").setValue(vot.getAbsent());
        item.getItemProperty("visible").setValue(vot.getVisible());
        
        
    }

}
