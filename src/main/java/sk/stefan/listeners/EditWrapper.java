/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.listeners;

import com.vaadin.ui.Button;
import sk.stefan.utils.ToolsDao;

/**
 *
 * @author stefan
 * @param <E>
 */
public class EditWrapper<E> {

    private final Button b;
    private final Class<E> clsE;
    private final E ent;

    public EditWrapper(Button but, Class<E> cls, E entit) {

        this.b = but;
        this.clsE = cls;
        this.ent = entit;

        String title = ToolsDao.getTitleName(clsE);
        b.addClickListener(new Input_EditButClickListener<>(clsE, title, ent));

    }

    public Button getB() {
        return b;
    }

    public Class<E> getClsE() {
        return clsE;
    }

    public E getEnt() {
        return ent;
    }
}
