/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view;

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
import java.io.IOException;
import java.io.InputStream;
import org.apache.log4j.Logger;
import sk.stefan.MVP.view.components.NavigationComponent;
import sk.stefan.listeners.TestProgressListener;
import sk.stefan.utils.FileUploader;

/**
 *
 * @author stefan
 */
public class DocumentView extends VerticalLayout implements View {

    private static final long serialVersionUID = 1L;
    
    private static final Logger log = Logger.getLogger(DocumentView.class);

    private Upload upload;
    
    private FileUploader uploadera;

    private FileDownloader fileDownloader;
    
    private StreamSource source;
    
    private StreamResource sr;
    
    
    
    
    public DocumentView() {

        this.initLayout();

    }

    private void initLayout() {

        uploadera = new FileUploader();
        

        // Create the upload with a caption and set receiver later
        upload = new Upload("Upload Image Here", uploadera);
        upload.setButtonCaption("Start Upload");
//        upload.addProgressListener(new TestProgressListener());
        upload.addProgressListener(new ProgressListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void updateProgress(long readBytes, long contentLength) {
            }
        });

        upload.addSucceededListener(uploadera);

        // Put the components in a panel
        // Panel panel = new Panel("Cool Image Storage");
        // Layout panelContent = new VerticalLayout();
        this.addComponents(upload, uploadera.getImage());

    }

    /**
     * Method to download file.
     */
    private void fileDownload() {
        
        Button l = new Button("Link to pdf");
        StreamResource sr = getPDFStream();
        fileDownloader = new FileDownloader(sr);
        fileDownloader.extend(l);
    }

    /**
     * Get Source stream
     *
     */
    private StreamResource getPDFStream() {

        source = new StreamResource.StreamSource() {
            
            private static final long serialVersionUID = 1L;

            @Override
            public InputStream getStream() {
                // return your file/bytearray as an InputStream
                File file = uploadera.getFile(); //new File("/Users/mkyong/Downloads/file.js");
                try {
                    InputStream inputStream = new FileInputStream(file);
                    return inputStream;
                } catch (FileNotFoundException ex) {
                    log.error(ex.getMessage(),ex);
                    return null;
                }
            }
        };
        
        StreamResource resource = new StreamResource(source, getFileName());
        return resource;

    }
    
    private String getFileName(){
        return "filename";
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        this.addComponent(NavigationComponent.getNavComp());
    }
}
