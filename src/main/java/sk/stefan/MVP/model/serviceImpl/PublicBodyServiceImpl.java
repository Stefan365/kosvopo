/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.serviceImpl;

import com.vaadin.ui.Notification;
import java.util.ArrayList;
import java.util.List;
import sk.stefan.MVP.model.entity.PublicBody;
import sk.stefan.MVP.model.entity.PublicPerson;
import sk.stefan.MVP.model.entity.PublicRole;
import sk.stefan.MVP.model.repo.GeneralRepo;
import sk.stefan.MVP.model.repo.UniRepo;
import sk.stefan.MVP.model.service.PublicBodyService;

/**
 *
 * @author stefan
 */
public class PublicBodyServiceImpl implements PublicBodyService {

    private final GeneralRepo generalRepo;
    private final UniRepo<PublicBody> publicBodyRepo;
    private final UniRepo<PublicPerson> ppRepo;
    private final UniRepo<PublicRole> pubRoleRepo;

    public PublicBodyServiceImpl() {

        generalRepo = new GeneralRepo();
        publicBodyRepo = new UniRepo<>(PublicBody.class);
        ppRepo = new UniRepo<>(PublicPerson.class);
        pubRoleRepo = new UniRepo<>(PublicRole.class);

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
        PublicPerson predseda = ppRepo.findOne(ppId);
        return predseda.getPresentationName();
    }

    @Override
    public List<PublicBody> findAll() {
        return publicBodyRepo.findAll();
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
        pbIds = this.generalRepo.findIds(sql);

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
                + " WHERE loc.location_name like '%" + tx + "%'"
                + " OR loc.town_section like '%" + tx + "%' "
                + " AND pb.visible = true"
                + " AND loc.visible = true";
        
        pbIds = this.generalRepo.findIds(sql);

        return pbIds;

    }


    @Override
    public List<PublicBody> findPublicBodies(List<Integer> pbIds) {
        
        List<PublicBody> publicBodies = new ArrayList<>();

        for (Integer i : pbIds) {
            publicBodies.add(publicBodyRepo.findOne(i));
        }

        return publicBodies;

    }
}
