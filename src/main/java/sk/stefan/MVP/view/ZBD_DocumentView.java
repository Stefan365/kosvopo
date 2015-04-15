/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileDownloader;
import com.vaadin.ui.Button;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.VerticalLayout;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.dao.Document;
import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.MVP.view.components.NavigationComponent;
import sk.stefan.documents.ZBD_FileUploader;
import sk.stefan.documents.MyFileDownloader;

/**
 *
 * @author stefan
 */
public class ZBD_DocumentView extends VerticalLayout implements View {

    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger(ZBD_DocumentView.class);

//    uploader:
    private Upload upload;
    private ZBD_FileUploader uploadera;

//    downloader:
    private FileDownloader downloader;
    private Button downloadBt;
    private MyFileDownloader myDownloader;
    
    
    

    public ZBD_DocumentView() {

        this.setMargin(true);
        this.setSpacing(true);
        
        this.initUploader();
        this.initDownloader();

    }

    private void initUploader() {

        uploadera = new ZBD_FileUploader();

        // Create the upload with a caption and set receiver later
        upload = new Upload("Upload Image Here", uploadera);
        upload.setButtonCaption("Start Upload");
        upload.addProgressListener(new ProgressListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void updateProgress(long readBytes, long contentLength) {
            }
        });

        upload.addSucceededListener(uploadera);
        this.addComponents(upload, uploadera.getImage());

    }

    /**
     * 
     */
    private void initDownloader() {

        Button but = new Button("Download");
        UniRepo<Document> docRepo = new UniRepo<>(Document.class);
//        Document doc = docRepo.findOne(37);
        Document doc = null;

//        this.myDownloader = new MyFileDownloader(doc, but);
        this.addComponent(but);

        
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        this.addComponent(NavigationComponent.getNavComp());
    }
}
