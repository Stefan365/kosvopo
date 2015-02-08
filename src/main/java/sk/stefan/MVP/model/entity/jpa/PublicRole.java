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
public class PublicRole {
	/*
	-- 10.
	CREATE TABLE t_public_role
	(
	ksp_id INT(11) NOT NULL AUTO_INCREMENT,s
	pub_public_body_id INT(11) NOT NULL,
	ten_tenure_id INT(11) NOT NULL,
	pup_public_person_id INT(11) NOT NULL,
	name VARCHAR(50) NOT NULL,
	visible INT(1) NOT NULL DEFAULT 1,
	
	CONSTRAINT pur_PK PRIMARY KEY(ksp_id),
	CONSTRAINT pur_FK1 FOREIGN KEY(pub_public_body_id) REFERENCES t_public_body(ksp_id),
	CONSTRAINT pur_FK2 FOREIGN KEY(ten_tenure_id) REFERENCES t_tenure(ksp_id),
	CONSTRAINT pur_FK3 FOREIGN KEY(pup_public_person_id) REFERENCES t_public_person(ksp_id)
	);
	*/

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int ksp_id;
	
	@NotNull
    @ManyToOne
    private PublicBody public_body;

	@NotNull
    @ManyToOne
    private Tenure tenure;
	
	@NotNull
    @ManyToOne
    private PublicPerson public_person;

    @NotNull
    @Size(min=2, max=50)
    private String name;
    
    @NotNull
    private boolean visible;
	
    //getters:
    public int getKsp_id() {
		return this.ksp_id;
	}
	public PublicBody getPublic_body() {
        return this.public_body;
    }
	public Tenure getTenure() {
        return this.tenure;
    }
	public PublicPerson getPublic_person() {
        return this.public_person;
    }
	
	@NotNull
    @OneToMany(mappedBy = "T_PUBLIC_ROLE")
    private Set<Subject> t_subjects;

	@NotNull
    @OneToMany(mappedBy = "T_PUBLIC_ROLE")
    private Set<VoteOfRole> t_votes_of_role;

	//setters:
	public void setKsp_id(int ksp_id) {
		this.ksp_id = ksp_id;
	}
	public void setPublic_body(PublicBody pb) {
        this.public_body = pb;
    }
	public void setTenure(Tenure ten) {
        this.tenure = ten;
    }
	public void setPublic_person(PublicPerson pp) {
        this.public_person = pp;
    }	
	public void setVisible(boolean vis) {
		this.visible = vis;
	}
	
}
