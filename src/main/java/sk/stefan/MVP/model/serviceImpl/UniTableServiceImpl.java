/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.serviceImpl;

import com.vaadin.data.Item;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.entity.A_UserRole;
import sk.stefan.MVP.model.repo.GeneralRepo;
import sk.stefan.MVP.model.repo.UniRepo;
import sk.stefan.MVP.model.service.UniTableService;
import sk.stefan.utils.ToolsDao;

/**
 *
 * @author stefan
 * @param <E>
 */
public class UniTableServiceImpl<E> implements UniTableService<E> {

    private static final Logger log = Logger.getLogger(UniTableServiceImpl.class);

    private final UniRepo<E> uniRepo;

    private final GeneralRepo genRepo;

    private final Class<E> clsE;

    private final String tn;

    public UniTableServiceImpl(Class<E> cls) {

        this.clsE = cls;
        this.uniRepo = new UniRepo<>(clsE);
        this.genRepo = new GeneralRepo();
        this.tn = ToolsDao.getTableName(clsE);

    }

    @Override
    public String getClassTableName() {

        return tn;

    }

    @Override
    public void deactivateById(Integer entId) throws SQLException {

        genRepo.deactivateWithSlavesTree(tn, entId);
        genRepo.doCommit();

    }

    @Override
    public E getObjectFromItem(Item item, Map<String, Class<?>> mapPar) {

        Object val;
        Method entMethod;
        String entMetName;
        Class<?> clsPom;
        try {
            E ent = clsE.newInstance();

            for (String param : mapPar.keySet()) {

                //item:
                val = item.getItemProperty(param).getValue();
                log.info("1. PARAM: *" + param + "*, value: " + val);

                if (val != null) {
                    clsPom = val.getClass();
                    log.info("2. Classpom: *" + clsPom.getCanonicalName() + "*");
                }

                //entita:
                entMetName = ToolsDao.getG_SetterName(param, "set");
                log.info("3. SETTER NAME: *" + entMetName + "*");
                log.info("4. PARAM CLASS NAME: *" + mapPar.get(param).getCanonicalName() + "*");

                entMethod = clsE.getMethod(entMetName, mapPar.get(param));

//                    pre potreby transformacie do enum:
                if (mapPar.get(param).getCanonicalName().contains(".enums.")) {

                    Integer valInt = (Integer) val;
                    
                    Class<?> clsEnum = mapPar.get(param);
                    Method enumMethod = clsEnum.getDeclaredMethod("values");
                    Object[] enumVals = (Object[]) (enumMethod.invoke(null));
                    Object enumVal = enumVals[valInt];

                    entMethod.invoke(ent, enumVal);
                    
                    
                } else {
                    entMethod.invoke(ent, val);
                }

            }

            return ent;

        } catch (InstantiationException | IllegalAccessException |
                SecurityException | NoSuchMethodException | IllegalArgumentException |
                InvocationTargetException e) {
            log.error(e.getLocalizedMessage(), e);
            return null;
        }

    }

    @Override
    public E save(E ent, boolean noteChange) {
        return uniRepo.save(ent, noteChange);
    }

    @Override
    public List<Integer> findMeAndAllVolunteers(Integer uid) {
        return genRepo.findMeAndAllVolunteers(uid);
    }

    @Override
    public void saveRole(A_UserRole urole) {

        UniRepo<A_UserRole> roleRepo = new UniRepo<>(A_UserRole.class);
        roleRepo.save(urole, true);

    }

}
