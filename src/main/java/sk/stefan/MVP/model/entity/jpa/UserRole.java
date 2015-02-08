package sk.stefan.MVP.model.entity.jpa;


import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;


@Entity
public class UserRole {
/*
 	//3.
	 CREATE TABLE t_user_role 
	(
	ksp_id INT(11) NOT NULL AUTO_INCREMENT, 
	rol_role_id INT(11),
	usr_user_id INT(11),
	since DATE,
	till DATE,
	
	CONSTRAINT uro_PK PRIMARY KEY (ksp_id),
	CONSTRAINT uro_FK1 FOREIGN KEY(usr_user_id) REFERENCES t_user(ksp_id),
	CONSTRAINT uro_FK2 FOREIGN KEY(rol_role_id) REFERENCES t_role(ksp_id)
	); 
 */
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int ksp_id;
	
    @NotNull
    @ManyToOne
    private User user;
    
    @NotNull
    @ManyToOne
    private Role role;

    @NotNull
    private Date since;
    
    private Date till;


	//getters:
    public int getKsp_id() {
		return this.ksp_id;
	}
	public User getUser() {
        return this.user;
    }
	public Role getRole() {
        return this.role;
    }
	public Date getTill() {
		return this.till;
	}

	//setters:
	public void setKsp_id(int ksp_id) {
		this.ksp_id = ksp_id;
	}
	public void setUser(User user) {
        this.user = user;
    }
	public void setRole(Role role) {
        this.role = role;
    }
	public void setSince(Date since) {
		this.since = since;
	}
	public void setTill(Date till) {
		this.till = till;
	}
	
}
