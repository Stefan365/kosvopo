/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.documents;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.VerticalLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.dao.Document;
import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.MVP.view.components.NavigationComponent;
import sk.stefan.documents.FileUploader;
import sk.stefan.documents.MyFileDownloader;

/**
 *
 * @author stefan
 */
public class DocumentView extends VerticalLayout implements View {

    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger(DocumentView.class);

//    uploader:
    private Upload upload;
    private FileUploader uploadera;

//    downloader:
    private FileDownloader downloader;
    private Button downloadBt;
    private MyFileDownloader myDownloader;
    
    
    

    public DocumentView() {

        this.setMargin(true);
        this.setSpacing(true);
        
        this.initUploader();
        this.initDownloader();

    }

    private void initUploader() {

        uploadera = new FileUploader();

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
        Document doc = docRepo.findOne(23);

        this.myDownloader = new MyFileDownloader(doc, but);
        this.addComponent(but);

        
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        this.addComponent(NavigationComponent.getNavComp());
    }
}
