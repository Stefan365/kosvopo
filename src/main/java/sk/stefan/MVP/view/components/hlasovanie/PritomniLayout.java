/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.view.components.hlasovanie;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.dao.PublicBody;
import sk.stefan.MVP.model.entity.dao.PublicRole;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.MVP.model.entity.dao.VoteOfRole;
import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.MVP.view.components.InputFormLayout;
import sk.stefan.listeners.OkCancelListener;
import sk.stefan.utils.Tools;

/**
 *
 * @author stefan
 */
public class PritomniLayout extends VerticalLayout implements OkCancelListener {
    
   private static final Logger log = Logger.getLogger(PritomniLayout.class);
    
    private static final long serialVersionUID = 1L;
    
    private final Navigator navigator;
    
    private List<PritomnyComponent> pritomni;
    
    private final PublicBody verOrg;
    
    private final Vote hlasovanie;
    
    private List<PublicRole> prActual;
    
    private Button saveBt;
    
    private Button cancelBt;
    
    private HorizontalLayout buttonsLayout;
        
    private final UniRepo<VoteOfRole> vorRepo;
    
    //0. konstruktor.
    /**
     * 
     * @param pb
     * @param hlas
     * @param nav
     */
    public PritomniLayout(PublicBody pb, Vote hlas, Navigator nav){
        
        //dostat to z Vote by bolo zdlhave.
        this.verOrg = pb;
        this.hlasovanie = hlas;
        this.navigator =nav;
        
        this.vorRepo = new UniRepo<>(VoteOfRole.class);
                
        this.initLayout();
        
    }
    
    /**
     * Vytvori layout a naplni ho prislusnymi komponentami.  
     */
    private void initLayout(){
        
        prActual = Tools.getActualPublicRoles(verOrg);
        pritomni = new ArrayList<>();
        
        PritomnyComponent prComp;
        
        log.info("INITLAYOUT PRITOMNI:" + prActual.size());
        for (PublicRole pr : prActual){
////            prComp = new PritomnyComponent(pr, hlasovanie);
//            this.pritomni.add(prComp);
//            this.addComponent(prComp);
        }
        
        this.initButtons();
        
        buttonsLayout.setMargin(true);
        buttonsLayout.setSpacing(true);
        this.addComponent(buttonsLayout);
    }
    
    private void initButtons(){
        
        buttonsLayout = new HorizontalLayout();
        
        cancelBt = new Button("zrušiť");
        
        saveBt = new Button("uložiť", (Button.ClickEvent event) -> {
            doOkAction();
        });
        
        cancelBt = new Button("zrušiť", (Button.ClickEvent event) -> {
            doCancelAction();
        });
        
        buttonsLayout.addComponents(saveBt, cancelBt);
        
    }

    @Override
    public void doOkAction() {
        for (PritomnyComponent prtka : this.pritomni){
            vorRepo.save(prtka.getHlasovanieVerOs());
        }
        Notification.show("Uloženie prebehlo v poriadku!");
    }

    @Override
    public void doCancelAction() {
        navigator.navigateTo("A_inputAll");
    }
}
