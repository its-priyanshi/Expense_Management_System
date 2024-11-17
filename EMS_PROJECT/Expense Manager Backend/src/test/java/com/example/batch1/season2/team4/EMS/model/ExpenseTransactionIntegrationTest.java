package com.example.batch1.season2.team4.EMS.model;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExpenseTransactionIntegrationTest {

    private ExpenseTransaction  transaction=null;
    
    @BeforeEach
    void setUp() throws Exception
    {
        transaction=new ExpenseTransaction(12345,"HGHASV4554GHSH","Relocation",Date.valueOf("2022-02-20"),Date.valueOf("2022-02-21"),25000,new byte[]{1,2,3},"More Details Required","Rejected",Date.valueOf("2022-02-20"),Date.valueOf("2022-02-21"));
    }
    
    @AfterEach
    void tearDown() throws Exception {
        transaction=null;
    }
    
    @Test
    void testGetExpenseId() {
        assertEquals(12345, transaction.getExpenseId());
    }
    
    @Test
    void testGetAssociate_id() {
        assertEquals("HGHASV4554GHSH", transaction.getAssociateId());
    }
    
    @Test
    void testGetExpenseType() {
        assertEquals("Relocation", transaction.getExpenseType());
    }
    
    
    @Test
    void testGetStartDate() {
        assertEquals(Date.valueOf("2022-02-20"), transaction.getStartDate());
    }
    
    @Test
    void testGetEndDate() {
        assertEquals(Date.valueOf("2022-02-21"), transaction.getEndDate());
    }
    
    
    @Test
    void testGetClaimAmount() {
        assertEquals(25000, transaction.getClaimAmount());
    }
    
    
    @Test
    void testGetManagerComments()  {
        assertEquals("More Details Required", transaction.getManagerComments());
    }
    
    
    @Test
    void testGetStatus()  {
        assertEquals("Rejected", transaction.getStatus());
    }
    
    
    @Test
    void testGetAppliedDate() {
        assertEquals(Date.valueOf("2022-02-20"), transaction.getAppliedDate());
    }
    
    
    @Test
    void testGetApprovedDate() {
        assertEquals(Date.valueOf("2022-02-21"), transaction.getApprovedDate());
    }
    
    @Test
    void testSetExpenseId() {
        transaction.setExpenseId(12346);
        assertEquals(12346, transaction.getExpenseId());
    }
    
    @Test
    void testSetAssociate_Id() {
        transaction.setAssociateId("HGHASV4554GHSS");
        assertEquals("HGHASV4554GHSS", transaction.getAssociateId());
    }
    
    @Test
    void testSetExpenseType() {
        transaction.setExpenseType("Medical");
        assertEquals("Medical", transaction.getExpenseType());
    }
    
    @Test
    void testSetStartDate() {
        transaction.setStartDate(Date.valueOf("2021-03-30"));
        assertEquals(Date.valueOf("2021-03-30"), transaction.getStartDate());
    }
    
    @Test
    void testSetEndDate() {
        transaction.setEndDate(Date.valueOf("2021-03-31"));
        assertEquals(Date.valueOf("2021-03-31"), transaction.getEndDate());
    }
    
    @Test
    void testSetClaimAmount() {
        transaction.setClaimAmount(3000);
        assertEquals(3000, transaction.getClaimAmount());
    }
    
    @Test
    void testSetManagerComments()  {
        transaction.setManagerComments("Approved");
        assertEquals("Approved", transaction.getManagerComments());
    }
    
    @Test
    void testSetStatus()  {
        transaction.setStatus("Accept");
        assertEquals("Accept", transaction.getStatus());
    }
    
    @Test
    void testSetAppliedDate() {
        transaction.setAppliedDate(Date.valueOf("2021-03-30"));
        assertEquals(Date.valueOf("2021-03-30"), transaction.getAppliedDate());
    }
    
    @Test
    void testSetApprovedDate() {
        transaction.setApprovedDate(Date.valueOf("2021-03-31"));
        assertEquals(Date.valueOf("2021-03-31"), transaction.getApprovedDate());
    }
    
    
}

