package com.example.batch1.season2.team4.EMS.service;

import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.batch1.season2.team4.EMS.dao.AssociateDao;
import com.example.batch1.season2.team4.EMS.dao.ExpenseTransactionDao;
import com.example.batch1.season2.team4.EMS.model.Associates;
import com.example.batch1.season2.team4.EMS.model.ExpenseTransaction;
@Service
public class AssociateServiceImpl implements AssociateService{
	@Autowired
    ExpenseTransactionDao dao;
	
	@Autowired 
	AssociateDao associateDao;
    
    @Override
    public ExpenseTransaction addExpense(ExpenseTransaction expense) {
        // TODO Auto-generated method stub
        Random random=new Random();
        int eid=random.nextInt((100000 /*Max value*/-10000/*Min value*/)+ 10000/*Min value*/);//Returns fixed length random values
        int nod=0;
        expense.setExpenseId(eid);
        expense.setAppliedDate(Date.valueOf(LocalDate.now()));
        expense.setStatus("pending");
        System.out.println(expense.toString());
        return dao.save(expense);
    }

    
    @Override
    public Optional<Page<ExpenseTransaction>> viewStatus(String associateId,Pageable pageable) {
        // TODO Auto-generated method stub
        //System.out.println(dao.findByAssociateIdOrderByAppliedDateDesc(associate_id));
        return dao.findByAssociateIdOrderByAppliedDateDesc(associateId,pageable);
    }


    @Override
    public Optional<List<ExpenseTransaction>> findByExpenseType(String expenseType) {
        // TODO Auto-generated method stub
        return dao.findByExpenseTypeOrderByAppliedDateDesc(expenseType);
    }


    @Override
    public Optional<List<ExpenseTransaction>> getAllTransactions() {
        // TODO Auto-generated method stub
        return Optional.of(dao.findAll());
    }


	@Override
	public Associates getAssociateById(String associateId) {
		// TODO Auto-generated method stub
		return associateDao.findById(associateId).get();
	}
	

    @Override
    public List<Integer> getType(String expense_type, String associate_id,int month) {
        // TODO Auto-generated method stub
        List<Integer> ans = associateDao.getAmountByTypeByMonth(expense_type,associate_id,month,LocalDate.now().getYear());
        if(ans.size() == 0)
        {
            ans.add(0);
        }        
        return ans;
    }

    @Override
    public Optional<List<Associates>> getEmpByMgrId(String managerId) {
        // TODO Auto-generated method stub
        return Optional.of(associateDao.findEmpByManagerId(managerId));
    }
	    
    public List<Float> getStatus(String status, String associate_id,int month,String type) {
        // TODO Auto-generated method stub
    	List<Float> ans=null;
    	if(type.equals("none"))
    		ans = associateDao.getAmountByStatusByMonth(status,associate_id,month,LocalDate.now().getYear());
    	else
    		ans = associateDao.getAmountByStatusByMonthByType(status,associate_id,month,LocalDate.now().getYear(),type);
        if(ans.size() == 0)
        {
            ans.add((float)0);
        }
        
        return ans;
    }


	@Override
	public ExpenseTransaction getExpenseById(int expenseId) {
		// TODO Auto-generated method stub
		return dao.findById(expenseId).get();
	}


	@Override
	public List<Float> getTypeWiseStauts(String type, String associateId) {
		// TODO Auto-generated method stub
		List<Float> ans = associateDao.getApprovedTransactionByType(type,associateId,LocalDate.now().getYear(),"accept");
        if(ans.size() == 0)
        {
            ans.add(0f);
        }
        return ans;
	}


	@Override
	public Page<ExpenseTransaction> getTransactionByIdByStatus(String associateId, String status,Pageable pageable) {
		Page<ExpenseTransaction> statusWiseTransactions=dao.findByAssociateIdAndStatus(associateId, status,pageable);
		return statusWiseTransactions;
	}


	@Override
	public Integer getAmountByStatusWise(String associateId, String status) {
		// TODO Auto-generated method stub
		Integer result=dao.getAmountStatusWiseForId(associateId, status).orElse(null);
		if(result==null)
			return 0;
		return result;
	}


	@Override
	public Integer getCountByStatusWise(String associateId, String status) {
		// TODO Auto-generated method stub
		Integer result= dao.getCountStatusWiseForId(associateId,status).orElse(null);
		if(result==0)
			return 0;
		return result;
	}
}
