package sk.stefan.MVP.view.components.documents;

import sk.stefan.MVP.view.components.panels.DownAndUploaderBriefPanel;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.Document;
import sk.stefan.MVP.model.service.DocumentService;

/**
 * @author stefan
 * @param <E> typ entity ku ktorej sa dokumnet vztahuje.
 */
public class MyFileDownAndUploader<E> implements Receiver, SucceededListener {

    private static final Logger log = Logger.getLogger(MyFileDownAndUploader.class);
    private static final long serialVersionUID = 1L;
    private File file;

    private final E ent;
    private String fileName;
    
    private final DownAndUploaderBriefPanel<E> listener;
    private final DocumentService<E> documentService;

     
    public MyFileDownAndUploader(E en, DownAndUploaderBriefPanel<E> lisnr, DocumentService<E> docS) {
        
        this.documentService = docS;
        this.ent = en;
        this.listener = lisnr;

    }

    @Override
    public OutputStream receiveUpload(String filename, String mimeType) {

        this.fileName = filename;
        FileOutputStream fos;

        try {

            file = new File(fileName);
            fos = new FileOutputStream(file);

        } catch (final java.io.FileNotFoundException e) {
            new Notification("Could not open file<br/>",
                    e.getMessage(), Notification.Type.ERROR_MESSAGE)
                    .show(Page.getCurrent());
            return null;
        }

        return fos; // Return the output stream to write to
    }

    /**
     *
     */
    @Override
    public void uploadSucceeded(SucceededEvent event) {

        InputStream inStream = null;
        
        Integer rid = documentService.getEntityId(ent);
        String tn = documentService.getClassTableName();
        
        try {

            Document doc = new Document();
            doc.setFile_name(fileName);
            doc.setTable_name(tn);
            doc.setTable_row_id(rid);
            doc.setUpload_date(new Date());
//            log.info("DATUM UPLOAD:" + doc.getUpload_date());
            doc.setVisible(Boolean.TRUE);
            
            
            inStream = new FileInputStream(this.getFile());
            byte[] bFile = new byte[inStream.available()];
            inStream.read(bFile);
            doc.setDocument(bFile);
            
            doc = documentService.save(doc);
            Notification.show("File saved to Database!");
            
            //druhy krok: zmena nastavenie v MyFileUploaderi:
            listener.setDocument(doc);
            listener.getMyDownloader().refreshDownloader(doc);

        } catch (IOException | IllegalArgumentException |
                SecurityException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (inStream != null) {
                    inStream.close();
                }
            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        
    }
    
    

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
