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
public class Location {
	/*
	-- 8.2.
	CREATE TABLE t_location 
	(
	ksp_id INT(11) NOT NULL AUTO_INCREMENT, 
	obec_name VARCHAR(50) NOT NULL, 
	mestka_cast VARCHAR(50), 
	okres_ido INT(11) NOT NULL, 
	visible INT(1) NOT NULL DEFAULT 1,
	
	CONSTRAINT loc_PK PRIMARY KEY (ksp_id),
	CONSTRAINT loc_FK FOREIGN KEY(okres_ido) REFERENCES T_OKRES(ksp_id),
	CONSTRAINT loc_UN UNIQUE(obec_name, mestka_cast)
	);
	*/
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int ksp_id;
	
    @NotNull
    @Size(min=2, max=50)
    private String obec_name;
    
    @Size(min=2, max=50)
    private String mestka_cast;
    
    @NotNull
    @ManyToOne
    private Okres okres;
    
	@NotNull
    private boolean visible;
	
    @NotNull
    @OneToMany(mappedBy = "T_LOCATION")
    private Set<PublicBody> t_public_bodies;
	
    //getters:
    public int getKsp_id() {
		return this.ksp_id;
	}
	public String getObec_name() {
        return this.obec_name;
    }
	public String getMestka_cast() {
        return this.mestka_cast;
    }
	public Okres getOkres() {
        return this.okres;
    }
	

	//setters:
	public void setKsp_id(int ksp_id) {
		this.ksp_id = ksp_id;
	}
	public void setObec_name(String obn) {
        this.obec_name = obn;
    }
	public void setMestka_cast(String mc) {
        this.mestka_cast = mc;
    }
	public void setOkres(Okres okr) {
        this.okres = okr;
    }	
	public void setVisible(boolean vis) {
		this.visible = vis;
	}

}
