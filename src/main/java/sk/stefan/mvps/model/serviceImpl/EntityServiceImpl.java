package sk.stefan.mvps.model.serviceImpl;

import org.springframework.stereotype.Service;
import sk.stefan.mvps.model.entity.A_User;
import sk.stefan.mvps.model.entity.PublicBody;
import sk.stefan.mvps.model.entity.PublicPerson;
import sk.stefan.mvps.model.entity.PublicRole;
import sk.stefan.interfaces.TabEntity;
import sk.stefan.mvps.model.entity.Vote;
import sk.stefan.mvps.model.repo.UniRepo;
import sk.stefan.mvps.model.service.EntityService;

/**
 * Created by elopin on 08.11.2015.
 */
@Service
public class EntityServiceImpl implements EntityService {

    private UniRepo<PublicBody> pubBodyRepo;
    private UniRepo<PublicRole> pubRoleRepo;
    private UniRepo<PublicPerson> pubPersonRepo;
    private UniRepo<Vote> voteRepo;
    private UniRepo<A_User> userRepo;


    public EntityServiceImpl() {
        pubBodyRepo = new UniRepo<>(PublicBody.class);
        pubRoleRepo = new UniRepo<>(PublicRole.class);
        pubPersonRepo = new UniRepo<>(PublicPerson.class);
        voteRepo = new UniRepo<>(Vote.class);
        userRepo = new UniRepo<>(A_User.class);
    }

    @Override
    public TabEntity getTabEntityByNameAndId(String entityName, Integer entityId) {
            switch (entityName) {
                case "body":
                    return pubBodyRepo.findOne(entityId);
                case "role":
                    return pubRoleRepo.findOne(entityId);
                case "person":
                    return pubPersonRepo.findOne(entityId);
                default:
                    throw new RuntimeException("Nepodporované vyhledání entity pro název: " + entityName);
            }
    }

    @Override
    public String getTabIdByEntity(TabEntity entity) {
        if (PublicBody.class.isAssignableFrom(entity.getClass())) {
            return "verejnyOrgan" + entity.getId();
        } else if (PublicRole.class.isAssignableFrom(entity.getClass())) {
            return "verejnaRole" + entity.getId();
        } else if (PublicPerson.class.isAssignableFrom(entity.getClass())) {
            return "verejnaOsoba" + entity.getId();
        } else if (Vote.class.isAssignableFrom(entity.getClass())) {
            return "hlasovani" + entity.getId();
        } else if(A_User.class.isAssignableFrom(entity.getClass())) {
            return "uzivatel" + entity.getId();
        }
        throw new RuntimeException("Nepodporovaná tvorba id záložky pro entitu " + entity.getClass());
    }

    @Override
    public TabEntity saveEntity(TabEntity entity) {
        if (PublicBody.class.isAssignableFrom(entity.getClass())) {
            return pubBodyRepo.save((PublicBody) entity, entity.getId() != null);
        } else if (PublicRole.class.isAssignableFrom(entity.getClass())){
            return pubRoleRepo.save((PublicRole) entity, entity.getId() != null);
        } else if (PublicPerson.class.isAssignableFrom(entity.getClass())) {
            return pubPersonRepo.save((PublicPerson) entity, entity.getId() != null);
        } else if(Vote.class.isAssignableFrom(entity.getClass())) {
            return voteRepo.save((Vote) entity, entity.getId() != null);
        } else if (A_User.class.isAssignableFrom(entity.getClass())) {
            return userRepo.save((A_User) entity, entity.getId() != null);
        } else {
            throw new RuntimeException("Nepodarilo sa uložiť entitu " + entity);
        }
    }

    @Override
    public boolean removeEntity(TabEntity entity) {
        if (PublicBody.class.isAssignableFrom(entity.getClass())) {
            return pubBodyRepo.deactivateOneOnly((PublicBody) entity, false);
        } else if (A_User.class.isAssignableFrom(entity.getClass())) {
            return userRepo.deactivateOneOnly((A_User) entity, false);
        } else if (PublicPerson.class.isAssignableFrom(entity.getClass())) {
            return pubPersonRepo.deactivateOneOnly((PublicPerson) entity, false);
        } else if (PublicRole.class.isAssignableFrom(entity.getClass())) {
            return pubRoleRepo.deactivateOneOnly((PublicRole) entity, false);
        }
        throw new RuntimeException("Nepodarilo sa odstranit entitu " + entity);
    }

    @Override
    public TabEntity getTabEntityByTabName(String tabName, Integer entityId) {
        switch (tabName) {
            case "verejnyOrgan":
                return pubBodyRepo.findOne(entityId);
            case "verejnaRole":
                return pubRoleRepo.findOne(entityId);
            case "verejnaOsoba":
                return pubPersonRepo.findOne(entityId);
            case "hlasovani":
                return voteRepo.findOne(entityId);
            case "uzivatel":
                return userRepo.findOne(entityId);
            default:
                throw new RuntimeException("Nepodporované vyhledání entity pro záložku: " + tabName);
        }
    }
}
