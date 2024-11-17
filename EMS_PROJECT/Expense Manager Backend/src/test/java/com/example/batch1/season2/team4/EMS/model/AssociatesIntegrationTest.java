package com.example.batch1.season2.team4.EMS.model;
import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AssociatesIntegrationTest {

    
    Associates associate = null;
    
    @BeforeEach
    void setUp() throws Exception {
        associate = new Associates("100","Bob","Developer","200","bob@gmail.com",10000,1);
    }

    @AfterEach
    void tearDown() throws Exception {
        associate=null;
    }

    @Test
    void testGetAssociateId() {
        assertEquals("100",associate.getAssociateId());
    }

    @Test
    void testSetAssociateId() {
        associate.setAssociateId("101");
        assertEquals("101", associate.getAssociateId());
        }

    @Test
    void testGetAssociateName() {
        assertEquals("Bob",associate.getAssociateName());
    }

    @Test
    void testSetAssociateName() {
        associate.setAssociateName("Alex");
        assertEquals("Alex",associate.getAssociateName());
    }

    @Test
    void testGetPosition() {
        assertEquals("Developer",associate.getPosition());
    }

    @Test
    void testSetPosition() {
        associate.setPosition("Analyst");
        assertEquals("Analyst",associate.getPosition());
    }

    @Test
    void testGetReportingManagerId() {
        assertEquals("200",associate.getReportingManagerId());
    }

    @Test
    void testSetReportingManagerId() {
        associate.setReportingManagerId("201");
        assertEquals("201",associate.getReportingManagerId());
    }

    @Test
    void testGetEmailId() {
        assertEquals("bob@gmail.com",associate.getEmailId());
    }

    @Test
    void testSetEmailId() {
        associate.setEmailId("Alex@gmail.com");
        assertEquals("Alex@gmail.com",associate.getEmailId());
    }

    @Test
    void testGetAmount() {
        assertEquals(10000,associate.getAmount());
    }

    @Test
    void testSetAmount() {
        associate.setAmount(6000);
        assertEquals(6000,associate.getAmount());
    }

    @Test
    void testGetNotificationCount() {
        assertEquals(1,associate.getNotificationCount());
    }

    @Test
    void testSetNotificationCount() {
        associate.setNotificationCount(0);
        assertEquals(0,associate.getNotificationCount());
    }

}


