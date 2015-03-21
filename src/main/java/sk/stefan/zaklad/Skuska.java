package sk.stefan.zaklad;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import sk.stefan.MVP.model.entity.dao.Kraj;
import sk.stefan.MVP.model.entity.dao.Location;
import sk.stefan.MVP.model.entity.dao.Vote;
import sk.stefan.MVP.model.repo.dao.UniRepo;

public class Skuska {

    @Autowired
    private UniRepo<Vote> voteRepo;
    
    @Autowired
    private UniRepo<Location> locRepo;
    
    @Autowired
    private UniRepo<Kraj> krRepo;
    
    

    private int aj;
    private static final Logger log = Logger.getLogger(Skuska1.class);

    public Skuska(int i) {
        aj = i;
    }

    public List<Integer> getList() {
        List<Integer> la = new ArrayList<>();
        la.add(aj);
        return la;

    }

    public void skusRepo() {
        
        Vote v = voteRepo.findOne(4);
        log.info("VOTE SKUSKA: " + v.getPresentationName());
        
        Location l = locRepo.findOne(4);
        log.info("LOC SKUSKA: " + l.getObec_name());
        
        Kraj k = krRepo.findOne(3);
        log.info("KRAJ SKUSKA: " + k.getKraj_name());
        

    }

    /**
     * @return the voteRepo
     */
    public UniRepo<Vote> getVoteRepo() {
        return voteRepo;
    }

    /**
     * @param voteRepo the voteRepo to set
     */
    public void setVoteRepo(UniRepo<Vote> voteRepo) {
        this.voteRepo = voteRepo;
    }

    /**
     * @return the locRepo
     */
    public UniRepo<Location> getLocRepo() {
        return locRepo;
    }

    /**
     * @param locRepo the locRepo to set
     */
    public void setLocRepo(UniRepo<Location> locRepo) {
        this.locRepo = locRepo;
    }

    /**
     * @return the krRepo
     */
    public UniRepo<Kraj> getKrajRepo() {
        return krRepo;
    }

    /**
     * @param krRepo the krRepo to set
     */
    public void setKrajRepo(UniRepo<Kraj> krRepo) {
        this.krRepo = krRepo;
    }
}
