package com.example.batch1.season2.team4.EMS.service;

import org.springframework.stereotype.Service;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.batch1.season2.team4.EMS.dao.AssociateDao;
import com.example.batch1.season2.team4.EMS.dao.ExpenseTransactionDao;
import com.example.batch1.season2.team4.EMS.model.Associates;
import com.example.batch1.season2.team4.EMS.model.ExpenseTransaction;
@Service
public class ManagerServiceImpl implements ManagerService{
	@Autowired
	ExpenseTransactionDao dao;

	@Autowired
	AssociateDao associateDao;
	
	public static int pendingAmount=0;
	public static int approvedAmount=0;

	public Associates setBudgetByAssociateId(String associateId,float claimAmount) {
	Associates associate = associateDao.findById(associateId).get();
	//associate.getReportingManagerId()
	Associates manager = associateDao.findById(associate.getReportingManagerId()).get();
	manager.setAmount(manager.getAmount() - claimAmount);
	associateDao.save(manager);
	return manager;
	}

	@Override
	public ExpenseTransaction actionOnExpenseRequest(int expId,String action,String comments) {


	ExpenseTransaction expense = dao.findById(expId).get();
	Associates associate = associateDao.findById(expense.getAssociateId()).get();
	//associate.getReportingManagerId()
	Associates manager = associateDao.findById(associate.getReportingManagerId()).get();
	manager.setAmount(manager.getAmount() - expense.getClaimAmount());
	associateDao.save(manager);

	expense.setStatus(action);
	expense.setManagerComments(comments);
	expense.setApprovedDate(Date.valueOf(LocalDate.now()));


	dao.save(expense);

	return expense;

	}

	
	/*
	@Override
	public Optional<List<ExpenseTransaction>> getAllExpenses() {
	// TODO Auto-generated method stub
	List<ExpenseTransaction> expenses = dao.findAllByOrderByAppliedDateDesc();
	return Optional.of(expenses);
	}*/

	

	@Override
	public Optional<List<ExpenseTransaction>> getAllPendingExpensesByAssociateId(String managerId) {
	// TODO Auto-generated method stub
	List<Associates> associates = associateDao.findEmpByManagerId(managerId);
	System.out.println(associates);
	List<ExpenseTransaction> pendingExpenses = new ArrayList<>();
	pendingAmount=0;
	approvedAmount=0;
	for(Associates employee : associates) {

	List<ExpenseTransaction> allExpenses = dao.findAllByAssociateIdOrderByAppliedDateDesc(employee.getAssociateId());
	System.out.println(allExpenses.toString());
	for(ExpenseTransaction et : allExpenses) {
		if(et.getStatus().equals("pending")) {
			pendingExpenses.add(et);
			pendingAmount+=et.getClaimAmount();
		}
		else if(et.getStatus().equals("accept")) {
			approvedAmount+=et.getClaimAmount();
		}
	}
	}
	//findAllByAssociateIdOrderByAppliedDateDesc
	return Optional.of(pendingExpenses);
	}

	@Override
	public Optional<List<Associates>> getEmpByManagerId(String manager_id) {
	// TODO Auto-generated method stub
	return Optional.of(associateDao.findEmpByManagerId(manager_id));
	}

	
	
	@Override
    public List<Float> getAmount(String status, String associate_id,int month) {
        // TODO Auto-generated method stub
        List<Float> ans = associateDao.getAmountByStatusByMonth(status,associate_id,month,LocalDate.now().getYear());
        if(ans.size() == 0)
        {
            ans.add((float)0);
        }
        
        return ans;
    }

	@Override
	public Float getBudgetById(String managerId) {
		// TODO Auto-generated method stub
		Float ans = associateDao.findAmountByAssociateId(managerId).get();
		if(ans == 0)
        {
            ans = 0F;
        }
		return ans;
	}
}