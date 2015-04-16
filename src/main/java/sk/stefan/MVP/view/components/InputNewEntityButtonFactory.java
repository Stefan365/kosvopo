/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components;

import com.vaadin.ui.Button;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.listeners.InputButClickListener;
import sk.stefan.listeners.InputVoteButClickListener;
import sk.stefan.utils.ToolsDao;

/**
 * Trieda, ktorá uchováva v zálohe všetky tlačítka na pridávanie nových entít.
 * Má to tú výhodu, že všetko je na jednom mieste, a v prípade potreby si View,
 * potrebné tlačítko vypýta (na jednej stránke bude max. 1 tlačítko druhu danej
 * entity.
 *
 *
 * @author stefan
 */
public abstract class InputNewEntityButtonFactory {

//    private static final Logger log = Logger.getLogger(InputNewEntityButtonFactoryImpl.class);

    private static final long serialVersionUID = 1645436L;

    /**
     *
     */
    public InputNewEntityButtonFactory() {
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
