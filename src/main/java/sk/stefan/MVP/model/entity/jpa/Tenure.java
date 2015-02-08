package sk.stefan.MVP.model.entity.jpa;


import java.sql.Date;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Tenure {
	/*
	-- 5.
	CREATE TABLE t_tenure
	(
	ksp_id INT(11) NOT NULL AUTO_INCREMENT,
	since DATE NOT NULL,
	till DATE,
	visible INT(1) NOT NULL DEFAULT 1,
	
	CONSTRAINT ten_PK PRIMARY KEY(ksp_id)
	);
	
	*/
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int ksp_id;
	
    @NotNull
    private Date since;
    private Date till;
    
	@NotNull
    private boolean visible;
	
	@NotNull
    @OneToMany(mappedBy = "T_TENURE")
    private Set<PublicRole> t_public_roles;
    
    //getters:
    public int getKsp_id() {
		return this.ksp_id;
	}
	public Date getSince() {
        return this.since;
    }
	public Date getTill() {
        return this.till;
    }
	public boolean getVisible() {
		return this.visible;
	}
	

	//setters:
	public void setKsp_id(int ksp_id) {
		this.ksp_id = ksp_id;
	}
	public void setSince(Date since) {
        this.since = since;
    }
	public void setTill(Date till) {
        this.till = till;
    }
	public void setVisible(boolean vis) {
		this.visible = vis;
	}

}
