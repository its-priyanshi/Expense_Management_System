package com.example.batch1.season2.team4.EMS.dao;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.batch1.season2.team4.EMS.model.ExpenseTransaction;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@AutoConfigureTestDatabase(replace=Replace.NONE)
@DataJpaTest
class ExpenseTransactionDaoIntegrationTest {
    
    @Autowired
    TestEntityManager entityManager;
    @Autowired 
    ExpenseTransactionDao expensedao;
    
    private ExpenseTransaction exp1,exp2,exp3,exp4;
    private byte[] byteArray = {41, 42 , 43};

    @Test
    public void whenFindById_thenReturnExpenseTransaction() {
        exp1 = new ExpenseTransaction(1005,"100","internet_expense",Date.valueOf("2023-11-09"),Date.valueOf("2023-12-11"),2000,byteArray,"Waiting","Pending",Date.valueOf("2023-12-12"),Date.valueOf("2023-12-13"));
        entityManager.persistAndFlush(exp1);
        ExpenseTransaction result=expensedao.findById(1005).orElse(null);
        System.out.println(result.toString());
        assertThat(exp1.getAssociateId()).isEqualTo(result.getAssociateId());
    }
    
    @Test
    public void whenFindByInvalidId_thenReturnException() {
        ExpenseTransaction fromDb = expensedao.findById(-1).orElse(null);
        assertThat(fromDb).isNull();
    }
    
    @Test
    public void whenFindByExpenseTypeOrderByAppliedDateDesc_thenReturnExpenseTransaction() {
        exp1 = new ExpenseTransaction(1005,"100","internet_expense",Date.valueOf("2023-11-09"),Date.valueOf("2023-12-11"),2000,byteArray,"Waiting","Pending",Date.valueOf("2023-12-12"),Date.valueOf("2023-12-13"));
        exp2 = new ExpenseTransaction(1006,"101","food_expense",Date.valueOf("2023-10-09"),Date.valueOf("2023-10-09"),5000,byteArray,"Done","Approved",Date.valueOf("2023-10-10"),Date.valueOf("2023-10-11"));
        exp3 = new ExpenseTransaction(1007,"102","travel_expense",Date.valueOf("2023-11-09"),Date.valueOf("2023-11-12"),2000,byteArray,"Not done","Rejected",Date.valueOf("2023-12-12"),Date.valueOf("2023-12-13"));
        
      
        entityManager.persistAndFlush(exp1);
        entityManager.persistAndFlush(exp2);
        entityManager.persistAndFlush(exp3);
    
        List<ExpenseTransaction> l1 = expensedao.findByExpenseTypeOrderByAppliedDateDesc("food_expense").get();
        assertThat(l1).hasSize(1);
        
    }
    
    @Test
    public void whenFindByExpenseTypeOrderByAppliedDateDesc_thenReturnNull() {
        
        List<ExpenseTransaction> l1 = expensedao.findByExpenseTypeOrderByAppliedDateDesc("food").get();
        assertThat(l1).hasSize(0);
    }
    
