package sk.stefan.MVP.model.entity.dao;



import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.interfaces.PresentationName;

public class VoteOfRole implements PresentationName {

	private static final String TN = "T_Vote_Of_Role";

	private Integer id;

	private Integer public_role_id;

	private Integer vote_id;

	private String decision;

	private Boolean visible;

	// getters:
	public Integer getId() {
		return this.id;
	}

	public Integer getPublic_role_id() {
		return this.public_role_id;
	}

	public Integer getVote_id() {
		return this.vote_id;
	}

	public String getDecision() {
		return this.decision;
	}

	public Boolean getVisible() {
		return this.visible;
	}

	public static String getTN() {
		return TN;
	}

	// setters:
	public void setId(Integer id) {
		this.id = id;
	}

	public void setPublic_role_id(Integer prid) {
		this.public_role_id = prid;
	}

	public void setVote_id(Integer votid) {
		this.vote_id = votid;
	}

	public void setDecision(String dec) {
		this.decision = dec;
	}

	public void setVisible(Boolean vis) {
		this.visible = vis;
	}

	@Override
	public String getPresentationName() {

		UniRepo<PublicRole> prRepo = new UniRepo<PublicRole>(PublicRole.class);
		UniRepo<PublicPerson> ppRepo = new UniRepo<PublicPerson>(
				PublicPerson.class);
		UniRepo<Vote> votRepo = new UniRepo<Vote>(Vote.class);

		if (public_role_id != null) {
			PublicRole pr = prRepo.findOne(public_role_id);
			PublicPerson pp = ppRepo.findOne(pr.getPublic_person_id());
			Vote vot = votRepo.findOne(vote_id);

			return pp.getPresentationName() + ", " + vot.getPresentationName();
		} else {
			return id + ", ";
		}

	}

}