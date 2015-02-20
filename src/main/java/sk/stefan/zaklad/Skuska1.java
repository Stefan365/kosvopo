package sk.stefan.zaklad;

import java.lang.reflect.Constructor;
import sk.stefan.interfaces.PresentationName;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.dao.Change;
import sk.stefan.MVP.model.entity.dao.Kraj;
import sk.stefan.MVP.model.entity.dao.Location;
import sk.stefan.MVP.model.entity.dao.PersonClassification;
import sk.stefan.MVP.model.entity.dao.PublicPerson;
import sk.stefan.MVP.model.entity.dao.PublicPerson2;
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.entity.dao.Role;
import sk.stefan.MVP.model.entity.dao.Subject;
import sk.stefan.MVP.model.entity.dao.Tenure;
import sk.stefan.MVP.model.entity.dao.Theme;
import sk.stefan.MVP.model.entity.dao.User;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.MVP.model.entity.dao.VoteOfRole;
import sk.stefan.MVP.model.entity.dao.VoteClassification;
import sk.stefan.MVP.model.entity.dao.Okres;
import sk.stefan.MVP.model.entity.dao.PublicBody;

import sk.stefan.MVP.model.repo.dao.UniRepo;

public class Skuska1<T> {

    private static final Logger log = Logger.getLogger(Skuska1.class);

