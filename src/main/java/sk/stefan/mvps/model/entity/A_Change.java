package sk.stefan.mvps.model.entity;

import java.util.Date;
import lombok.Data;
import sk.stefan.interfaces.PresentationName;

/**
 * Entita zachytavajuca akukolvek zmenu v databaze.
 */
@Data
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
    
    //0.konstruktor
    public A_Change(){
        this.date_stamp = new Date();
    }

    public static String getTN() {
        return TN;
    }

    @Override
    public String getPresentationName() {
        return id + ", " + table_name + ", " + date_stamp;
    }
}
