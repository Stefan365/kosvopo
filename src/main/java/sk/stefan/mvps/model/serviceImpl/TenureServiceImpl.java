package sk.stefan.mvps.model.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stefan.mvps.model.entity.Tenure;
import sk.stefan.mvps.model.repo.UniRepo;
import sk.stefan.mvps.model.service.ActiveUserService;
import sk.stefan.mvps.model.service.TenureService;

import java.util.List;

/**
 * Created by elopin on 14.11.2015.
 */
@Service
public class TenureServiceImpl implements TenureService {

    private UniRepo<Tenure> tenureRepo;

    @Autowired
    private ActiveUserService activeUserService;

    public TenureServiceImpl() {
        tenureRepo = new UniRepo<>(Tenure.class);
    }

    @Override
    public List<Tenure> findAllTenures() {
        return tenureRepo.findAll();
    }

    @Override
    public void removeTenure(Tenure tenure) {
        tenureRepo.deactivateOneOnly(tenure, tenure.getId() != null, activeUserService.getActualUser());
    }

    @Override
    public Tenure saveTenure(Tenure tenure) {
        return tenureRepo.save(tenure, true, activeUserService.getActualUser());
    }
}
