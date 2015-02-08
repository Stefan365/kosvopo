package sk.stefan.MVP.model.service;

import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

import sk.stefan.MVP.model.entity.dao.PublicPerson;
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.entity.dao.Tenure;
import sk.stefan.MVP.model.repo.dao.UniRepo;

public class PublicRoleService {

	private UniRepo<PublicRole> publicRoleRepo;
	private UniRepo<Tenure> tenureRepo;

	public PublicRoleService() {

		publicRoleRepo = new UniRepo<PublicRole>(PublicRole.class);
		tenureRepo = new UniRepo<Tenure>(Tenure.class);

	}

	/**
	 * Get All public roles for public person (historical included)
	 */
	public List<PublicRole> getAllPublicRolesOfPublicPerson(PublicPerson pp) {
		if (pp == null){
			return new ArrayList<>();
		}
		return publicRoleRepo.findByParam("public_person_id", "" + pp.getId());
	
	}

	/**
	 * Get actual public roles for public person
	 * 
	 */
	public List<PublicRole> getActualPublicRolesOfPublicPerson(PublicPerson pp) {

		Tenure ten;
		Date dSince;
		Date dTill;

		List<PublicRole> lprAct = new ArrayList<>();
		List<PublicRole> lpr = this.getAllPublicRolesOfPublicPerson(pp);
		// actual date
		java.util.Date ad = new java.util.Date();
		java.sql.Date sad = new java.sql.Date(ad.getTime()); // actual date in
																// sql mode

		for (PublicRole pr : lpr) {
			Integer tid = pr.getTenure_id();
			ten = tenureRepo.findOne(tid);
			dSince = ten.getSince();
			dTill = ten.getTill();
			if ((sad.compareTo(dSince) == 1 && ((dTill == null) || dTill
					.compareTo(sad) == 1))) {
				lprAct.add(pr);
			}

		}

		return lprAct;
	}

}
