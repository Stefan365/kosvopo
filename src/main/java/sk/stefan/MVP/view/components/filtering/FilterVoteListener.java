/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components.filtering;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.PublicBody;
import sk.stefan.MVP.model.entity.Vote;
import sk.stefan.MVP.model.service.PublicBodyService;
import sk.stefan.MVP.model.serviceImpl.PublicBodyServiceImpl;
import sk.stefan.MVP.view.components.vote.PritomniLayout;
import sk.stefan.MVP.view.components.layouts.InputVoteFormLayout;
import sk.stefan.MVP.view.components.layouts.InputVoteComplexLayout;

/**
 *
 * @author stefan
 */
public class FilterVoteListener implements ValueChangeListener {

    private static final long serialVersionUID = 1L;
    
    private static final Logger log = Logger.getLogger(FilterVoteListener.class);

    
    private final InputVoteFormLayout<Vote> voteInputLy;
    private final PritomniLayout pritomniLy;
    private final InputVoteComplexLayout votingLy;

    private final PublicBodyService publicBodyService;
    
    //0. konstruktor
    /**
     *
     * @param vinLy
     * @param pLy
     * @param vLy
     */
    public FilterVoteListener(InputVoteFormLayout<Vote> vinLy, 
            PritomniLayout pLy, InputVoteComplexLayout vLy) {
        
        this.voteInputLy = vinLy;
        this.pritomniLy = pLy;
        this.votingLy = vLy;

        this.publicBodyService = new PublicBodyServiceImpl();
    
        
    }

    @Override
    public void valueChange(ValueChangeEvent event) {

        Object o = event.getProperty().getValue();

        Integer pbId = (Integer) o;
        
        if (pbId != null) {
            
            PublicBody pb = publicBodyService.findOne(pbId);
        
            voteInputLy.setPublicBodyId(pb);
            
            
            pritomniLy.setEntities(pb, null);
            
            votingLy.setPubBody(pb);
            
            pritomniLy.setEnabled(true);
            
        }
    }


}
