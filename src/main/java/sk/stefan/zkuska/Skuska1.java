package sk.stefan.zkuska;

import com.vaadin.data.Container;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.util.filter.Like;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.OptionGroup;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sk.stefan.DBconnection.DoDBconn;
import sk.stefan.MVP.model.entity.A_Hierarchy;
import sk.stefan.MVP.model.entity.Location;
import sk.stefan.MVP.model.entity.PublicPerson2;
import sk.stefan.MVP.model.entity.A_User;
import sk.stefan.MVP.model.entity.Document;
import sk.stefan.MVP.model.entity.District;
import sk.stefan.MVP.model.entity.PublicPerson;
import sk.stefan.MVP.model.entity.PublicRole;
import sk.stefan.MVP.model.entity.Region;
import sk.stefan.MVP.model.entity.Tenure;
import sk.stefan.MVP.model.entity.Vote;
import sk.stefan.MVP.model.entity.VoteClassification;
import sk.stefan.MVP.model.entity.VoteOfRole;
import sk.stefan.MVP.model.repo.GeneralRepo;
import sk.stefan.MVP.model.repo.UniRepo;
import sk.stefan.MVP.model.service.SecurityService;
import sk.stefan.MVP.model.service.UniTableService;
import sk.stefan.MVP.model.serviceImpl.SecurityServiceImpl;
import sk.stefan.MVP.model.serviceImpl.UniTableServiceImpl;
import sk.stefan.MVP.model.serviceImpl.UserServiceImpl;
import sk.stefan.enums.NonEditableFields;
import sk.stefan.enums.VoteAction;
import sk.stefan.enums.VoteResult;
import sk.stefan.interfaces.PresentationName;
import sk.stefan.utils.ToolsNames;
import sk.stefan.utils.ToolsDao;

public class Skuska1<T> {

    private A a, b, c;

    private static final Logger log = Logger.getLogger(Skuska1.class);

