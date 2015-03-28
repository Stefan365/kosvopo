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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    private StreamSource source;

    private StreamResource resource;

    private Button downlBt;

    private Document document;

    private InputStream inputStream;

    
    //0.konstruktor.
    /**
     * 
     * @param doc
     */
    public MyFileDownloader(Document doc){
        this.document = doc;
    }
    
    /**
     * Method to download file.
     */
    private void fileDownload() {

        downlBt = new Button("Link to pdf");
        resource = getInputResource();
        fileDownloader = new FileDownloader(resource);
        fileDownloader.extend(downlBt);

    }

    /**
     * Get Source stream
     *
     */
    private StreamResource getInputResource() {

        source = new StreamResource.StreamSource() {

            private static final long serialVersionUID = 1L;

            @Override
            public InputStream getStream() {
                // return your file/bytearray as an InputStream

//                File file = new File(document.getFile_name());
//                try {
//                    inputStream = new FileInputStream(file);
                inputStream = document.getDocument();
                return inputStream;
//                } catch (FileNotFoundException ex) {
//                    log.error(ex.getMessage(), ex);
//                    return null;
//                }
            }
        };

        resource = new StreamResource(source, getFileName());
        return resource;

    }

    /**
     * return s filename;
     */
    private String getFileName() {
        return document.getFile_name() + "kokotnar";
    }

}
