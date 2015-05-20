/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.service;

/**
 * Obsluhuje vstupny view.
 *  
 * @author stefan
 */
public interface EnterViewService {
    
    /**
     * Zistuje, ci je v DB user admin.
     * @return 
     */
    public Boolean isThereAdmin();

    /**
     * Pokial nieje tak ho touto metodou vytvori.
     */
    public void initAdmin();
    
}