    public Skuska1() {

    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws NoSuchFieldException,
            SecurityException, InstantiationException, IllegalAccessException,
            NoSuchMethodException, IllegalArgumentException,
            InvocationTargetException, SQLException {

//        Byte[] b = new  Byte[12];
//        log.info(b.getClass().getCanonicalName());
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/context/BasicContext.xml");

        Skuska ska = (Skuska) ctx.getBean("skuskaApp");
//        Skuska ska = new Skuska(4); //nefunguje!!! vsetko musi byt vytvorene v ramci springu 
//        ska.skusRepo();

        Skuska1<VoteClassification> sk;
        sk = (Skuska1<VoteClassification>) ctx.getBean("skuska1App", Skuska1.class);


//        sk.skusRolu();
        sk.skusEnum();
//        sk.skusSaveVsPassupdate();
//        sk.skusStromTree();
//        sk.skusItemToEntity();
//        sk.skusChangedRepo();
//        sk.skusNewRepo();
//        sk.skusField();
//        sk.skusAdmin();
//        sk.skusVoteSave();
//        sk.skusFormatter();
//        sk.skusSaveU();
//        sk.skusUniRepo();
//        sk.skusUser();
//        sk.skusRepo();
//        sk.skusFileDownload();
//        sk.skusUpdaredRepo();
//        sk.skusPole();
//        sk.skusSqlDate();

//        sk.skusItem();
//        sk.skusA();
//
//        log.info("CLASS:*" + VoteClassification.class.getCanonicalName()+"*");
//        
//        UniRepo<Vote> votRepo;
//        votRepo = (UniRepo<Vote>) ctx.getBean("uniRepo", UniRepo.class);
////        
//        Vote v = votRepo.findOne(4);
//        log.info("VOTE: " + v.getPresentationName());
//        
//        
//        sk.skusDeclaredVsmethod();
//        sk.skusShortFromEnum();
//        sk.skusEnumFromShort();
//        
//        sk.skusVoteOfRole();
//        skc.testAHierarchy();
//        Skuska ska = (Skuska)ctx.getBean("skuskaApp");
//        
//        List<Integer> lis = ska.getList();
//        for (int i:lis){
//            log.info("LIST: " + i);
//        }
//        Skuska1<VoteClassification> sk = new Skuska1<>();
//        sk.skusKlonuj();
//        sk.loadProperties();
//        sk.addToIterableList();
//        String s = skc.makeReference("t_location");
//        log.info("*" + s + "*");
//        skc.checkContains();
//        skc.skusDate();
//        sk.skusDepict();
//        
//        
//        Byte[] by = new Byte[5];
//        byte[] byq = new byte[5];
//        
//        log.info("TRIEDA:" + by.getClass().getCanonicalName());
//        log.info("TRIEDA:" + byq.getClass().getCanonicalName());
//        
        //sk.skusToArray();
//        List<Location> loc = uniRepo.findAll();
//        for (Location l: loc){
//            log.info("L" + l.getMestka_cast());
//        }
        //******* SKUSKA REFLEXIE, map:
//        Skuska1<VoteClassification> sk = new Skuska1<>();
//        Map<String, Integer> map = sk.findAllByClass(Kraj.class);
//        if (map != null) {
//            for (String s:map.keySet()){
//               log.info("PRVKY MAPY: " + s + " : " + map.get(s));                
//            }
//        }
//        
//        map = sk.findAllByClass(Location.class);
//        if (map != null) {
//            for (String s:map.keySet()){
//               log.info("PRVKY MAPY: " + s + " : " + map.get(s));                
//            }
//        }
//        map = sk.findAllByClass(Okres.class);
//        if (map != null) {
//            for (String s:map.keySet()){
//               log.info("PRVKY MAPY: " + s + " : " + map.get(s));                
//            }
//        }
//        map = sk.findAllByClass(PersonClassification.class);
//        if (map != null) {
//            for (String s:map.keySet()){
//               log.info("PRVKY MAPY: " + s + " : " + map.get(s));                
//            }
//        }
//        map = sk.findAllByClass(PublicBody.class);
//        if (map != null) {
//            for (String s:map.keySet()){
//               log.info("PRVKY MAPY: " + s + " : " + map.get(s));                
//            }
//        }
//        map = sk.findAllByClass(PublicPerson.class);
//        if (map != null) {
//            for (String s:map.keySet()){
//               log.info("PRVKY MAPY: " + s + " : " + map.get(s));                
//            }
//        }
//        map = sk.findAllByClass(PublicRole.class);
//        if (map != null) {
//            for (String s:map.keySet()){
//               log.info("PRVKY MAPY: " + s + " : " + map.get(s));                
//            }
//        }
//        map = sk.findAllByClass(Subject.class);
//        if (map != null) {
//            for (String s:map.keySet()){
//               log.info("PRVKY MAPY: " + s + " : " + map.get(s));                
//            }
//        }
//        map = sk.findAllByClass(Tenure.class);
//        if (map != null) {
//            for (String s:map.keySet()){
//               log.info("PRVKY MAPY: " + s + " : " + map.get(s));                
//            }
//        }
//        map = sk.findAllByClass(Theme.class);
//        if (map != null) {
//            for (String s:map.keySet()){
//               log.info("PRVKY MAPY: " + s + " : " + map.get(s));                
//            }
//        }
//        map = sk.findAllByClass(Vote.class);
//        if (map != null) {
//            for (String s:map.keySet()){
//               log.info("PRVKY MAPY: " + s + " : " + map.get(s));                
//            }
//        }
//        map = sk.findAllByClass(VoteClassification.class);
//        if (map != null) {
//            for (String s:map.keySet()){
//               log.info("PRVKY MAPY: " + s + " : " + map.get(s));                
//            }
//        }
//        map = sk.findAllByClass(VoteOfRole.class);
//        if (map != null) {
//            for (String s:map.keySet()){
//               log.info("PRVKY MAPY: " + s + " : " + map.get(s));                
//            }
//        }
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
        T ent2 = uniRepo.save(ent1, false);
        log.info("4. PASSED! *" + ((PresentationName) ent2).getPresentationName() + "*");

        log.info("\nXXXXX: 5.SAVE ALREDY IN DB");
        // 5.
        @SuppressWarnings("unchecked")
        T ent3 = uniRepo.save(ent2, false);
        log.info("5. PASSED *"
                + ((PresentationName) ent3).getPresentationName());

        log.info("\nXXXXX: 6.DELETE");
        // 6.
//        uniRepo.delete(ent1);
//        uniRepo.delete(ent2);
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

    private void skusUniRepoParam() {
        try {
            UniRepo<Location> uniRepo = new UniRepo<>(Location.class);

            uniRepo.updateParam("mestka_cast", "KOKOSOVO", "6", false);
            uniRepo.updateParam("visible", "false", "6", false);
            uniRepo.updateParam("mestka_cast", null, "8", false);

            UniRepo<A_User> uniRepo1 = new UniRepo<>(A_User.class);
            uniRepo.updateParam("password", "KOKOSOVO", "1", false);

            List<A_User> allUs = uniRepo1.findAll();
            for (A_User u : allUs) {
                log.info(u.getPresentationName() + " : " + Arrays.toString(u.getPassword()));
            }
        } catch (SQLException ex) {
            log.error(ex);
        }
    }

    private void skusToArray() {
        Filter f1 = new Like("koko", "dfd");
        Filter f2 = new Like("joko", "dfd");
        Filter f3 = new Like("moko", "dfd");

        List<Filter> fls = new ArrayList<>();

        fls.add(f1);
        fls.add(f2);
        fls.add(f3);

        Filter[] fla = fls.toArray(new Filter[0]);
        Container.Filter f2r = new Or(fla);

        log.info("DLZKA: " + fla.length);

    }

    /**
     *
     */
    public void skusDepict() {

        Properties pro = ToolsNames.getDepictParams("t_vote");//.getProperty(key);
        for (String s : pro.stringPropertyNames()) {
            log.info(s + " : " + pro.getProperty(s));

        }

    }

    public void optionSelect() {
        OptionGroup sample = new OptionGroup("Select an option");
        for (int i = 0; i < 5; i++) {
            sample.addItem(i);
            sample.setItemCaption(i, "Option " + i);
        }
        sample.select(2);
        sample.setNullSelectionAllowed(false);
        sample.setHtmlContentAllowed(true);
        sample.setImmediate(true);
    }

    private List<String> klonujList(List<String> list) {
        List<String> cloned = new ArrayList<>(list);
        return cloned;
    }

    private void skusKlonuj() {
        List<String> b, c, d;

        List<String> a = new ArrayList<>();
        a.add("a");
        b = klonujList(a);
        c = klonujList(a);

        a.add("a");
        b.add("b");
        c.add("c");

        for (String s : a) {
            log.info("A:" + s);
        }
        for (String s : b) {
            log.info("B:" + s);
        }
        for (String s : c) {
            log.info("C:" + s);
        }

    }

    private Map<String, String> loadProperties() {
        this.prop = loadProperties(hierProps);
        log.info("SIZE:" + prop.size());
        return null;

    }

    private final Map<String, String> futuresMap = new HashMap<String, String>();
    private Properties prop;

    private final String hierProps
            = "C:\\Users\\stefan\\Desktop\\kosvopo6\\src\\main\\resources\\hierarchy\\classHierarchy.properties";

    //1.
    private Properties loadProperties(String hp) {

        Properties defaultProps = null;
        try {
            defaultProps = new Properties();
            FileInputStream in = new FileInputStream(hp);
            defaultProps.load(in);
            in.close();
        } catch (IOException ex) {
            log.error(ex);
        }

        Enumeration e = defaultProps.propertyNames();

        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            log.info(key + " -- " + defaultProps.getProperty(key));
        }

        return defaultProps;
    }

    private void addToIterableList() {

        List<List<String>> mainList = new ArrayList<>();
        List<String> list;

        for (int i = 0; i < 5; i++) {
            list = new ArrayList<>();
            list.add("ZACIATOK" + i);
            mainList.add(list);
        }

        for (List<String> l : mainList) {
            l.add("KONIEC");
        }

        for (List<String> l : mainList) {
            for (String s : l) {
                log.info(s);
            }
            log.info("");
        }
    }

    private String makeReference(String tn) {
        String replace = tn.replace("t_", "");
        replace += "_id";
        return replace;
    }

    private void checkContains() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        Integer in = new Integer(1);
        Integer ina = 1;

        log.info("PRVY: " + list.contains(in));
        log.info("DRUHY: " + list.contains(ina));
    }

