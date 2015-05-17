/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components;

import com.vaadin.ui.PasswordField;
import com.vaadin.ui.VerticalLayout;
import java.util.Locale;
import sk.stefan.converters.PasswordConverter;

/**
 *
 * @author stefan
 */
public class PasswordDoubleFieldComponent extends VerticalLayout {
    
    private static final long serialVersionUID = 123L;
    
    private final PasswordField pw1;
    
    private final PasswordField pw2;
    
    public PasswordDoubleFieldComponent (){
        
        this.pw1 = new PasswordField("zadaj heslo:");
        this.pw2 = new PasswordField("potvrď heslo:");
        
        this.initTextFields();
        
        this.addComponents(pw1);
        
        
    
    }

    /**
     * @return the pw1
     */
    public PasswordField getPw1() {
        return pw1;
    }
    
    private void initTextFields(){
        
        pw1.setConverter(new PasswordConverter());
        pw1.setImmediate(true);
        pw1.setLocale(new Locale("cz", "CZ"));
        
        pw2.setImmediate(true);
        pw2.setLocale(new Locale("cz", "CZ"));
        

    }
    /**
     * Zistuje, ci sa hodnoty v oboch poliach rovnaju.
     * 
     * @return 
     */
    public boolean validateFields(){
        
        return pw1.getValue().equals(pw2.getValue());
        
    }
    
}
