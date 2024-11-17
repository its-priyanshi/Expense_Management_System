package com.example.batch1.season2.team4.EMS.controller;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.batch1.season2.team4.EMS.model.Associates;
import com.example.batch1.season2.team4.EMS.model.ExpenseTransaction;
import com.example.batch1.season2.team4.EMS.service.AssociateService;

class EmployeeControllerIntegrationTest {

    @Mock
    private AssociateService service;
    
    @InjectMocks
    private EmployeeController controller;
    
    @Autowired
    private MockMvc mvc;
    
    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        mvc=MockMvcBuilders.standaloneSetup(controller).build();
    }
    
    
    @Test
    public void givenEmployee_whenGetEmployee_thenReturnJsonArray() throws Exception {
        Associates anurag = new Associates("E1H0A4TVX77D10BC","Anurag","Analyst","GGGAV8HGSGVDCB31","Anurag@ADP.COM",450000,0);
        given(service.getAssociateById(anurag.getAssociateId())).willReturn(anurag);
        mvc.perform(get("/employee/getEmployee/{id}",anurag.getAssociateId()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.associateName", is(anurag.getAssociateName())));
        verify(service, VerificationModeFactory.times(1)).getAssociateById(anurag.getAssociateId());
        reset(service);
    }
    
    @Test
    public void givenEmployee_whenGetExpense_thenReturnJsonArray() throws Exception {
        ExpenseTransaction exp = new ExpenseTransaction(38362,"E1VH4F0XGJX4Z09C","medical_expense",Date.valueOf("2023-11-10"),Date.valueOf("2023-11-16"),50000,new byte[]{1,2,3},"Take care","accept",Date.valueOf("2023-11-26"),Date.valueOf("2023-11-26"));
        given(service.getExpenseById(exp.getExpenseId())).willReturn(exp);
        mvc.perform(get("/employee/getExpenseById/{id}",exp.getExpenseId()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.associateId", is(exp.getAssociateId())));
        verify(service, VerificationModeFactory.times(1)).getExpenseById(exp.getExpenseId());
        reset(service);
    }
    
    @Test
    public void givenAssociateId_whenGetAmountByStatus_thenReturnJsonArray() throws Exception {
    	
    	ExpenseTransaction exp = new ExpenseTransaction(38362,"E1VH4F0XGJX4Z09C","medical_expense",Date.valueOf("2023-11-10"),Date.valueOf("2023-11-16"),50000,new byte[]{1,2,3},"Take care","accept",Date.valueOf("2023-11-26"),Date.valueOf("2023-11-26"));
        given(service.getAmountByStatusWise(exp.getAssociateId(),exp.getStatus())).willReturn(50000);
        
        mvc.perform(get("/employee/getAmountByStatus/{Id}",exp.getAssociateId()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
        
        verify(service, VerificationModeFactory.times(1)).getAmountByStatusWise(exp.getAssociateId(),exp.getStatus());
        reset(service);
    }
    
    @Test
    public void givenAssociateId_whenGetCountByStatus_thenReturnJsonArray() throws Exception {
    	
    	ExpenseTransaction exp = new ExpenseTransaction(38362,"E1VH4F0XGJX4Z09C","medical_expense",Date.valueOf("2023-11-10"),Date.valueOf("2023-11-16"),50000,new byte[]{1,2,3},"Take care","accept",Date.valueOf("2023-11-26"),Date.valueOf("2023-11-26"));
        given(service.getCountByStatusWise(exp.getAssociateId(),exp.getStatus())).willReturn(1);
        
        mvc.perform(get("/employee/getCountByStatus/{Id}",exp.getAssociateId()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
        
        verify(service, VerificationModeFactory.times(1)).getCountByStatusWise(exp.getAssociateId(),exp.getStatus());
        reset(service);
    }
    
    /*
     @Test
        public void givenTransaction_whenGetTransacation_thenReturnJsonArray() throws Exception {
            ExpenseTransaction exp1 = new ExpenseTransaction(38362,"E1VH4F0XGJX4Z09C","medical_expense",Date.valueOf("2023-11-10"),Date.valueOf("2023-11-16"),50000,new byte[]{1,2,3},"Take care","accept",Date.valueOf("2023-11-26"),Date.valueOf("2023-11-26"));
            ExpenseTransaction exp2 = new ExpenseTransaction(12345,"HGHASV4554GHSH","Relocation",Date.valueOf("2022-02-20"),Date.valueOf("2022-02-21"),25000,new byte[]{1,2,3},"More Details Required","Rejected",Date.valueOf("2022-02-20"),Date.valueOf("2022-02-21"));
            
            List<ExpenseTransaction> allEmpTransaction = Arrays.asList(exp1 , exp2);
            
            given(service.getAllTransactions()).willReturn(Optional.of(allEmpTransaction));
            
            mvc.perform(get("/employee/transactions").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].expenseType", is(exp1.getExpenseType())))
            .andExpect(jsonPath("$[1].expenseType", is(exp2.getExpenseType())));
            
            verify(service, VerificationModeFactory.times(1)).getAllTransactions();
            reset(service);
        }
     
     
     @Test
     public void givenExpense_whenGetExpenseByType_thenreturnJsonArray() throws Exception
     {
         ExpenseTransaction exp1 = new ExpenseTransaction(38362,"E1VH4F0XGJX4Z09C","medical_expense",Date.valueOf("2023-11-10"),Date.valueOf("2023-11-16"),50000,new byte[]{1,2,3},"Take care","accept",Date.valueOf("2023-11-26"),Date.valueOf("2023-11-26"));
         ExpenseTransaction exp2 = new ExpenseTransaction(12345,"HGHASV4554GHSH","Relocation",Date.valueOf("2022-02-20"),Date.valueOf("2022-02-21"),25000,new byte[]{1,2,3},"More Details Required","Rejected",Date.valueOf("2022-02-20"),Date.valueOf("2022-02-21"));
         
         List<ExpenseTransaction> allEmpTransaction = Arrays.asList(exp1 , exp2);
         
         given(service.findByExpenseType(exp1.getExpenseType())).willReturn(Optional.of(allEmpTransaction));
         
         mvc.perform(get("/employee/ExpenseCategory/{expenseType}",exp1.getExpenseType()).contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$", hasSize(2)))
         .andExpect(jsonPath("$[0].expenseType", is(exp1.getExpenseType())))
         .andExpect(jsonPath("$[1].expenseType", is(exp2.getExpenseType())));
         
         verify(service, VerificationModeFactory.times(1)).findByExpenseType(exp1.getExpenseType());
         reset(service);
     }
     */

    
    

    
}
