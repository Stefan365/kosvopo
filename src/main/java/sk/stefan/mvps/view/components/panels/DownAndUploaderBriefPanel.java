/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.panels;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import java.io.File;
import org.apache.log4j.Logger;
import sk.stefan.mvps.model.entity.Document;
import sk.stefan.mvps.model.service.DocumentService;
import sk.stefan.mvps.view.components.documents.MyFileDownAndUploader;
import sk.stefan.mvps.view.components.documents.MyFileDownloader;
import sk.stefan.mvps.view.components.layouts.DownAndUploaderBriefLayout;

/**
 *
 * @author stefan
 * @param <E>
 */
public class DownAndUploaderBriefPanel<E> extends HorizontalLayout {
    
//    Panel p; 
    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger(DownAndUploaderBriefPanel.class);

    //spolocne:
    private final DownAndUploaderBriefLayout<E> listener;
    private Document document;
    private final E ent;
    
    private final DocumentService<E> documentService;
    
//    private final Class<E> clsE;
    private final DownAndUploaderBriefPanel<E> thisS;

    //1. Uploader:
    private Upload upload;
    private MyFileDownAndUploader<?> myUploader;

    //2. Downloader:
//    private FileDownloader download;
    private Button downloadBt;
    private MyFileDownloader myDownloader;

    //3.Delete button
    private Button removeBt;

    //0. konstruktor.
    /**
     *
     * @param doc
     * @param lisnr
     * @param docS
     */
    public DownAndUploaderBriefPanel(Document doc, DownAndUploaderBriefLayout<E> lisnr, DocumentService<E> docS) {

//        this.setMargin(true);
//        this.setSpacing(true);
//        this.addStyleName("horizontal-separator");
//        this.addStyleName("vertical-separator");

        
        this.thisS = this;
        this.listener = lisnr;
        this.documentService = docS;
        if (doc == null) {
            document = new Document();
        } else {
            this.document = doc;
        }
        this.ent = listener.getEnt();
//        this.clsE = documentService.getEntityClass();

        this.initDownloader();
        this.initUploader();
        this.initRemoveBt();

        this.addComponents(upload, downloadBt, removeBt);

    }

    //1.
    /**
     * Inicializuje uploader.
     */
    private void initUploader() {
        
        myUploader = new MyFileDownAndUploader<>(ent, this,documentService);
//        myUploader = new MyFileUploader<>(ent, this.myDownloader);

        // Create the upload with a caption and set receiver later
        upload = new Upload("nahraj dokumnet", myUploader);
        upload.setButtonCaption("upload");
        upload.addProgressListener(new Upload.ProgressListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void updateProgress(long readBytes, long contentLength) {
            }
        });

        upload.addSucceededListener(myUploader);
//        this.addComponents(upload);

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

        this.removeBt = new Button();

        String basepath = VaadinService.getCurrent()
                .getBaseDirectory().getAbsolutePath();
        FileResource resource = new FileResource(new File(basepath
//                + "/WEB-INF/images/close.gif"));
                + "/VAADIN/themes/mytheme/images/close.gif"));
        this.removeBt.setIcon(resource);

        this.removeBt.addClickListener(new Button.ClickListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(Button.ClickEvent event) {

//                deaktivuje dokument v DB:
                boolean isDone = documentService.deactivate(document);
                //odstrani komponentu D&Uploader z Layoutu:
                if (isDone) {
                    boolean add = listener.getUploadComponents().remove(thisS);
                    listener.removeComponent(thisS);
                    Notification.show("Dokument bol odstránený");
                }
            }
        });

//        fileN = "C:\\Users\\stefan\\Desktop\\kosvopo6\\src\\main\\resources\\depictNames\\";
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

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

}
