/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.model.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stefan.mvps.model.entity.District;
import sk.stefan.mvps.model.entity.Location;
import sk.stefan.mvps.model.entity.Region;
import sk.stefan.mvps.model.repo.UniRepo;
import sk.stefan.mvps.model.service.ActiveUserService;
import sk.stefan.mvps.model.service.LocationService;

import java.util.List;

/**
 * Výchozí implementace služby pro lokace.
 * @author stefan
 */
@Service
public class LocationServiceImpl implements LocationService {
    
    private final UniRepo<Location> locRepo;
    private final UniRepo<District> distrRepo;
    private final UniRepo<Region> regRepo;

    @Autowired
    private ActiveUserService activeUserService;


    /**
     * Konstruktor inicializuje repozitáře.
     */
    public LocationServiceImpl(){
        
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

    @Override
    public List<Location> findAll() {
        return locRepo.findAll();
    }

    @Override
    public List<Region> findAllRegions() {
        return regRepo.findAll();
    }

    @Override
    public Region saveRegion(Region region) {
        return regRepo.save(region, true,activeUserService.getActualUser());
    }

    @Override
    public void removeRegion(Region region) {
        regRepo.deactivateOneOnly(region, region.getId() != null, activeUserService.getActualUser());
    }

    @Override
    public List<District> findAllDistricts() {
        return distrRepo.findAll();
    }

    @Override
    public District saveDistrict(District district) {
        return distrRepo.save(district, true, activeUserService.getActualUser());
    }

    @Override
    public void removeDistrict(District district) {
        distrRepo.deactivateOneOnly(district, district.getId() != null, activeUserService.getActualUser());
    }

    @Override
    public List<Location> findAllLocations() {
        return locRepo.findAll();
    }

    @Override
    public Location saveLocation(Location location) {
        return locRepo.save(location, true, activeUserService.getActualUser());
    }

    @Override
    public void removeLocation(Location location) {
        locRepo.deactivateOneOnly(location, location.getId() != null, activeUserService.getActualUser());
    }

}
