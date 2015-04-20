/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import sk.stefan.MVP.model.entity.dao.PersonClassification;
import sk.stefan.MVP.model.entity.dao.PublicPerson;
import sk.stefan.MVP.model.repo.dao.GeneralRepo;
import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.MVP.model.service.ClassificationService;

/**
 *
 * @author stefan
 */
public class ClassificationServiceImpl implements ClassificationService {
    
    
    private final UniRepo<PersonClassification> classRepo;
    private final GeneralRepo generalRepo;
    
    //0.konstruktor
    /**
     */
    public ClassificationServiceImpl(){
        
        classRepo = new UniRepo<>(PersonClassification.class) ;
        generalRepo = new GeneralRepo();
    
    } 


    @Override
    public List<PersonClassification> findNewPersonClass(List<Integer> pclIds) {

        List<PersonClassification> personClass = new ArrayList<>();

        for (Integer i : pclIds) {
            personClass.add(classRepo.findOne(i));
        }

        return personClass;

    }

    @Override
    public List<Integer> findActualPersonClassIds(Integer ppId) {
        
        
        List<Integer> pclIds;

        String sql = "SELECT id FROM t_person_classification WHERE public_person_id = " + ppId 
                +" AND actual = true AND visible = true";
        pclIds = this.generalRepo.findIds(sql);

        return pclIds;

        
    }

    
}
