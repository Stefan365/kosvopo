/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.entity.dao;

import java.io.IOException;
import java.util.Date;
import org.apache.log4j.Logger;
import sk.stefan.interfaces.PresentationName;
import sk.stefan.utils.Tools;

/**
 * reprezentuje dokumnet.
 *
 * @author stefan
 */
public class Document  implements PresentationName {
    
    private static final Logger log = Logger.getLogger(Document.class);
    
    private static final String TN = "t_document";
    
    private static final String CLASS_PRESENTATION_NAME = "Dokument";

    /**
     * @return the TN
     */
    public static String getTN() {
        return TN;
    }

    /**
     * @return the CLASS_PRESENTATION_NAME
     */
    public static String getCLASS_PRESENTATION_NAME() {
        return CLASS_PRESENTATION_NAME;
    }

    private Integer id;

    private String table_name;
    
    private Integer table_row_id;
    
    private Date upload_date;

    private String link;
    
    private Boolean visible;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the upload_date
     */
    public Date getUpload_date() {
        return upload_date;
    }

    /**
     * @param upload_date the upload_date to set
     */
    public void setUpload_date(Date upload_date) {
        this.upload_date = upload_date;
    }

    /**
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link the link to set
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * @return the table_id
     */
    public Integer getTable_id() {
        return table_row_id;
    }

    /**
     * @param table_id the table_id to set
     */
    public void setTable_id(Integer table_id) {
        this.table_row_id = table_id;
    }

    /**
     * @return the table_name
     */
    public String getTable_name() {
        return table_name;
    }

    /**
     * @param table_name the table_name to set
     */
    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    /**
     * @return the visible
     */
    public Boolean getVisible() {
        return visible;
    }

    /**
     * @param visible the visible to set
     */
    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    @Override
    public String getPresentationName() {
        try {
            String tn = Tools.getTableDepictNames(table_name);
            return tn + "-" + this.id;
        } catch (IOException ex) {
            log.error(ex.getMessage(),ex);
            return null;
        }
    }
    
}
