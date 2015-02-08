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
public class Kraj {
	/*
	-- 8.0.
	CREATE TABLE T_KRAJ 
	(
	ksp_id INT(11) NOT NULL AUTO_INCREMENT, 
	kraj_name VARCHAR(20) NOT NULL, 
	
	CONSTRAINT kra_PK PRIMARY KEY (ksp_id)
	);

	*/
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int ksp_id;
	
    @NotNull
    @Size(min=2, max=20)
    private String kraj_name;
    
    @NotNull
    @OneToMany(mappedBy = "T_KRAJ")
    private Set<Okres> t_okreses;
    
    //getters:
    public int getKsp_id() {
		return this.ksp_id;
	}
	public String getKraj_name() {
        return this.kraj_name;
    }
    public Set<Okres> getT_okreses() {
        return this.getT_okreses();
    }

	

	//setters:
	public void setKsp_id(int ksp_id) {
		this.ksp_id = ksp_id;
	}
	public void setKraj_name(String kn) {
        this.kraj_name = kn;
    }
    public void setT_okreses(Set<Okres> okr) {
        this.t_okreses = okr;
    }

	
}
