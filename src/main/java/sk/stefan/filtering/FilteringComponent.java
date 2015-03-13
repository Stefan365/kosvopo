/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.filtering;

import com.vaadin.data.Container.Filter;
import com.vaadin.ui.Component;
import java.util.List;
import sk.stefan.MVP.model.entity.dao.A_Hierarchy;

/**
 *
 * @author stefan
 */
public interface FilteringComponent {
        //do tejto mapy naloadovat properties hierarchy.properties
    
    //1. hlavna filtrovacia funkcia
    public List<Integer> getTouchedIds(String touchedTn, String touchingTn);
    
    
//    //2. toto sa urobi nakoniec.
//    public Filter generujFilter(List<Integer> touchedIds);
    
    //2. kam sa ma aplikovat filter.
    public void applyFilter(List<Integer> touchedIds, Object comp);

    
//    public void resetLocationValues(Integer integer);
//    public void resetPubBodyValues(Integer integer);
//    public void resetPubPersonValues(Integer integer);
//    public void refreshState();

    public List<A_Hierarchy> getAllHierarchies();
    
    public List<String> getAllHierarchySequencies(String touchedTn, String touchingTn);

//    public List<String> getNextHierarchLayer(String tn);

//    public List<List<String>> createAllSequences(List<List<String>> strs);

    //spravi kartezky sucin
//    public List<List<String>> pripoj(List<String> sequences, List<String> heads);

    //toto je pomocna funkcia:
//    public List<String> klonujList(List<String> list);

    public List<List<String>> getActualSequencies(List<List<String>> all);

    public String getBoss(List<String> list);

    public List<A_Hierarchy> getFinalSequence(List<String> actualThreads);

    /**
     * Zostavi konecny sql dotaz, podla ktoreho sa bude nakoniec filtrovat dana
     * tabulka.
     * @param hs
     * @return 
     */
    public String createMySelect(List<A_Hierarchy> hs);
    
    /**
     * returns e.g. okres_id
     * @param tn
     * @return 
     */
    public String getBossReference(String tn);

    /**
     * Zisti, ktore checkboxu su prave zaskrtnute a podla toho zostavi aktualny
     * zoznam tabuliek, ktore budu zohladnene.
     * @return 
     */
    public List<String> checkActualFilTns();
}


//    public void addFilter(Container.Filter f);
//
//    public void removeFilter(Container.Filter f);
//
//    public void refreshFilter(Filter f, FilterType ft);
