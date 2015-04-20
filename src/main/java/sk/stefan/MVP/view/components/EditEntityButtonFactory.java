/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components;

import com.vaadin.ui.Button;
import sk.stefan.MVP.model.entity.dao.Subject;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.listeners.InputButClickListener;
import sk.stefan.listeners.InputVoteButClickListener;
import sk.stefan.utils.ToolsDao;

/**
 * Trieda, vytvara tlacitka na editaciu entit. 
 *
 *
 * @author stefan
 */
public abstract class EditEntityButtonFactory {

    private static final long serialVersionUID = 1645436L;

    public static Button createMyButton(Class<?> cls, Object ent) {
        return null;
    }

    //0. konstruktor
    /**
     *
     */
    public EditEntityButtonFactory() {
    }


    /**
     * inicializacia tlacitiek:
     */
    private static Button initButton(Button b, Class<?> cls) {

        String title = ToolsDao.getTitleName(cls);

        b.setCaption(title);
        if ((Vote.class).equals(cls)) {
            b.addClickListener(new InputVoteButClickListener(title));
        } else {
            b.addClickListener(new InputButClickListener(cls, title));
        }
        
        return b;

    }


    public static Button createMyButton(Class<?> cls) {
        
        Button b = new Button();
        
        return initButton(b, cls);
        
    }

}
