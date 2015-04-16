package sk.stefan.utils;

import java.util.List;

import sk.stefan.interfaces.PresentationName;
import sk.stefan.MVP.model.repo.dao.UniRepo;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ListSelect;

public class ZBD_PomT<T extends Object> {

    private UniRepo<T> uniRepo;
    private T obj;
    private Class<?> cls;

    public ZBD_PomT(Class<?> cls, T obj) {

        this.obj = obj;
        this.cls = cls;
        uniRepo = new UniRepo<T>(cls);

    }

    // 1.UNI
    /**
     * AbstractSelect and fills it with properties as needed. Abstractselect =
     * predok CoboBox, ListSelect, etc...
     */
    public void nastavSelectList(AbstractSelect sel) {

        List<T> listEnt = uniRepo.findAll();

        sel.removeAllItems();

        if (listEnt != null) {
            for (T ent : listEnt) {
                sel.addItem(ent);
                sel.setItemCaption(ent,
                        ((PresentationName) ent).getPresentationName());
            }
        }

        ZBD_PomT.doladSelectList(sel);
        if (true) { // dat tam podmienku, pokial uz
            this.nastavListener(sel);
        }

        if (obj != null) {
            sel.setValue(obj);
        }

    }

    public static void doladSelectList(AbstractSelect sel) {

        if (sel.getClass().equals(ListSelect.class)) {
            ((ListSelect) sel).setRows(20);
        } else if (sel.getClass().equals(ComboBox.class)) {
            ((ComboBox) sel).setTextInputAllowed(false);
        }

        sel.setNullSelectionAllowed(false);
        sel.setImmediate(true);
        sel.setNewItemsAllowed(false);
    }

    private void nastavListener(final AbstractSelect sel) {

		// remove all listeners.
        // add new listener:
        sel.addValueChangeListener(new ValueChangeListener() {

            @Override
            public void valueChange(ValueChangeEvent event) {
                T ent = (T) event.getProperty().getValue();

                // Notification.show("Value changed:", ent.getPresvalueString);
                try {
                    if (obj == null) {
                        obj = (T) cls.newInstance();
                    }
                    uniRepo.copy(ent, obj);
                    sel.setValue(ent);

                } catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

}
