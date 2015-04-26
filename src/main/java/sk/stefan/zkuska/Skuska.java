package sk.stefan.zkuska;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import sk.stefan.MVP.model.entity.Region;
import sk.stefan.MVP.model.entity.Location;
import sk.stefan.MVP.model.entity.Vote;
import sk.stefan.MVP.model.repo.UniRepo;

public class Skuska {

    @Autowired
    private UniRepo<Vote> voteRepo;
    
    @Autowired
    private UniRepo<Location> locRepo;
    
    @Autowired
    private UniRepo<Region> krRepo;
    
    

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
        log.info("LOC SKUSKA: " + l.getLocation_name());
        
        Region k = krRepo.findOne(3);
        log.info("KRAJ SKUSKA: " + k.getRegion_name());
        

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
    public UniRepo<Region> getKrajRepo() {
        return krRepo;
    }

    /**
     * @param krRepo the krRepo to set
     */
    public void setKrajRepo(UniRepo<Region> krRepo) {
        this.krRepo = krRepo;
    }
}
