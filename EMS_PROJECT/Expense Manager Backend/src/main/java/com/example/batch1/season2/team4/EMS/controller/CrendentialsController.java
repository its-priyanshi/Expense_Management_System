package com.example.batch1.season2.team4.EMS.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.batch1.season2.team4.EMS.model.Credentials;
import com.example.batch1.season2.team4.EMS.service.CredentialsService;

@RestController
@RequestMapping("/user")
public class CrendentialsController {
	@Autowired
    CredentialsService service;
    
    @GetMapping("/getcredentials/{associate_id}/{password}")
    @CrossOrigin("*")
    public ResponseEntity<Credentials> getUser(@PathVariable String associate_id,@PathVariable String password){
        Credentials user=service.getUser(associate_id, password);
        if(user==null) {
            return new ResponseEntity(null,HttpStatus.NOT_FOUND);
        }
        else {
        	System.out.println(user.toString());
            return new ResponseEntity(user,HttpStatus.OK);
        }
    }
    @GetMapping("/getAssociateById/{associate_id}")
    @CrossOrigin("*")
    public ResponseEntity<Credentials> getUsersbyId(@PathVariable String associate_id){
        
    		Credentials users=service.getUsersbyId(associate_id).get();
    		if(users==null)
    			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    		return new ResponseEntity<>(users,HttpStatus.OK);
    }
    @PutMapping("/updateCredentials/{associate_id}/{password}")
    @CrossOrigin("*")
    public ResponseEntity<String> updateCredentials(@PathVariable String associate_id,@PathVariable String password){
        Credentials user=service.updateCredentials(associate_id,password);
        if(user==null) {
            return new ResponseEntity(null,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity("User updated Successfully",HttpStatus.OK);
    }
    
}
