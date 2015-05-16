package sk.stefan.MVP.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.A_User;
import sk.stefan.MVP.model.repo.UniRepo;
import sk.stefan.MVP.model.service.EnterViewService;
import sk.stefan.MVP.model.service.SecurityService;
import sk.stefan.MVP.model.serviceImpl.EnterViewServiceImpl;
import sk.stefan.MVP.model.serviceImpl.SecurityServiceImpl;
//import sk.stefan.MVP.view.components.MyTimeline;

/**
 * 
 */
public class V2_EnterView extends VerticalLayout implements View {

    private static final Logger log = Logger.getLogger(V2_EnterView.class);

    private static final long serialVersionUID = -2001141270398193257L;
   
    private final EnterViewService enterService;
    
    private Button initAdminBt;
    
    private final V2_EnterView thiss;
    
    
//    private final MyTimeline timeline;


    /**
     * 
     */
    public V2_EnterView() {

        this.setMargin(true);
        this.setSpacing(true);
        
        thiss = this;
        this.enterService = new EnterViewServiceImpl();
        this.initlayout();
        this.initAdminButton();
        
        
//        this.timeline = new MyTimeline();
//        this.addComponent(timeline);
        
    }

    private void initlayout() {

        Resource res = new ThemeResource("images/demokracia_ciel.jpg");
        Image image = new Image("budovanie demokracie", res);
        this.addComponent(image);
    }
    
    /**
     * 
     */
    private void initAdminButton(){

        this.initAdminBt = new Button("Inicializuj admina");
        
        initAdminBt.addClickListener(new Button.ClickListener() {
            
            private static final long serialVersionUID = 45331L;

            @Override
            public void buttonClick(Button.ClickEvent event) {
                enterService.initAdmin();
                thiss.removeComponent(initAdminBt);
            }
        });
        
    }

    @Override
    public void enter(ViewChangeEvent event) {
        
        Boolean isThereAdm = enterService.isThereAdmin();
        log.info("IS THERE ADMIN:" + isThereAdm);
        
        if (!isThereAdm){
            this.addComponent(initAdminBt);
        } else if(initAdminBt != null){
            this.removeComponent(initAdminBt);
//            this.initAdminBt = null;
        }
        
    }

}
