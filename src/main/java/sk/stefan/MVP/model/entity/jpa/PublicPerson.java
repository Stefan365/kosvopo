package sk.stefan.MVP.model.entity.jpa;


import java.io.Serializable;
import java.sql.Date;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author stefan
 */
@Entity
public class PublicPerson implements Serializable {
    private static final long serialVersionUID = 1L;
	/*
	-- 6.
	CREATE TABLE t_public_person
	(
	ksp_id INT(11) NOT NULL AUTO_INCREMENT,
	first_name VARCHAR(50) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	date_of_birth  DATE NOT NULL,
	visible INT(1) NOT NULL DEFAULT 1,
	
	CONSTRAINT pup_PK PRIMARY KEY(ksp_id)
	);
	*/

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int ksp_id;
	
    @NotNull
    @Size(min=2, max=50)
    private String first_name;
    
    @NotNull
    @Size(min=2, max=50)
    private String last_name;
    
    @NotNull
    private Date date_of_birth;
    
	@NotNull
    private boolean visible;
    
	@NotNull
    @OneToMany(mappedBy = "T_PUBLIC_PERSON")
    private Set<PublicRole> t_public_roles;
    
    //getters:
    public int getKsp_id() {
		return this.ksp_id;
	}
	public String getFirst_name() {
        return this.first_name;
    }
	public String getLast_name() {
        return this.last_name;
    }
	public Date getDate_of_birth() {
        return this.date_of_birth;
    }
	public boolean getVisible() {
		return this.visible;
	}
	

	//setters:
	public void setKsp_id(int ksp_id) {
		this.ksp_id = ksp_id;
	}
	public void setFirst_name(String fn) {
        this.first_name = fn;
    }
	public void setLast_name(String ln) {
        this.last_name = ln;
    }
	public void setDate_of_birth(Date bd) {
        this.date_of_birth = bd;
    }
	public void setVisible(boolean vis) {
		this.visible = vis;
	}
}
