package sk.stefan.mvps.model.service;

import sk.stefan.mvps.model.entity.Tenure;

import java.util.List;

/**
 * Created by elopin on 14.11.2015.
 */
public interface TenureService {

    List<Tenure> findAllTenures();
}
