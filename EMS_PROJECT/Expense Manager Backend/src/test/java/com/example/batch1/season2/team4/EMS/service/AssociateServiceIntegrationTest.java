package com.example.batch1.season2.team4.EMS.service;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

import com.example.batch1.season2.team4.EMS.dao.AssociateDao;
import com.example.batch1.season2.team4.EMS.dao.ExpenseTransactionDao;
import com.example.batch1.season2.team4.EMS.model.Associates;
import com.example.batch1.season2.team4.EMS.model.ExpenseTransaction;



@ExtendWith(SpringExtension.class)
class AssociateServiceIntegrationTest {
    
    private Associates Joe,Alex,Bob;
    private ExpenseTransaction exp1,exp2,exp3;
    private byte[] byteArray = {41, 42 , 43};
    
    @TestConfiguration
    static class AssociatesServiceImplTestContextConfiguration {
        @Bean
        public AssociateService associateService() {
            return new AssociateServiceImpl();
        }
    }
    
    @Autowired
    private AssociateService associateService;

    @MockBean
    private AssociateDao associateRepository;
    
    @MockBean
    private ExpenseTransactionDao transactionRepository;
    
    
    @BeforeEach
    void setUp() throws Exception {
        
        Joe =  new Associates("100","Joe","Joe@gmail.com","Director","200",50000,0);
        Alex =  new Associates("101","Alex","Alex@gmail.com","Analyst","201",60000,0);
        Bob =  new Associates("102","Bob","Bob@gmail.com","Developer","202",80000,0);
        
        
        exp1 = new ExpenseTransaction(1005,"100","internet_expense",Date.valueOf("2023-11-09"),Date.valueOf("2023-12-11"),2000,byteArray,"Waiting","Pending",Date.valueOf("2023-12-12"),Date.valueOf("2023-12-13"));
        exp2 = new ExpenseTransaction(1006,"101","food_expense",Date.valueOf("2023-10-09"),Date.valueOf("2023-10-09"),5000,byteArray,"Done","Approved",Date.valueOf("2023-10-10"),Date.valueOf("2023-10-11"));
        exp3 = new ExpenseTransaction(1007,"102","travel_expense",Date.valueOf("2023-11-09"),Date.valueOf("2023-11-12"),2000,byteArray,"Not done","Rejected",Date.valueOf("2023-12-12"),Date.valueOf("2023-12-13"));
        
        List<Associates> emp = Arrays.asList(Alex, Bob);
        List<Associates> emptyAssociates = new ArrayList<Associates>();
        List<ExpenseTransaction> empExpenses = Arrays.asList(exp1,exp2,exp3);
        List<ExpenseTransaction> emptytransaction = new ArrayList<ExpenseTransaction>();
        
        List<Integer> amountReturns = new ArrayList<>();            
        amountReturns.add(2000);
        
        List<Integer> amountNull = new ArrayList<>();
        
        List<Float> amountFloatReturns = new ArrayList<>();            
        amountFloatReturns.add(5000.0F);
        
        List<Float> amountFloatNull = new ArrayList<>();
        
        int countOne = 1,countZero =0,amount = 2000;
        
        Mockito.when(associateRepository.findById(Alex.getAssociateId())).thenReturn(Optional.of(Alex));
        Mockito.when(associateRepository.findById("-1")).thenThrow(new NoSuchElementException());
        
        Mockito.when(transactionRepository.findById(exp3.getExpenseId())).thenReturn(Optional.of(exp3));
        Mockito.when(transactionRepository.findById(-1)).thenThrow(new NoSuchElementException());
        
        Mockito.when(transactionRepository.save(exp2)).thenReturn(exp2);
        
        Mockito.when(associateRepository.getAmountByTypeByMonth("internet_expense","100",12,2023)).thenReturn(amountReturns);
        Mockito.when(associateRepository.getAmountByTypeByMonth("Food", "100", 12, 2023)).thenReturn(amountNull);
        
        Mockito.when(associateRepository.getAmountByStatusByMonthByType("Approved", "101", 10, 2023,"food_expense")).thenReturn(amountFloatReturns);
        Mockito.when(associateRepository.getAmountByStatusByMonthByType("Pending", "101", 10, 2023,"travel_expense")).thenReturn(amountFloatNull);
        
        Mockito.when(transactionRepository.getCountStatusWiseForId("100", "Pending")).thenReturn(Optional.of(countOne));
        Mockito.when(transactionRepository.getCountStatusWiseForId("103", "Pending")).thenReturn(Optional.of(countZero));
        
        Mockito.when(transactionRepository.getAmountStatusWiseForId("102", "Rejected")).thenReturn(Optional.of(amount));
        Mockito.when(transactionRepository.getAmountStatusWiseForId("103", "Approved")).thenThrow(new NoSuchElementException());
    }
    @AfterEach
    void tearDown() throws Exception {
        Joe=null;
        Alex=null;
        Bob=null;
        
        exp1=null;
        exp2=null;
        exp3=null;
    }
    
    
    private void verifyFindByIdIsCalledOnce(String id) {
        Mockito.verify(associateRepository , VerificationModeFactory.times(1)).findById(id);
        Mockito.reset(associateRepository);
    }
    
    @Test
    public void whenGetAssociateById_thenReturnAssociate() {
        String id = Alex.getAssociateId();
        Associates found = associateService.getAssociateById(id);
        verifyFindByIdIsCalledOnce(id);
        assertThat(found.getAssociateId()).isEqualTo(id);
    }
    
