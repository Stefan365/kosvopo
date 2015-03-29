package sk.stefan.documents;

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
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.dao.Document;
import sk.stefan.MVP.model.repo.dao.GeneralRepo;

/**
 * @author stefan
 * @param <E>
 */
public class MyFileUploader<E> implements Receiver, SucceededListener {

    private static final Logger log = Logger.getLogger(FileUploader.class);
    private static final long serialVersionUID = 1L;
    private File file;
//    private final Embedded image;

    private final E ent;
//    private InputStream inStream;
    private String fileName;
    private final Class<?> clsE;
    private final GeneralRepo genRepo = new GeneralRepo();

    private final MyFileDownloader listener;
     
    public MyFileUploader(Class<?> cls, E en, MyFileDownloader lisnr) {

        this.clsE = cls;
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

//        image.setVisible(true);
//        image.setSource(new FileResource(file));
        InputStream inStream = null;
        
        try {
            Method entMethod = clsE.getMethod("getId");
            Integer rid = (Integer) entMethod.invoke(ent);
            Field tnFld = clsE.getDeclaredField("TN");
            String tn = (String) tnFld.get(null);

            inStream = new FileInputStream(this.getFile());
            Document doc = genRepo.insertFileInDB(inStream, fileName, tn, rid);
            
            Notification.show("File saved to Database!");
            
            //druhy krok: zmena nastavenie v MyFileUploaderi:
            listener.refreshDownloader(null);

        } catch (FileNotFoundException | IllegalAccessException | IllegalArgumentException |
                SecurityException | NoSuchMethodException | InvocationTargetException |
                NoSuchFieldException e) {
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
