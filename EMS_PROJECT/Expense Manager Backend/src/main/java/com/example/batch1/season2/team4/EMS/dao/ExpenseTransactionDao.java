package com.example.batch1.season2.team4.EMS.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.batch1.season2.team4.EMS.model.ExpenseTransaction;

@Repository
public interface ExpenseTransactionDao extends JpaRepository<ExpenseTransaction,Integer>{
    
    //@Query(nativeQuery = true,value= "select * from season2_batch1_team4_EMS_transaction where associate_id = ?1 order by applied_date desc")
    public Optional<Page<ExpenseTransaction>> findByAssociateIdOrderByAppliedDateDesc(String associateId,Pageable pageable);
    
    public Optional<List<ExpenseTransaction>> findByExpenseTypeOrderByAppliedDateDesc(String expenseType);

    public List<ExpenseTransaction> findAllByOrderByAppliedDateDesc();

    public List<ExpenseTransaction> findAllByAssociateIdOrderByAppliedDateDesc(String associate_id);

    public List<ExpenseTransaction> findAllByExpenseType(String expType);
    
    public Page<ExpenseTransaction> findByAssociateIdAndStatus(String associateId,String status,Pageable pageable);
    
    @Query(nativeQuery=true,value="select count(claim_amount) from season2_batch1_team4_EMS_transaction where associate_id=?1 and status=?2")
    public Optional<Integer> getCountStatusWiseForId(@Param("associate_id") String associate_id,@Param("status") String status);
    
    @Query(nativeQuery=true,value="select sum(claim_amount) from season2_batch1_team4_EMS_transaction where associate_id=?1 and status=?2")
    public Optional<Integer> getAmountStatusWiseForId(@Param("associateId")String associateId,@Param("status")String status);

}