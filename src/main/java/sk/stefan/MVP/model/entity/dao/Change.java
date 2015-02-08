package sk.stefan.MVP.model.entity.dao;

import sk.stefan.interfaces.PresentationName;

import java.sql.Date;


public class Change implements PresentationName {
	
	private static final String TN = "T_Change";

	private Integer id;
	
	private Date date_stamp;
	
    private Integer user_id;
  
	private String table_name;

    private Integer row_id;
    
    private Boolean visible_status;

	
	
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
	public Boolean getVisible_status() {
		return this.visible_status;
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
	public void setVisible_status(Boolean vs) {
		this.visible_status = vs;
	}
	
	@Override
	public String getPresentationName() {
		
		return id + ", " + table_name + ", " + date_stamp;
	}
}