    public static void main(String[] args) throws NoSuchFieldException,
            SecurityException, InstantiationException, IllegalAccessException,
            NoSuchMethodException, IllegalArgumentException,
            InvocationTargetException, SQLException {

        //******* SKUSKA REFLEXIE, map:
        Skuska1<VoteClassification> sk = new Skuska1<>();
        Map<String, Integer> map = sk.findAllByClass(Kraj.class);
        if (map != null) {
            for (String s:map.keySet()){
               log.info("PRVKY MAPY: " + s + " : " + map.get(s));                
            }
        }
        
        map = sk.findAllByClass(Location.class);
        if (map != null) {
            for (String s:map.keySet()){
               log.info("PRVKY MAPY: " + s + " : " + map.get(s));                
            }
        }
        map = sk.findAllByClass(Okres.class);
        if (map != null) {
            for (String s:map.keySet()){
               log.info("PRVKY MAPY: " + s + " : " + map.get(s));                
            }
        }
        map = sk.findAllByClass(PersonClassification.class);
        if (map != null) {
            for (String s:map.keySet()){
               log.info("PRVKY MAPY: " + s + " : " + map.get(s));                
            }
        }
        map = sk.findAllByClass(PublicBody.class);
        if (map != null) {
            for (String s:map.keySet()){
               log.info("PRVKY MAPY: " + s + " : " + map.get(s));                
            }
        }
        map = sk.findAllByClass(PublicPerson.class);
        if (map != null) {
            for (String s:map.keySet()){
               log.info("PRVKY MAPY: " + s + " : " + map.get(s));                
            }
        }
        map = sk.findAllByClass(PublicRole.class);
        if (map != null) {
            for (String s:map.keySet()){
               log.info("PRVKY MAPY: " + s + " : " + map.get(s));                
            }
        }
        map = sk.findAllByClass(Subject.class);
        if (map != null) {
            for (String s:map.keySet()){
               log.info("PRVKY MAPY: " + s + " : " + map.get(s));                
            }
        }
        map = sk.findAllByClass(Tenure.class);
        if (map != null) {
            for (String s:map.keySet()){
               log.info("PRVKY MAPY: " + s + " : " + map.get(s));                
            }
        }
        map = sk.findAllByClass(Theme.class);
        if (map != null) {
            for (String s:map.keySet()){
               log.info("PRVKY MAPY: " + s + " : " + map.get(s));                
            }
        }
        map = sk.findAllByClass(Vote.class);
        if (map != null) {
            for (String s:map.keySet()){
               log.info("PRVKY MAPY: " + s + " : " + map.get(s));                
            }
        }
        map = sk.findAllByClass(VoteClassification.class);
        if (map != null) {
            for (String s:map.keySet()){
               log.info("PRVKY MAPY: " + s + " : " + map.get(s));                
            }
        }
        map = sk.findAllByClass(VoteOfRole.class);
        if (map != null) {
            for (String s:map.keySet()){
               log.info("PRVKY MAPY: " + s + " : " + map.get(s));                
            }
        }
        
//      
        
        //******* koniec SKUSKy REFLEXIE, map.
        
        
        //******* SKUSKA REFLEXIE:
//        Skuska1<VoteClassification> sk = new Skuska1<>();
//        List<? extends Object> obj = sk.skusReflex(Kraj.class);
//        if (obj != null) {
//            log.info("DELLKA kraj: " + obj.size());
//        }
//
//        obj = sk.skusReflex(VoteClassification.class);
//        if (obj != null) {
//            log.info("DELLKA: act_cl:" + obj.size());
//        }
//
//        obj = sk.skusReflex(Location.class);
//        if (obj != null) {
//            log.info("DELLKA location: " + obj.size());
//        }
//        obj = sk.skusReflex(Okres.class);
//        if (obj != null) {
//            log.info("DELLKA okres: " + obj.size());
//        }
//        obj = sk.skusReflex(PersonClassification.class);
//        if (obj != null) {
//            log.info("DELLKA: per_clas:" + obj.size());
//        }
//        obj = sk.skusReflex(PublicBody.class);
//        if (obj != null) {
//            log.info("DELLKA: pub_body" + obj.size());
//        }
//        obj = sk.skusReflex(PublicPerson.class);
//        if (obj != null) {
//            log.info("DELLKA pub_person: " + obj.size());
//        }
//        obj = sk.skusReflex(PublicRole.class);
//        if (obj != null) {
//            log.info("DELLKA pub_role: " + obj.size());
//        }
//        obj = sk.skusReflex(Role.class);
//        if (obj != null) {
//            log.info("DELLKA role: " + obj.size());
//        }
//        obj = sk.skusReflex(Subject.class);
//        if (obj != null) {
//            log.info("DELLKA subject: " + obj.size());
//        }
//        obj = sk.skusReflex(Tenure.class);
//        if (obj != null) {
//            log.info("DELLKA tenure: " + obj.size());
//        }
//        obj = sk.skusReflex(Theme.class);
//        if (obj != null) {
//            log.info("DELLKA theme: " + obj.size());
//        }
//        obj = sk.skusReflex(User.class);
//        if (obj != null) {
//            log.info("DELLKA user: " + obj.size());
//        }
//        obj = sk.skusReflex(Vote.class);
//        if (obj != null) {
//            log.info("DELLKA vote: " + obj.size());
//        }
//        obj = sk.skusReflex(VoteOfRole.class);
//        if (obj != null) {
//            log.info("DELLKA vot_of_role: " + obj.size());
//        }

        //*******KONIEC SKUSKY REFLEXIE:
//		sk1.tryDate();
//		sk1.skusIf();
        //sk.tryDate1();
//        sk.showTypes();
        //sk.skusString();
        //sk1.tryDate3();
        //sk1.tryParse();
        // PublicPerson ent = new PublicPerson();
        // UniGetAllRepo<PublicPerson> uniRepo = new
        // UniGetAllRepo<>(ent.getClass());
        // UniRepo<PublicPerson> uniRepo = new UniRepo<>(PublicPerson.class);
//********************* testovanie UNIREPO
//          Skuska1<VoteClassification> sk1 = new Skuska1<>();
//          sk1.skusaj(VoteClassification.class);
//          
//          Skuska1<Change> sk2 = new Skuska1<>(); sk2.skusaj(Change.class);
//          
//          Skuska1<Kraj> sk3 = new Skuska1<>(); sk3.skusaj(Kraj.class);
//          
//          Skuska1<Location> sk4 = new Skuska1<>(); sk4.skusaj(Location.class);
//          
//          Skuska1<PersonClassification> sk5 = new Skuska1<>();
//          sk5.skusaj(PersonClassification.class);
//         
//          Skuska1<PublicBody> sk6 = new Skuska1<>();
//          sk6.skusaj(PublicBody.class);
//          
//          Skuska1<PublicPerson> sk7 = new Skuska1<>();
//          sk7.skusaj(PublicPerson.class);
//          
//          Skuska1<PublicRole> sk8 = new Skuska1<>();
//          sk8.skusaj(PublicRole.class);
//          
//          Skuska1<Role> sk9 = new Skuska1<>(); sk9.skusaj(Role.class);
//          
//          Skuska1<Subject> sk10 = new Skuska1<>();
//          sk10.skusaj(Subject.class);
//          
//          Skuska1<Tenure> sk11 = new Skuska1<>();
//          sk11.skusaj(Tenure.class);
//          
//          Skuska1<Theme> sk12 = new Skuska1<>(); sk12.skusaj(Theme.class);
//          
//          Skuska1<User> sk13 = new Skuska1<>(); sk13.skusaj(User.class);
//          
//          Skuska1<UserRole> sk14 = new Skuska1<>();
//          sk14.skusaj(UserRole.class);
//          
//          Skuska1<Vote> sk15 = new Skuska1<>(); sk15.skusaj(Vote.class);
//          
//          Skuska1<VoteOfRole> sk16 = new Skuska1<>();
//          sk16.skusaj(VoteOfRole.class);
        //*********************
        /* Class c = Integer.class; Class ca = PublicPerson.class; Class
         cb = ent.getClass();
         
         // log.info(c.getCanonicalName() + ":" + c.getName() + ":" +
         // c.getSimpleName());

         /*
         * try { //List<PublicPerson> listLudi =
         * uniRepo.findAllUni(ent.getClass()); List<PublicPerson> listLudi =
         * uniRepo.findAll();
         * 
         * for (PublicPerson pp : listLudi){
         * log.info(pp.getPresentationName()); } } catch
         * (InstantiationException | IllegalAccessException |
         * NoSuchMethodException | IllegalArgumentException |
         * InvocationTargetException | SQLException e) { e.printStackTrace(); }
         */

        /*
         * PublicPerson ent = new PublicPerson(); ent.setId(1234); try {
         * PomDao.doIt(ent); } catch (IllegalAccessException |
         * IllegalArgumentException | InvocationTargetException |
         * NoSuchMethodException e) { e.printStackTrace(); }
         */
        /*
         * PublicPerson pp = new PublicPerson(); Map<String, String> ms =
         * PomDao.getTypParametrov(pp); for (String s : ms.keySet()){
         * log.info(s + ":"+ ms.get(s)); } log.info("KOKO" +
         * ":"+ "KOKOR".substring(0,3));
         */

        /*
         * Set<String> sa = Pom.getClassProperties(pp, true);
         * 
         * for (String str : sa){ log.info("*" +
         * Pom.getSetterName(str) + "*"); log.info("*" +
         * Pom.getGetterName(str) + "*");
         * 
         * }
         * 
         * VoteOfRole vor = new VoteOfRole(); vor.setDecision("ANO");
         * Pom.getComboList(vor.getClass(), vor);
         * 
         * log.info(Pom.getSetterName("id")); /
         */
    }

