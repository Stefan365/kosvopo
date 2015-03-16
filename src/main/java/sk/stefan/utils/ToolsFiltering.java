/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.utils;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.filter.Like;
import com.vaadin.data.util.filter.Or;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.dao.A_Hierarchy;
import sk.stefan.MVP.model.repo.dao.UniRepo;

/**
 *
 * @author stefan
 */
public class ToolsFiltering {

    private static final Logger log = Logger.getLogger(ToolsFiltering.class);

    private static final UniRepo<A_Hierarchy> uniRepo = new UniRepo<>(A_Hierarchy.class);

//    private static final String[] relFilTns = new String[]{"t_location", "t_public_body", "t_public_person"};
    //1.
    /**
     * vrati hierarchicku sekvenciu tried. tj. napr <t_vote, t_subect, t_theme>
     *
     * @param touchedTn
     * @param touchingTn
     * @return
     */
    public static List<String> getHierarchicalSequence(String touchedTn, String touchingTn) {

//        log.info("TOUCHED: " + touchedTn);
//        log.info("TOUCHING: " + touchingTn);

        List<String> pom;
        List<String> start = new ArrayList<>();

        if (touchedTn.equals(touchingTn)) {
            start.add(touchedTn);
            return start;
        }

        start.add(touchedTn);
        String posledny;
        List<List<String>> strs = new ArrayList<>();
        List<List<String>> strs2 = new ArrayList<>();
        List<List<String>> pom2;
        strs.add(start);

//        log.info("KAROLKO: " + strs.size());
        int counter = -1;
        do {
            //priprava:
            if (!strs2.isEmpty()) {
//                log.info("KAROLKO1: " + strs2.size());
                strs = strs2;
//                for (List<String> lis : strs) {
//                    for (String s : lis) {
////                        log.info("LIS2:" + s);
//                    }
//                }
                strs2 = new ArrayList<>();
            } 
            if (counter >= 10) {
//                log.info("VYCHADZAM0: " + strs.size());
//                log.info("VYCHADZAM1: " + touchingTn);
//                for (List<String> lis : strs) {
//                    for (String s : lis) {
//                        log.info("VYCHADZAM:" + s);
//                    }
//                }
                return vytried(strs, touchingTn);
            }

//            samotny cyklus:
//            log.info("KAROLKO3: " + strs.size());
            int c = 0;
            for (List<String> list : strs) {
                posledny = list.get(list.size() - 1);
//                log.info("POSLEDNY: " + c + ": *" +posledny+"*");
                c++;
                
                if (posledny == null
                        || "null".equals(posledny.toLowerCase())
                        || touchingTn.equals(posledny)) {
                    strs2.add(list);
                    counter += 1;
//                    log.info("TOCIM  SA v SMYCKE: " + counter);
                    continue;
                } else {
                    counter = -1;
                }
//                log.info("PRED NEXT HIER LAYER: " + posledny);
                pom = getNextHierarchLayer(posledny);
                int f = 0;
                
//                if ("t_public_body".equals(posledny)) {
//                    log.info("ANO SOM TU: " + pom.size());
//                    for (String s : pom) {
//                        log.info("NEXT HIER LAYER:" + f + " : " + s);
//                        f++;
//                    }
//                }

//                log.info("POM SIZE: " + pom.size());
//                log.info("POM: " + pom.get(0));
                pom2 = pripoj(list, pom);
                int g = 0;
                int h = 0;
//                if ("t_public_body".equals(posledny)) {
//                    log.info("ANO SOM TU 2: " + pom2.size());
//                    for (List<String> lis : pom2) {
//                        log.info("vonkajsi cyklus:" + g + " : " + lis.size());
//                        g++;
//                        for (String s : lis) {
//                            log.info("VNUTORNY CYKLUS:" + h + " : " + s);
//                            h++;
//                        }
//                    }
//                }
                strs2.addAll(pom2);
//                log.info("KAROLKO4: " + strs2.size());
//                for (List<String> lis : strs2) {
//                    for (String s : lis) {
////                        log.info("LIS:" + s);
//                    }
//                }
            }
        } while (true);
    }

    //2.
    /**
     * Vrati hierarchicku sekvenciu obalenu do wrappovacej triedy.
     *
     * @param hierarchicalSeq
     * @return
     */
    public static List<A_Hierarchy> getFinalHierSequence(List<String> hierarchicalSeq) {

        List<A_Hierarchy> hs = new ArrayList<>();
        A_Hierarchy ae;
        String btn, tn, ref;
        int size = hierarchicalSeq.size();
        for (int i = 0; i < size; i++) {
            if (i != (size - 1)) {

                tn = hierarchicalSeq.get(i);
                btn = hierarchicalSeq.get(i + 1);
                ref = makeReference(btn);
                ae = new A_Hierarchy(tn, btn, ref);
                hs.add(ae);

            } else {

                tn = hierarchicalSeq.get(i);
                ae = new A_Hierarchy(tn, null, "id");
                hs.add(ae);
            }
        }

        return hs;

    }

