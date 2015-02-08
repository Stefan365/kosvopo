package sk.stefan.MVP.model.entity.dao;


import sk.stefan.interfaces.PresentationName;

import java.io.Serializable;


public class User implements Serializable, PresentationName {

	private static final long serialVersionUID = 1L;

	private static final String TN = "T_User";
	
	private Integer id;
	
	private String first_name;
    
    private String last_name;

    private String e_mail;
    
    private String login;
    
    private String password;


	public User(String fn, String ln, String em, String lg, String pw) {
		this.first_name = fn;
		this.last_name = ln;
		this.e_mail = em;
		this.login = lg;
		this.password = pw;
	}

	public User() {
	}

	//getters:
	public Integer getId() {
		return this.id;
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
	public static String getTN() {
        return TN;
    }
	
	
	//setters:
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public void setLast_name(String last_name) {
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

	@Override
	public String getPresentationName() {
		
		return "USER: " + this.first_name + " " + this.last_name;
	}

}
