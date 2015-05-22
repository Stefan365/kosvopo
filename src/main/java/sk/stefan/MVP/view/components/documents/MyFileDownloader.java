/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components.documents;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.Button;
import com.vaadin.ui.themes.BaseTheme;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.Document;
import sk.stefan.MVP.model.repo.UniRepo;
import sk.stefan.MVP.model.service.DocumentService;
import sk.stefan.MVP.model.serviceImpl.DocumentServiceImpl;

/**
 *
 * @author stefan
 */
public class MyFileDownloader {

    private static final Logger log = Logger.getLogger(MyFileDownloader.class);

    private FileDownloader fileDownloader;

    private final Button downlBt;

    private Document document;

    //nie service. toto je umyselna vynimka z MVP, mnohy, ked sa pozrie do kodu pochopi preco:
    private final UniRepo<Document> docRepo = new UniRepo<>(Document.class);
    

    //0.konstruktor.
    /**
     * 
     * @param doc
     * @param but
     */
    public MyFileDownloader(Document doc, Button but){
        
        this.document = doc;
        this.downlBt = but;
        
        this.setButton();
        this.initDownload();
        
    }
    

    /**
     * Sets buttons properties
     */
    private void setButton(){
        
        this.downlBt.setStyleName(BaseTheme.BUTTON_LINK);
//        log.info("BUTTON: " + (downlBt == null));
//        log.info("DOCUMENT: " + (document == null));
        
        this.downlBt.setCaption(document.getFile_name());
        
    }
    
    /**
     * Method to download file.
     */
    private void initDownload() {

        StreamResource resource = getInputResource();
        fileDownloader = new FileDownloader(resource);
        fileDownloader.extend(downlBt);

    }
    
    /**
     *
     * @param doc
     */
    public void refreshDownloader(Document doc){

        //deaktivacia starej entity:
        if (document.getId() != null){
            this.document.setVisible(Boolean.FALSE);
            docRepo.save(document, true);
        }
        
        this.document = doc;
        StreamResource resource = getInputResource();

        this.downlBt.setCaption(doc.getFile_name());
        fileDownloader.setFileDownloadResource(resource);
 
    }

    /**
     * Get Source stream
     *
     */
    private StreamResource getInputResource() {
        
        StreamSource source;
        StreamResource resource;

        source = new StreamSource() {

            private static final long serialVersionUID = 1L;

            @Override
            public InputStream getStream() {
                InputStream inputStream = new ByteArrayInputStream(document.getDocument());
                return inputStream;
            }
        };

        resource = new StreamResource(source, getFileName());
        return resource;

    }

    /**
     * return s filename;
     */
    private String getFileName() {
        return document.getFile_name();
    }

}
