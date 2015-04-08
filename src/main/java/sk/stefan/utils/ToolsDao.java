package sk.stefan.utils;

import com.vaadin.data.Item;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.dao.A_Hierarchy;
import sk.stefan.MVP.model.entity.dao.PublicBody;
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.entity.dao.Tenure;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.interfaces.Filterable;

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
    public static synchronized Set<String> getClassProperties(Class<?> cls, boolean keepId)
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
    public static synchronized String getG_SetterName(String p, String type) {

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
    public static synchronized Map<String, Class<?>> getTypParametrov(Class<?> cls)
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
     * 
     * @param cls
     * @return 
     */
    public static Class<?> transformToPrimitive(Class<?> cls) {

        String typ = cls.getCanonicalName();

        switch (typ) {
            case "java.lang.Integer":
//                return int.class;
                return Integer.TYPE;
                
            case "java.lang.Byte[]":
                return byte[].class;
//                return Byte[].TYPE;
            case "java.util.Date":
                
                 return String.class;
            case "java.sql.Timestamp":
                 return String.class;
            case "java.sql.Date":
                 return String.class;
            
                
            case "java.lang.Short":
            case "sk.stefan.enums.VoteResult":
            case "sk.stefan.enums.VoteAction":
            case "sk.stefan.enums.Stability":
            case "sk.stefan.enums.UserType":
            case "sk.stefan.enums.PublicUsefulness":
            case "sk.stefan.enums.FilterType":
            case "sk.stefan.enums.NonEditableFields":
            case "sk.stefan.enums.PublicRoleType":
//                return short.class;
                return Integer.TYPE;
                
            case "java.lang.Long":
//                return long.class;
                return Long.TYPE;
            
            case "java.lang.Boolean":
//                return boolean.class;
                return Boolean.TYPE;
            default:
                return cls;
        }

    }
    


    /**
     * Ziska mapu parameter : typ danej triedy.
     *
     * @param typ
     * @return
     */
    public static synchronized String getShortTyp(String typ) {
        String[] parts = typ.split("\\.");
        List<String> str = new ArrayList<>(Arrays.asList(parts));
        return str.get(str.size() - 1);
    }

    /**
     * Ziska getter pre result set.
     *
     * @param cls
     * @return
     */
    public static synchronized String getGettersForResultSet(Class<?> cls) {

        String typ = cls.getCanonicalName();

        StringBuilder sb = new StringBuilder();

        sb.append("get");

        if (null != typ) {
            switch (typ) {
                case "java.lang.Integer":
                    sb.append("Int");
                    break;
                case "java.util.Date":
                    sb.append("Timestamp");
//                    sb.append("Date");
                    break;
                case "java.sql.Date":
                    sb.append("Date");
                    break;
                case "byte[]":
                case "java.lang.Byte[]":
                case "java.io.InputStream":
                    sb.append("Bytes");
//                    sb.append("Blob");

                    break;
                case "sk.stefan.enums.VoteResult":
                case "sk.stefan.enums.VoteAction":
                case "sk.stefan.enums.Stability":
                case "sk.stefan.enums.UserType":
                case "sk.stefan.enums.PublicUsefulness":
                case "sk.stefan.enums.FilterType":
                case "sk.stefan.enums.NonEditableFields":
                case "sk.stefan.enums.PublicRoleType":

                    sb.append("Short");
                    break;
//                    sb.append("d");
//                    break;
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
     * Ziska getter pre result set.
     *
     * @param cls
     * @return
     */
    public static synchronized String getSetterForPreparedStatement(Class<?> cls) {

        String typ = cls.getCanonicalName();

        StringBuilder sb = new StringBuilder();

        sb.append("set");

        if (null != typ) {
            switch (typ) {
                case "java.lang.Integer":
                    sb.append("Int");
                    break;
                case "java.util.Date":
                case "java.sql.Date":
                    //toto nieje potreba, ale budiz.
                    sb.append("String");
                    break;
                case "byte[]":
                case "java.lang.Byte[]":
                case "java.io.InputStream":
                    sb.append("Bytes");

                    break;
                case "sk.stefan.enums.VoteResult":
                case "sk.stefan.enums.VoteAction":
                case "sk.stefan.enums.Stability":
                case "sk.stefan.enums.UserType":
                case "sk.stefan.enums.PublicUsefulness":
                case "sk.stefan.enums.FilterType":
                case "sk.stefan.enums.NonEditableFields":
                case "sk.stefan.enums.PublicRoleType":

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
     *
     * @param date
     * @return
     */
    public static synchronized String utilDateToString(java.util.Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date.getTime());

    }

    /**
     * Metoda sluzi k prevodu java.sqlDate do stringu, ktory je ochotny
     * spolknout typ MYSQL timestamp.
     *
     * @param date
     * @return
     */
    public static synchronized String sqlDateToString(java.sql.Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date.getTime());

    }

    /**
     *
     * @param cls
     * @param value
     * @return
     */
    public static synchronized Integer getShortFromEnum(Class<?> cls, Object value) {

        try {

            Method entMethod = cls.getMethod("ordinal");
//            log.info("KOKSKO1: " + entMethod.getName());
//            log.info("KOKSKO2: " + value);
//            log.info("KOKSKO3: " + cls.getCanonicalName());

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
    public static synchronized Object getEnumVal(Class<?> clsEnum, Short sh) {

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
     * Neuniverzalna metoda len pre potreby hlasovania. zuniverzalnit v
     * buducnosti.
     *
     * @param item
     * @param vot
     */
    @SuppressWarnings("unchecked")
    public static synchronized void updateVoteItem(Item item, Vote vot) {

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

    /**
     * Nastavi filter na zdruzeny comboBox.
     *
     * @param c
     * @param touchingTn
     * @param intVal
     */
    public static synchronized void addFiltersToTouched(Filterable c, String touchingTn, Integer intVal) {

        String comboTouchedTn = c.getTableName();
        List<String> hSeq = ToolsFiltering.getHierarchicalSequence(comboTouchedTn, touchingTn);
        List<A_Hierarchy> hASeq = ToolsFiltering.getFinalHierSequence(hSeq);
        List<Integer> idsC = ToolsFiltering.getFinalIds(hASeq, intVal);
        c.applyFilter(idsC);

    }

    /**
     * pomocna funkcia.
     *
     * @param clsE
     * @return
     */
    public static synchronized String getTableName(Class<?> clsE) {

        try {
            Field tnFld = clsE.getDeclaredField("TN");
            String tn = (String) tnFld.get(null);

//            Method getTnMethod = clsE.getDeclaredMethod("getTN");
//            String Tn = (String) getTnMethod.invoke(null);
            return tn;
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException |
                SecurityException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * pomocna funkcia.
     *
     * @param clsE
     * @return
     */
    public static synchronized String getTitleName(Class<?> clsE) {
        try {
            Field tnFld = clsE.getDeclaredField("PRES_NAME");
            String title = (String) tnFld.get(null);
            return title;
        } catch (IllegalAccessException | IllegalArgumentException |
                NoSuchFieldException | SecurityException e) {
//            Notification.show("");
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * @param cls
     * @return
     */
    public static synchronized Map<String, Integer> findAllByClass(Class<?> cls) {
        Map<String, Integer> map = new HashMap<>();
        String repN;
        Integer id;
        try {
            Class<?> repoCls = Class.forName("sk.stefan.MVP.model.repo.dao.UniRepo");
            Constructor<UniRepo<? extends Object>> repoCtor;
            repoCtor = (Constructor<UniRepo<? extends Object>>) repoCls.getConstructor(Class.class);
            List<? extends Object> listObj;
            log.info("KAROLKO, CLS:" + (cls.getCanonicalName()));
            listObj = repoCtor.newInstance(cls).findAll();
            for (Object o : listObj) {
                Method getRepNameMethod = cls.getMethod("getPresentationName");
                repN = (String) getRepNameMethod.invoke(o);
                Method getIdMethod = cls.getMethod("getId");
                id = (Integer) getIdMethod.invoke(o);
                map.put(repN, id);
            }
            return map;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalArgumentException | IllegalAccessException | InstantiationException | SecurityException | ClassNotFoundException ex) {
            log.error(ex.getMessage(), ex);
        }
        return null;
    }

    /**
     * Kontroluje, ci je volebne obdobie aktualne.
     *
     * @param tenure_id
     * @return
     */
    public static synchronized Boolean isActual(Integer tenure_id) {
        UniRepo<Tenure> tenRepo = new UniRepo<>(Tenure.class);
        Tenure ten = tenRepo.findOne(tenure_id);
        if (ten != null) {
            Long today = (new Date()).getTime();
            Long since = ten.getSince().getTime();
            if (since <= today && (ten.getTill() == null || ten.getTill().getTime() >= today)) {
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
    public static synchronized List<PublicRole> getActualPublicRoles(PublicBody pubB) {

        List<PublicRole> prActual = new ArrayList<>();
        UniRepo<PublicRole> prRepo = new UniRepo<>(PublicRole.class);
        List<PublicRole> pubRoles = prRepo.findByParam("public_body_id", "" + pubB.getId());
        Integer tid;
        for (PublicRole pr : pubRoles) {
            tid = pr.getTenure_id();
            if (ToolsDao.isActual(tid)) {
                prActual.add(pr);
            }
        }
        return prActual;
    }

}
