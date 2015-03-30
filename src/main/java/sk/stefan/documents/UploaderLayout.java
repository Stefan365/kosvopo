/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.documents;

import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.dao.Document;
import sk.stefan.MVP.model.repo.dao.UniRepo;

/**
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
    private final Class<E> clsE;
    private List<DownAndUploaderComponent<E>> uploadComponents;
    private List<Document> entDocuments;
    private final UniRepo<Document> docRepo;
    private Button addDocBt;
    private final UploaderLayout<E> thisS;
    

    public UploaderLayout(Class<E> cls) {

        this.setMargin(true);
        this.setSpacing(true);

        this.initAddButton();

        this.clsE = cls;
        this.docRepo = new UniRepo<>(Document.class);
        thisS = this;

    }

    /**
     * NAdstavi entitu do filesLayoutu
     *
     * @param en
     */
    public void setEntity(E en) {

        this.ent = en;
        this.initLayout();

    }

    /**
     *
     */
    private void initLayout() {

        if (ent != null) {

            this.removeAllComponents();
            
            this.initAddButton();
            this.addComponent(addDocBt);
            
            this.uploadComponents = new ArrayList<>();

            Integer rid;
            String tn;

            try {
                Method entMethod = clsE.getMethod("getId");
                rid = (Integer) entMethod.invoke(ent);
                Field tnFld = clsE.getDeclaredField("TN");
                tn = (String) tnFld.get(null);

                if (rid != null && tn != null) {

                    entDocuments = docRepo.findByTwoParams("table_name", tn, "table_row_id", "" + rid);
                    log.debug("entDocmsts:" + (entDocuments == null) + " : " + entDocuments.size());
                    DownAndUploaderComponent<E> fc;
                    if (entDocuments != null && !entDocuments.isEmpty()) {
                        for (Document d : entDocuments) {
                            fc = new DownAndUploaderComponent<>(d, this);
                            this.uploadComponents.add(fc);
                            this.addComponent(fc);
                        }
                    }
                }

            } catch (IllegalAccessException | IllegalArgumentException |
                    SecurityException | NoSuchMethodException | InvocationTargetException |
                    NoSuchFieldException e) {
                log.error(e.getMessage(), e);
            }
        }
    }


    /**
     * 
     */
    private void initAddButton() {
        this.addDocBt = new Button("pridaj");
        
        this.addDocBt.addClickListener(new Button.ClickListener() {
            
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(Button.ClickEvent event) {
                DownAndUploaderComponent<E> newComp = new DownAndUploaderComponent<>(null, thisS);
                boolean add = thisS.getUploadComponents().add(newComp);
                thisS.addComponent(thisS);
            }
        });
    }

    //*****************************************************88
//    GETTERS AND SETTERS:
    //*****************************************************88
    public E getEnt() {
        return ent;
    }

    public Class<E> getClsE() {
        return clsE;
    }

    public UniRepo<Document> getDocRepo() {
        return docRepo;
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
