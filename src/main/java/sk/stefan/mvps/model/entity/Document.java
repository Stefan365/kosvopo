/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.model.entity;

//import java.sql.Timestamp;

import java.util.Date;
import lombok.Data;
import org.apache.log4j.Logger;
import sk.stefan.interfaces.PresentationName;
import sk.stefan.utils.ToolsNames;

/**
 * Reprezentuje dokument, ktory sa moze vztahovat k akejkolvek entite.
 * Vsechen spech jest od dabla!
 * 
 * @author stefan
 */
@Data
public class Document implements PresentationName {

    private static final Logger log = Logger.getLogger(Document.class);

    public static final String TN = "t_document";

    public static final String PRES_NAME = "Dokument";
    
    private Integer id;
    
    private String table_name;

    private Integer table_row_id;
    
    private String file_name;
    
    private Date upload_date;
    
   private byte[] document;
    
    private Boolean visible;    
    

    public Document() {
    }
    
    @Override
    public String getPresentationName() {
        String tn = ToolsNames.getTableDepictNames(table_name);
        return tn + "-" + this.id;
    }

}
