/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components.hlasovanie;

import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.Component;
import java.util.List;
import sk.stefan.MVP.model.entity.dao.PublicBody;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.MVP.view.components.InputComboBox;
import sk.stefan.MVP.view.components.InputFormLayout;
import sk.stefan.utils.ToolsDao;

/**
 *
 * @author stefan
 * @param <T>
 */
public class VoteInputFormLayout<T> extends InputFormLayout<T> {
    
    private static final long serialVersionUID = 1L;

    public VoteInputFormLayout(Class<?> clsT, Item item, SQLContainer sqlCont, Component cp, List<String> nEditFn) {
        super(clsT, item, sqlCont, cp, nEditFn);
        
        
    }
    
    
        /**
     * Neuniverzalna metoda len pre potreby hlasovania. 
     * zuniverzalnit v buducnosti.
     * 
     * @param vot
     */
    public void updateVote(Vote vot){
        
        ToolsDao.updateVoteItem(this.getItem(), vot);
        
    }
    
    /**
     * Neuniverzalna metoda na filtrovanie subject_id po zadani publicBody objektu.
     * Potom to zuniverzalnit.
     * Ta neuniverzalita vyplyva z poziadavku vyplnat Vote a Role of Vote naraz.
     * keby sa to nemuselo davat dohromady, takto by som to neprznil.
     * @param pubBody
     */
    public void filterSubject(PublicBody pubBody){
        
        @SuppressWarnings("unchecked")
        InputComboBox<Integer> combo = (InputComboBox<Integer>)this.getFieldMap().get("subject_id");

        
    }

    
    
}
