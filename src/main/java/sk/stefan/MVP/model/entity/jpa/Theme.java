package sk.stefan.MVP.model.entity.jpa;


import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Theme {
	/*
	-- 11.
	CREATE TABLE t_theme
	(
	ksp_id INT(11) NOT NULL AUTO_INCREMENT,
	brief_description VARCHAR(50) NOT NULL,
	description BLOB,
	visible INT(1) NOT NULL DEFAULT 1,
	
	CONSTRAINT the_PK PRIMARY KEY(ksp_id)
	);

	*/
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int ksp_id;
	
	@NotNull
    @Size(min=2, max=50)
    private String brief_description;
    
    @Size(min=2, max=5000)
    private String description;
	
    @NotNull
    private boolean visible;
    
    //getters:
    public int getKsp_id() {
		return this.ksp_id;
	}
	public String getBrief_description() {
        return this.brief_description;
    }
	public String getDescription() {
        return this.description;
    }
	public boolean getVisible() {
        return this.visible;
    }
	
	@NotNull
    @OneToMany(mappedBy = "T_THEME")
    private Set<Subject> t_subjects;


	//setters:
	public void setKsp_id(int ksp_id) {
		this.ksp_id = ksp_id;
	}
	public void setBrief_description(String brdes) {
        this.brief_description = brdes;
    }
	public void setDescription(String des) {
        this.description = des;
    }
	public void setVisible(boolean vis) {
		this.visible = vis;
	}


}