    @Test
    public void whenInvalidId_thenReturnError() {
        assertThrows(NoSuchElementException.class,()->{associateService.getAssociateById("-1");});
        verifyFindByIdIsCalledOnce("-1");
    
}
    
    private void verifyFindByExpenseIdIsCalledOnce(int id) {
        Mockito.verify(transactionRepository , VerificationModeFactory.times(1)).findById(id);
        Mockito.reset(transactionRepository);
    }
    
    @Test
    public void whenGetExpenseById_thenReturnExpenseTransaction() {
        int id = exp3.getExpenseId();
        ExpenseTransaction found = associateService.getExpenseById(id);
        verifyFindByExpenseIdIsCalledOnce(id);
        assertThat(found.getExpenseId()).isEqualTo(id);
    }
    
    @Test
    public void whenInvalidExpenseId_thenReturnError() {
        assertThrows(NoSuchElementException.class,()->{associateService.getExpenseById(-1);});
        verifyFindByExpenseIdIsCalledOnce(-1);
    
    }
    
    private void verifyaddExpenseIsCalledOnce(ExpenseTransaction expense) {
        Mockito.verify(transactionRepository , VerificationModeFactory.times(1)).save(expense);
        Mockito.reset(transactionRepository);
    }
    
    @Test
    public void whenaddExpense_thenReturnExpenseTransaction() {
        
        assertThat(exp2).isEqualTo(associateService.addExpense(exp2));
        verifyaddExpenseIsCalledOnce(exp2);
             
    }
    
    private void verifygetTypeIsCalledOnce(String expenseType,String associateId,int month,int year) {
        Mockito.verify(associateRepository , VerificationModeFactory.times(1)).getAmountByTypeByMonth(expenseType,associateId,month,2023);
        Mockito.reset(associateRepository);
    }
    
    
    @Test
    public void whenGetType_thenReturnAmount() {
        
        String expenseType = exp1.getExpenseType();
        String associateId = Joe.getAssociateId();
        
        List<Integer> amount = associateService.getType(expenseType,associateId,12);
        verifygetTypeIsCalledOnce(expenseType,associateId,12,2023);
        assertThat(amount.get(0)).isEqualTo(2000);
    }
    
    @Test
    public void whenInvalidgetType_thenReturnNull() {        
        List<Integer> list = associateService.getType("Food","100", 12);
        verifygetTypeIsCalledOnce("Food","100", 12,2023);
        assertThat(list.get(0)).isEqualTo(0);
    }
    
    private void verifygetStatusIsCalledOnce(String status,String associateId,int month,int year,String expenseType) {
        Mockito.verify(associateRepository , VerificationModeFactory.times(1)).getAmountByStatusByMonthByType(status,associateId,month,2023,expenseType);
        Mockito.reset(associateRepository);
    }
    
    @Test
    public void whenGetStatus_thenReturnAmount() {
        
        String expenseType = exp2.getExpenseType();
        String status = exp2.getStatus();
        String associateId = Alex.getAssociateId();
        
        List<Float> amount = associateService.getStatus(status,associateId,10,expenseType);
        verifygetStatusIsCalledOnce(status,associateId,10,2023,expenseType);
        assertThat(amount.get(0)).isEqualTo(5000.0F);
    }
    
    @Test
    public void whenInvalidgetStatus_thenReturnNull() {        
        List<Float> list = associateService.getStatus("Pending", "101", 10,"travel_expense");
        verifygetStatusIsCalledOnce("Pending", "101", 10, 2023,"travel_expense");
        assertThat(list.get(0)).isEqualTo(0);
    }
    
    private void verifyGetCountByStatusWiseIsCalledOnce(String associateId, String status) {
    	Mockito.verify(transactionRepository , VerificationModeFactory.times(1)).getCountStatusWiseForId(associateId,status);
    	Mockito.reset(associateRepository);
    }
    
    @Test
    public void whenGetCountByStatusWise_thenReturnCount() {
    	
    	int count = associateService.getCountByStatusWise("100","Pending");
    	verifyGetCountByStatusWiseIsCalledOnce("100","Pending");
    	assertThat(count).isEqualTo(1);
    }
    
    @Test
    public void whenGetCountByStatusWise_thenReturnZero() {
    	
    	int count = associateService.getCountByStatusWise("103","Pending");
    	verifyGetCountByStatusWiseIsCalledOnce("103","Pending");
    	assertThat(count).isEqualTo(0);
    }
    
    private void verifyGetAmountByStatusWiseIsCalledOnce(String associateId, String status) {
    	Mockito.verify(transactionRepository , VerificationModeFactory.times(1)).getAmountStatusWiseForId(associateId,status);
    	Mockito.reset(associateRepository);
    }
    
    @Test
    public void whenGetAmountByStatusWise_thenReturnAmount() {
    	
    	int amount = associateService.getAmountByStatusWise("102", "Rejected");
    	verifyGetAmountByStatusWiseIsCalledOnce("102", "Rejected");
    	assertThat(amount).isEqualTo(2000);
    }
    
    @Test
    public void whenGetAmountByStatusWise_thenReturnNull() {    	
    	
    	assertThrows(NoSuchElementException.class,()->{associateService.getAmountByStatusWise("103", "Approved");});
    	verifyGetAmountByStatusWiseIsCalledOnce("103", "Approved");
    }
    
}