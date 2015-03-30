/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.documents;

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
public class DownloaderLayout<E> extends VerticalLayout {

    private static final Logger log = Logger.getLogger(DownloaderLayout.class);

    private static final long serialVersionUID = 1L;

    /**
     * entita, ktorej budu zodpovedat dokumenty.
     */
    private E ent; 

    private final Class<?> clsE;

    private List<DownloaderComponent> downloadComponents;

    private List<Document> entDocuments;

    private final UniRepo<Document> docRepo;
    

    public DownloaderLayout(Class<?> cls) {

        this.setMargin(true);
        this.setSpacing(true);

        this.clsE = cls;
        this.docRepo = new UniRepo<>(Document.class);

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

            this.downloadComponents = new ArrayList<>();

            Integer rid;
            String tn;

            try {
                Method entMethod = clsE.getMethod("getId");
                rid = (Integer) entMethod.invoke(ent);
                Field tnFld = clsE.getDeclaredField("TN");
                tn = (String) tnFld.get(null);

                if (rid != null && tn != null) {

                    entDocuments = docRepo.findByTwoParams("table_name", tn, "table_row_id", "" + rid);
                    log.debug("DOOOWNentDocmsts:" + (entDocuments == null) + " : " + entDocuments.size());
                    
                    DownloaderComponent fc;
                    if (entDocuments != null && !entDocuments.isEmpty()) {
                        for (Document d : entDocuments) {
                            fc = new DownloaderComponent(d);
                            this.downloadComponents.add(fc);
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
