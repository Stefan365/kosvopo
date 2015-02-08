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
public class Role {
	/*
-- 2.
CREATE TABLE t_role 
(
ksp_id INT(11) NOT NULL AUTO_INCREMENT, 
role_name VARCHAR(50), 
rights_description VARCHAR(500),

CONSTRAINT rol_PK PRIMARY KEY (ksp_id)
); 

	*/
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int ksp_id;
	
    @NotNull
    @Size(min=2, max=50)
	private String role_name;
    
    @Size(min=2, max=500)
	private String rights_description;
    
    @NotNull
    @OneToMany(mappedBy = "T_ROLE")
    private Set<UserRole> t_user_roles;
    
	//getters:
  	public int getKsp_id() {
  		return this.ksp_id;
  	}
  	
	public String getRole_name() {
		return this.role_name;
	}

	public String getRights_description() {
		return this.rights_description;
	}
	
	
	//setters:
	public void setKsp_id(int ksp_id) {
		this.ksp_id = ksp_id;
	}
	
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public void setRights_description(String rights_description) {
		this.rights_description = rights_description;
	}
	
	
}
