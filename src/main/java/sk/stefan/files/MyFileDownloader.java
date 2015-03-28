/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.files;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.Button;
import com.vaadin.ui.themes.BaseTheme;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.dao.Document;

/**
 *
 * @author stefan
 */
public class MyFileDownloader {

    private static final Logger log = Logger.getLogger(MyFileDownloader.class);

    private FileDownloader fileDownloader;

    private final Button downlBt;

    private final Document document;

    
    //0.konstruktor.
    /**
     * 
     * @param doc
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
