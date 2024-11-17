package com.example.batch1.season2.team4.EMS.service;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.batch1.season2.team4.EMS.dao.CredentialsDao;
import com.example.batch1.season2.team4.EMS.model.Credentials;

@ExtendWith(SpringExtension.class)
public class CredentialsServiceIntegrationTest {
    
    
    private Credentials satya , raju;
    
    @TestConfiguration
    static class CredentialServiceImplTestContextConfiguration {
        @Bean
        public CredentialsService credentialService() {
            return new CredentialServiceImpl();
        }
    }

        @Autowired
        private CredentialsService credentialService;
    
        @MockBean
        private CredentialsDao credentialDao;
        
        @BeforeEach
        public void setUp() {
            satya = new Credentials("GGGAV8HGSGVDCB32" , "@Satya1" , "Developer");
            raju = new Credentials("GGGAV8HGSGVDCB35" , "@Raju2" , "Tester");
        
            //List<String> allIds = Arrays.asList(satya.getAssociate_id() , raju.getAssociate_id());
            Mockito.when(credentialDao.findById(satya.getAssociate_id())).thenReturn(Optional.of(satya));
            Mockito.when(credentialDao.getById(satya.getAssociate_id())).thenReturn(satya);
            Mockito.when(credentialDao.findById(raju.getAssociate_id())).thenReturn(Optional.of(raju));
            Mockito.when(credentialDao.findByAssociateIdAndPassword(satya.getAssociate_id(), satya.getPassword())).thenReturn(satya);
            Mockito.when(credentialDao.save(satya)).thenReturn(satya);
            Mockito.when(credentialDao.findById("-1")).thenThrow(new NoSuchElementException());
        }
        
        @AfterEach
        public void cleanup() {
            satya=null;
            raju=null;
    
        }
        
        private void verifyFindByIdIsCalledOnce(String id) {
            Mockito.verify(credentialDao , VerificationModeFactory.times(1)).findById(id);
            Mockito.reset(credentialDao);
        }
        
        @Test
        public void whenGetUsersbyId_thenReturnCredentials() {
            String id = satya.getAssociate_id();
            Credentials found = credentialService.getUsersbyId(id).orElse(null);
            verifyFindByIdIsCalledOnce(id);
            assertThat(found.getAssociate_id()).isEqualTo(id);
        }
        
        @Test
        public void whenGetUser_thenReturnCredentials() {
            String id = satya.getAssociate_id();
            String password=satya.getPassword();
            Credentials found = credentialService.getUser(id,password);
            assertThat(found.getAssociate_id()).isEqualTo(id);
            assertThat(found.getPassword()).isEqualTo(password);
        }
        
        @Test
        public void whenUpdateCredentials_thenReturnCredentials() {
            String id = satya.getAssociate_id();
            Credentials found = credentialService.updateCredentials(id,"@Satya2");
            //System.out.println(satya);
            assertThat(found.getAssociate_id()).isEqualTo(id);
            assertThat(found.getPassword()).isEqualTo(satya.getPassword());
        }
}
