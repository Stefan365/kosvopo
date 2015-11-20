package sk.stefan.mvps.model.serviceImpl;

import org.springframework.stereotype.Service;
import sk.stefan.mvps.model.entity.Tenure;
import sk.stefan.mvps.model.repo.UniRepo;
import sk.stefan.mvps.model.service.TenureService;

import java.util.List;

/**
 * Created by elopin on 14.11.2015.
 */
@Service
public class TenureServiceImpl implements TenureService {

    private UniRepo<Tenure> tenureRepo;

    public TenureServiceImpl() {
        tenureRepo = new UniRepo<Tenure>(Tenure.class);
    }

    @Override
    public List<Tenure> findAllTenures() {
        return tenureRepo.findAll();
    }
}
