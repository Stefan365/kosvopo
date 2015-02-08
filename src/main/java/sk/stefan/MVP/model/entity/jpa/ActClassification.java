
package sk.stefan.MVP.model.entity.jpa;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class ActClassification {
	/*
	-- 15.
	CREATE TABLE t_act_classification
	(
	ksp_id INT(11) NOT NULL AUTO_INCREMENT,
	vote_of_role_id  INT(11),
	subject_id  INT(11),
	zhoda_s_programom INT(2) NOT NULL,
	public_malignity INT(2) NOT NULL,
	visible INT(1) NOT NULL DEFAULT 1,
	
	CONSTRAINT acl_PK PRIMARY KEY(ksp_id),
	CONSTRAINT acl_FK1 FOREIGN KEY(vote_of_role_id) REFERENCES t_vote_of_role(ksp_id),
	CONSTRAINT acl_FK2 FOREIGN KEY(subject_id) REFERENCES t_subject(ksp_id),
	CONSTRAINT acl_CHK CHECK ((vote_of_role_id IS NOT NULL AND subject_id IS NULL) OR 
	                          (vote_of_role_id IS NULL AND subject_id IS NOT NULL)),
	CONSTRAINT acl_UN UNIQUE(vote_of_role_id, subject_id)
	);

	*/
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int ksp_id;
	
	@NotNull
    @ManyToOne
    private VoteOfRole vote_of_role;
	
	@NotNull
    @ManyToOne
    private Subject subject;
	
	@NotNull
	@Size(min=1, max=5)
	private int zhoda_s_programom;

	@NotNull
	@Size(min=1, max=5)
	private int public_malignity;

	@NotNull
    private boolean visible;

	
	
	//getters:
	public int getKsp_id() {
		return this.ksp_id;
	}
	public VoteOfRole getVote_of_role() {
		return this.vote_of_role;
	}
	public Subject getSubject() {
		return this.subject;
	}
	public int getZhoda_s_programom() {
		return this.zhoda_s_programom;
	}
	public int getPublic_malignity() {
		return this.public_malignity;
	}
	public boolean getVisible() {
		return this.visible;
	}
	
	
	//setters:i
	public void setKsp_id(int id) {
		this.ksp_id = id;
	}
	public void setVote_of_role(VoteOfRole vor) {
		this.vote_of_role = vor;
	}
	public void setSubject(Subject sub) {
		this.subject = sub;
	}
	public void setZhoda_s_programom(int zp) {
		this.zhoda_s_programom = zp;
	}
	public void getPublic_malignity(int pm) {
		this.public_malignity = pm;
	}
	public void setVisible(boolean vis) {
		this.visible = vis;
	}


}
