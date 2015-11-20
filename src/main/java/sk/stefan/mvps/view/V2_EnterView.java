package sk.stefan.mvps.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import sk.stefan.annotations.MenuButton;
import sk.stefan.annotations.ViewTab;
import sk.stefan.mvps.model.service.UserService;
import sk.stefan.mvps.model.serviceImpl.UserServiceImpl;
import sk.stefan.mvps.view.tabs.TabComponent;

/**
 * Vstupny View. 
 */
@MenuButton(name = "Hlavná stránka", position = 0, icon = FontAwesome.HOME)
@ViewTab("enterTab")
@SpringComponent
@Scope("prototype")
public class V2_EnterView extends VerticalLayout implements TabComponent {

    private static final Logger log = Logger.getLogger(V2_EnterView.class);

    private final UserService userService;
    
    
    
    private Button initAdminBt;
    
    private final V2_EnterView thiss;
    
    
    /**
     * 
     */
    public V2_EnterView() {
//        super("Vstupná ");
        this.setMargin(true);
        this.setSpacing(true);

        thiss = this;
        this.userService = new UserServiceImpl();
        this.initlayout();
        this.initAdminButton();
        
        
    }

    private void initlayout() {

        Resource res = new ThemeResource("images/demokracia_ciel.jpg");
        Image image = new Image(null,  res);
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
                userService.initAdmin();
                thiss.removeComponent(initAdminBt);
            }
        });
        
    }

//    @Override
//    public void enter(ViewChangeEvent event) {
//
//        Boolean isThereAdm = userService.isThereAdmin();
//        log.info("IS THERE ADMIN:" + isThereAdm);
//
//        if (!isThereAdm){
//
//            this.addComponent(initAdminBt);
//        } else if(initAdminBt != null){
//
//            this.removeComponent(initAdminBt);
//        }
//
//    }

    @Override
    public String getTabCaption() {
        return "Hlavná stránka";
    }

    @Override
    public void show() {

    }

    @Override
    public String getTabId() {
        return "enterTab";
    }
}
