/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components;

import com.vaadin.data.Property;
import com.vaadin.ui.Table;
import java.text.SimpleDateFormat;

/**
 * Tabulka, ktora sa zobrazuje vo V8. 
 * potom treba este rozsirit metody: formatProperyValue,
 * aby sa uzivatelovi nezobrazovali len napr. cisla (id),
 * ale reprezentacne mena danych entit. 
 * To je este vela prace.
 *
 * @author stefan
 */
public class MyTable extends Table {

    private static final long serialVersionUID = 1L;

    @Override
    protected String formatPropertyValue(Object rowId, Object colId,
            Property<?> property) {
        Object v = property.getValue();
        if (v instanceof java.util.Date) {
            
            java.util.Date dateValue = (java.util.Date) v;
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            return sdf.format(dateValue);
//                    return "Formatted date: " + (1900 + dateValue.getYear())
//                            + "-" + dateValue.getMonth() + "-"
//                            + dateValue.getDate();
        } else if (v instanceof java.sql.Date) {
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            java.sql.Date dateValue = (java.sql.Date) v;
            
            return sdf.format(dateValue);
        }

        return super.formatPropertyValue(rowId, colId, property);
    }

}
