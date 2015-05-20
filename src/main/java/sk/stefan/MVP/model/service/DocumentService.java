/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.service;

import java.util.List;
import sk.stefan.MVP.model.entity.Document;

/**
 * Obsluhuje dokument.
 * 
 * @author stefan
 * @param <E>
 */
public interface DocumentService<E> {

    /**
     * @param tn table name
     * @param rid row id
     * @return 
     */
    public List<Document> findAllEntityDocuments(String tn, Integer rid);
    
    /**
     * @param ent entita
     * @return 
     */
    public Integer getEntityId(E ent);
    
    /**
     * @return nazov DB tabulky danej triedy.
     */
    public String getClassTableName();
    
    /**
     * Vrati class entity ku ktorej patri dokument.
     * 
     * @return 
     */
    public Class<E> getEntityClass();
    
    /**
     * @param doc dokument
     * @return 
     */
    public Boolean deactivate(Document doc);
    
    /**
     * @param doc
     * @return 
     */
    public Document save(Document doc);
    
}