    private void testAHierarchy() {
        Class<A_Hierarchy> clsT = A_Hierarchy.class;
        UniRepo<A_Hierarchy> repo = new UniRepo<>(clsT);
        log.info("HIERARCHIA0");

//        A_Hierarchy a = new A_Hierarchy();
        try {
            A_Hierarchy ent = (A_Hierarchy) clsT.newInstance();
            log.info("HIERARCHIA1:" + ent.getTable_name());

        } catch (InstantiationException ex) {
            log.error(ex);
        } catch (IllegalAccessException ex) {
            log.error(ex);
        }

        Class<Location> cls = Location.class;
        UniRepo<Location> repoL = new UniRepo<>(cls);
        log.info("HIERARCHIA2");

        try {
            Location ent = (Location) cls.newInstance();
            log.info("HIERARCHIA3:" + ent.getLocation_name());

        } catch (InstantiationException ex) {
            log.error(ex);
        } catch (IllegalAccessException ex) {
            log.error(ex);
        }

    }

    private void skusDate() {

//        java.sql.Date dateA = java.sql.Date.valueOf("2008-06-18");
        Date d = new Date();
        java.sql.Date dateA = new java.sql.Date(d.getTime());
        java.sql.Date dateB;

        UniRepo<Tenure> ur = new UniRepo<>(Tenure.class);

        Tenure t = ur.findOne(3);
        dateA = t.getSince();
        dateB = t.getTill();
        Date tdy = new Date();
        Long todays = tdy.getTime();

        log.info("Date value : " + dateA); //prints out 2008-06-18
        log.info("Converted to Timestamp : " + dateA.getTime());
        log.info("Date value : " + dateB); //prints out 2008-06-18
        log.info("Converted to Timestamp : " + dateB.getTime());
        Long rozd = todays - dateA.getTime();
        log.info("Rozdiel ms: " + rozd);
        log.info("Rozdiel sec: " + rozd / 1000);
        log.info("Rozdiel dni: " + rozd / 1000 / 3600 / 24);

    }

