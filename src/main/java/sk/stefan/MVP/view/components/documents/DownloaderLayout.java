/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components.documents;

import com.vaadin.ui.VerticalLayout;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.Document;
import sk.stefan.MVP.model.service.DocumentService;
import sk.stefan.MVP.model.serviceImpl.DocumentServiceImpl;

/**
 * Trieda ktoa predstavuje dokumenty ktore prisluchaju danej entite bez moznosti
 * editacie. Tj. len pre uzivatela "obcan".
 *
 * @author stefan
 * @param <E>
 */
public class DownloaderLayout<E> extends VerticalLayout {

    private static final Logger log = Logger.getLogger(DownloaderLayout.class);

    private static final long serialVersionUID = 1L;

    /**
     * entita, ktorej budu zodpovedat dokumenty.
     */
    private E ent;

    private List<DownloaderComponent> downloadComponents;

    private List<Document> entDocuments;

    private final DocumentService<E> documentService;

    @SuppressWarnings("unchecked")
    public DownloaderLayout(Class<E> cls) {

        documentService = new DocumentServiceImpl<>(cls);

    }

    /**
     * NAdstavi entitu do filesLayoutu
     *
     * @param en
     */
    public void setEntity(E en) {

        this.ent = en;
        this.initLayout();
        this.initDownloaderComponents();

    }

    /**
     *
     */
    private void initLayout() {

        if (ent != null) {

            this.removeAllComponents();

            this.downloadComponents = new ArrayList<>();

            Integer rid = documentService.getEntityId(ent);
            String tn = documentService.getClassTableName();

            entDocuments = documentService.findAllEntityDocuments(tn, rid);

        }
    }

    /**
     */
    private void initDownloaderComponents() {

        DownloaderComponent fc;
        if (entDocuments != null && !entDocuments.isEmpty()) {
            for (Document d : entDocuments) {
                fc = new DownloaderComponent(d);
                this.downloadComponents.add(fc);
                this.addComponent(fc);
            }
        }        
    }

    //*****************************************************88
//    GETTERS AND SETTERS:
    //*****************************************************88
    public E getEnt() {
        return ent;
    }

    public void setEnt(E ent) {
        this.ent = ent;
    }

}
