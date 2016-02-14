/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.model.serviceImpl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;

import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sk.stefan.mvps.model.entity.Document;
import sk.stefan.mvps.model.repo.UniRepo;
import sk.stefan.mvps.model.service.ActiveUserService;
import sk.stefan.mvps.model.service.DocumentService;

/**
 *
 * @author stefan
 */
@Service
public class DocumentServiceImpl implements DocumentService {

    private static final Logger log = Logger.getLogger(DocumentServiceImpl.class);

    private UniRepo<Document> docRepo;

	@Autowired
    private ActiveUserService activeUserService;


    public DocumentServiceImpl() {

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
    public String getClassTableName(Class clazz) {

        String tn;

        try {
            Field tnFld = clazz.getDeclaredField("TN");
            tn = (String) tnFld.get(null);
            return tn;

        } catch (IllegalAccessException | IllegalArgumentException |
                NoSuchFieldException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public Boolean deactivate(Document doc) {
//        dokumenty su koncova entita, tj. deaktivujeme je jednoduchsou cestou (nie zbytocne cez 
//                deaktivaciu stromu entit)
        Boolean b = this.docRepo.deactivateOneOnly(doc, doc.getId() != null, activeUserService.getActualUser());
        return b;
    }

    @Override
    public Document save(Document doc ) {
        return docRepo.save(doc, true, activeUserService.getActualUser());
                
    }

    @Override
    public Resource getDocumentResource(Document document) {
        StreamResource.StreamSource source;
        StreamResource resource;

        source = (StreamResource.StreamSource) () -> {
            InputStream inputStream = new ByteArrayInputStream(document.getDocument());
            return inputStream;
        };

        resource = new StreamResource(source, document.getFile_name());
        return resource;
    }

}
