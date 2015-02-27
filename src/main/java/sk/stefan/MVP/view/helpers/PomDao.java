package sk.stefan.MVP.view.helpers;

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

public class PomDao {

    //1. 
    /**
     * Ziska nazvy parametrov danej triedy ako zoznam typu String
     *
     */
    public static Set<String> getClassProperties(Class<?> cls, boolean keepId)
            throws NoSuchFieldException, SecurityException {

        Set<String> properties = new HashSet<String>();

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
     */
    public static Map<String, Class<?>> getTypParametrov(Class<?> cls)
            throws NoSuchFieldException, SecurityException {

        Map<String, Class<?>> typy = new HashMap<String, Class<?>>();

        Set<String> zozPar = PomDao.getClassProperties(cls, true);

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
     */
    public static String getShortTyp(String typ) {
        String[] parts = typ.split("\\.");
        List<String> str = new ArrayList<String>(Arrays.asList(parts));
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
                case "sk.stefan.enums.VoteResults":
                    sb.append("Int");
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
     * @param date
     * @return 
     */
    public static String utilDateToString(java.util.Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date.getTime());

    }

}
