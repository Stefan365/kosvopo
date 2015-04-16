package sk.stefan.utils;

import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 *
 * @author User
 */
// Show uploaded file in this placeholder


// Implement both receiver that saves upload in a file and
// listener for successful upload
public class ZBD_ImageUploader implements Receiver, SucceededListener {
    private static final long serialVersionUID = 1L;
	
    private File file;
    
    final Embedded image = new Embedded("UUU");
    
    
    @Override
    public OutputStream receiveUpload(String filename,
                                      String mimeType) {
        // Create upload stream
        FileOutputStream fos = null; // Stream to write to
        try {
            // Open the file for writing.: sem sa dany subor uploaduje. tj. napr. na server
            setFile(new File("C:\\Users\\User\\Desktop\\kosvopo\\mavenproject2\\"+filename));
            fos = new FileOutputStream(getFile());
            
            
        } catch (final java.io.FileNotFoundException e) {
            new Notification("Could not open file<br/>",
                             e.getMessage(),
                             Notification.Type.ERROR_MESSAGE)
                .show(Page.getCurrent());
            return null;
        }
        
        return fos; // Return the output stream to write to
    }

    public void uploadSucceeded(SucceededEvent event) {
        // Show the uploaded file in the image viewer
        
        image.setVisible(true);
        image.setSource(new FileResource(getFile()));
        
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