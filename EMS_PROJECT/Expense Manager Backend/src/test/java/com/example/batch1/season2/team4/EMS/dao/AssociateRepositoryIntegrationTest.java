package com.example.batch1.season2.team4.EMS.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;
import java.util.ArrayList;
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

import com.example.batch1.season2.team4.EMS.model.Associates;
import com.example.batch1.season2.team4.EMS.model.ExpenseTransaction;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@AutoConfigureTestDatabase(replace=Replace.NONE)
@DataJpaTest
public class AssociateRepositoryIntegrationTest {
	@Autowired
	TestEntityManager entityManager;
	@Autowired 
	AssociateDao associatesDao;
	
	@Test
	public void whenFindById_thenReturnAssociate() {
		Associates emp=new Associates("AS123RT","Srikar","Manager","ABC123","srikar@xyz.com",100000,0);
		entityManager.persistAndFlush(emp);
		Associates result=associatesDao.findById("AS123RT").orElse(null);
		System.out.println(result.toString());
		assertThat(emp.getAssociateName()).isEqualTo(result.getAssociateName());
	}
	
	@Test
	public void whenFindByInvalidId_thenReturnException() {
		Associates fromDb = associatesDao.findById("-1").orElse(null);
        assertThat(fromDb).isNull();
	}
	
	@Test
	public void whenGetAmountByStatusByMonth_thenReturnCategoryExpenses() {
		ExpenseTransaction expense1=new ExpenseTransaction(1001,"ABC123","internet_expense",Date.valueOf("2023-11-22"),Date.valueOf("2023-11-25"),45000,new byte[] {},"approved","accept",Date.valueOf("2023-11-25"),Date.valueOf("2023-11-27"));
		entityManager.persistAndFlush(expense1);
		ExpenseTransaction expense2=new ExpenseTransaction(1002,"ABC123","internet_expense",Date.valueOf("2023-11-05"),Date.valueOf("2023-11-07"),45000,new byte[] {},"approved","accept",Date.valueOf("2023-11-09"),Date.valueOf("2023-11-09"));
		entityManager.persistAndFlush(expense2);
		ExpenseTransaction expense3=new ExpenseTransaction(1003,"ABC123","internet_expense",Date.valueOf("2023-11-22"),Date.valueOf("2023-11-25"),45000,new byte[] {},null,"pending",Date.valueOf("2023-11-25"),Date.valueOf("2023-11-27"));
		entityManager.persistAndFlush(expense3);
		ExpenseTransaction expense4=new ExpenseTransaction(1004,"ABC123","internet_expense",Date.valueOf("2023-11-12"),Date.valueOf("2023-11-15"),45000,new byte[] {},"rejected","reject",Date.valueOf("2023-11-17"),Date.valueOf("2023-11-17"));
		entityManager.persistAndFlush(expense4);
		ExpenseTransaction expense5=new ExpenseTransaction(1005,"ABC123","internet_expense",Date.valueOf("2023-11-28"),Date.valueOf("2023-11-28"),45000,new byte[] {},"rejected","reject",Date.valueOf("2023-11-28"),Date.valueOf("2023-11-28"));
		entityManager.persistAndFlush(expense5);
		/*List<ExpenseTransaction> expenses=new ArrayList<>();
		expenses.add(expense1);
		expenses.add(expense2);
		expenses.add(expense3);
		expenses.add(expense4);
		expenses.add(expense5);*/
		List<Float> result=associatesDao.getAmountByStatusByMonth("pending","ABC123",11,2023);
		System.out.println(result);
		assertThat(result.get(0)).isEqualTo(45000.0F);
	}
	
