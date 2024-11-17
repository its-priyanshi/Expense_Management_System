package com.example.batch1.season2.team4.EMS.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.batch1.season2.team4.EMS.dao.CredentialsDao;
import com.example.batch1.season2.team4.EMS.model.Credentials;

@Service
public class CredentialServiceImpl implements CredentialsService{
    
	@Autowired
    CredentialsDao dao;
    
    @Override
    public Credentials getUser(String associate_id, String password) {
        // TODO Auto-generated method
        Credentials user=dao.findByAssociateIdAndPassword(associate_id, password);
            if(user==null) {
                return null;
            }
            return user;
    }

    @Override
    public Optional<Credentials> getUsersbyId(String associate_id) {
        // TODO Auto-generated method stub
        return dao.findById(associate_id);
    }

    @Override
    public Credentials updateCredentials(String associate_id, String password) {
        if(getUsersbyId(associate_id)==null) {
            return null;
        }
        Credentials users=dao.getById(associate_id);
        users.setPassword(password);
        return dao.save(users);
    }

}
