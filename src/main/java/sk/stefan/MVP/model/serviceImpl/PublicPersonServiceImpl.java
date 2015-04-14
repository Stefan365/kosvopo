/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import sk.stefan.MVP.model.entity.dao.PublicPerson;
import sk.stefan.MVP.model.repo.dao.GeneralRepo;
import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.MVP.model.service.PublicPersonService;

/**
 *
 * @author stefan
 */
public class PublicPersonServiceImpl implements PublicPersonService {

    private final GeneralRepo generalRepo;
    private final UniRepo<PublicPerson> publicPersonRepo;

    public PublicPersonServiceImpl() {

        generalRepo = new GeneralRepo();
        publicPersonRepo = new UniRepo<>(PublicPerson.class);

    }


    @Override
    public List<PublicPerson> findAll() {
        return publicPersonRepo.findAll();
    }

    
    /**
     *
     * @param name
     * @return
     */
    @Override
    public List<Integer> findNewPublicPersonsIds(String name) {

        List<Integer> ppIds;

        String sql = "SELECT id FROM t_public_person WHERE first_name like '%" + name + "%'"
                + " OR last_name like '%" + name + "%' AND visible = true";
        ppIds = this.generalRepo.findIds(sql);

        return ppIds;

    }

    @Override
    public List<PublicPerson> findNewPublicPersons(List<Integer> ppIds) {
        
        List<PublicPerson> publicPersons = new ArrayList<>();

        for (Integer i : ppIds) {
            publicPersons.add(publicPersonRepo.findOne(i));
        }

        return publicPersons;

    }
    
}
