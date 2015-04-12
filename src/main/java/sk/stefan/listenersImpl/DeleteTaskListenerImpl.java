/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.listenersImpl;

import com.vaadin.ui.Component;
import sk.stefan.listeners.YesNoWindowListener;

/**
 * Vnitřní třída implementující Yes-No Listener
 */
public class DeleteTaskListenerImpl implements YesNoWindowListener {

    @Override
    public void doYesAction(Component.Event event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
//    
//    private final InputFormLayout<T> outer;
//
//    public DeleteTaskListener(final InputFormLayout<T> outer) {
//        this.outer = outer;
//    }
//
//    @Override
//    public void doYesAction(Component.Event event) {
//        if (outer.itemId != null) {
//            outer.item.getItemProperty("deleted").setValue(true);
//        } else {
//            Notification.show("Není co mazat!");
//            return;
//        }
//        try {
//            outer.sqlContainer.commit();
//            outer.item = null;
//            outer.itemId = null;
//            updateTaskLabel();
//            updateTodoInfo();
//            Notification.show("Úkol úspešne vymazán!");
//        } catch (UnsupportedOperationException | SQLException e) {
//            try {
//                outer.sqlContainer.rollback();
//            } catch (UnsupportedOperationException | SQLException ex) {
//                Notification.show("Mazanie sa nepodarilo!", Notification.Type.ERROR_MESSAGE);
//                outer.logger.warn(ex.getLocalizedMessage());
//                throw new RuntimeException(ex);
//            }
//        }
//    }
//    
//}
//    private void refreshComboboxes() {
//        for (Component c: fieldMap.values()){
//            if (c instanceof InputComboBox){
//                //((InputComboBox<? extends Object>) c).refreshFg();
//            }
//        }
//    }