	@Test
	public void whenGetAmountByStatusByMonthInvalidId_thenZero() {
		ExpenseTransaction expense1=new ExpenseTransaction(1001,"ABC123","internet_expense",Date.valueOf("2023-11-22"),Date.valueOf("2023-11-25"),45000,new byte[] {},"approved","accept",Date.valueOf("2023-11-25"),Date.valueOf("2023-11-27"));
		entityManager.persistAndFlush(expense1);
		ExpenseTransaction expense2=new ExpenseTransaction(1002,"ABC123","internet_expense",Date.valueOf("2023-11-05"),Date.valueOf("2023-11-07"),45000,new byte[] {},"approved","accept",Date.valueOf("2023-11-09"),Date.valueOf("2023-11-09"));
		entityManager.persistAndFlush(expense2);
		ExpenseTransaction expense3=new ExpenseTransaction(1003,"ABC123","internet_expense",Date.valueOf("2023-11-22"),Date.valueOf("2023-11-25"),45000,new byte[] {},null,"pending",Date.valueOf("2023-11-25"),Date.valueOf("2023-11-27"));
		entityManager.persistAndFlush(expense3);
		ExpenseTransaction expense4=new ExpenseTransaction(1004,"ABC123","internet_expense",Date.valueOf("2023-11-12"),Date.valueOf("2023-11-15"),45000,new byte[] {},"rejected","reject",Date.valueOf("2023-11-17"),Date.valueOf("2023-11-17"));
		entityManager.persistAndFlush(expense4);
		ExpenseTransaction expense5=new ExpenseTransaction(1005,"ABC123","internet_expense",Date.valueOf("2023-11-28"),Date.valueOf("2023-11-28"),45000,new byte[] {},"rejected","reject",Date.valueOf("2023-11-28"),Date.valueOf("2023-11-28"));
		entityManager.persistAndFlush(expense5);
		List<Float> result=associatesDao.getAmountByStatusByMonth("pending","12345",21,2023);
		System.out.println(result);
		assertThat(result.size()).isEqualTo(0);
	}
	
	@Test
	public void whenGetAmountByStatusByMonthNoData_thenZero() {
		List<Float> result=associatesDao.getAmountByStatusByMonth("pending","12345",11,2023);
		System.out.println(result);
		assertThat(result.size()).isEqualTo(0);
	}
	
	@Test 
	public void whengetAmountByStatusByMonthByType_thenReturnAmount() {
		ExpenseTransaction expense1=new ExpenseTransaction(1001,"ABC123","internet_expense",Date.valueOf("2023-11-22"),Date.valueOf("2023-11-25"),45000,new byte[] {},"approved","accept",Date.valueOf("2023-11-25"),Date.valueOf("2023-11-27"));
		entityManager.persistAndFlush(expense1);
		ExpenseTransaction expense2=new ExpenseTransaction(1002,"ABC123","meal_expense",Date.valueOf("2023-11-05"),Date.valueOf("2023-11-07"),45000,new byte[] {},"approved","accept",Date.valueOf("2023-11-09"),Date.valueOf("2023-11-09"));
		entityManager.persistAndFlush(expense2);
		ExpenseTransaction expense3=new ExpenseTransaction(1003,"ABC123","internet_expense",Date.valueOf("2023-11-22"),Date.valueOf("2023-11-25"),45000,new byte[] {},null,"pending",Date.valueOf("2023-11-25"),Date.valueOf("2023-11-27"));
		entityManager.persistAndFlush(expense3);
		ExpenseTransaction expense4=new ExpenseTransaction(1004,"ABC123","meal_expense",Date.valueOf("2023-11-12"),Date.valueOf("2023-11-15"),45000,new byte[] {},"rejected","reject",Date.valueOf("2023-11-17"),Date.valueOf("2023-11-17"));
		entityManager.persistAndFlush(expense4);
		ExpenseTransaction expense5=new ExpenseTransaction(1005,"ABC123","relocation_expense",Date.valueOf("2023-11-28"),Date.valueOf("2023-11-28"),45000,new byte[] {},"rejected","reject",Date.valueOf("2023-11-28"),Date.valueOf("2023-11-28"));
		entityManager.persistAndFlush(expense5);
		List<Float> result=associatesDao.getAmountByStatusByMonthByType("accept","ABC123",11,2023,"meal_expense");
		System.out.println(result);
		assertThat(result.get(0)).isEqualTo(45000.0F);
	}
	
	@Test
	public void whenAmountByStatusByMonthByTypeInvalidId_thenZero() {
		ExpenseTransaction expense1=new ExpenseTransaction(1001,"ABC123","internet_expense",Date.valueOf("2023-11-22"),Date.valueOf("2023-11-25"),45000,new byte[] {},"approved","accept",Date.valueOf("2023-11-25"),Date.valueOf("2023-11-27"));
		entityManager.persistAndFlush(expense1);
		ExpenseTransaction expense2=new ExpenseTransaction(1002,"ABC123","internet_expense",Date.valueOf("2023-11-05"),Date.valueOf("2023-11-07"),45000,new byte[] {},"approved","accept",Date.valueOf("2023-11-09"),Date.valueOf("2023-11-09"));
		entityManager.persistAndFlush(expense2);
		ExpenseTransaction expense3=new ExpenseTransaction(1003,"ABC123","internet_expense",Date.valueOf("2023-11-22"),Date.valueOf("2023-11-25"),45000,new byte[] {},null,"pending",Date.valueOf("2023-11-25"),Date.valueOf("2023-11-27"));
		entityManager.persistAndFlush(expense3);
		ExpenseTransaction expense4=new ExpenseTransaction(1004,"ABC123","internet_expense",Date.valueOf("2023-11-12"),Date.valueOf("2023-11-15"),45000,new byte[] {},"rejected","reject",Date.valueOf("2023-11-17"),Date.valueOf("2023-11-17"));
		entityManager.persistAndFlush(expense4);
		ExpenseTransaction expense5=new ExpenseTransaction(1005,"ABC123","internet_expense",Date.valueOf("2023-11-28"),Date.valueOf("2023-11-28"),45000,new byte[] {},"rejected","reject",Date.valueOf("2023-11-28"),Date.valueOf("2023-11-28"));
		entityManager.persistAndFlush(expense5);
		List<Float> result=associatesDao.getAmountByStatusByMonthByType("accept","1233",11,2023,"internet_expense");
		System.out.println(result);
		assertThat(result.size()).isEqualTo(0);
	}
	
