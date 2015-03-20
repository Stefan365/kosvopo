/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.entity.dao;

import java.io.IOException;
import java.util.Date;
import org.apache.log4j.Logger;
import static sk.stefan.MVP.model.entity.dao.Kraj.TN;
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
    
    private static final String PRES_NAME = "Dokument";

    
    public Document(){
    }
    /**
     * @return the TN
     */
    public static String getTN() {
        return TN;
    }

    /**
     * @return the PRES_NAME
     */
    public static String getPRES_NAME() {
        return PRES_NAME;
    }

    private Integer id;

    private String table_name;
    
    private Integer table_row_id;
    
    private Date upload_date;

    private String link;
    
    private Boolean visible;

    
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getTable_id() {
        return table_row_id;
    }

    public void setTable_id(Integer table_id) {
        this.table_row_id = table_id;
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
        try {
            String tn = Tools.getTableDepictNames(table_name);
            return tn + "-" + this.id;
        } catch (IOException ex) {
            log.error(ex.getMessage(),ex);
            return null;
        }
    }
    
}
