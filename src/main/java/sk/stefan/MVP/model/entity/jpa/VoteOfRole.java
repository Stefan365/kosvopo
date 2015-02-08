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
public class VoteOfRole {
	/*
	-- 14.
	CREATE TABLE t_vote_of_role
	(
	ksp_id INT(11) NOT NULL AUTO_INCREMENT,
	public_role_id INT(11) NOT NULL,
	vote_id INT(11) NOT NULL,
	decision VARCHAR(10) NOT NULL,
	visible INT(1) NOT NULL DEFAULT 1,
	
	CONSTRAINT vor_PK PRIMARY KEY(ksp_id),
	CONSTRAINT vor_FK1 FOREIGN KEY(public_role_id) REFERENCES t_public_role(ksp_id),
	CONSTRAINT vor_FK2 FOREIGN KEY(vote_id) REFERENCES t_vote(ksp_id),
	CONSTRAINT vor_UN UNIQUE(public_role_id, vote_id)
	);	
	 */
	
	//private static final long serialVersionUID = -1520923107014804137L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int ksp_id;
	
	@NotNull
    @ManyToOne
    private PublicRole public_role;
	
	@NotNull
    @ManyToOne
    private Vote vote;
	
	@NotNull
	@Size(min=2, max=10)
	private String decision;

	@NotNull
    private boolean visible;

	
	@NotNull
    @OneToMany(mappedBy = "T_VOTE_OF_ROLE")
    private Set<ActClassification> t_act_classifications;

    
	//getters:
	public int getKsp_id() {
		return this.ksp_id;
	}
	public PublicRole getPublic_role() {
		return this.public_role;
	}
	public Vote getVote() {
		return this.vote;
	}
	public String getDecision_nr() {
		return this.decision;
	}
	public boolean getVisible() {
		return this.visible;
	}
	
	
	//setters:
	public void setKsp_id(int id) {
		this.ksp_id = id;
	}
	public void setPublic_role(PublicRole pr) {
		this.public_role = pr;
	}
	public void setVote(Vote vot) {
		this.vote = vot;
	}
	public void setDecision(String dec) {
		this.decision = dec;
	}
	public void setVisible(boolean vis) {
		this.visible = vis;
	}
}
