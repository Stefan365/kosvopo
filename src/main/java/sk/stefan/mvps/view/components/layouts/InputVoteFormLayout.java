/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.view.components.layouts;

import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.Component;
import java.util.List;
import org.apache.log4j.Logger;
import sk.stefan.mvps.model.entity.PublicBody;
import sk.stefan.mvps.model.entity.Vote;
import sk.stefan.mvps.view.components.InputClassComboBox;
import sk.stefan.mvps.view.components.layouts.InputFormLayout;
import sk.stefan.utils.ToolsDao;

/**
 *
 * @author stefan
 * @param <E>
 */
public class InputVoteFormLayout<E> extends InputFormLayout<E> {

    private static final Logger log = Logger.getLogger(InputVoteFormLayout.class);
        
    private static final long serialVersionUID = 1L;

    private PublicBody pubBody;

    public InputVoteFormLayout(Class<E> clsE, Item item, SQLContainer sqlCont, 
            Component cp, String[] nEditFn, String[] crutialFn) {
        
        super(clsE, item, sqlCont, cp, nEditFn, crutialFn);

    }

    /**
     * Neuniverzalna metoda len pre potreby hlasovania. Tj. ked by sa do formulara malo
     * vlozit uz existujuce hlasovanie. v reale to vsak nenastava, pretoze VotingLyout sa
     * bude pouzivat len pre input, tj. Vote bude vzdy novy.
     * 
     *
     * @param vot
     */
    public void updateVote(Vote vot) {

        ToolsDao.updateVoteItem(this.getItem(), vot);

    }

    /**
     * Neuniverzalna metoda na filtrovanie subject_id po zadani publicBody
     * objektu. Potom to zuniverzalnit. Ta neuniverzalita vyplyva z poziadavku
     * vyplnat Vote a Role of Vote naraz. keby sa to nemuselo davat dohromady,
     * takto by som to neprznil.
     *
     * @param pubBody
     */
    private void filterSubject(PublicBody pb) {

        InputClassComboBox<?> combo = (InputClassComboBox<?>) this.getFieldMap().get("subject_id");

        ToolsDao.addFiltersToTouched(combo, PublicBody.TN, pb.getId());

    }

    public void setPublicBodyId(PublicBody pb) {

        this.pubBody = pb;
        filterSubject(pb);
    }

    public PublicBody getPubBody() {
        return pubBody;
    }

    public void setPubBody(PublicBody pubBody) {
        this.pubBody = pubBody;
    }

//    /**
//     * nastavi vysledky hlasovania z hlasovani jednotlivych poslancov
//     * @param res
//     */
//    @SuppressWarnings("unchecked")
//    public void setResults(List<Integer> res) {
//        
////        getItem().getItemProperty("for_vote").setValue(res.get(0));
////        getItem().getItemProperty("against_vote").setValue(res.get(1));
////        getItem().getItemProperty("refrain_vote").setValue(res.get(2));
////        getItem().getItemProperty("absent").setValue(res.get(3));
//        
////        getSqlContainer().refresh();
//    }
    
    /**
     * ziska aktualne id hlasovania, na kotorm je postaveny inputFormLayout
     * @return 
     */
    public Integer getVoteId(){
        
//        Item itema = getItem();
//        log.info("VOTE Itema: " + itema);
//        Integer vida = (Integer) getSqlContainer().getItem(getItemId()).getItemProperty("id").getValue();
//        log.info("VOTE IDA: " + vida);
//        
//        
        Integer vid = (Integer) getItem().getItemProperty("id").getValue();
        
//        log.info("VOTE ID: " + vid);
        
        
        
        return vid;
    }
    
    

}
