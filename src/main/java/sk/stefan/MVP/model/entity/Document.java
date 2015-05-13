/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.entity;

//import java.sql.Timestamp;

import java.util.Date;
import org.apache.log4j.Logger;
import sk.stefan.interfaces.PresentationName;
import sk.stefan.utils.ToolsNames;

/**
 * reprezentuje dokumnet.
 * Elinecka je najlepsia vo vesmire.
 * 
 * @author stefan
 */
public class Document implements PresentationName {

    private static final Logger log = Logger.getLogger(Document.class);

    public static final String TN = "t_document";

    public static final String PRES_NAME = "Dokument";
    
    private Integer id;
    
    private String table_name;

    private Integer table_row_id;
    
    private String file_name;
    
//    private Timestamp upload_date;
    
    private Date upload_date;
    
//    private InputStream document;
    private byte[] document;
    
    private Boolean visible;    
    
    
    

    public Document() {
    }

    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getUpload_date() {
        return upload_date;
    }

    public void setUpload_date(Date upload_date) {
        this.upload_date = upload_date;
    }


    public Integer getTable_row_id() {
        return table_row_id;
    }

    public void setTable_row_id(Integer rid) {
        this.table_row_id = rid;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    @Override
    public String getPresentationName() {
        
        String tn = ToolsNames.getTableDepictNames(table_name);
        return tn + "-" + this.id;
        
    }

    /**
     * @return the file_name
     */
    public String getFile_name() {
        return file_name;
    }

    /**
     * @param file_name the file_name to set
     */
    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    /**
     * @return the document
     */
    public byte[] getDocument() {
        return document;
    }

    /**
     * @param document the document to set
     */
    public void setDocument(byte[] document) {
        this.document = document;
    }

}