	@Test
	public void whenAmountByStatusByMonthByTypeNoData_thenZero() {
		List<Float> result=associatesDao.getAmountByStatusByMonthByType("accept","ABC123",11,2023,"internet_expense");
		System.out.println(result);
		assertThat(result.size()).isEqualTo(0);
	}
	
	@Test 
	public void whengetAmountByTypeByMonth_thenReturnAmount() {
		ExpenseTransaction expense1=new ExpenseTransaction(1001,"ABC123","internet_expense",Date.valueOf("2023-11-22"),Date.valueOf("2023-11-25"),45000,new byte[] {},"approved","accept",Date.valueOf("2023-11-25"),Date.valueOf("2023-11-27"));
		entityManager.persistAndFlush(expense1);
		ExpenseTransaction expense2=new ExpenseTransaction(1002,"ABC123","meal_expense",Date.valueOf("2023-11-05"),Date.valueOf("2023-11-07"),45000,new byte[] {},"approved","accept",Date.valueOf("2023-11-09"),Date.valueOf("2023-11-09"));
		entityManager.persistAndFlush(expense2);
		ExpenseTransaction expense3=new ExpenseTransaction(1003,"ABC123","internet_expense",Date.valueOf("2023-11-22"),Date.valueOf("2023-11-25"),45000,new byte[] {},null,"pending",Date.valueOf("2023-11-25"),Date.valueOf("2023-11-27"));
		entityManager.persistAndFlush(expense3);
		ExpenseTransaction expense4=new ExpenseTransaction(1004,"ABC123","meal_expense",Date.valueOf("2023-11-12"),Date.valueOf("2023-11-15"),45000,new byte[] {},"rejected","reject",Date.valueOf("2023-11-17"),Date.valueOf("2023-11-17"));
		entityManager.persistAndFlush(expense4);
		ExpenseTransaction expense5=new ExpenseTransaction(1005,"ABC123","relocation_expense",Date.valueOf("2023-11-28"),Date.valueOf("2023-11-28"),45000,new byte[] {},"rejected","reject",Date.valueOf("2023-11-28"),Date.valueOf("2023-11-28"));
		entityManager.persistAndFlush(expense5);
		List<Integer> result=associatesDao.getAmountByTypeByMonth("internet_expense","ABC123",11,2023);
		System.out.println(result);
		assertThat(result.get(0)).isEqualTo(90000);
	}
	
	@Test
	public void whengetAmountByTypeByMonthInvalidId_thenZero() {
		ExpenseTransaction expense1=new ExpenseTransaction(1001,"ABC123","internet_expense",Date.valueOf("2023-11-22"),Date.valueOf("2023-11-25"),45000,new byte[] {},"approved","accept",Date.valueOf("2023-11-25"),Date.valueOf("2023-11-27"));
		entityManager.persistAndFlush(expense1);
		ExpenseTransaction expense2=new ExpenseTransaction(1002,"ABC123","internet_expense",Date.valueOf("2023-11-05"),Date.valueOf("2023-11-07"),45000,new byte[] {},"approved","accept",Date.valueOf("2023-11-09"),Date.valueOf("2023-11-09"));
		entityManager.persistAndFlush(expense2);
		ExpenseTransaction expense3=new ExpenseTransaction(1003,"ABC123","internet_expense",Date.valueOf("2023-11-22"),Date.valueOf("2023-11-25"),45000,new byte[] {},null,"pending",Date.valueOf("2023-11-25"),Date.valueOf("2023-11-27"));
		entityManager.persistAndFlush(expense3);
		ExpenseTransaction expense4=new ExpenseTransaction(1004,"ABC123","internet_expense",Date.valueOf("2023-11-12"),Date.valueOf("2023-11-15"),45000,new byte[] {},"rejected","reject",Date.valueOf("2023-11-17"),Date.valueOf("2023-11-17"));
		entityManager.persistAndFlush(expense4);
		ExpenseTransaction expense5=new ExpenseTransaction(1005,"ABC123","internet_expense",Date.valueOf("2023-11-28"),Date.valueOf("2023-11-28"),45000,new byte[] {},"rejected","reject",Date.valueOf("2023-11-28"),Date.valueOf("2023-11-28"));
		entityManager.persistAndFlush(expense5);
		List<Integer> result=associatesDao.getAmountByTypeByMonth("internet_expense","12345",11,2023);
		System.out.println(result);
		assertThat(result.size()).isEqualTo(0);
	}
	
