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
public class PublicBody {
	
	/*
	-- 9.
	CREATE TABLE t_public_body
	(
	ksp_id INT(11) NOT NULL AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	loc_location_id INT(11) NOT NULL,
	visible INT(1) NOT NULL DEFAULT 1,
	
	CONSTRAINT pub_PK PRIMARY KEY(ksp_id),
	CONSTRAINT pub_FK FOREIGN KEY(loc_location_id) REFERENCES t_location(ksp_id)
	);
	*/
    
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int ksp_id;
	
    @NotNull
    @Size(min=2, max=50)
    private String name;
    
    @NotNull
    @ManyToOne
    private Location location;
    
    @NotNull
    private boolean visible;
	
    @NotNull
    @OneToMany(mappedBy = "T_PUBLIC_BODY")
    private Set<PublicRole> t_public_roles;
    
    @NotNull
    @OneToMany(mappedBy = "T_PUBLIC_BODY")
    private Set<Vote> t_votes;
	
    //getters:
    public int getKsp_id() {
		return this.ksp_id;
	}
	public String getNname() {
        return this.name;
    }
	public Location getLocation() {
        return this.location;
    }
	public boolean getVisible() {
        return this.visible;
    }
	

	//setters:
	public void setKsp_id(int ksp_id) {
		this.ksp_id = ksp_id;
	}
	public void setName(String nam) {
        this.name = nam;
    }
	public void setLocation(Location loc) {
        this.location = loc;
    }
	public void setVisible(boolean vis) {
        this.visible = vis;
    }	
	
}
