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
import java.util.Arrays;
import java.util.List;
import sk.stefan.MVP.model.entity.dao.A_Hierarchy;
import sk.stefan.MVP.model.repo.dao.UniRepo;

/**
 *
 * @author stefan
 */
public class ToolsFiltering {

    private static final UniRepo<A_Hierarchy> uniRepo = new UniRepo<>(A_Hierarchy.class);

    private static final String[] relFilTns = new String[]{"t_location", "t_public_body", "t_public_person"};

    //1.
    /**
     * @param touchedTn
     * @param touchingTn
     * @return
     */
    public static List<String> getAllHierarchySequencies(String touchedTn, String touchingTn) {

        List<String> pom;
        List<String> start = new ArrayList<>();

        start.add(touchedTn);
        String posledny;
        List<List<String>> strs = new ArrayList<>();
        List<List<String>> strs2 = new ArrayList<>();
        strs.add(start);

        int counter = 0;
        do {
            //priprava:
            if (!strs2.isEmpty()) {
                strs = strs2;
                strs2 = new ArrayList<>();
            } else if (counter >= strs2.size()) {
                return vytried(strs2, touchingTn);
            }

            //samotny cyklus:
            for (List<String> list : strs) {
                posledny = list.get(list.size() - 1);

                if (posledny == null
                        || "null".equals(posledny.toLowerCase())
                        || touchingTn.equals(posledny)) {
                    counter += 1;
                    continue;
                } else {
                    counter = 0;
                }
                pom = getNextHierarchLayer(posledny);
                strs2.addAll(pripoj(list, pom));
            }
        } while (true);
    }

    //2.
    /**
     * Ziska zoznam nazvov tabuliek na ktore sa dana tabulka odkazuje, tj.
     * zoznam jej bossov.
     */
    private static List<String> getNextHierarchLayer(String tn) {

        List<A_Hierarchy> ret;
        ret = uniRepo.findByParam("table_name", tn);

        List<String> hier = new ArrayList<>();
        for (A_Hierarchy h : ret) {
            hier.add(h.getBoss_table());
        }
        return hier;
    }

    //3.
    /**
     * metoda vytriedi len hierarchicke postupnosti, ktore su potrebne.
     *
     */
    private static List<String> vytried(List<List<String>> strs, String tn) {

        List<List<String>> ret = new ArrayList<>();
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
        for (int i = 0; i < ret.size(); i++) {
            if (ret.get(i).size() < min) {
                min = ret.get(i).size();
                poz = i;
            }
        }
        return ret.get(poz);
    }

    //4.
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

    //5.
    /**
     * pomocna funkcia na klonovanie listu:
     */
    private static List<String> klonujList(List<String> list) {
        List<String> cloned = new ArrayList<>(list);
        return cloned;
    }

    //6.
    /**
     *
     * @param actualThread
     * @return
     */
    public static List<A_Hierarchy> getFinalSequence(List<String> actualThread) {

        List<A_Hierarchy> hs = new ArrayList<>();
        A_Hierarchy ae;
        String btn, tn, ref;

        String sql;

        for (int i = 0; i < actualThread.size() - 1; i++) {
            tn = actualThread.get(i);
            btn = actualThread.get(i + 1);
            ref = makeReference(btn);
            ae = new A_Hierarchy(tn, btn, ref);
            hs.add(ae);
//          Collections.reverse(hs.getHierarchySequence());
        }

        return hs;

    }

    //7.
    /**
     *
     * @param hs
     * @param value
     * @return
     */
    public static List<Integer> getFinalIds(List<A_Hierarchy> hs, Integer value) {

        String sql;
        sql = createMySelect(hs, value);
        return uniRepo.findAllFilteringIds(sql);

    }

    /**
     * Vrati pozadovany filter.
     *
     * @param ids
     * @return
     */
    public static Filter createFiler(List<Integer> ids) {
        Filter o;
        List<Filter> fls = new ArrayList<>();
                
        for (Integer id : ids) {
            String tx = "" + id;
            if (!"".equals(tx)) {
                fls.add(new Like("id", tx));
            }
        }
        o = new Or(fls.toArray(new Filter[0]));
                
        return o;
    }
    
    

    //8.
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
    public static String createMySelect(List<A_Hierarchy> hs, Integer value) {

        StringBuilder sql = new StringBuilder();
        String tn, tnb, br;
//        Integer value;
        int size = hs.size();

        for (int i = 0; i < size - 1; i++) {

            tn = hs.get(i).getTable_name();
            br = hs.get(i).getBoss_reference();

            sql.append("SELECT ID FROM ");
            sql.append(tn);
            sql.append(" WHERE ");
            sql.append(br);
            sql.append(" IN (");
        }
        //tabulka, nejvyssi boss v dane posloupnosti:
        tnb = hs.get(hs.size() - 1).getTable_name();

        //pridani hodnoty, ok ktorej sa vsetko odvija:
        sql.append(value);

        //uzavretie dotazu:
        for (int i = 0; i < size - 1; i++) {
            sql.append(")");
        }

        return sql.toString();
    }

    //9.
    /**
     * vrati referenciu na sefa, tj, napr. ked sa z tabulky
     */
    private static String makeReference(String tn) {

        String replace = tn.replace("t_", "");
        replace += "_id";
        return replace;
    }

    //10.
    /**
     * Zisti, ktore checkboxu su prave zaskrtnute a podla toho zostavi aktualny
     * zoznam tabuliek, ktore budu zohladnene.
     *
     * @return
     */
    public static List<String> checkActualFilTns() {

        List<String> aktualList = new ArrayList<>();

        aktualList.addAll(Arrays.asList(relFilTns));
        return aktualList;

    }

}