    @Test
    public void whenFindAllByOrderByAppliedDateDesc_thenReturnExpenseTransaction() {
        
        exp1 = new ExpenseTransaction(1005,"100","internet_expense",Date.valueOf("2023-11-09"),Date.valueOf("2023-12-11"),2000,byteArray,"Waiting","Pending",Date.valueOf("2023-12-12"),Date.valueOf("2023-12-13"));
        exp2 = new ExpenseTransaction(1006,"101","food_expense",Date.valueOf("2023-10-09"),Date.valueOf("2023-10-09"),5000,byteArray,"Done","Approved",Date.valueOf("2023-10-10"),Date.valueOf("2023-10-11"));
        exp3 = new ExpenseTransaction(1007,"102","travel_expense",Date.valueOf("2023-11-09"),Date.valueOf("2023-11-12"),2000,byteArray,"Not done","Rejected",Date.valueOf("2023-12-12"),Date.valueOf("2023-12-13"));
        
        List<ExpenseTransaction> l1 = expensedao.findAllByOrderByAppliedDateDesc();
        
        entityManager.persistAndFlush(exp1);
        entityManager.persistAndFlush(exp2);
        entityManager.persistAndFlush(exp3);
        
        List<ExpenseTransaction> l2 = expensedao.findAllByOrderByAppliedDateDesc();
        l1.forEach(System.out::println);
        assertThat(l2).hasSize(l1.size() + 3);
        
        
    }
    @Test
    public void whenFindAllByAssociateIdOrderByAppliedDateDesc_thenReturnExpenseTransaction() {
        
        exp1 = new ExpenseTransaction(1005,"100","internet_expense",Date.valueOf("2023-11-09"),Date.valueOf("2023-12-11"),2000,byteArray,"Waiting","Pending",Date.valueOf("2023-12-12"),Date.valueOf("2023-12-13"));
        exp2 = new ExpenseTransaction(1006,"101","food_expense",Date.valueOf("2023-10-09"),Date.valueOf("2023-10-09"),5000,byteArray,"Done","Approved",Date.valueOf("2023-10-10"),Date.valueOf("2023-10-11"));
        exp3 = new ExpenseTransaction(1007,"102","travel_expense",Date.valueOf("2023-11-09"),Date.valueOf("2023-11-12"),2000,byteArray,"Not done","Rejected",Date.valueOf("2023-12-12"),Date.valueOf("2023-12-13"));
        
        List<ExpenseTransaction> l1 = expensedao.findAllByAssociateIdOrderByAppliedDateDesc("100");
        
        entityManager.persistAndFlush(exp1);
        entityManager.persistAndFlush(exp2);
        entityManager.persistAndFlush(exp3);
        
        List<ExpenseTransaction> l2 = expensedao.findAllByAssociateIdOrderByAppliedDateDesc("100");
        
        assertThat(l2).hasSize(l1.size() + 1);
    }
    
    @Test
    public void whenFindAllByAssociateIdOrderByAppliedDateDesc_thenReturnNull() {
        
        exp1 = new ExpenseTransaction(1005,"100","internet_expense",Date.valueOf("2023-11-09"),Date.valueOf("2023-12-11"),2000,byteArray,"Waiting","Pending",Date.valueOf("2023-12-12"),Date.valueOf("2023-12-13"));
        exp2 = new ExpenseTransaction(1006,"101","food_expense",Date.valueOf("2023-10-09"),Date.valueOf("2023-10-09"),5000,byteArray,"Done","Approved",Date.valueOf("2023-10-10"),Date.valueOf("2023-10-11"));
        exp3 = new ExpenseTransaction(1007,"102","travel_expense",Date.valueOf("2023-11-09"),Date.valueOf("2023-11-12"),2000,byteArray,"Not done","Rejected",Date.valueOf("2023-12-12"),Date.valueOf("2023-12-13"));
        
        List<ExpenseTransaction> l1 = expensedao.findAllByAssociateIdOrderByAppliedDateDesc("108");
        
        entityManager.persistAndFlush(exp1);
        entityManager.persistAndFlush(exp2);
        entityManager.persistAndFlush(exp3);
        
        List<ExpenseTransaction> l2 = expensedao.findAllByAssociateIdOrderByAppliedDateDesc("108");
        
        assertThat(l2).hasSize(l1.size());
    }
    
    @Test
    public void whenFindAllByExpenseType_thenReturnExpenseTransaction() {
        
        exp1 = new ExpenseTransaction(1005,"100","internet_expense",Date.valueOf("2023-11-09"),Date.valueOf("2023-12-11"),2000,byteArray,"Waiting","Pending",Date.valueOf("2023-12-12"),Date.valueOf("2023-12-13"));
        exp2 = new ExpenseTransaction(1006,"101","food_expense",Date.valueOf("2023-10-09"),Date.valueOf("2023-10-09"),5000,byteArray,"Done","Approved",Date.valueOf("2023-10-10"),Date.valueOf("2023-10-11"));
        exp3 = new ExpenseTransaction(1007,"102","travel_expense",Date.valueOf("2023-11-09"),Date.valueOf("2023-11-12"),2000,byteArray,"Not done","Rejected",Date.valueOf("2023-12-12"),Date.valueOf("2023-12-13"));
        
        List<ExpenseTransaction> l1 = expensedao.findAllByExpenseType("travel_expense");
        
        entityManager.persistAndFlush(exp1);
        entityManager.persistAndFlush(exp2);
        entityManager.persistAndFlush(exp3);
        
        List<ExpenseTransaction> l2 = expensedao.findAllByExpenseType("travel_expense");
        assertThat(l2).hasSize(l1.size() + 1);
    }
    
    @Test
    public void whenFindAllByExpenseType_thenReturnNull() {
        
        List<ExpenseTransaction> l1 = expensedao.findAllByExpenseType("travel");
        assertThat(l1).hasSize(0);
    }
    
