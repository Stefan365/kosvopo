/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components.panels;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.Document;
import sk.stefan.MVP.view.components.documents.MyFileDownloader;

/**
 *
 * @author stefan
 */
public class DownloaderBriefPanel extends HorizontalLayout {
    
    private static final long serialVersionUID = 1L;
    
    private static final Logger log = Logger.getLogger(DownloaderBriefPanel.class);
    
    private MyFileDownloader myDownloader;
    
    private Button downloadBt;
    
    
    //0. konstruktor.
    /**
     * 
     * @param doc
     */
    public DownloaderBriefPanel(Document doc){
        
        this.setMargin(true);
        this.setSpacing(true);
        
        this.downloadBt = new Button();
        this.myDownloader = new MyFileDownloader(doc, downloadBt);
        this.addComponent(downloadBt);
        
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
