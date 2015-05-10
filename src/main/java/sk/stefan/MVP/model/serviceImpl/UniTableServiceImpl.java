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
import java.util.Map;
import org.apache.log4j.Logger;
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

    private final Class<E> clsE;

    public UniTableServiceImpl(Class<E> cls) {

        this.clsE = cls;
        this.uniRepo = new UniRepo<>(clsE);

    }

    @Override
    public String getClassTableName() {

        return ToolsDao.getTableName(clsE);

    }

    @Override
    public void deactivateById(Integer entId) throws SQLException {

        uniRepo.updateParam("visible", "false", "" + entId);

    }

    @Override
    public E getObjectFromItem(Item item, Map<String, Class<?>> mapPar) {

        Object val;
        Method entMethod;
        String entSetterName;
        
        try {
            E ent = clsE.newInstance();
            
            for (String param : mapPar.keySet()){
                log.info("PARAM: *" + param + "*");
                //item:
                val = item.getItemProperty(param).getValue();                
                
                //entita:
                entSetterName = ToolsDao.getG_SetterName(param, "set");
                entMethod = clsE.getMethod(entSetterName, mapPar.get(param));
                entMethod.invoke(ent, val);
                
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
    public E save(E ent) {
        return uniRepo.save(ent);
    }

}
