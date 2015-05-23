/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.serviceImpl;

import sk.stefan.MVP.model.entity.District;
import sk.stefan.MVP.model.entity.Location;
import sk.stefan.MVP.model.entity.Region;
import sk.stefan.MVP.model.repo.UniRepo;
import sk.stefan.MVP.model.service.LocationService;

/**
 *
 * @author stefan
 */
public class ZBD_LocationServiceImpl implements LocationService {
    
    private final UniRepo<Location> locRepo;
    private final UniRepo<District> distrRepo;
    private final UniRepo<Region> regRepo;
    

    
    public ZBD_LocationServiceImpl(){
        
        locRepo = new UniRepo<>(Location.class);
        distrRepo = new UniRepo<>(District.class);
        regRepo = new UniRepo<>(Region.class);
        
    }
    
    
    @Override
    public Location findOneLocation(Integer locId) {
        
        return locRepo.findOne(locId);
        
    }
   
    @Override
    public District findOneDistrict(Integer distrId) {
        
        return distrRepo.findOne(distrId);
        
    }

    @Override
    public Region findOneRegion(Integer regId) {
        
        return regRepo.findOne(regId);
        
    }
    
}
