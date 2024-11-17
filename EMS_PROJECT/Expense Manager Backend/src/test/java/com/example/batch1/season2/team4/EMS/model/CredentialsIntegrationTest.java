package com.example.batch1.season2.team4.EMS.model;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CredentialsIntegrationTest {

    private Credentials credential=null;
    
    @BeforeEach
    void setUp() throws Exception
    {
        credential= new Credentials("GHGDH4645BVJ","@Jackadp5","Developer");
    }
    
    @AfterEach
    void tearDown() throws Exception {
        credential=null;
    }
    
    @Test
    void testGetAssociate_id() {
        assertEquals("GHGDH4645BVJ", credential.getAssociate_id());
    }
    
    @Test
    void testGetPassword()
    {
        assertEquals("@Jackadp5", credential.getPassword());
    }
    
    @Test
    void testGetRole()
    {
        assertEquals("Developer", credential.getRole());
    }
    
    @Test
    void testSetAssociate_id() {
        credential.setAssociate_id("GHGDH4645BVP");
        assertEquals("GHGDH4645BVP", credential.getAssociate_id());
    }
    
    @Test
    void testSetPassword() {
        credential.setPassword("@Bondadp7");
        assertEquals("@Bondadp7", credential.getPassword());
    }
    
    @Test
    void testSetRole() {
        credential.setRole("Analyst");
        assertEquals("Analyst", credential.getRole());
    }
}

