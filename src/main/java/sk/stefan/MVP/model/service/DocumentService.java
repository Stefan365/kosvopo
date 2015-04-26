/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.service;

import java.util.List;
import sk.stefan.MVP.model.entity.Document;

/**
 *
 * @author stefan
 */
public interface DocumentService<E> {
    
    public List<Document> findAllEntityDocuments(String tn, Integer rid);
    
    public Integer getEntityId(E ent);
    
    public String getClassTableName();
    
    public Class<E> getEntityClass();
    
    public Boolean deactivate(Document doc);
    
    public Document save(Document doc);
    
    

}
