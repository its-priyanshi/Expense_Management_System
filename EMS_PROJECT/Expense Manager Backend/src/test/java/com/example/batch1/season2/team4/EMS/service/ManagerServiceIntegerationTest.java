package com.example.batch1.season2.team4.EMS.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.assertj.core.util.Arrays;
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

import com.example.batch1.season2.team4.EMS.dao.AssociateDao;
import com.example.batch1.season2.team4.EMS.dao.ExpenseTransactionDao;
import com.example.batch1.season2.team4.EMS.model.Associates;
import com.example.batch1.season2.team4.EMS.model.ExpenseTransaction;

@ExtendWith(SpringExtension.class)
public class ManagerServiceIntegerationTest {
	private ExpenseTransaction expense1,expense2,expense3,expense4;
	private Associates srikar,anurag,surya,joe;
	@TestConfiguration
	static class AssociatesServiceImplTestContextConfiguration {
		@Bean
	    public ManagerService associateService() {
	        return new ManagerServiceImpl();
	    }
	}
	
	@Autowired
	ManagerService mngrService;
	
	@MockBean
	private ExpenseTransactionDao expenseDao;
	
	@MockBean
	private AssociateDao associatesDao;
	
	@BeforeEach
	public void setUp() {
		expense1=new ExpenseTransaction(1001,"AS123RT","internet_expense",Date.valueOf("2023-11-22"),Date.valueOf("2023-11-25"),45000,new byte[] {},"approved","accept",Date.valueOf("2023-11-25"),Date.valueOf("2023-11-27"));
		expense2=new ExpenseTransaction(1002,"AS123RT","internet_expense",Date.valueOf("2023-11-05"),Date.valueOf("2023-11-07"),45000,new byte[] {},"approved","accept",Date.valueOf("2023-11-09"),Date.valueOf("2023-11-09"));
		expense3=new ExpenseTransaction(1003,"AS123RT","internet_expense",Date.valueOf("2023-11-22"),Date.valueOf("2023-11-25"),45000,new byte[] {},"","pending",Date.valueOf("2023-11-26"),Date.valueOf("2023-11-27"));
		expense4 =new ExpenseTransaction(1004,"AS123RT","internet_expense",Date.valueOf("2023-11-22"),Date.valueOf("2023-11-25"),45000,new byte[] {},"","pending",Date.valueOf("2023-11-26"),null);
		
		srikar=new Associates("AS123RT","Srikar","Employee","ABC123","srikar@xyz.com",0,0);
		surya=new Associates("AS123RU","Surya","Employee","ABC123","surya@xyz.com",0,0);
		anurag=new Associates("AS123RS","Anurag","Manager","ABC123","anurag@xyz.com",100000,0);
		joe =new Associates("ABC123","joe","Manager","ACD563","joe@xyz.com",100000,0);
		
		List<Associates> subordinates=new ArrayList<>();
		subordinates.add(srikar);
		//subordinates.add(surya);
		
		//Mockito.when(associatesDao.findById(srikar.getAssociateId())).thenReturn(Optional.);
		List<ExpenseTransaction> sortedExpenses=new ArrayList<>();
		sortedExpenses.add(expense3);
		sortedExpenses.add(expense1);
		sortedExpenses.add(expense2);
		
		List<Float> approvedExpense=new ArrayList<>();
		approvedExpense.add(90000.0F);
		
		
		Mockito.when(associatesDao.findEmpByManagerId("AS123RS")).thenReturn(subordinates);
		Mockito.when(expenseDao.findAllByAssociateIdOrderByAppliedDateDesc("AS123RT")).thenReturn(sortedExpenses);
		Mockito.when(associatesDao.getAmountByStatusByMonth("accept","AS123RT", 11,2023)).thenReturn(approvedExpense);
		Mockito.when(expenseDao.findById(1004)).thenReturn(Optional.of(expense4));
        Mockito.when(associatesDao.findById("AS123RT")).thenReturn(Optional.of(srikar));
        
        Mockito.when(associatesDao.findById("ABC123")).thenReturn(Optional.of(joe));
        
        Mockito.when(associatesDao.findAmountByAssociateId("AS123RS")).thenReturn(Optional.of(100000.0F));
        Mockito.when(associatesDao.findAmountByAssociateId("AS123R")).thenThrow(new NoSuchElementException());
        
	}
	
	@AfterEach
	public void cleanUp() {
		expense1=null;
		expense2=null;
		expense3=null;
		srikar=null;
		surya=null;
		anurag=null;
	}
	
	private void verifyFindEmpBtManagerIdCalls(String id) {
		Mockito.verify(associatesDao,VerificationModeFactory.times(1)).findEmpByManagerId(id);
	}
	
	private void verifyFindAllByAssociateIdOrderByAppliedDateDesc(String id) {
		Mockito.verify(expenseDao,VerificationModeFactory.times(1)).findAllByAssociateIdOrderByAppliedDateDesc(id);
	}
	
	private void verifyGetAmountByStatusByMonthCalls(String status,String associateId,int month,int year) {
		Mockito.verify(associatesDao,VerificationModeFactory.times(1)).getAmountByStatusByMonth(status, associateId, month, year);
	}
	private void whenGetBudgetByIdIsCalledOnce(String managerId) {
		Mockito.verify(associatesDao , VerificationModeFactory.times(1)).findAmountByAssociateId(managerId);
        Mockito.reset(associatesDao);
	}
	
	@Test
	public void whenGetEmpByManagerId_thenReturnAssociates() {
		String id=anurag.getAssociateId();
		List<Associates> result=mngrService.getEmpByManagerId(id).orElse(null);
		verifyFindEmpBtManagerIdCalls(id);
		List<Associates> subordinates=new ArrayList<>();
		subordinates.add(srikar);
		//subordinates.add(surya);
		assertThat(result).isEqualTo(subordinates);
	}
	
	@Test
	public void whenGetAllPendingTransactions_thenReturnTransactions() {
		String id=anurag.getAssociateId();
		List<ExpenseTransaction> result=mngrService.getAllPendingExpensesByAssociateId(id).orElse(null);
		List<ExpenseTransaction> pendingExpenses=new ArrayList<>();
		pendingExpenses.add(expense3);
		assertThat(result).isEqualTo(pendingExpenses);
	}
	
	@Test
	public void whenGetAmount_ReturnStatusWiseAmount() {
		String id=srikar.getAssociateId();
		List<Float> result=mngrService.getAmount("accept",id,11);
		assertThat(result.get(0)).isEqualTo(90000.0F);
	}
	
	@Test
    public void whenActionOnExpenseRequest_thenReturnExpenseTransaction() {
        int expenseId = expense4.getExpenseId();
        ExpenseTransaction found = mngrService.actionOnExpenseRequest(expenseId,"accept","ok_done");
        assertThat(found.getStatus()).isEqualTo("accept");
        assertThat(found.getManagerComments()).isEqualTo("ok_done");
        assertThat(joe.getAmount()).isEqualTo(55000);
        
    }
	
	@Test
	public void whenGetBudgetById_thenReturnAmount() {
		String associateId = anurag.getAssociateId();
		float amount = mngrService.getBudgetById(associateId);
		whenGetBudgetByIdIsCalledOnce(associateId);
		assertThat(amount).isEqualTo(100000.0F);
	}
	
	@Test
	public void whenGetBudgetById_thenReturnZero() {
		assertThrows(NoSuchElementException.class,()->{mngrService.getBudgetById("AS123R");});
		whenGetBudgetByIdIsCalledOnce("AS123R");
	}
	
}
