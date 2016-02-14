/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.model.service;

import java.util.List;

import com.vaadin.server.Resource;

import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.mvps.model.entity.Document;

/**
 * Obsluhuje dokument.
 * 
 * @author stefan
 */
public interface DocumentService {

    /**
     * @param tn table name
     * @param rid row id
     * @return 
     */
    public List<Document> findAllEntityDocuments(String tn, Integer rid);

    /**
     * @return nazov DB tabulky danej triedy.
     */
    public String getClassTableName(Class clazz);

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

    Resource getDocumentResource(Document document);
}
