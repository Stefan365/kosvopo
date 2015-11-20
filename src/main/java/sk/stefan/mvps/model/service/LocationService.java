/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.mvps.model.service;

import sk.stefan.mvps.model.entity.District;
import sk.stefan.mvps.model.entity.Location;
import sk.stefan.mvps.model.entity.Region;

import java.util.List;

/**
 *
 * @author stefan
 */
public interface LocationService {
    
    public Location findOneLocation(Integer locId);
    
    public District findOneDistrict(Integer distrId);

    public Region findOneRegion(Integer regId);

    List<Location> findAll();
}