    @Test
    public void whenSaveExpense_thenReturnExpenseTransaction() {
        
        exp4 = new ExpenseTransaction(1008,"101","internet_expense",Date.valueOf("2023-10-10"),Date.valueOf("2023-11-10"),6000,byteArray,"Done","Approved",Date.valueOf("2023-11-12"),Date.valueOf("2023-11-13"));
        
        List<ExpenseTransaction> l1 = expensedao.findAllByOrderByAppliedDateDesc();
        
        ExpenseTransaction expense = expensedao.save(exp4);
        
        List<ExpenseTransaction> l2 = expensedao.findAllByOrderByAppliedDateDesc();
        
        assertThat(l2).hasSize(l1.size() + 1);
    }
    
    @Test
    public void whenGetCountStatusWiseForId_thenReturnCount() {
    	
        exp2 = new ExpenseTransaction(1006,"101","food_expense",Date.valueOf("2023-10-09"),Date.valueOf("2023-10-09"),5000,byteArray,"Done","Approved",Date.valueOf("2023-10-10"),Date.valueOf("2023-10-11"));
        exp4 = new ExpenseTransaction(1008,"101","internet_expense",Date.valueOf("2023-10-10"),Date.valueOf("2023-11-10"),6000,byteArray,"Done","Pending",Date.valueOf("2023-11-12"),Date.valueOf("2023-11-13"));
      
        entityManager.persistAndFlush(exp2);
        entityManager.persistAndFlush(exp4);
    	
    	int count = expensedao.getCountStatusWiseForId("101", "Pending").get();
    	
    	assertThat(count).isEqualTo(1);
    }
    
    @Test
    public void whenGetCountStatusWiseForInvalidId_thenReturnZero() {
    	
    	exp2 = new ExpenseTransaction(1006,"101","food_expense",Date.valueOf("2023-10-09"),Date.valueOf("2023-10-09"),5000,byteArray,"Done","Approved",Date.valueOf("2023-10-10"),Date.valueOf("2023-10-11"));
        exp4 = new ExpenseTransaction(1008,"101","internet_expense",Date.valueOf("2023-10-10"),Date.valueOf("2023-11-10"),6000,byteArray,"Done","Pending",Date.valueOf("2023-11-12"),Date.valueOf("2023-11-13"));
      
        entityManager.persistAndFlush(exp2);
        entityManager.persistAndFlush(exp4);
        
        int count = expensedao.getCountStatusWiseForId("102", "Approved").get();
        
        assertThat(count).isEqualTo(0);
    }
    
    @Test
    public void whengetAmountStatusWiseForId_thenReturnAmount() {
    	
    	exp2 = new ExpenseTransaction(1006,"101","food_expense",Date.valueOf("2023-10-09"),Date.valueOf("2023-10-09"),5000,byteArray,"Done","Approved",Date.valueOf("2023-10-10"),Date.valueOf("2023-10-11"));
        exp4 = new ExpenseTransaction(1008,"101","internet_expense",Date.valueOf("2023-10-10"),Date.valueOf("2023-11-10"),6000,byteArray,"Done","Pending",Date.valueOf("2023-11-12"),Date.valueOf("2023-11-13"));
      
        entityManager.persistAndFlush(exp2);
        entityManager.persistAndFlush(exp4);
    	
        int amount = expensedao.getAmountStatusWiseForId("101", "Pending").get();
        
        assertThat(amount).isEqualTo(exp4.getClaimAmount());
    }
    
    @Test
    public void whengetAmountStatusWiseForInvalidId_thenReturnZero() {
    	
    	exp2 = new ExpenseTransaction(1006,"101","food_expense",Date.valueOf("2023-10-09"),Date.valueOf("2023-10-09"),5000,byteArray,"Done","Approved",Date.valueOf("2023-10-10"),Date.valueOf("2023-10-11"));
        exp4 = new ExpenseTransaction(1008,"101","internet_expense",Date.valueOf("2023-10-10"),Date.valueOf("2023-11-10"),6000,byteArray,"Done","Pending",Date.valueOf("2023-11-12"),Date.valueOf("2023-11-13"));
      
        entityManager.persistAndFlush(exp2);
        entityManager.persistAndFlush(exp4);
    	
        assertThrows(NoSuchElementException.class,()->{expensedao.getAmountStatusWiseForId("102", "Pending").get();});
    }
}