    private void skusVoteOfRole() {
        UniRepo<VoteOfRole> vorRepo = new UniRepo<>(VoteOfRole.class);

        VoteOfRole vor = new VoteOfRole();
        vor.setPublic_role_id(1);
        vor.setVote_id(4);
        vor.setDecision(VoteAction.REFAIN);
        vor.setVisible(Boolean.TRUE);

        vorRepo.save(vor, true);
    }

    private void skusShortFromEnum() {

        VoteAction va = VoteAction.REFAIN;//3

        int vas = ToolsDao.getShortFromEnum(VoteAction.class, va);

        log.info("VAS:" + vas);

    }

    private void skusEnumFromShort() {

        try {
            Short sh = 3;

            Object en = ToolsDao.getEnumVal(VoteAction.class, sh);

            log.info("MENO: " + ((VoteAction) en).getName());

            UniRepo<VoteOfRole> vorRepo = new UniRepo<>(VoteOfRole.class);
            VoteOfRole vor = vorRepo.findOne(44);
//            vor.setDecision(VoteAction.REFAIN);

            Method entMethod = (VoteOfRole.class).getMethod("setDecision", VoteAction.class);
            entMethod.invoke(vor, en);
            vorRepo.save(vor, false);

        } catch (NoSuchMethodException | SecurityException |
                IllegalArgumentException | InvocationTargetException |
                IllegalAccessException ex) {
            log.error(ex.getMessage(), ex);
        }

    }

    private void skusDeclaredVsmethod() {
        VoteAction va;

        Class<?> cls = VoteAction.class;

        for (Method m : cls.getDeclaredMethods()) {
            log.info("DECLARED: " + m.getName());
        }
        for (Method m : cls.getMethods()) {
            log.info("METHOD: " + m.getName());
        }

    }

    private void skusA() {
        List<A> lisa = new ArrayList<>();
        a = new A(1);
        b = new A(2);
        c = new A(3);

        lisa.add(a);
        lisa.add(b);
        lisa.add(c);

        for (A a : lisa) {
            log.info("PRED: " + a.getNum());
        }

        a = new A(5);
        b = new A(6);
        c = new A(7);

//        for (A a : lisa){
//            a = new A(5);
//        }
        for (A a : lisa) {
            log.info("PO: " + a.getNum());
        }

    }

    private void skusItem() {

        UniRepo<Vote> votRepo = new UniRepo<>(Vote.class);

        try {
            String tn = "t_vote";
            SQLContainer sqlCont = DoDBconn.createSqlContainera(tn);

            Object itemId = sqlCont.addItem();
            Item item = sqlCont.getItem(itemId);

            Vote vot = votRepo.findOne(4);

            ToolsDao.updateVoteItem(item, vot);

            sqlCont.commit();

        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }

    }

