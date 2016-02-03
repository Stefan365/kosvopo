package sk.stefan.mvps.model.entity;

import java.util.Date;
import sk.stefan.interfaces.PresentationName;

/**
 * Entita zachytavajuca akukolvek zmenu v databaze.
 */
public class A_Change implements PresentationName {

    public static final String TN = "a_change";
    
    public static final String PRES_NAME = "Zmena";

    private Integer id;

    private Date date_stamp;

    private Integer user_id;

    private String table_name;
    
    private String column_name;
    
    private Integer row_id;

    private byte[] old_value;
    
    private byte[] new_value;
    
    private Boolean visible;
    
    
    public A_Change(){
        
        this.date_stamp = new Date();
        
    }

    //getters:
    public Integer getId() {
        return this.id;
    }

    public Date getDate_stamp() {
        return this.date_stamp;
    }

    public Integer getUser_id() {
        return this.user_id;
    }

    public String getTable_name() {
        return this.table_name;
    }

    public Integer getRow_id() {
        return this.row_id;
    }

    public static String getTN() {
        return TN;
    }

    //setters:
    public void setId(Integer id) {
        this.id = id;
    }

    public void setDate_stamp(Date date_stamp) {
        this.date_stamp = date_stamp;
    }

    public void setUser_id(Integer uid) {
        this.user_id = uid;
    }

    public void setTable_name(String tn) {
        this.table_name = tn;
    }

    public void setRow_id(Integer row_id) {
        this.row_id = row_id;
    }


    @Override
    public String getPresentationName() {

        return id + ", " + table_name + ", " + date_stamp;
    }

    /**
     * @return the column_name
     */
    public String getColumn_name() {
        return column_name;
    }

    /**
     * @param column_name the column_name to set
     */
    public void setColumn_name(String column_name) {
        this.column_name = column_name;
    }

    public byte[] getOld_value() {
        return old_value;
    }

    public void setOld_value(byte[] old_value) {
        this.old_value = old_value;
    }
    
    

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public byte[] getNew_value() {
        return new_value;
    }

    public void setNew_value(byte[] new_value) {
        this.new_value = new_value;
    }
}
