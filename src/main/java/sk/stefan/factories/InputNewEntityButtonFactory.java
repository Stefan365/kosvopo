/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.factories;

import com.vaadin.ui.Button;
import org.apache.log4j.Logger;
import sk.stefan.mvps.model.entity.Vote;
import sk.stefan.listeners.InputVoteButClickListener;
import sk.stefan.listeners.Input_EditButClickListener;
import sk.stefan.utils.ToolsDao;

/**
 * Trieda, vytvara tlacitka na pridavanie novych entit.
 * 
 * @author stefan
 */
public abstract class InputNewEntityButtonFactory {

    private static final Logger log = Logger.getLogger(InputNewEntityButtonFactory.class);

    private static final long serialVersionUID = 1645436L;


    /**
     * 
     * @param cls
     * @return 
     */
    public static Button createMyInputButton(Class<?> cls) {
        
        Button b = new Button();
        return initButton(b, cls);
        
    }

    
    /**
     * inicializacia tlacitiek:
     */
    private static Button initButton(Button b, Class<?> cls) {

        String title = "pridaj " + ToolsDao.getTitleName(cls);

        b.setCaption(title);
        if ((Vote.class).equals(cls)) {
            b.addClickListener(new InputVoteButClickListener(title));
        } else {
            
            b.addClickListener(new Input_EditButClickListener<>(cls, title, null));
        }
        
        return b;

    }



}
