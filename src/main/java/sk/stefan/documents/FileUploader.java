package sk.stefan.documents;

import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Embedded;
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
import sk.stefan.MVP.model.entity.dao.Document;
import sk.stefan.MVP.model.repo.dao.GeneralRepo;
import sk.stefan.MVP.model.repo.dao.UniRepo;

/**
 *
 */
public class FileUploader implements Receiver, SucceededListener {

    private static final Logger log = Logger.getLogger(FileUploader.class);
    private static final long serialVersionUID = 1L;
    private File file;
    private final Embedded image;

    private final String tn = "t_vote";
    private final Integer rid = 2;

    private InputStream inStream;
    private String fileName;

    private final GeneralRepo genRepo = new GeneralRepo();

    private final UniRepo<Document> docRepo = new UniRepo<>(Document.class);

    
    public FileUploader() {

        image = new Embedded("Uploaded Image");
        image.setVisible(true);
        String basepath = VaadinService.getCurrent()
                  .getBaseDirectory().getAbsolutePath();
        FileResource res = new FileResource(new File(basepath +
                        "/WEB-INF/images/Bobo_ta_sleduje.JPG"));
        image.setSource(res);


    }

    public Embedded getImage() {
        return this.image;
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

//        image.setVisible(true);
//        String basepath = VaadinService.getCurrent()
//                  .getBaseDirectory().getAbsolutePath();
//        FileResource res = new FileResource(new File(basepath +
//                        "/WEB-INF/images/Bobo_ta_sleduje.JPG"));
//        image.setSource(res);

//        image.setSource(new FileResource(file));

        Document doc;
        try {
            doc = new Document();
            doc.setFile_name(fileName);
            doc.setTable_name(tn);
            doc.setTable_row_id(rid);
            doc.setUpload_date(new Date());
//            log.info("DATUM UPLOAD:" + doc.getUpload_date());
            doc.setVisible(Boolean.TRUE);

            
            inStream = new FileInputStream(this.getFile());
            byte[] bFile = new byte[inStream.available()];
            try {
                inStream.read(bFile);
                doc.setDocument(bFile);
            } catch (IOException ex) {
                
            }
            docRepo.save(doc);
//            genRepo.insertFileInDB(inStream, fileName, tn, rid);

            Notification.show("File saved to Database!");

        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        } 
        finally {
            try {
                inStream.close();
            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
            }
        }
    }

    /**
     * @return the file
     */
    public File getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(File file) {
        this.file = file;
    }

}
