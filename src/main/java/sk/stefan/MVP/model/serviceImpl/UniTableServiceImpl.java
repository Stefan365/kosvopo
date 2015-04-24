/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.serviceImpl;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sk.stefan.MVP.model.repo.dao.UniRepo;
import sk.stefan.MVP.model.service.UniTableService;
import sk.stefan.utils.ToolsDao;

/**
 *
 * @author stefan
 * @param <E>
 */
public class UniTableServiceImpl<E> implements UniTableService<E> {
    
    private final UniRepo<E> uniRepo;
    
    private final Class<E> clsE;
    
    public UniTableServiceImpl(Class<E> cls){
        
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
    
}
