package sk.stefan.MVP.model.entity.jpa;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Change {
	/*
	-- 4.
	CREATE TABLE t_change
	(
	ksp_id INT(11) NOT NULL AUTO_INCREMENT,
	date_stamp DATE NOT NULL,
	usr_user_id INT(11) NOT NULL,
	table_name VARCHAR(30) NOT NULL,
	row_id INT(11) NOT NULL,
	visible_status INT(1) NOT NULL,
	
	CONSTRAINT chg_PK PRIMARY KEY(ksp_id),
	CONSTRAINT chg_FK FOREIGN KEY(usr_user_id) REFERENCES t_user(ksp_id),
	CONSTRAINT chg_UN1 UNIQUE(date_stamp, usr_user_id)
	);
*/
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int ksp_id;
	
	@NotNull
	private Date date_stamp;
	
    @NotNull
    @ManyToOne
    private User user;
  
	@NotNull
	private String table_name;

	@NotNull
    private int row_id;
    
	@NotNull
    private boolean visible_status;

	
	
    //getters:
    public int getKsp_id() {
		return this.ksp_id;
	}
	public Date getDate_stamp() {
        return this.date_stamp;
    }
	public User getUser() {
        return this.user;
    }
	public int getRow_id() {
		return this.row_id;
	}
	public boolean getVisible_status() {
		return this.visible_status;
	}
	

	//setters:
	public void setKsp_id(int id) {
		this.ksp_id = id;
	}
	public void setDate_stamp(Date date_stamp) {
        this.date_stamp = date_stamp;
    }
	public void setUser(User user) {
        this.user = user;
    }
	public void setRow_id(int row_id) {
		this.row_id = row_id;
	}
	public void setVisible_status(boolean vs) {
		this.visible_status = vs;
	}
}
