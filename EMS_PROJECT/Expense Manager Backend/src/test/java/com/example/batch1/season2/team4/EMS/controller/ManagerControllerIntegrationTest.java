package com.example.batch1.season2.team4.EMS.controller;

import java.sql.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.example.batch1.season2.team4.EMS.Application;
import com.example.batch1.season2.team4.EMS.model.Associates;
import com.example.batch1.season2.team4.EMS.model.ExpenseTransaction;
import com.example.batch1.season2.team4.EMS.service.ManagerService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Application.class })
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc 
public class ManagerControllerIntegrationTest {
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ManagerService mngrService;
	
	
	@Test
	public void givenActionDetails_performActionOnExpense_returnTransaction() throws Exception {
		ExpenseTransaction updatedExpense=new ExpenseTransaction(1001,"AS123RT","internet_expense",Date.valueOf("2023-11-22"),Date.valueOf("2023-11-25"),45000,new byte[] {},"approved","accept",Date.valueOf("2023-11-25"),Date.valueOf("2023-11-27"));
		ExpenseTransaction unupdatedTransaction=new ExpenseTransaction(1001,"AS123RT","internet_expense",Date.valueOf("2023-11-22"),Date.valueOf("2023-11-25"),45000,new byte[] {},"","pending",Date.valueOf("2023-11-25"),Date.valueOf("2023-11-27"));
		
		given(mngrService.actionOnExpenseRequest(unupdatedTransaction.getExpenseId(), "accept", "approved")).willReturn(updatedExpense);
		
		mvc.perform(post("/manager/actionOnExpense/{expId}/{action}/{comments}",unupdatedTransaction.getExpenseId(),"accept","approved")
		.contentType(MediaType.APPLICATION_JSON)
		.content(JsonUtil.toJson(updatedExpense)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.status",is(updatedExpense.getStatus())));
		
		verify(mngrService, VerificationModeFactory.times(1)).actionOnExpenseRequest(unupdatedTransaction.getExpenseId(), "accept", "approved");
		reset(mngrService);
		
	}
	
	/*
	@Test
	public void givenManagerId_getTransactionsPending_returnTransaction() throws Exception {
		ExpenseTransaction expense1=new ExpenseTransaction(1001,"AS123RT","internet_expense",Date.valueOf("2023-11-22"),Date.valueOf("2023-11-25"),45000,new byte[] {},"","pending",Date.valueOf("2023-11-25"),Date.valueOf("2023-11-27"));
		ExpenseTransaction expense2=new ExpenseTransaction(1002,"AS123RT","meal_expense",Date.valueOf("2023-11-22"),Date.valueOf("2023-11-25"),45000,new byte[] {},"","pending",Date.valueOf("2023-11-25"),Date.valueOf("2023-11-27"));
		//ExpenseTransaction unupdatedTransaction=new ExpenseTransaction(1001,"AS123RT","internet_expense",Date.valueOf("2023-11-22"),Date.valueOf("2023-11-25"),45000,new byte[] {},"","pending",Date.valueOf("2023-11-25"),Date.valueOf("2023-11-27"));
		List<ExpenseTransaction> pendingExpenses=new ArrayList<>();
		pendingExpenses.add(expense1);
		pendingExpenses.add(expense2);
		
		given(mngrService.getAllPendingExpensesByAssociateId("AS123RT").get()).willReturn(pendingExpenses);
		
		mvc.perform(get("/manager/getExpenseByEmpId/{mngrId}","AS123RT").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].expenseType",is(expense1.getExpenseType())))
		.andExpect(jsonPath("$[1].expenseType",is(expense2.getExpenseType())));
		
		verify(mngrService, VerificationModeFactory.times(1)).getAllPendingExpensesByAssociateId("AS123RT");
		reset(mngrService);
		
	}*/
	
	@Test
	public void givenManagerId_getSubAssociates_returnAssociates() throws Exception {
		Associates srikar=new Associates("AS123RT","Srikar","Employee","ABC123","srikar@xyz.com",0,0);
		Associates surya=new Associates("AS123RU","Surya","Employee","ABC123","surya@xyz.com",0,0);
		Associates anurag=new Associates("AS123RS","Anurag","Manager","ABC123","anurag@xyz.com",100000,0);
		Associates joe =new Associates("ABC123","joe","Manager","ACD563","joe@xyz.com",100000,0);
		//ExpenseTransaction unupdatedTransaction=new ExpenseTransaction(1001,"AS123RT","internet_expense",Date.valueOf("2023-11-22"),Date.valueOf("2023-11-25"),45000,new byte[] {},"","pending",Date.valueOf("2023-11-25"),Date.valueOf("2023-11-27"));
		
		List<Associates> associates=new ArrayList<>();
		associates.add(srikar);
		associates.add(surya);
		associates.add(anurag);
		
		given(mngrService.getEmpByManagerId("ABC123")).willReturn(Optional.of(associates));
		
		mvc.perform(get("/manager/getEmpByMngId/{mngId}","ABC123").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].associateName",is(srikar.getAssociateName())))
		.andExpect(jsonPath("$[1].associateName",is(surya.getAssociateName())));
		
		verify(mngrService, VerificationModeFactory.times(1)).getEmpByManagerId("ABC123");
		reset(mngrService);
		
	}
	
	@Test
	public void givenAssociateId_getAmountByMonth_returnAmountData() throws Exception {
		ExpenseTransaction expense1=new ExpenseTransaction(1001,"AS123RT","internet_expense",Date.valueOf("2023-11-22"),Date.valueOf("2023-11-25"),45000,new byte[] {},"approved","accept",Date.valueOf("2023-11-25"),Date.valueOf("2023-11-27"));
		ExpenseTransaction expense2=new ExpenseTransaction(1002,"AS123RT","internet_expense",Date.valueOf("2023-11-05"),Date.valueOf("2023-11-07"),45000,new byte[] {},"approved","accept",Date.valueOf("2023-11-09"),Date.valueOf("2023-11-09"));
		ExpenseTransaction expense3=new ExpenseTransaction(1003,"AS123RT","internet_expense",Date.valueOf("2023-11-22"),Date.valueOf("2023-11-25"),45000,new byte[] {},"","pending",Date.valueOf("2023-11-26"),Date.valueOf("2023-11-27"));
		ExpenseTransaction expense4 =new ExpenseTransaction(1004,"AS123RT","internet_expense",Date.valueOf("2023-11-22"),Date.valueOf("2023-11-25"),45000,new byte[] {},"insufficient data","rejected",Date.valueOf("2023-11-26"),null);
		
		Associates srikar=new Associates("AS123RT","Srikar","Employee","ABC123","srikar@xyz.com",0,0);
		Associates surya=new Associates("AS123RU","Surya","Employee","ABC123","surya@xyz.com",0,0);
		Associates anurag=new Associates("AS123RS","Anurag","Manager","ABC123","anurag@xyz.com",100000,0);
		Associates joe =new Associates("ABC123","joe","Manager","ACD563","joe@xyz.com",100000,0);
		//ExpenseTransaction unupdatedTransaction=new ExpenseTransaction(1001,"AS123RT","internet_expense",Date.valueOf("2023-11-22"),Date.valueOf("2023-11-25"),45000,new byte[] {},"","pending",Date.valueOf("2023-11-25"),Date.valueOf("2023-11-27"));
		
		List<Associates> associates=new ArrayList<>();
		associates.add(srikar);
		associates.add(surya);
		associates.add(anurag);
		
		given(mngrService.getEmpByManagerId("ABC123")).willReturn(Optional.of(associates));
		//Map<String, Float> mp = new LinkedHashMap<>();
		/*mp.put("Pending",45000.0F);
		mp.put("Approved",90000.0F);
		mp.put("Rejected",45000.0F);*/
		List<Float> mockData=new ArrayList<>();
		mockData.add(Float.parseFloat("90000.0"));
		given(mngrService.getAmount("accept","AS123RT",11)).willReturn(mockData);
		mvc.perform(get("/manager/getMngAmountChart/{associate_id}/{month}","ABC123",11).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.Approved",is((double)mockData.get(0))));
		verify(mngrService, VerificationModeFactory.times(1)).getEmpByManagerId("ABC123");
		reset(mngrService);
		
	}
	
	@Test
    public void givenAssociateId_whenGetBudgetById_thenReturnJsonArray() throws Exception {
    	
		Associates anurag=new Associates("AS123RS","Anurag","Manager","ABC123","anurag@xyz.com",100000,0);
		
        given(mngrService.getBudgetById(anurag.getAssociateId())).willReturn(100000.0F);
        
        mvc.perform(get("/manager/getBudget/{mngrId}",anurag.getAssociateId()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
        
        verify(mngrService, VerificationModeFactory.times(1)).getBudgetById(anurag.getAssociateId());
        reset(mngrService);
    }

}