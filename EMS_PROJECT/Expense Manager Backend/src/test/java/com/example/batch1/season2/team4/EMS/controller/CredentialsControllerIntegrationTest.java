package com.example.batch1.season2.team4.EMS.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.example.batch1.season2.team4.EMS.Application;
import com.example.batch1.season2.team4.EMS.model.Credentials;
import com.example.batch1.season2.team4.EMS.service.CredentialServiceImpl;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { Application.class })
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc 
public class CredentialsControllerIntegrationTest {
    
    @Autowired       
    private MockMvc mvc;
    
    @MockBean
    private CredentialServiceImpl credentialService;
    
    @Test
    public void giveCredentials_whenGetUsersbyId_thenReturnJsonArray() throws Exception {
        Optional<Credentials> satya = Optional.ofNullable(new Credentials("GGGAV8HGSGVDCB32" , "@Satya1" , "Developer"));
  
        given(credentialService.getUsersbyId(satya.get().getAssociate_id())).willReturn(satya);
        
        mvc.perform(get("/user/getAssociateById/{associate_id}",satya.get().getAssociate_id()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.role", is(satya.get().getRole())));
        
        verify(credentialService, VerificationModeFactory.times(1)).getUsersbyId(satya.get().getAssociate_id());
        reset(credentialService);
    }
    
    @Test
    public void giveCredentials_whenGetUser_thenReturnJsonArray() throws Exception {
        Credentials satya = new Credentials("GGGAV8HGSGVDCB32" , "@Satya1" , "Developer");
        
        given(credentialService.getUser(satya.getAssociate_id(),satya.getPassword())).willReturn(satya);
        
        mvc.perform(get("/user/getcredentials/{associate_id}/{password}",satya.getAssociate_id(),satya.getPassword()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.role", is(satya.getRole())));
        
        verify(credentialService, VerificationModeFactory.times(1)).getUser(satya.getAssociate_id(),satya.getPassword());
        reset(credentialService);
    }
}
