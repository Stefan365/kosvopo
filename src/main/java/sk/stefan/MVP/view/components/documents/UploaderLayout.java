/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components.documents;

import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.Document;
import sk.stefan.MVP.model.service.DocumentService;
import sk.stefan.MVP.model.serviceImpl.DocumentServiceImpl;

/**
 * Trieda ktora predstavuje dokumenty ktore prisluchaju danej entite s moznostou
 * editacie, tj. vkladania a mazania. pre uzivatelov "dobrovolnik" a "admin".
 *
 * @author stefan
 * @param <E>
 */
public class UploaderLayout<E> extends VerticalLayout {

    private static final Logger log = Logger.getLogger(UploaderLayout.class);

    private static final long serialVersionUID = 1L;

    //Common:
    /**
     * entita, na ktoru sa budu vztahovat dokumenty.
     */
    private E ent;
//    private final Class<E> clsE;

    private List<DownAndUploaderComponent<E>> uploadComponents;

    private List<Document> entDocuments;

    private final DocumentService<E> documentService;

//    private final UniRepo<Document> docRepo;
    private Button addDocBt;

    private final UploaderLayout<E> thisS;

    //0. konstruktor
    /**
     *
     * @param cls
     * @param entit
     */
    public UploaderLayout(Class<E> cls, E entit){

        documentService = new DocumentServiceImpl<>(cls);
        this.setMargin(true);
        this.setSpacing(true);

        this.ent = entit;
        this.initLayout();
//        this.initAddButton();

//        this.clsE = cls;
//        this.docRepo = new UniRepo<>(Document.class);
        thisS = this;

    }

    /**
     * ZBYTOCNE< POTOM VYMAZAT!!!!!
     * NAdstavi entitu do filesLayoutu
     *
     * @param en
     */
    public void setEntity(E en) {

        this.ent = en;
        this.initLayout();
        this.initUploaderComponents();
    
    }
//    **** ZBYTOCNE< POTOM VYMAZAT!!!!

    /**
     *
     */
    private void initLayout() {

        if (ent != null) {

            this.removeAllComponents();

            this.initAddButton();
            this.addComponent(addDocBt);

            this.uploadComponents = new ArrayList<>();

            Integer rid = documentService.getEntityId(ent);
            String tn = documentService.getClassTableName();

            entDocuments = documentService.findAllEntityDocuments(tn, rid);
            
            this.initUploaderComponents();

        } 
    }

    /**
     */
    private void initUploaderComponents() {

        DownAndUploaderComponent<E> fc;
        
        if (entDocuments != null && !entDocuments.isEmpty()) {
            for (Document d : entDocuments) {
                fc = new DownAndUploaderComponent<>(d, this, documentService);
                this.uploadComponents.add(fc);
                this.addComponent(fc);
            }
        }
    }

    /**
     *
     */
    private void initAddButton() {

        this.addDocBt = new Button("pridaj dokumnet");

        this.addDocBt.addClickListener(new Button.ClickListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(Button.ClickEvent event) {
                DownAndUploaderComponent<E> newComp = new DownAndUploaderComponent<>(null, thisS, documentService);
                boolean add = thisS.getUploadComponents().add(newComp);
                thisS.addComponent(newComp);
            }
        });
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

    /**
     * @return the uploadComponents
     */
    public List<DownAndUploaderComponent<E>> getUploadComponents() {
        return uploadComponents;
    }

}
