package sk.stefan.MVP.model.entity.jpa;


import java.sql.Date;
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
public class Vote {
	/*
		-- 13. VOTE
	CREATE TABLE t_vote
	(
	ksp_id INT(11) NOT NULL AUTO_INCREMENT,
	vote_date DATE NOT NULL,
	public_body_id INT(11) NOT NULL,
	subject_id INT(11) NOT NULL,
	internal_nr VARCHAR(30),
	result_vote VARCHAR(10) NOT NULL, 
	for_vote INT(3) NOT NULL,
	against_vote INT(3) NOT NULL,
	refrain_vote INT(3) NOT NULL,
	absent INT(11) NOT NULL,
	visible INT(11) NOT NULL DEFAULT 1,
	
	CONSTRAINT vot_PK PRIMARY KEY(ksp_id),
	CONSTRAINT vot_FK1 FOREIGN KEY(public_body_id) REFERENCES t_public_body(ksp_id),
	CONSTRAINT vot_FK2 FOREIGN KEY(subject_id) REFERENCES t_subject(ksp_id)
	);
	 */
	
	//private static final long serialVersionUID = -1520923107014804137L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int ksp_id;
	
	@NotNull
	private Date vote_date;
	
	@NotNull
    @ManyToOne
    private PublicBody public_body;
	
	@NotNull
    @ManyToOne
    private Subject subject;
	
	@Size(min=2, max=30)
	private String internal_nr;

	@NotNull
	@Size(min=2, max=10)
	private String result_vote;

	@NotNull
	private int for_vote;
	
	@NotNull
	private int against_vote;
	
	@NotNull
	private int refrain_vote;

	@NotNull
	private int absent;

    @NotNull
    private boolean visible;
    
	@NotNull
    @OneToMany(mappedBy = "T_VOTE")
    private Set<VoteOfRole> t_votes_of_role;


	//getters:
	public int getKsp_id() {
		return this.ksp_id;
	}
	public Date getVote_date() {
		return this.vote_date;
	}
	public PublicBody getPublic_body() {
		return this.public_body;
	}
	public Subject getSubject() {
		return this.subject;
	}
	public String getInternal_nr() {
		return this.internal_nr;
	}
	public String getResult_vote() {
		return this.result_vote;
	}
	public int getFor_vote() {
		return this.for_vote;
	}
	public int getAgainst_vote() {
		return this.against_vote;
	}
	public int getAbsent() {
		return this.absent;
	}
	public boolean getVisible() {
		return this.visible;
	}
	
	
	//setters:
	public void setKsp_id(int id) {
		this.ksp_id = id;
	}
	public void setVote_date(Date date) {
		this.vote_date = date;
	}
	public void setPublic_body(PublicBody pb) {
		this.public_body = pb;
	}
	public void setSubject_id(Subject sub) {
		this.subject = sub;
	}
	public void setInternal_nr(String inr) {
		this.internal_nr = inr;
	}
	public void setResult_vote(String rv) {
		this.result_vote = rv;
	}
	public void setFor_vote(int fv) {
		this.for_vote = fv;
	}
	public void setAgainst_vote(int av) {
		this.against_vote = av;
	}
	public void setAbsent(int a) {
		this.absent = a;
	}
	public void setVisible(boolean vis) {
		this.visible = vis;
	}

}
