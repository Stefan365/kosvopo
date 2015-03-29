/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.documents;

import com.vaadin.server.FileDownloader;
import com.vaadin.ui.Button;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    private final Class<?> clsE;
    private List<DownAndUploaderComponent<E>> uploadComponents;
    private List<Document> documents;
    private final UniRepo<Document> docRepo;
    

//    //1. Uploader:
//    private Upload upload;
//    private FileUploader uploadera;
//
//    //2. Downloader:
//    private FileDownloader downloader;
//    private Button downloadBt;
//    private MyFileDownloader myDownloader;
//
//    //3.Delete button
//    private Button removeBt;
    

    public UploaderLayout(Class<?> cls) {

//        this.setMargin(true);
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

            Integer rid;
            String tn;

            try {
                Method entMethod = clsE.getMethod("getId");
                rid = (Integer) entMethod.invoke(ent);
                Field tnFld = clsE.getDeclaredField("TN");
                tn = (String) tnFld.get(null);

                if (rid != null && tn != null) {

                    documents = docRepo.findByTwoParams("table_name", tn, "table_row_id", "" + rid);

                    DownAndUploaderComponent<E> fc;
                    if (documents != null && !documents.isEmpty()) {
                        for (Document d : documents) {
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
