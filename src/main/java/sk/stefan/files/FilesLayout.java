/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.files;

import com.vaadin.ui.VerticalLayout;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.dao.Document;
import sk.stefan.MVP.model.repo.dao.UniRepo;

/**
 *
 * @author stefan
 * @param <E>
 */
public class FilesLayout<E> extends VerticalLayout {

    private static final Logger log = Logger.getLogger(FilesLayout.class);

    private static final long serialVersionUID = 1L;

    /**
     * entita, ktorej budu zodpovedat dokumenty.
     */
    private E ent;

    private final Class<?> clsE;

//    private List<FileComponent> fileComponents;
    private List<Document> documents;

    private final UniRepo<Document> docRepo;

    public FilesLayout(Class<?> cls) {

        this.clsE = cls;
        this.docRepo = new UniRepo<>(Document.class);

    }

    public void setEntity(E en) {

        this.ent = en;
        Integer rid;
        String tn;
        try {
            Method entMethod = clsE.getMethod("getId");
            rid = (Integer) entMethod.invoke(ent);
            Field tnFld = clsE.getDeclaredField("TN");
            tn = (String) tnFld.get(null);

            if (ent != null && rid != null && tn != null) {
                
                documents = docRepo.findByTwoParams("table_name", tn, "table_row_id", "" + rid);
                
                DownloaderComponent fc;
                for (Document d : documents){
                    fc = new DownloaderComponent();
                }
                
            }
            
            

        } catch (IllegalAccessException | IllegalArgumentException |
                SecurityException | NoSuchMethodException | InvocationTargetException |
                NoSuchFieldException e) {
            log.error(e.getMessage(), e);
        }

    }

    /**
     *
     */
    private void initLayout() {

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
