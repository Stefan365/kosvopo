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
public class Okres {
	/*
	-- 8.1.
	CREATE TABLE T_OKRES 
	(
	ksp_id INT(11) NOT NULL AUTO_INCREMENT, 
	okres_name VARCHAR(20) NOT NULL, 
	kraj_ido INT(11) NOT NULL, 

	CONSTRAINT okr_PK PRIMARY KEY (ksp_id),
	CONSTRAINT okr_FK FOREIGN KEY(kraj_ido) REFERENCES T_KRAJ(ksp_id)
	);
	*/
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int ksp_id;
	
    @NotNull
    @Size(min=2, max=20)
    private String okres_name;
    
    @NotNull
    @ManyToOne
    private Kraj kraj;
    
    @NotNull
    @OneToMany(mappedBy = "T_OKRES")
    private Set<Location> t_locations;

    
    //getters:
    public int getKsp_id() {
		return this.ksp_id;
	}
	public String getOkres_name() {
        return this.okres_name;
    }
	public Kraj getKraj() {
        return this.kraj;
    }
	

	//setters:
	public void setKsp_id(int ksp_id) {
		this.ksp_id = ksp_id;
	}
	public void setOkres_name(String on) {
        this.okres_name = on;
    }
	public void setKraj(Kraj kr) {
        this.kraj = kr;
    }	
}