    private void skusSqlDate() {
        Date d = new Date();
        java.sql.Date dateA = new java.sql.Date(d.getTime());

        String s = ToolsDao.sqlDateToString(dateA);

        log.debug("DATE:*" + s + "*");
    }

    private void skusPole() {

        String[] uneditCol = new String[]{"c", "a", "b"};
        List<String> ne = Arrays.asList(uneditCol);
        for (String s : ne) {
            log.debug("NAME: " + s);
        }

//        String[] stockArr = new String[ne.size()];
        String[] stockArr = ne.toArray(uneditCol);

        for (String s : stockArr) {
            log.debug("NAME ARRAY: " + s);

        }
    }

    private void skusUpdaredRepo() {
        UniRepo<PublicRole> prolRepo = new UniRepo<>(PublicRole.class);
        PublicRole pr1 = prolRepo.findOne(2);
        PublicRole pr2 = prolRepo.findOne(4);

        log.debug("PROLE 1: " + pr1.getName());
        log.debug("PROLE 2: " + pr2);

    }

    private void skusFileDownload() {

        FileOutputStream fos = null;
        try {
            UniRepo<Document> docRepo = new UniRepo<>(Document.class);
            Document doc = docRepo.findOne(4);
            log.debug("DOKUMENT: " + doc.getFile_name());

            //tuto logiku skryva FileDOwnloader:
            String fileN = "C:\\Users\\stefan\\Desktop\\downloads\\" + doc.getFile_name();
            fos = new FileOutputStream(fileN);
            fos.write(doc.getDocument());
            fos.flush();

        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }

            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
            }
        }
    }

    private void skusRepo() {
        try {
            UniRepo<District> uniRepo = new UniRepo<>(District.class);
//        List<Okres> listEnt = uniRepo.findByParam("id", "7");
            List<District> listEnt = uniRepo.findByTwoParams("id", "7", "region_id", "2");

            for (District o : listEnt) {
                log.info("OKRES:" + o.getDistrict_name());
            }

            uniRepo.updateParam("district_name", "ZVOLENA", "7", false);
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);

        }
    }

    private void skusUser() {

        UserServiceImpl userv = new UserServiceImpl();
        UniRepo<A_User> usrRepo = new UniRepo<>(A_User.class);

        A_User u = new A_User("jojo", "kajo", "jojo@kajo.com", "kajo", "kokos");
//        userv.save(u);
        usrRepo.save(u, true);

    }

    /**
     *
     */
    private void skusUniRepo() {

        UniRepo<District> uniRepo = new UniRepo<>(District.class);

//        String a = uniRepo.createInsertQueryPrepared();
//        String b = uniRepo.createUpdateQueryPrepared("7");
//        
//        log.debug("A:*"+a+"*");
//        log.debug("B:*"+b+"*");
//      
    }

    private void skusSaveU() {

        UniRepo<District> okresRepo = new UniRepo<>(District.class);

        District o = new District();
        o.setId(5);
        o.setDistrict_name("KOLAROVO");
        o.setRegion_id(3);
        o.setVisible(Boolean.TRUE);

        okresRepo.save(o, false);
    }

    private void skusFormatter() {

        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Object o = new Date();
        Object b = new java.sql.Date(new Date().getTime());

        log.info("OBJEKT DATUMU: " + o);
        String createDate = formatter.format(o);
        log.info("UTIL DATE: *" + createDate + "*");

        String rate = formatter.format(b);

        log.info("SQL DATE: *" + rate + "*");

    }

    private void skusVoteSave() {

        UniRepo<Vote> voteRepo = new UniRepo<>(Vote.class);

        Vote v = voteRepo.findOne(2);

        v.setResult_vote(VoteResult.APPROVED);

        v = voteRepo.save(v, true);

        log.info("RESULT:" + v.getResult_vote());

    }

    private void skusAdmin() {

        GeneralRepo genRepo = new GeneralRepo();

        genRepo.initAdmin();
        log.info("Vytvoril som admina!");
    }

    private void skusField() {
        List<Object> objs = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            objs.add(null);
        }
        int j = 0;
        for (Object o : objs) {

            log.info("J:" + o + " . " + j);
            j++;

        }

    }

    private void skusNewRepo() {

        UniRepo<Region> krajRepo = new UniRepo<>(Region.class);

        Region reg = new Region();
        reg.setRegion_name("KOKOSOSLOVENKY");

        krajRepo.save(reg, false);

    }

    private void skusChangedRepo() {
        UniRepo<Region> krajRepo = new UniRepo<>(Region.class);

        Region reg = krajRepo.findOne(6);
        if (reg != null) {
            reg.setRegion_name("KOKOSOSLOVENKY_VERES");
            krajRepo.save(reg, true);
            log.info("DONE");

        } else {
            log.info("NOT DONE");

        }

    }

    private void skusItemToEntity() {

        PublicPerson pp;
        Item item;
        Object itemId;
        Integer ppId = 1;//johan prochazka
        Class<PublicPerson> clsE = PublicPerson.class;
        UniTableService<PublicPerson> uniTabSrv = new UniTableServiceImpl<>(clsE);
        Map<String, Class<?>> mapPar;

        try {
            mapPar = ToolsDao.getTypParametrov(clsE);
            SQLContainer sqlCont = DoDBconn.createSqlContainera("t_public_person");
//            UniRepo<PublicPerson> ppRepo = new UniRepo<>(clsE);
            item = sqlCont.getItem(new RowId(new Object[]{ppId}));
            pp = uniTabSrv.getObjectFromItem(item, mapPar);

            log.info("pp " + pp.getId());
            log.info("pp " + pp.getFirst_name());
            log.info("pp " + pp.getLast_name());
            log.info("pp " + pp.getDate_of_birth());

        } catch (SQLException | NoSuchFieldException | SecurityException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    private void skusStromTree() {

        GeneralRepo genRepo = new GeneralRepo();

        try {
            genRepo.deactivateWithSlavesTree("a_user", 2);
            genRepo.doCommit();
            log.info("DONE");
//        userRepo.deactivateOneOnly(user);
//        toto sa musi deaktivovat stromovo.
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }

    }

    private void skusSaveVsPassupdate() {

        UniRepo<A_User> userRepo = new UniRepo<>(A_User.class);
        SecurityService secServ = new SecurityServiceImpl();
        GeneralRepo genRepo = new GeneralRepo();

        A_User usr1 = userRepo.findOne(1);

//        String pwd = "KOKOSASELAKOS";
        String pwd = "KOKO";
        
        byte[] pwdb = secServ.encryptPassword(pwd);

        usr1.setPassword(pwdb);
        userRepo.save(usr1, false);

        try {
            genRepo.updatePassword(pwd, "2");
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }
        this.skusZobraz(pwdb);

    }

    private void skusZobraz(byte[] pwdb) {

        UniRepo<A_User> userRepo = new UniRepo<>(A_User.class);

        A_User usr2 = userRepo.findOne(2);
        A_User usr3 = userRepo.findOne(1);

        StringBuilder sb0 = new StringBuilder();
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        for (byte db : pwdb) {
            sb0.append(db);
        }
        for (byte ab : usr3.getPassword()) {
            sb1.append(ab);
        }
        for (byte cb : usr2.getPassword()) {
            sb2.append(cb);
        }

        log.info("       ENCRYPT:*" + sb0.toString() + "*");
        log.info("      VIA SAVE:*" + sb1.toString() + "*");
        log.info("VIA UPDATE PWD:*" + sb2.toString() + "*");

    }

    private void skusEnum() {
        
        String tn = "T_PUBLIC_PERSON";
        NonEditableFields nf = NonEditableFields.valueOf(tn.toUpperCase());
        log.info("N F: " + nf.name());
        
        
        String[] nonEdCols = (NonEditableFields.valueOf(tn.toUpperCase())).getNonEditableParams();
        
        log.info("NON EDITABL COLS IS NULL:" + (nonEdCols == null));
        if ((nonEdCols != null)) {
            log.info("NON ED SIZE:" + nonEdCols.length);
            log.info("COL NAME: " + nonEdCols[0]);
            
        }
    }
    
    private void skusRolu(){
        
        UniRepo<PublicRole> roleRepo = new UniRepo<>(PublicRole.class);
        
        PublicRole pr = roleRepo.findOne(3);
        
        log.info(pr.getId());
        log.info(pr.getName());
        
    }

}
