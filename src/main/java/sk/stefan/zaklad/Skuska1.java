package sk.stefan.zaklad;

import sk.stefan.interfaces.PresentationName;





import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import sk.stefan.MVP.model.entity.dao.*;
import sk.stefan.MVP.model.repo.dao.UniRepo;





public class Skuska1<T> {

	public static void main(String[] args) throws NoSuchFieldException,
			SecurityException, InstantiationException, IllegalAccessException,
			NoSuchMethodException, IllegalArgumentException,
			InvocationTargetException, SQLException {

		Skuska1<ActClassification> sk = new Skuska1<>();
//		sk1.tryDate();
//		sk1.skusIf();
		

		
		//sk.tryDate1();
		sk.showTypes();
		//sk.skusString();
		//sk1.tryDate3();
		
		//sk1.tryParse();
		// PublicPerson ent = new PublicPerson();

		// UniGetAllRepo<PublicPerson> uniRepo = new
		// UniGetAllRepo<>(ent.getClass());

		// UniRepo<PublicPerson> uniRepo = new UniRepo<>(PublicPerson.class);
		/*
		 * Skuska1<ActClassification> sk1 = new Skuska1<>();
		 * sk1.skusaj(ActClassification.class);
		 * 
		 * Skuska1<Change> sk2 = new Skuska1<>(); sk2.skusaj(Change.class);
		 * 
		 * Skuska1<Kraj> sk3 = new Skuska1<>(); sk3.skusaj(Kraj.class);
		 * 
		 * Skuska1<Location> sk4 = new Skuska1<>(); sk4.skusaj(Location.class);
		 * 
		 * Skuska1<PersonClassification> sk5 = new Skuska1<>();
		 * sk5.skusaj(PersonClassification.class); /
		 *//*
			 * Skuska1<PublicBody> sk6 = new Skuska1<>();
			 * sk6.skusaj(PublicBody.class);
			 * 
			 * Skuska1<PublicPerson> sk7 = new Skuska1<>();
			 * sk7.skusaj(PublicPerson.class);
			 * 
			 * Skuska1<PublicRole> sk8 = new Skuska1<>();
			 * sk8.skusaj(PublicRole.class);
			 * 
			 * Skuska1<Role> sk9 = new Skuska1<>(); sk9.skusaj(Role.class);
			 * 
			 * Skuska1<Subject> sk10 = new Skuska1<>();
			 * sk10.skusaj(Subject.class);
			 * 
			 * Skuska1<Tenure> sk11 = new Skuska1<>();
			 * sk11.skusaj(Tenure.class);
			 * 
			 * Skuska1<Theme> sk12 = new Skuska1<>(); sk12.skusaj(Theme.class);
			 * 
			 * Skuska1<User> sk13 = new Skuska1<>(); sk13.skusaj(User.class);
			 * 
			 * Skuska1<UserRole> sk14 = new Skuska1<>();
			 * sk14.skusaj(UserRole.class);
			 * 
			 * Skuska1<Vote> sk15 = new Skuska1<>(); sk15.skusaj(Vote.class);
			 * 
			 * Skuska1<VoteOfRole> sk16 = new Skuska1<>();
			 * sk16.skusaj(VoteOfRole.class);
			 * 
			 * /* Class c = Integer.class; Class ca = PublicPerson.class; Class
			 * cb = ent.getClass();
			 */
		// System.out.println(c.getCanonicalName() + ":" + c.getName() + ":" +
		// c.getSimpleName());

		/*
		 * try { //List<PublicPerson> listLudi =
		 * uniRepo.findAllUni(ent.getClass()); List<PublicPerson> listLudi =
		 * uniRepo.findAll();
		 * 
		 * for (PublicPerson pp : listLudi){
		 * System.out.println(pp.getPresentationName()); } } catch
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
		 * System.out.println(s + ":"+ ms.get(s)); } System.out.println("KOKO" +
		 * ":"+ "KOKOR".substring(0,3));
		 */

		/*
		 * Set<String> sa = Pom.getClassProperties(pp, true);
		 * 
		 * for (String str : sa){ System.out.println("*" +
		 * Pom.getSetterName(str) + "*"); System.out.println("*" +
		 * Pom.getGetterName(str) + "*");
		 * 
		 * }
		 * 
		 * VoteOfRole vor = new VoteOfRole(); vor.setDecision("ANO");
		 * Pom.getComboList(vor.getClass(), vor);
		 * 
		 * System.out.println(Pom.getSetterName("id")); /
		 */
	}

	@SuppressWarnings("unused")
	private void skusString(){
		String str1 = "KOKOTKO";
		String str2 = "KO";
		
		str1.toLowerCase().contains(str2.toLowerCase());
		System.out.println("contains: "+ str1.toLowerCase().contains(str2.toLowerCase()));
		
		String st = "sadd".getClass().toString();
		Class<?> cls = "sadd".getClass();
		
		if (cls.toString().contains("java.lang.String") ) {
			System.out.println("TRIEDA: "+ st.contains("java.lang.String"));
		}
		
	}
	
