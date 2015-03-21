/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import sk.stefan.MVP.model.entity.dao.PublicBody;
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.entity.dao.Tenure;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.MVP.view.components.NavigationComponent;
import sk.stefan.MVP.view.components.hlasovanie.PritomniLayout;
import sk.stefan.utils.Tools;

/**
 *
 * @author stefan
 */
public class VotingView extends VerticalLayout implements View {

    private static final long serialVersionUID = 1L;

    private final PritomniLayout pritomniLy;

    private final List<PublicRole> hlasujuci;

//    @Autowired
    private final UniRepo<PublicRole> prRepo;

//    @Autowired
    private final UniRepo<Tenure> tenureRepo;
    
//    @Autowired
    private final UniRepo<PublicBody> pbRepo;
    
//    @Autowired
    private final UniRepo<Vote> voteRepo;
    
    

    private final PublicBody pubBody;
    
    private final Navigator navigator;

    //0. konstruktor
    /**
     * @param nav
     */
    public VotingView(Navigator nav) {
        
        this.setSpacing(true);
        this.setMargin(true);
        
        this.navigator = nav;
        
        pbRepo = new UniRepo<>(PublicBody.class);
        pubBody = pbRepo.findOne(5);
                
        prRepo = new UniRepo<>(PublicRole.class);
        tenureRepo = new UniRepo<>(Tenure.class);
            
        hlasujuci = new ArrayList<>();
        
        List<PublicRole> hlasciAll
                = this.prRepo.findByParam("public_body_id", "" + pubBody.getId());
        
        voteRepo = new UniRepo<>(Vote.class);
        Vote hlas = voteRepo.findOne(4);
        
        pritomniLy = new PritomniLayout(pubBody, hlas, navigator);
        
        this.addComponent(pritomniLy);
        
        Boolean isValid;
//        
//        
        for (PublicRole pr: hlasciAll){
            
            isValid = Tools.isActual(pr.getTenure_id());
            if (isValid){
                hlasujuci.add(pr);
            }
        }
        
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        this.addComponent(NavigationComponent.getNavComp());
        
        //do nothing
    }

}
