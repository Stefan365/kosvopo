/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.filtering;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.ComboBox;
import java.util.List;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.dao.A_Hierarchy;
import sk.stefan.utils.ToolsFiltering;

/**
 *
 * @author stefan
 */
public class FiltersListener implements ValueChangeListener {

    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(FiltersListener.class);

    private final SQLContainer sqlCont;
    private final String parentTn;
    private final String listenerTn;
    private Filter listenerFt;
    
    private final ComboBox combo;

    private Boolean bolVal;
    private Boolean doRefilter = Boolean.FALSE;
    private Integer intVal;

    public FiltersListener(String tn, String ptn, ComboBox com, SQLContainer sqlcont) {
        this.listenerTn = tn;
        this.parentTn = ptn;
        this.combo = com;
        this.sqlCont = sqlcont;
    }

    @Override
    public void valueChange(ValueChangeEvent event) {

        Object o = event.getProperty().getValue();

        if (o instanceof Boolean) {
            bolVal = (Boolean) o;
            if (bolVal) {
                intVal = (Integer) combo.getValue();
                if (intVal != null) {
                    doRefilter = Boolean.TRUE;
                }
            }
        } else if (o instanceof Integer) {
            intVal = (Integer) o;
            if (intVal != null) {
                doRefilter = Boolean.TRUE;
            }
        }

        if (doRefilter) {
            doRefilter = Boolean.FALSE;
            List<String> hierarchicalSeq = ToolsFiltering.getHierarchicalSequence(parentTn, listenerTn);
            List<A_Hierarchy> hierSeq = ToolsFiltering.getFinalHierSequence(hierarchicalSeq);
            List<Integer> ids = ToolsFiltering.getFinalIds(hierSeq, intVal);
            this.sqlCont.removeContainerFilter(listenerFt);
            listenerFt = ToolsFiltering.createFiler(ids);
            this.sqlCont.addContainerFilter(listenerFt);
            
        }
    }

}
