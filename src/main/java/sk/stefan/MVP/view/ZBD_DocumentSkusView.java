/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.HorizontalLayout;
import sk.stefan.MVP.model.entity.Vote;
import sk.stefan.MVP.model.repo.UniRepo;
import sk.stefan.MVP.view.components.documents.DownloaderLayout;
import sk.stefan.MVP.view.components.documents.UploaderLayout;

/**
 *
 * @author stefan
 */
public class ZBD_DocumentSkusView extends HorizontalLayout implements View {
    
    private static final long serialVersionUID = 1L;

    private final Vote hlasovanie;
    private final UniRepo<Vote> voteRepo;
    private final UploaderLayout<Vote> upLy;
    private final DownloaderLayout<Vote> dwLy;
    
    public ZBD_DocumentSkusView(){
        
        this.voteRepo = new UniRepo<>(Vote.class);
        this.hlasovanie = voteRepo.findOne(2);
        this.upLy = new UploaderLayout<>(Vote.class, null);
        this.dwLy = new DownloaderLayout<>(Vote.class, null);
        this.upLy.setEntity(hlasovanie);
        this.dwLy.setEntity(hlasovanie);
        
        this.addComponents(upLy, dwLy);
        
    }
    
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        
    }
    
}