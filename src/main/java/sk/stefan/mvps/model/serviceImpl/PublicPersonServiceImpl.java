/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.model.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import sk.stefan.mvps.model.entity.PublicPerson;
import sk.stefan.mvps.model.repo.GeneralRepo;
import sk.stefan.mvps.model.repo.UniRepo;
import sk.stefan.mvps.model.service.PublicPersonService;

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
    public List<Integer> findPublicPersonsIdsByFilter(String name) {

        List<Integer> ppIds;

        String sql = "SELECT id FROM t_public_person WHERE (first_name like '%" + name + "%'"
                + " OR last_name like '%" + name + "%') AND visible = true";
        
        ppIds = this.generalRepo.findIds(sql);
        
        return ppIds;

    }

    @Override
    public List<PublicPerson> findPublicPersons(List<Integer> ppIds) {
        
        List<PublicPerson> publicPersons = new ArrayList<>();

        for (Integer i : ppIds) {
            publicPersons.add(publicPersonRepo.findOne(i));
        }

        return publicPersons;

    }

    @Override
    public PublicPerson findOne(Integer ppId) {
        
        return publicPersonRepo.findOne(ppId);
    }
    
}
