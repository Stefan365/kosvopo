/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.serviceImpl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.dao.Document;
import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.MVP.model.service.DocumentService;

/**
 *
 * @author stefan
 * @param <E>
 */
public class DocumentServiceImpl<E> implements DocumentService<E> {

    private static final Logger log = Logger.getLogger(DocumentServiceImpl.class);

    private final UniRepo<Document> docRepo;

    private final Class<E> clsE;

    public DocumentServiceImpl(Class<E> cls) {

        this.clsE = cls;
        this.docRepo = new UniRepo<>(Document.class);

    }

    /**
     *
     * @param tn
     * @param rid
     * @return
     */
    @Override
    public List<Document> findAllEntityDocuments(String tn, Integer rid) {

        List<Document> entDocuments;
        if (rid != null && tn != null) {

            entDocuments = docRepo.findByTwoParams("table_name", tn, "table_row_id", "" + rid);
            return entDocuments;

        } else {
            return null;

        }

    }

    @Override
    public Integer getEntityId(E ent) {

        Integer rid;

        try {
            Method entMethod = clsE.getMethod("getId");
            rid = (Integer) entMethod.invoke(ent);

            return rid;

        } catch (IllegalAccessException | IllegalArgumentException |
                SecurityException | NoSuchMethodException | InvocationTargetException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public String getClassTableName() {

        String tn;

        try {
            Field tnFld = clsE.getDeclaredField("TN");
            tn = (String) tnFld.get(null);
            return tn;

        } catch (IllegalAccessException | IllegalArgumentException |
                NoSuchFieldException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public Class<E> getEntityClass() {
        return this.clsE;
    }

    @Override
    public Boolean deactivate(Document doc) {
        Boolean b = this.docRepo.deactivate(doc);
        return b;
    }

    @Override
    public Document save(Document doc) {
        return docRepo.save(doc);
                
    }

}
