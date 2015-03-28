package sk.stefan.files;

import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.repo.dao.GeneralRepo;

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

    
    
    public FileUploader() {

        image = new Embedded("Uploaded Image");
        image.setVisible(true);

    }

    public Embedded getImage() {
        return this.image;
    }

    @Override
    public OutputStream receiveUpload(String filename, String mimeType) {
        
        this.fileName = filename;
                
        // Create upload stream
        FileOutputStream fos; // Stream to write to

        try {
            // Open the file for writing.
            // file = new File("/tmp/uploads/" + filename);
            String fileN = "C:\\Users\\stefan\\Desktop\\obrazky\\" + filename;

//            Properties prop = new Properties();
//            input = new FileInputStream(fileN);
//            prop.load(input);
//            input.close();
            file = new File(fileN);
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

        image.setVisible(true);
        image.setSource(new FileResource(file));

        
        try {
        
            inStream = new FileInputStream(this.getFile());
            genRepo.insertFileInDB(inStream, tn, rid, fileName);
            Notification.show("File saved to Database!");
        
        } catch (FileNotFoundException ex) {
            log.error(ex.getMessage(), ex);
        } finally {
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