	private void skusaj(Class<?> cls) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {

		System.out.println("\nXXXXX: 1.FIND ALL");
		// 1.
		UniRepo<T> uniRepo = new UniRepo<>(cls);
		List<T> listEnt = uniRepo.findAll();
		for (T t : listEnt) {
			System.out.println("1."
					+ ((PresentationName) t).getPresentationName());
		}

		System.out.println("\nXXXXX: 2.FIND ONE");
		// 2.
		int i;
		T ent;
		for (i = 1; i <= listEnt.size(); i++) {
			ent = uniRepo.findOne(i);
			System.out.println("2."
					+ ((PresentationName) ent).getPresentationName());
		}

		System.out.println("\nXXXXX: 3.FIND BY PARAM");
		// 3.
		List<T> listEnt1 = uniRepo.findByParam("id", "1");
		for (T t : listEnt1) {
			System.out.println("3."
					+ ((PresentationName) t).getPresentationName());
		}

		System.out.println("\nXXXXX: 4.SAVE NEW");
		// 4.
		@SuppressWarnings("unchecked")
		T ent1 = (T) cls.newInstance();
		T ent2 = uniRepo.save(ent1);
		System.out.println("4."
				+ ((PresentationName) ent2).getPresentationName());

		System.out.println("\nXXXXX: 5.SAVE ALREDY IN DB");
		// 5.
		@SuppressWarnings("unchecked")
		T ent3 = uniRepo.save(ent2);
		System.out.println("5."
				+ ((PresentationName) ent3).getPresentationName());

		System.out.println("\nXXXXX: 6.DELETE");
		// 6.
		uniRepo.delete(ent1);
		uniRepo.delete(ent2);

		System.out.println("\nXXXXX: 7.COPY");
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
			System.out.println("7."
					+ ((PresentationName) ent4).getPresentationName());
			System.out.println("7. i3 = " + i3 + " i4 = " + i4);

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

			System.out.println("java.util VETSI/MENSI: "
					+ actualDate.compareTo(date1));
			System.out.println("java.util MENSI/VETSI: "
					+ date1.compareTo(actualDate));
			System.out.println("java.util ROVNY/ROVNY: "
					+ date1.compareTo(date1));

			System.out.println("java.sql V/M: " + sqlDate.compareTo(sdate1));
			System.out.println("java.sql M/V: " + sdate1.compareTo(sqlDate));
			System.out.println("java.sql R/R: " + sdate1.compareTo(sdate1));

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	private void skusIf() {
		String s = null;
		String s1 = "";

		if (true && (s1.equals("") || s.equals("KOKOS"))) {
			System.out.println("YES");
		} else {
			System.out.println("NO");
		}
	}
	
	@SuppressWarnings("deprecation")
	private void tryDate1(){
		
		
		java.util.Date ad = new java.util.Date();
		java.sql.Date sad = new java.sql.Date(ad.getTime()); // actual date in
		//System.out.println("util:" + ad + " sql:" + sad);
		
		java.sql.Date sqlDate = new java.sql.Date(ad.getTime());
		
		
	
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");	
			Date date1 = sdf.parse("2009-12-31 23:32:34");
			java.sql.Date sdate1 = new java.sql.Date(date1.getTime());
			String clName =  date1.getClass().getCanonicalName();
			
			//System.out.println("util2:*" + date1 + "* sql2:*" + sdate1 + "* CLname:*" + clName + "*");
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
			if (lpp2 != null){
				System.out.println("VELKOST: " + lpp2.size());
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	
	
	private void tryParse(){
		Integer i = 1;
		String s = "", s1;
		Date d = new Date();
		
		s1 =  d.getClass().getCanonicalName();
		
		System.out.format("Int: %s %n", i.getClass().getCanonicalName());
		System.out.format("String: %s %n", s.getClass().getCanonicalName());
		System.out.format("Date: %s %n", d.getClass().getCanonicalName());
		
		String fields[] = s1.split("\\.");
		System.out.format("Date2: %s %n", fields[fields.length -1]);
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
	private String getTimeStampDate(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
		//Calendar s = new GregorianCalendar(2013,0,0,13,24,56);
		//System.out.println(sdf.format(s.getTime()));
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
	
	private String getTimeStampDate(java.util.Date date){
	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
		return sdf.format(date.getTime());
		
	}

	/**
	 * Metoda sluziaca k vypisaniu id, predpoklada sa, ze objekt ma metodu getId
	 * 
	 */
	public static void doIt(Object ent) throws IllegalAccessException, IllegalArgumentException, 
		InvocationTargetException, NoSuchMethodException, SecurityException{
		
		Class<?> cls = ent.getClass();
		Method  entMethod = cls.getDeclaredMethod ("getId");
		
		Integer id = (Integer) entMethod.invoke (ent);
		System.out.println("ID: " + id);
		System.out.println("ID: "  + " = '"+ entMethod.invoke (ent)+ "'");
		
	}

	
	private void tryDate3(){
		System.out.println(this.getTimeStampDate(new Date()));
	}
	
	private void showTypes(){
		Integer i = 4;
		String s = "";
		Boolean bol = true;
		
		System.out.println(i.getClass().getCanonicalName());
		System.out.println(s.getClass().getCanonicalName());
		System.out.println(bol.getClass().getCanonicalName());
		
		
		
	}
}
