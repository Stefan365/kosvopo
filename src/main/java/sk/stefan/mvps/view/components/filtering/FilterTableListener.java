/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.filtering;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.Property;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.Notification;
import java.util.List;
import org.apache.log4j.Logger;
import sk.stefan.mvps.model.entity.A_Hierarchy;
import sk.stefan.mvps.view.components.SilentCheckBox;
import sk.stefan.interfaces.Filterable;
import sk.stefan.utils.ToolsDao;
import sk.stefan.utils.ToolsFiltering;

/**
 *
 * @author stefan
 * @param <E>
 */
public class FilterTableListener<E> implements Property.ValueChangeListener {

    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(FilterTableListener.class);

    private final SQLContainer sqlCont;
    private final String touchedTn;
    private final String touchingTn;
    private final List<FilterComboBox<?>> touchedChBs;

    private Filter listenerFt;

    private final FilterComboBox<E> combo;
    private final SilentCheckBox chb;

    private Boolean bolVal;
    private Boolean doRefilter = Boolean.FALSE;
    private Integer intVal;

    //0. konstruktor
    /**
     *
     * @param tdTn
     * @param tgTn
     * @param com
     * @param ch
     * @param sqlcont
     * @param touchedChBs
     */
    public FilterTableListener(String tdTn, String tgTn, FilterComboBox<E> com, SilentCheckBox ch,
            SQLContainer sqlcont, List<FilterComboBox<?>> touchedChBs) {

        this.touchedTn = tdTn;
        this.touchingTn = tgTn;

        this.combo = com;
        this.sqlCont = sqlcont;
        this.chb = ch;
        this.touchedChBs = touchedChBs;

    }

    @Override
    public void valueChange(Property.ValueChangeEvent event) {

        Object o = event.getProperty().getValue();

        if (o instanceof Boolean) {
            bolVal = (Boolean) o;
            
            if (bolVal) {
                intVal = (Integer) combo.getValue();
                combo.setEnabled(true);
                if (intVal != null) {
                    doRefilter = Boolean.TRUE;
                }
            } else {
                combo.setEnabled(false);
                log.debug("CONT IS NULL: " + (sqlCont == null));
                log.debug("listenr is NULL: " + (listenerFt == null));
                
                this.sqlCont.removeContainerFilter(listenerFt);
                removeFiltersFromTouched();
            }

        } else if (o instanceof Integer) {
            intVal = (Integer) o;
            if (intVal != null) {
                doRefilter = Boolean.TRUE;
            }
        }

        if (doRefilter && chb.getValue()) {

            doRefilter = Boolean.FALSE;
            
            //filtrovanie hlavnej tabulky:
            List<String> hierarchicalSeq = ToolsFiltering.getHierarchicalSequence(touchedTn, touchingTn);
            if (hierarchicalSeq == null) {
                Notification.show("Táto podložka nemá vplyv na tabuľku!");
            } else {
                List<A_Hierarchy> hierSeq = ToolsFiltering.getFinalHierSequence(hierarchicalSeq);
                List<Integer> ids = ToolsFiltering.getFinalIds(hierSeq, intVal);

                this.sqlCont.removeContainerFilter(listenerFt);
                listenerFt = ToolsFiltering.createFilter(ids);
                this.sqlCont.addContainerFilter(listenerFt);

            }

            //filtrovanie ostatnych comboboxov:
            if (touchedChBs != null) {
                addFiltersToTouched();
            }
        }

    }

    /**
     * Nastavi filter na zdruzeny comboBox:
     */
    private void addFiltersToTouched() {

        for (FilterComboBox<?> c : touchedChBs) {
            
            ToolsDao.addFiltersToTouched((Filterable)c, touchingTn, intVal);
//            String comboTouchedTn = c.getTableName();
//            List<String> hSeq = ToolsFiltering.getHierarchicalSequence(comboTouchedTn, touchingTn);
//            List<A_Hierarchy> hASeq = ToolsFiltering.getFinalHierSequence(hSeq);
//            List<Integer> idsC = ToolsFiltering.getFinalIds(hASeq, intVal);
//            c.applyFilter(idsC);
        }
    }

    /**
     * odstani prislusny filter:
     */
    private void removeFiltersFromTouched() {

        if (touchedChBs != null) {
            for (FilterComboBox<?> c : touchedChBs) {
                c.applyFilter(null);
            }
        }
    }

}
