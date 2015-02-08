package sk.stefan.MVP.model.entity.jpa;


import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Subject {
	/*
	-- 12. VOTING SUBJECT
	CREATE TABLE t_subject
	(
	ksp_id INT(11) NOT NULL AUTO_INCREMENT,
	brief_description VARCHAR(50) NOT NULL,
	description VARCHAR(5000),
	public_role_id INT(11) NOT NULL,
	the_theme_id INT(11),
	visible INT(1) NOT NULL DEFAULT 1,
	
	CONSTRAINT sub_PK PRIMARY KEY(ksp_id),
	CONSTRAINT sub_FK1 FOREIGN KEY(public_role_id) REFERENCES t_public_role(ksp_id),
	CONSTRAINT sub_FK2 FOREIGN KEY(the_theme_id) REFERENCES t_theme(ksp_id)
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
    @ManyToOne
    private PublicRole public_role;
    
    @NotNull
    @ManyToOne
    private Theme theme;
    	
    @NotNull
    private boolean visible;
    
    @NotNull
    @OneToMany(mappedBy = "T_SUBJECT")
    private Set<Vote> t_votes;

	@NotNull
    @OneToMany(mappedBy = "T_SUBJECT")
    private Set<ActClassification> t_act_classifications;

    
    
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
	public PublicRole getPublic_role() {
        return this.public_role;
    }
	public Theme getTheme() {
        return this.theme;
    }
	public boolean getVisible() {
        return this.visible;
    }

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
	public void setPublic_role(PublicRole pr) {
        this.public_role = pr;
    }
	public void getTheme(Theme th) {
        this.theme = th;
    }
	public void setVisible(boolean vis) {
		this.visible = vis;
	}


}
