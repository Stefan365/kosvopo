/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.model.service;

import sk.stefan.mvps.model.entity.District;
import sk.stefan.mvps.model.entity.Location;
import sk.stefan.mvps.model.entity.Region;

import java.util.Collection;
import java.util.List;

/**
 * Service pro lokace veřejných orgánů.
 * @author stefan
 */
public interface LocationService {
    
    Location findOneLocation(Integer locId);
    
    District findOneDistrict(Integer distrId);

    Region findOneRegion(Integer regId);

    List<Location> findAll();

    List<Region> findAllRegions();

    Region saveRegion(Region region);

    void removeRegion(Region region);

    List<District> findAllDistricts();

    District saveDistrict(District district);

    void removeDistrict(District district);

    List<Location> findAllLocations();

    Location saveLocation(Location location);

    void removeLocation(Location location);
}
