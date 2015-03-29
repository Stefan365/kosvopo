/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.documents;

import com.vaadin.server.FileDownloader;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Upload;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.dao.Document;

/**
 *
 * @author stefan
 */
public class DownAndUploaderComponent<E> extends HorizontalLayout {
    
    private static final long serialVersionUID = 1L;
    
    private static final Logger log = Logger.getLogger(DownloaderComponent.class);
    
    
    //spolocne:
    private final UploaderLayout<E> listener;
    private final Document document;
    private final E ent;
    
    
    
    //1. Uploader:
    private Upload upload;
    private FileUploader uploadera;

    //2. Downloader:
    private FileDownloader downloader;
    private Button downloadBt;
    private MyFileDownloader myDownloader;

    //3.Delete button
    private Button removeBt;

    
    //0. konstruktor.
    /**
     * 
     * @param doc
     * @param lisnr
     */
    public DownAndUploaderComponent(Document doc, UploaderLayout<E> lisnr){
        
        this.setMargin(true);
        this.setSpacing(true);
        
        this.listener = lisnr;
        this.document = doc;
        this.ent = listener.getEnt();
        
        this.initUploader();
        this.initDownloader();
        this.initRemoveBt();
    
    }
    
    //1.
    /**
     * Inicializuje uploader.
     */
    private void initUploader() {

        uploadera = new FileUploader();

        // Create the upload with a caption and set receiver later
        upload = new Upload("Upload Image Here", uploadera);
        upload.setButtonCaption("Start Upload");
        upload.addProgressListener(new Upload.ProgressListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void updateProgress(long readBytes, long contentLength) {
            }
        });

        upload.addSucceededListener(uploadera);
        this.addComponents(upload, uploadera.getImage());

    }

    
    //2.
    /**
     * 
     */
    private void initDownloader() {

        downloadBt = new Button();

        this.myDownloader = new MyFileDownloader(document, downloadBt);
        this.addComponent(downloadBt);

        
    }

    //3.
    /**
     */
    private void initRemoveBt() {
    
    }

    //************************************************************
//    GETTERS AND SETTERS
    //************************************************************
    
    public MyFileDownloader getMyDownloader() {
        return myDownloader;
    }

    public void setMyDownloader(MyFileDownloader myDownloader) {
        this.myDownloader = myDownloader;
    }

    public Button getDownloadBt() {
        return downloadBt;
    }

    public void setDownloadBt(Button downloadBt) {
        this.downloadBt = downloadBt;
    }

}
