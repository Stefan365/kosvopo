/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.files;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import org.apache.log4j.Logger;

/**
 *
 * @author stefan
 */
public class DownloaderComponent extends HorizontalLayout {
    
    private static final long serialVersionUID = 1L;
    
    private static final Logger log = Logger.getLogger(DownloaderComponent.class);
    
    private MyFileDownloader myDownloader;
    
    private Button downloadBt;
    
    
    //0. konstruktor.
    /**
     * 
     */
    public DownloaderComponent(){
        
    }
}
