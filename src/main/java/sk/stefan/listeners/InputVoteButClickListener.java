/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.listeners;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.UI;
import org.apache.log4j.Logger;
import sk.stefan.MVP.view.components.UniDlg;
import sk.stefan.MVP.view.components.vote.VotingLayout;

/**
 * Specialny listener pre tlacitko vlozenia Hlasovania, pretoze v tomto secialnom 
 * pripade je komponenta zlozena ako z formulara pre samotne hlasovanie, tak i podformulara 
 * s hlasovanim jednotlivych osob, vid obrazok z okazky v texte prace.
 *
 * @author stefan
 */
@SuppressWarnings("serial")
public class InputVoteButClickListener implements ClickListener {

    private static final Logger log = Logger.getLogger(InputVoteButClickListener.class);

    private final String title;
    private VotingLayout votingLy;
    private UniDlg tdlg;

    public InputVoteButClickListener(String title) {

        this.title = title;

    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        
        this.votingLy = new VotingLayout(null);

        tdlg = new UniDlg("Nový " + title, votingLy);
        tdlg.setSizeFull();
        
        UI.getCurrent().addWindow(tdlg);

    }

}
