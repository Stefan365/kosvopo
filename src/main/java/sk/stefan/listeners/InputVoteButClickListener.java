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
import sk.stefan.MVP.view.components.hlasovanie.VotingLayout;

/**
 *
 * @author stefan
 */
@SuppressWarnings("serial")
public class InputVoteButClickListener implements ClickListener {

    private static final Logger log = Logger.getLogger(InputButClickListener.class);

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