    @SuppressWarnings("unused")
    private void skusString() {
        String str1 = "KOKOTKO";
        String str2 = "KO";

        str1.toLowerCase().contains(str2.toLowerCase());
        log.info("contains: " + str1.toLowerCase().contains(str2.toLowerCase()));

        String st = "sadd".getClass().toString();
        Class<?> cls = "sadd".getClass();

        if (cls.toString().contains("java.lang.String")) {
            log.info("TRIEDA: " + st.contains("java.lang.String"));
        }

    }

    /**
     * This method is for testing reflective UniRepo class. to be tested on all
     * possible entities , on constraint-free database.
     *
     */
    private void skusaj(Class<?> cls) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {

        log.info("\nXXXXX: 1.FIND ALL");
        // 1.
        UniRepo<T> uniRepo = new UniRepo<>(cls);
        List<T> listEnt = uniRepo.findAll();
        for (T t : listEnt) {
            log.info("1. PASSED *"
                    + ((PresentationName) t).getPresentationName());
        }

        log.info("\nXXXXX: 2.FIND ONE");
        // 2.
        int i;
        T ent;
        for (i = 1; i <= listEnt.size(); i++) {
            ent = uniRepo.findOne(i);
            log.info("2. PASSED! *" + ((PresentationName) ent).getPresentationName() + "*");
        }

        log.info("\nXXXXX: 3.FIND BY PARAM");
        // 3.
        List<T> listEnt1 = uniRepo.findByParam("id", "1");
        for (T t : listEnt1) {
            log.info("3. PASSED! *" + ((PresentationName) t).getPresentationName() + "*");
        }

        log.info("\nXXXXX: 4.SAVE NEW");
        // 4.
        @SuppressWarnings("unchecked")
        T ent1 = (T) cls.newInstance();
        T ent2 = uniRepo.save(ent1);
        log.info("4. PASSED! *" + ((PresentationName) ent2).getPresentationName() + "*");

        log.info("\nXXXXX: 5.SAVE ALREDY IN DB");
        // 5.
        @SuppressWarnings("unchecked")
        T ent3 = uniRepo.save(ent2);
        log.info("5. PASSED *"
                + ((PresentationName) ent3).getPresentationName());

        log.info("\nXXXXX: 6.DELETE");
        // 6.
        uniRepo.delete(ent1);
        uniRepo.delete(ent2);
        log.info("\nXXXXX: 6. PASSED!");

        log.info("\nXXXXX: 7.COPY");
        // 7.
        @SuppressWarnings("unchecked")
        T ent4 = (T) cls.newInstance();
        uniRepo.copy(ent3, ent4);
        String entMetName = "getId";

        Method entMethod;
        try {
            entMethod = cls.getDeclaredMethod(entMetName);
            Integer i3 = (Integer) entMethod.invoke(ent3);
            Integer i4 = (Integer) entMethod.invoke(ent4);
            log.info("7. PASSED *"
                    + ((PresentationName) ent4).getPresentationName());
            log.info("7. i3 = " + i3 + " i4 = " + i4);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private int skusException() {

        int a = 123;

        try {

            // return a;
            throw new Exception();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 6;
        }
    }

    public void tryDate() {

        java.util.Date actualDate = new java.util.Date();

        java.sql.Date sqlDate = new java.sql.Date(actualDate.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date date1 = sdf.parse("2009-12-31 12:34:35");
            java.sql.Date sdate1 = new java.sql.Date(date1.getTime());

            // Date date2 = sdf.parse("2010-01-31");
            log.info("java.util VETSI/MENSI: "
                    + actualDate.compareTo(date1));
            log.info("java.util MENSI/VETSI: "
                    + date1.compareTo(actualDate));
            log.info("java.util ROVNY/ROVNY: "
                    + date1.compareTo(date1));

            log.info("java.sql V/M: " + sqlDate.compareTo(sdate1));
            log.info("java.sql M/V: " + sdate1.compareTo(sqlDate));
            log.info("java.sql R/R: " + sdate1.compareTo(sdate1));

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void skusIf() {
        String s = null;
        String s1 = "";

        if (true && (s1.equals("") || s.equals("KOKOS"))) {
            log.info("YES");
        } else {
            log.info("NO");
        }
    }

    @SuppressWarnings("deprecation")
    private void tryDate1() {

        java.util.Date ad = new java.util.Date();
        java.sql.Date sad = new java.sql.Date(ad.getTime()); // actual date in
        //log.info("util:" + ad + " sql:" + sad);

        java.sql.Date sqlDate = new java.sql.Date(ad.getTime());

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date1 = sdf.parse("2009-12-31 23:32:34");
            java.sql.Date sdate1 = new java.sql.Date(date1.getTime());
            String clName = date1.getClass().getCanonicalName();

            //log.info("util2:*" + date1 + "* sql2:*" + sdate1 + "* CLname:*" + clName + "*");
            //System.out.format("SYSTEM FORMAT: %s %n", date1);
            Calendar s = new GregorianCalendar();
            //System.out.format("SYSTEM CALENDAR: %s %n", s);

            UniRepo<PublicPerson2> pp2Repo = new UniRepo<PublicPerson2>(PublicPerson2.class);
//			PublicPerson2 pp2 = new PublicPerson2();
//			pp2.setFirst_name("Kamilko");
//			pp2.setLast_name("Maly");
//			pp2.setVisible(Boolean.TRUE);
//			pp2.setDate_of_birth(new Date());
//			pp2 = pp2Repo.save(pp2);

            List<PublicPerson2> lpp2 = pp2Repo.findByParam("visible", "TRUE");
            if (lpp2 != null) {
                log.info("VELKOST: " + lpp2.size());
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void tryParse() {
        Integer i = 1;
        String s = "", s1;
        Date d = new Date();

        s1 = d.getClass().getCanonicalName();

        System.out.format("Int: %s %n", i.getClass().getCanonicalName());
        System.out.format("String: %s %n", s.getClass().getCanonicalName());
        System.out.format("Date: %s %n", d.getClass().getCanonicalName());

        String fields[] = s1.split("\\.");
        System.out.format("Date2: %s %n", fields[fields.length - 1]);
    }

    /*
     * SYSTEM CALENDAR: java.util.GregorianCalendar[time=1415172328424,areFieldsSet=true,areAllFieldsSet=tr
     ue,lenient=true,zone=sun.util.calendar.ZoneInfo[id="Europe/Prague",offset=3600000,dstSavings=3600000
     ,useDaylight=true,transitions=141,lastRule=java.util.SimpleTimeZone[id=Europe/Prague,offset=3600000,
     dstSavings=3600000,useDaylight=true,startYear=0,startMode=2,startMonth=2,startDay=-1,startDayOfWeek=
     1,startTime=3600000,startTimeMode=2,endMode=2,endMonth=9,endDay=-1,endDayOfWeek=1,endTime=3600000,en
     dTimeMode=2]],firstDayOfWeek=2,minimalDaysInFirstWeek=4,ERA=1,YEAR=2014,MONTH=10,WEEK_OF_YEAR=45,WEE
     K_OF_MONTH=1,DAY_OF_MONTH=5,DAY_OF_YEAR=309,DAY_OF_WEEK=4,DAY_OF_WEEK_IN_MONTH=1,AM_PM=0,HOUR=8,HOUR
     _OF_DAY=8,MINUTE=25,SECOND=28,MILLISECOND=424,ZONE_OFFSET=3600000,DST_OFFSET=0] 
     */
    private String getTimeStampDate() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Calendar s = new GregorianCalendar(2013,0,0,13,24,56);
        //log.info(sdf.format(s.getTime()));
        //2013-24-28 13:24:56
        //2013-02-28 13:24:56

        String fin;
        Date date1;
        try {
            date1 = sdf.parse("2009-12-31 23:32:34");
            fin = sdf.format(date1.getTime());
            //2009-32-31 23:32:34
            //2009-12-31 23:32:34

            return fin;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        //System.out.format("SYSTEM CALENDAR: %s %n", s);
		/*
         StringBuilder sb = new StringBuilder();
         sb.append(s.YEAR);
         sb.append("-");
         sb.append(s.MONTH);
         sb.append("-");
         sb.append(s.DAY_OF_MONTH);
         sb.append(" ");
         sb.append(s.HOUR_OF_DAY);
         sb.append(":");
         sb.append(s.MINUTE);
         sb.append(":");
         sb.append(s.SECOND);
         return sb.toString();
         */

    }

    private String getTimeStampDate(java.util.Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date.getTime());

    }

    /**
     * Metoda sluziaca k vypisaniu id, predpoklada sa, ze objekt ma metodu getId
     *
     */
    public static void doIt(Object ent) throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException {

        Class<?> cls = ent.getClass();
        Method entMethod = cls.getDeclaredMethod("getId");

        Integer id = (Integer) entMethod.invoke(ent);
        log.info("ID: " + id);
        log.info("ID: " + " = '" + entMethod.invoke(ent) + "'");

    }

    private void tryDate3() {
        log.info(this.getTimeStampDate(new Date()));
    }

    private void showTypes() {
        Integer i = 4;
        String s = "";
        Boolean bol = true;

        log.info(i.getClass().getCanonicalName());
        log.info(s.getClass().getCanonicalName());
        log.info(bol.getClass().getCanonicalName());

    }

    private void vypisDaco() {
        log.info("Kamilko Peteraj");
    }

    @SuppressWarnings("unchecked")
    private List<? extends Object> skusReflex(Class<?> cls) {
        try {
            Class<?> clazz = Class.forName("sk.stefan.MVP.model.repo.dao.UniRepo");
            //Class<?> cls2 = Class.forName("sk.stefan.zaklad.Skuska");

            //log.info("CLAZZ: " + clazz.getCanonicalName());
            //log.info("CLS2: " + cls2.getCanonicalName());
            @SuppressWarnings("unchecked")
            Constructor<UniRepo<? extends Object>> ctor = (Constructor<UniRepo<? extends Object>>) clazz.getConstructor(Class.class);
            //Constructor<Skuska> csku = (Constructor<Skuska>) cls2.getConstructor(int.class);
            return ctor.newInstance(cls).findAll();
            //return csku.newInstance(5).getList();

        } catch (NoSuchMethodException | InvocationTargetException |
                IllegalArgumentException | IllegalAccessException |
                InstantiationException | SecurityException | ClassNotFoundException ex) {
            log.error(ex.getMessage());
        }
        return null;
    }

    @SuppressWarnings({"unchecked"})
    private Map<String, Integer> findAllByClass(Class<?> cls) {

        Map<String, Integer> map = new HashMap<>();
        String repN;
        Integer id;

        try {
            Class<?> repoCls = Class.forName("sk.stefan.MVP.model.repo.dao.UniRepo");
            Constructor<UniRepo<? extends Object>> repoCtor;
            repoCtor = (Constructor<UniRepo<? extends Object>>) repoCls.getConstructor(Class.class);
            List<? extends Object> listObj;
            listObj = repoCtor.newInstance(cls).findAll();

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

}
