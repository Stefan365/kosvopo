/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.model.serviceImpl;

import com.vaadin.ui.Notification;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import sk.stefan.mvps.model.entity.Location;
import sk.stefan.mvps.model.entity.PublicBody;
import sk.stefan.mvps.model.entity.PublicPerson;
import sk.stefan.mvps.model.entity.PublicRole;
import sk.stefan.mvps.model.repo.GeneralRepo;
import sk.stefan.mvps.model.repo.UniRepo;
import sk.stefan.mvps.model.service.PublicBodyService;

/**
 *
 * @author stefan
 */
public class PublicBodyServiceImpl implements PublicBodyService {
    
    
    private static final Logger log = Logger.getLogger(PublicPersonServiceImpl.class);
    
    private final GeneralRepo genRepo;
    private final UniRepo<PublicBody> pubBodyRepo;
    private final UniRepo<PublicRole> pubRoleRepo;
    private final UniRepo<PublicPerson> pubPersonRepo;
    private final UniRepo<Location> locRepo;

//    na odlahcenie RAM, nebudem tu pouzivat objemne Service.:    
//    private final PublicPersonService pubPersonService;
//    private final LocationService locationService;
    
    public PublicBodyServiceImpl() {

        genRepo = new GeneralRepo();
        pubBodyRepo = new UniRepo<>(PublicBody.class);
        pubRoleRepo = new UniRepo<>(PublicRole.class);
        pubPersonRepo = new UniRepo<>(PublicPerson.class);
        locRepo = new UniRepo<>(Location.class);
        
//        pubPersonService = new PublicPersonServiceImpl();
//        locationService = new LocationServiceImpl();
        

    }

    
    @Override
    public String getPublicBodyChief(PublicBody pb) {

        String pbId = "" + pb.getId();
        String chiefRole = "" + 3;

        List<PublicRole> pRoles
                = pubRoleRepo.findByTwoParams("public_body_id", pbId, "name", chiefRole);
        if (pRoles == null || pRoles.isEmpty()) {

            return "žiadny predseda";

        } else if (pRoles.size() > 1) {

            String msg = pb.getPresentationName() + " má " + pRoles.size() + " predsedov! Vyberám prvého.";
            Notification.show(msg, Notification.Type.TRAY_NOTIFICATION);

        }

        Integer ppId = pRoles.get(0).getId();
        //ziskavanie mena predsedu:
        PublicPerson predseda = pubPersonRepo.findOne(ppId);
        return predseda.getPresentationName();
    }

    @Override
    public List<PublicBody> findAll() {
        return pubBodyRepo.findAll();
    }

    /**
     * Vrati ids verejnych organov v danom okrese.
     *
     * @param districtId id okresu.
     * @return
     */
    @Override
    public List<Integer> findPublicBodyIdsByDistrictId(Integer districtId) {

        List<Integer> pbIds;

        String sql = "SELECT pb.id FROM t_public_body pb WHERE pb.location_id IN "
                + "(SELECT loc.id FROM t_location loc WHERE loc.district_id = " + districtId + " AND loc.visible = true) "
                + "AND pb.visible = true";
        pbIds = this.genRepo.findIds(sql);

        return pbIds;

    }
    
    /**
     *
     * @param tx
     * @return
     */
    @Override
    public List<Integer> findPublicBodyIdsByFilter(String tx) {

        List<Integer> pbIds;

        String sql = "SELECT pb.id FROM t_public_body pb JOIN t_location loc "
                + " ON (pb.location_id = loc.id) "
                + " WHERE (loc.location_name like '%" + tx + "%'"
                + " OR loc.town_section like '%" + tx + "%') "
                + " AND pb.visible = true"
                + " AND loc.visible = true";
        
        
        pbIds = this.genRepo.findIds(sql);

        return pbIds;

    }


    @Override
    public List<PublicBody> findPublicBodies(List<Integer> pbIds) {
        
        List<PublicBody> publicBodies = new ArrayList<>();

        for (Integer i : pbIds) {
            publicBodies.add(pubBodyRepo.findOne(i));
        }

        return publicBodies;

    }

    @Override
    public PublicBody findOne(Integer pbId) {
        
        return pubBodyRepo.findOne(pbId);
    }

    @Override
    public synchronized String getPresentationName(PublicBody pb) {
        
        Integer locId = pb.getLocation_id();
        log.info("LOC ID:" + locId);
        if (locId != null) {
            Location loc = locRepo.findOne(locId);
            return pb.getName() + ", " + loc.getPresentationName();
        } else {
            return pb.getName();
        }

    }
}