	@Test
	public void whengetAmountByTypeByMonthNoData_thenZero() {
		List<Integer> result=associatesDao.getAmountByTypeByMonth("internet_expense","12345",11,2023);
		System.out.println(result);
		assertThat(result.size()).isEqualTo(0);
	}
	
	@Test
	public void whenFindBtManagerId_thenReturnAssociates() {
		Associates emp1=new Associates("AS123RT","Srikar","Employee","ABC123","srikar@xyz.com",0,0);
		entityManager.persistAndFlush(emp1);
		Associates emp2=new Associates("AS123RS","Anurag","Manager","ABC123","anurag@xyz.com",100000,0);
		entityManager.persistAndFlush(emp2);
		Associates emp3=new Associates("AS123RU","Surya","Employee","ABC123","surya@xyz.com",0,0);
		entityManager.persistAndFlush(emp3);
		List<Associates> actual=new ArrayList<>();
		actual.add(emp1);
		actual.add(emp2);
		actual.add(emp3);
		System.out.println(actual);
		List<Associates> subordinates=associatesDao.findEmpByManagerId("ABC123");
		System.out.println(subordinates);
		assertThat(subordinates).isEqualTo(actual);
	}
	
	@Test
	public void whenFindBtManagerIdInvalidId_thenReturnAssociates() {
		Associates emp1=new Associates("AS123RT","Srikar","Employee","ABC123","srikar@xyz.com",0,0);
		entityManager.persistAndFlush(emp1);
		Associates emp2=new Associates("AS123RS","Anurag","Manager","ABC123","anurag@xyz.com",100000,0);
		entityManager.persistAndFlush(emp2);
		Associates emp3=new Associates("AS123RU","Surya","Employee","ABC123","surya@xyz.com",0,0);
		entityManager.persistAndFlush(emp3);
		List<Associates> subordinates=associatesDao.findEmpByManagerId("ABC678");
		assertThat(subordinates.size()).isEqualTo(0);
	}
	
	@Test
	public void whenFindBtManagerIdNoRecords_thenReturnAssociates() {
		List<Associates> subordinates=associatesDao.findEmpByManagerId("ABC678");
		assertThat(subordinates.size()).isEqualTo(0);
	}
	
	@Test
	public void whenFindAmountByAssociateId_thenReturnAmount() {
		Associates emp1=new Associates("AS123RT","Srikar","Employee","ABC123","srikar@xyz.com",0,0);
		entityManager.persistAndFlush(emp1);
		Associates emp2=new Associates("AS123RS","Anurag","Manager","ABC123","anurag@xyz.com",100000,0);
		entityManager.persistAndFlush(emp2);
		Associates emp3=new Associates("AS123RU","Surya","Employee","ABC123","surya@xyz.com",0,0);
		entityManager.persistAndFlush(emp3);
		
		float amount = associatesDao.findAmountByAssociateId("AS123RS").get();
		assertThat(amount).isEqualTo(100000.0F);
	}
	
	@Test
	public void whenFindAmountByAssociateId_thenReturnNull() {
		Associates emp1=new Associates("AS123RT","Srikar","Employee","ABC123","srikar@xyz.com",0,0);
		entityManager.persistAndFlush(emp1);
		Associates emp2=new Associates("AS123RS","Anurag","Manager","ABC123","anurag@xyz.com",100000,0);
		entityManager.persistAndFlush(emp2);
		Associates emp3=new Associates("AS123RU","Surya","Employee","ABC123","surya@xyz.com",0,0);
		entityManager.persistAndFlush(emp3);
		
		assertThrows(NoSuchElementException.class,()->{associatesDao.findAmountByAssociateId("AS123R").get();});
	}
	
}