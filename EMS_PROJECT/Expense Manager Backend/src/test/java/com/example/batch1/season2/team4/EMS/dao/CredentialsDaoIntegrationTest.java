package com.example.batch1.season2.team4.EMS.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.batch1.season2.team4.EMS.model.Credentials;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@AutoConfigureTestDatabase(replace=Replace.NONE)
@DataJpaTest
public class CredentialsDaoIntegrationTest {
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private CredentialsDao credentialsDao;
    
    @Test
    public void whenFindById_thenReturnCredentials() {
        Credentials satya = new Credentials("GGGAV8HGSGVDCB32" , "@Satya1" , "Developer");
        entityManager.persistAndFlush(satya);
        
        Credentials fromDB = credentialsDao.findById(satya.getAssociate_id()).orElse(null);
        assertThat(fromDB.getPassword()).isEqualTo(satya.getPassword());
        assertThat(fromDB.getRole()).isEqualTo(satya.getRole());
        
    }
    
    @Test
    public void whenFindByAssociateIdAndPassword_thenReturnCredentials() {
        Credentials satya = new Credentials("GGGAV8HGSGVDCB32" , "@Satya1" , "Developer");
        entityManager.persistAndFlush(satya);
        
        Credentials fromDB = credentialsDao.findByAssociateIdAndPassword(satya.getAssociate_id(),satya.getPassword());
        assertThat(fromDB.getAssociate_id()).isEqualTo(satya.getAssociate_id());
        assertThat(fromDB.getPassword()).isEqualTo(satya.getPassword());
        assertThat(fromDB.getRole()).isEqualTo(satya.getRole());
        
    }
    
    @Test
    public void whenUpdateCredential_thenUpdatedReturnCredentials() {

        Credentials satya = new Credentials("GGGAV8HGSGVDCB32" , "@Satya1" , "Developer");
        Credentials Satya = new Credentials("GGGAV8HGSGVDCB32" , "@Satya2" , "Developer");
        
        entityManager.persistAndFlush(satya);
        credentialsDao.save(satya);
        
        Credentials fromDB = credentialsDao.findById(satya.getAssociate_id()).orElse(null);
    
        credentialsDao.save(Satya);
        Credentials fromDB2 = credentialsDao.findById(Satya.getAssociate_id()).orElse(null);
        assertThat(fromDB.getPassword()).isEqualTo(fromDB2.getPassword());
    }
    
    

}


