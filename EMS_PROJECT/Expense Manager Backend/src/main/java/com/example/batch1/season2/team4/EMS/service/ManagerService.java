package com.example.batch1.season2.team4.EMS.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.example.batch1.season2.team4.EMS.model.Associates;
import com.example.batch1.season2.team4.EMS.model.ExpenseTransaction;

public interface ManagerService {
	public ExpenseTransaction actionOnExpenseRequest(int expId,String action,String comments);
	//public byte[] getReceiptByExpId(int expId);

	//public Optional<List<ExpenseTransaction>> getAllExpenses();
	public Optional<List<ExpenseTransaction>> getAllPendingExpensesByAssociateId(String associateId);
	public Optional<List<Associates>> getEmpByManagerId(String manager_id);
	public List<Float> getAmount(String status, String associate_id,int month);
	public Float getBudgetById(String managerId);
}
