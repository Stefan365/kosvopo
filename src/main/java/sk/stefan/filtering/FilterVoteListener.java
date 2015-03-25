/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.filtering;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.dao.PublicBody;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.MVP.view.components.hlasovanie.PritomniLayout;
import sk.stefan.MVP.view.components.hlasovanie.InputVoteFormLayout;
import sk.stefan.MVP.view.components.hlasovanie.VotingLayout;

/**
 *
 * @author stefan
 */
public class FilterVoteListener implements ValueChangeListener {

    private static final long serialVersionUID = 1L;
    
    private static final Logger log = Logger.getLogger(FilterVoteListener.class);

    
    private final InputVoteFormLayout<Vote> voteInputLy;
    private final PritomniLayout pritomniLy;
    private final VotingLayout votingLy;

    private final UniRepo<PublicBody> pbRepo;
    
    //0. konstruktor
    /**
     *
     * @param vinLy
     * @param pLy
     */
    public FilterVoteListener(InputVoteFormLayout<Vote> vinLy, PritomniLayout pLy, VotingLayout vLy) {
        
        this.voteInputLy = vinLy;
        this.pritomniLy = pLy;
        this.votingLy = vLy;
        
        this.pbRepo = new UniRepo<>(PublicBody.class);
    
        
    }

    @Override
    public void valueChange(ValueChangeEvent event) {

        Object o = event.getProperty().getValue();

        Integer pbId = (Integer) o;
        
        if (pbId != null) {
            
            PublicBody pb = pbRepo.findOne(pbId);
        
            voteInputLy.setPublicBodyId(pb);
            
//            log.info("KAMO SOM TU:" + (pb == null) + ":");
            
            pritomniLy.setEntities(pb, null);
            
            votingLy.setPubBody(pb);
            
            pritomniLy.setEnabled(true);
            
        }
    }


}
