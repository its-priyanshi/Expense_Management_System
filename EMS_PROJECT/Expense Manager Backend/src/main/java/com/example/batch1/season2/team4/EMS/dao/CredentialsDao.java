package com.example.batch1.season2.team4.EMS.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.batch1.season2.team4.EMS.model.Credentials;

@Repository
public interface CredentialsDao extends JpaRepository<Credentials,String>{
	public Credentials findByAssociateIdAndPassword(String associate_id, String password);
}
