/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.wrappers;

import com.vaadin.ui.Button;
import sk.stefan.listeners.Input_EditButClickListener;
import sk.stefan.utils.ToolsDao;

/**
 * This is special wrapper with some functionality inside. to make possible
 * construct factory as abstract class with static factory method. 
 * Otherwise factory must be generic and thus, factory method cannot be static.
 *
 * @author stefan
 * @param <E>
 */
public class FunctionalEditWrapper<E> {

    private final Button b;
    private final Class<E> clsE;
    private final E ent;

    public FunctionalEditWrapper(Class<E> cls, E entit) {

        this.b = new Button();
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
