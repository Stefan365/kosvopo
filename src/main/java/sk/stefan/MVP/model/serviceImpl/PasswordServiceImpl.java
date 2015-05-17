/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stefan.MVP.model.serviceImpl;

import java.sql.SQLException;
import org.apache.log4j.Logger;
import sk.stefan.MVP.model.repo.GeneralRepo;
import sk.stefan.MVP.model.service.PasswordService;

/**
 *
 * @author stefan
 */
public class PasswordServiceImpl implements PasswordService {

    private static final Logger log = Logger.getLogger(PasswordServiceImpl.class);

    private final GeneralRepo genRepo;

    public PasswordServiceImpl() {
        this.genRepo = new GeneralRepo();
    }

    @Override
    public byte[] getPassword(Integer id) throws SQLException {

        return genRepo.getPassword(id + "");

    }

    @Override
    public void updatePassword(String newPwd, String uid) throws SQLException  {
        
        genRepo.updatePassword(newPwd, uid);
        
    }

}
