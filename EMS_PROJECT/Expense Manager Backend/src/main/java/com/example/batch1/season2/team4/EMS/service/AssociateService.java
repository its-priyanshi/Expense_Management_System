package com.example.batch1.season2.team4.EMS.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.batch1.season2.team4.EMS.model.Associates;
import com.example.batch1.season2.team4.EMS.model.ExpenseTransaction;

public interface AssociateService {
public ExpenseTransaction addExpense(ExpenseTransaction expense);
    
    public Optional<Page<ExpenseTransaction>> viewStatus(String associateId,Pageable pageable);
    
    public Optional<List<ExpenseTransaction>> findByExpenseType(String expenseType);
    
    public Optional<List<ExpenseTransaction>> getAllTransactions();
    
    public Associates getAssociateById(String associateId);
    
    public List<Integer> getType(String type, String associate_id,int month);
    
    public Optional<List<Associates>> getEmpByMgrId(String managerId);
    
    public List<Float> getStatus(String status, String associate_id,int month,String type);
    
    public ExpenseTransaction getExpenseById(int expenseId);
    
    public List<Float> getTypeWiseStauts(String type,String associateId);
    
    public Page<ExpenseTransaction> getTransactionByIdByStatus(String associateId,String status,Pageable pageable);
    
    public Integer getAmountByStatusWise(String status,String associateId);
    
    public Integer getCountByStatusWise(String status,String associateId);
    
}
