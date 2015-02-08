package sk.stefan.MVP.model.entity.jpa;


import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class User implements Serializable {
	/*
	 * CREATE TABLE t_user ( ksp_id INT(11) NOT NULL AUTO_INCREMENT, first_name
	 * VARCHAR(20), last_name VARCHAR(20), e_mail VARCHAR(50), login
	 * VARCHAR(50), password VARCHAR(50),
	 * 
	 * CONSTRAINT usr_PK PRIMARY KEY (ksp_id) );
	 */
	private static final long serialVersionUID = -1520923107014804137L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int ksp_id;
	
    @NotNull
    @Size(min=2, max=20)
	private String first_name;
    
    @NotNull
    @Size(min=2, max=20)
    private String last_name;

    private String e_mail;
    
    @NotNull
    @Size(min=5, max=50)
    private String login;
    
    @NotNull
    @Size(min=8, max=50)
    private String password;

    @NotNull
    @OneToMany(mappedBy = "T_USER")
    private Set<UserRole> t_user_roles;
    

	public User(String fn, String ln, String em, String lg, String pw) {
		this.first_name = fn;
		this.last_name = ln;
		this.e_mail = em;
		this.login = lg;
		this.password = pw;
	}

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
	public String getE_mail() {
		return this.e_mail;
	}
	public String getLogin() {
		return this.login;
	}
	public String getPassword() {
		return this.password;
	}
	
	//setters:
	public void setKsp_id(int ksp_id) {
		this.ksp_id = ksp_id;
	}
	
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public void setLarst_name(String last_name) {
		this.last_name = last_name;
	}
	public void setE_mail(String e_mail) {
		this.e_mail = e_mail;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
