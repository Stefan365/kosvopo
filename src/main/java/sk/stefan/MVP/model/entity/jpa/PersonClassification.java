package sk.stefan.MVP.model.entity.jpa;


import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class PersonClassification {
	/*
	-- 7.
	CREATE TABLE t_person_classification
	(
	ksp_id INT(11) NOT NULL AUTO_INCREMENT,
	classification_date DATE NOT NULL,
	pup_public_person_id INT(11) NOT NULL,
	stability INT(2) NOT NULL,
	public_usefulness INT(2) NOT NULL,
	visible INT(1) NOT NULL DEFAULT 1,
	
	CONSTRAINT pcl_PK PRIMARY KEY(ksp_id),
	CONSTRAINT pcl_FK FOREIGN KEY(pup_public_person_id) REFERENCES t_public_person(ksp_id)
	);
	*/
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int ksp_id;

	@NotNull
    private Date classification_date;
	
    @NotNull
    @ManyToOne
    private PublicPerson pup_public_person;

    @NotNull
    @Size(min=1, max=5)
    private int stability;

    @NotNull
    @Size(min=1, max=5)
    private int public_usefulness;
    
	@NotNull
    private boolean visible;
    
    //getters:
    public int getKsp_id() {
		return this.ksp_id;
	}
	public Date getClassification_date() {
        return this.classification_date;
    }
	public PublicPerson getPup_public_person() {
        return this.pup_public_person;
    }
	public int getStability() {
        return this.stability;
    }
	public int getPublic_usefulness() {
		return this.public_usefulness;
	}
	public boolean getVisible() {
		return this.visible;
	}
	

	//setters:
	public void setKsp_id(int ksp_id) {
		this.ksp_id = ksp_id;
	}
	public void setClassification_date(Date dt) {
        this.classification_date = dt;
    }
	public void setPup_public_person(PublicPerson pp) {
        this.pup_public_person = pp;
    }
	public void setStability(int stab) {
        this.stability = stab;
    }
	public void setPublic_usefulness(int usness) {
        this.public_usefulness = usness;
    }
	public void setVisible(boolean vis) {
		this.visible = vis;
	}


}