    //3. 
    /**
     *
     * @param hs
     * @param value
     * @return
     */
    public static List<Integer> getFinalIds(List<A_Hierarchy> hs, Integer value) {

        String sql;
        sql = createMySelect(hs, value);
        log.info("MEGASQL:*" + sql + "*");
        return uniRepo.findAllFilteringIds(sql);

    }

    //4.
    /**
     * Vrati pozadovany filter.
     *
     * @param ids
     * @return
     */
    public static Filter createFilter(List<Integer> ids) {
        Filter o;
        List<Filter> fls = new ArrayList<>();

        if ((ids == null) || ids.isEmpty()){
            return new Like("id", "kokos");
        }
        
        for (Integer id : ids) {
            String tx = "" + id;
            if (!"".equals(tx)) {
                fls.add(new Like("id", tx));
            }
        }
        o = new Or(fls.toArray(new Filter[0]));

        return o;
    }

    //5. pom
    /**
     * Ziska zoznam nazvov tabuliek na ktore sa dana tabulka odkazuje, tj.
     * zoznam jej bossov.
     */
    private static List<String> getNextHierarchLayer(String tn) {

        List<A_Hierarchy> ret;
        ret = uniRepo.findByParam("table_name", tn);

//        log.info("GETNEXTLEVEL1:" + tn);
//        log.info("GETNEXTLEVEL2:" + ret.size());

        List<String> hier = new ArrayList<>();
        if (ret != null) {
            for (A_Hierarchy h : ret) {
                hier.add(h.getBoss_table());
            }
        }
        return hier;
    }

    //6. pom
    /**
     * metoda vytriedi len hierarchicke postupnosti, ktore su potrebne.
     *
     */
    private static List<String> vytried(List<List<String>> strs, String tn) {

        List<List<String>> ret = new ArrayList<>();
//        log.info("SIZE: " + ret.size());
//        log.info("SIZE STRS: " + strs.size());

        for (List<String> list : strs) {
            if (tn.equals(list.get(list.size() - 1))) {
                ret.add(list);
            }
        }

//        return ret;
//     toto je v nasom konkretnom pripade zbytocne. Ale vo vseobecnosti potrebne
//     pretoze uplne podla spravnosti by tam mal byt {tj.vracat zoznam zoznamov} 
//         v nasom pripade staci len 1, ale je to zjednodusenie.
//         inak su potrebene vsetky retazce a vysledok bude ich zjednotenim.
        int min = 1000;
        int poz = 0;
        if (!ret.isEmpty()) {
            for (int i = 0; i < ret.size(); i++) {
                if (ret.get(i).size() < min) {
                    min = ret.get(i).size();
                    poz = i;
                }
            }
            return ret.get(poz);
        } else {
            return null;
        }

    }

    //7. pom
    /**
     *
     * @param list
     * @param pom
     * @return
     */
    private static List<List<String>> pripoj(List<String> list, List<String> pom) {
        List<List<String>> pripojene;
        pripojene = new ArrayList<>();

        List<String> lis;
        for (String s : pom) {
            lis = klonujList(list);
            lis.add(s);
            pripojene.add(lis);
        }
        return pripojene;
    }

    //8. pom
    /**
     * pomocna funkcia na klonovanie listu:
     */
    private static List<String> klonujList(List<String> list) {
        List<String> cloned = new ArrayList<>(list);
        return cloned;
    }

    //9. pom
    /**
     * Zostavi konecny sql dotaz, podla ktoreho sa bude nakoniec filtrovat dana
     * tabulka. trocha to obchadza logiku veci (na vstupe by mal byt
     * List<A_Hierarchy>) ale je to elegantnejsie. (lebo vlastne nevyuzivam
     * boss_reference)
     *
     * @param hs
     * @param value
     * @return
     */
    private static String createMySelect(List<A_Hierarchy> hs, Integer value) {

        StringBuilder sql = new StringBuilder();

        log.info("HS SIZE:" + hs.size());
        String tn, br;
        int size = hs.size();

//        for (int i = 0; i < size - 1; i++) {
        for (int i = 0; i < size; i++) {

            tn = hs.get(i).getTable_name();
            br = hs.get(i).getBoss_reference();

            sql.append("SELECT ID FROM ");
            sql.append(tn);
            sql.append(" WHERE ");
            sql.append(br);
            sql.append(" IN (");
        }

        //pridani hodnoty, ok ktorej sa vsetko odvija:
        sql.append(value.toString());

        //uzavretie dotazu:
//        for (int i = 0; i < size - 1; i++) {
        for (int i = 0; i < size; i++) {
            sql.append(")");
        }

        return sql.toString();

    }

    //10. pom
    /**
     * vrati referenciu na sefa, tj, napr. ked sa z tabulky
     */
    private static String makeReference(String tn) {

        String replace = tn.replace("t_", "");
        replace += "_id";
        return replace;
    }

    //11. pom
    /**
     * Zisti, ktore checkboxu su prave zaskrtnute a podla toho zostavi aktualny
     * zoznam tabuliek, ktore budu zohladnene.
     *
     * @return
     */
//    public static List<String> checkActualFilTns() {
//
//        List<String> aktualList = new ArrayList<>();
//
//        aktualList.addAll(Arrays.asList(relFilTns));
//        return aktualList;
//
//    }
}
