package com.example.batch1.season2.team4.EMS.service;

import java.util.Optional;

import com.example.batch1.season2.team4.EMS.model.Credentials;

public interface CredentialsService {
	Credentials getUser(String associate_id, String password);

    Optional<Credentials> getUsersbyId(String associate_id);

    Credentials updateCredentials(String associate_id, String password);
}
