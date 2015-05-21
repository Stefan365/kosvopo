/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.serviceImpl;

import com.vaadin.ui.Notification;
import java.util.List;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.A_User;
import sk.stefan.MVP.model.repo.GeneralRepo;
import sk.stefan.MVP.model.repo.UniRepo;
import sk.stefan.MVP.model.service.EnterViewService;
import sk.stefan.MVP.view.components.vote.PritomniLayout;

/**
 *
 * @author stefan
 */
public class EnterViewServiceImpl implements EnterViewService {
    
    private static final Logger log = Logger.getLogger(EnterViewServiceImpl.class);


    private final UniRepo<A_User> userRepo;
    
    private final GeneralRepo genRepo;
    
    
    public EnterViewServiceImpl(){
        
        this.userRepo = new UniRepo<>(A_User.class);
        
        this.genRepo = new GeneralRepo();
        
    }
    
    @Override
    public Boolean isThereAdmin() {
        
        List<A_User> usrs = userRepo.findByParam("login", "admin");
//        log.info("USRS ADMIN is null:" + (usrs == null));
//        log.info("USRS ADMIN size:" + usrs.size());
        
        if (usrs != null && !usrs.isEmpty()){
//            log.info("USRS TRUE");
            return Boolean.TRUE;
        } else {
//            log.info("USRS FALSE");
            return Boolean.FALSE;
        }
    }
    

    @Override
    public void initAdmin() {
        genRepo.initAdmin();
        Notification.show("INICIALIZACIA ADMINA USPESNA!");
    }
    
    
    
}
