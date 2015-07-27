/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.utils;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.filter.Like;
import com.vaadin.data.util.filter.Or;
import com.vaadin.event.LayoutEvents;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import sk.stefan.mvps.model.entity.A_Hierarchy;
import sk.stefan.mvps.model.repo.GeneralRepo;
import sk.stefan.mvps.model.repo.UniRepo;

/**
 * Abstr. trieda s pomocnymi statickymi metodami tykajucimi sa filtrovania.
 * 
 * @author stefan
 */
public abstract class ToolsFiltering {

    
    private static final Logger log = Logger.getLogger(ToolsFiltering.class);

    private static final UniRepo<A_Hierarchy> hierRepo = new UniRepo<>(A_Hierarchy.class);

    private static final GeneralRepo genRepo = new GeneralRepo();

    private static GeneralRepo invasiveGenRepo;

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

        int counter = -1;
        do {
            //priprava:
            if (!strs2.isEmpty()) {
                strs = strs2;
                strs2 = new ArrayList<>();
            }
            if (counter >= 10) {
                
                return vytried(strs, touchingTn);
            }

//            samotny cyklus:
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
                pom = getBosses(posledny);
//                log.info("POM: " + pom.get(0));
                pom2 = pripoj(list, pom);
                int g = 0;
                int h = 0;
                
                strs2.addAll(pom2);
                
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
                ref = getParamName(btn);
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

        if (hs != null && !hs.isEmpty()) {
            sql = createMySelect(hs, value);
            return genRepo.findIds(sql);
        } else {
            return null;
        }
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

        if ((ids == null) || ids.isEmpty()) {
            return new Like("id", "kokos");
        }

        for (Integer id : ids) {
            if (!"".equals(id+"")) {
                fls.add(new Like("id", id+""));
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
    private static List<String> getBosses(String tn) {

        List<A_Hierarchy> bossesA;
        bossesA = hierRepo.findByParam("table_name", tn);

//        log.info("GETNEXTLEVEL1:" + tn);
//        log.info("GETNEXTLEVEL2:" + ret.size());
        List<String> bosses = new ArrayList<>();
        if (bossesA != null) {
            for (A_Hierarchy h : bossesA) {
                bosses.add(h.getBoss_table());
            }
        }
        return bosses;
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

//        log.info("HS SIZE:" + hs.size());
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
        sql.append(value);

        //uzavretie dotazu:
        for (int i = 0; i < size; i++) {
            if (i == 0) {
                sql.append(")");
            } else {
                sql.append(" AND visible = true)");
            }
        }

        Label l = new Label("kokos");
        HorizontalLayout hl = new HorizontalLayout();
        hl.addLayoutClickListener(new LayoutClickListener() {
            private static final long serialVersionUID = -7430315482468088485L;

            @Override
            public void layoutClick(LayoutEvents.LayoutClickEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
//        stacilo by aj toto, lebo tam je sqlcontainer filter
//        for (int i = 0; i < size; i++) {
//            sql.append(")");
//        }
        return sql.toString();

    }

    //10. pom
    /**
     * vrati nazov referencie na sefa, tj, napr. ked sa sefovska tabulka vola
     * t_location, vrati location_id;
     * @param tn
     * @return 
     */
    public static String getParamName(String tn) {

        String result = tn.replace("t_", "");
        result = result.replace("a_", "");
        
        result += "_id";
        return result;
    }

    //5. pom
    /**
     * Ziska zoznam nazvov tabuliek ktore na vstupnu tabulku odkazuju. tj.
     * zoznam jej podriadenych.
     * @param tn
     * @return 
     */
    public static List<String> getSlaves(String tn) {

        List<A_Hierarchy> ret;
        ret = hierRepo.findByParam("boss_table", tn);

        List<String> slaves = new ArrayList<>();
        if (ret != null) {
            for (A_Hierarchy h : ret) {
                slaves.add(h.getTable_name());
            }
        }
        return slaves;
    }


}
