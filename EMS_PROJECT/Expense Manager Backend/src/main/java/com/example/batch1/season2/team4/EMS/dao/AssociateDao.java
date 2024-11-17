package com.example.batch1.season2.team4.EMS.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.batch1.season2.team4.EMS.model.Associates;

@Repository
public interface AssociateDao extends JpaRepository<Associates,String>{
	
	/*@Query(nativeQuery = true, value = "select sum(claim_amount) from season2_batch1_team4_EMS_transaction group by associate_id, status having status = ?1 and associate_id = ?2")
    List<Float> getAmountByStatus(@Param("status") String status, @Param("associate_id") String associate_id);
    
    @Query(nativeQuery = true, value = "select sum(claim_amount) from season2_batch1_team4_EMS_transaction group by associate_id, expense_type having expense_type = ?1 and associate_id = ?2")
    List<Integer> getCountByType(@Param("expense_type") String expense_type, @Param("associate_id") String associate_id);*/
    
    @Query(nativeQuery = true, value = "select sum(claim_amount) from season2_batch1_team4_EMS_transaction group by associate_id, status,extract(month from applied_date),extract(year from applied_date) having status = ?1 and associate_id = ?2 and extract(month from applied_date)= ?3 and extract(year from applied_date)= ?4")
    List<Float> getAmountByStatusByMonth(@Param("status") String status, @Param("associate_id") String associate_id,@Param("extract(month from applied_date)")int month,@Param("extract(year from applied_date)")int year);
    
    @Query(nativeQuery = true, value = "select sum(claim_amount) from season2_batch1_team4_EMS_transaction group by associate_id, status,extract(month from applied_date),extract(year from applied_date),expense_type having status = ?1 and associate_id = ?2 and extract(month from applied_date)= ?3 and extract(year from applied_date)= ?4 and expense_type=?5")
    List<Float> getAmountByStatusByMonthByType(@Param("status") String status, @Param("associate_id") String associate_id,@Param("extract(month from applied_date)")int month,@Param("extract(year from applied_date)")int year,@Param("expense_type")String type);
    
    @Query(nativeQuery = true, value = "select sum(claim_amount) from season2_batch1_team4_EMS_transaction group by associate_id, expense_type,extract(month from applied_date),extract(year from applied_date) having expense_type = ?1 and associate_id = ?2 and extract(month from applied_date)= ?3 and extract(year from applied_date)= ?4")
    List<Integer> getAmountByTypeByMonth(@Param("expense_type") String expense_type, @Param("associate_id") String associate_id,@Param("extract(month from applied_date)")int month,@Param("extract(year from applied_date)")int year);

    /*@Query(nativeQuery = true, value = "select count(claim_amount) from season2_batch1_team4_EMS_transaction group by associate_id, status,extract(month from applied_date),extract(year from applied_date) having status = ?1 and associate_id = ?2 and extract(month from applied_date)= ?3 and extract(year from applied_date)=?4")
    List<Float> getCountByStatusByMonth(@Param("status") String status, @Param("associate_id") String associate_id,@Param("extract(month from applied_date)")int month,@Param("extract(year from applied_date)")int year);
    
    @Query(nativeQuery = true, value = "select count(claim_amount) from season2_batch1_team4_EMS_transaction group by associate_id, expense_type,extract(month from applied_date),extract(year from applied_date) having expense_type = ?1 and associate_id = ?2 and extract(month from applied_date)= ?3 and extract(year from applied_date)=?4")
    List<Integer> getCountByTypeByMonth(@Param("expense_type") String expense_type, @Param("associate_id") String associate_id,@Param("extract(month from applied_date)")int month,@Param("extract(year from applied_date)")int year);
	*/
    @Query(nativeQuery = true, value = "select sum(claim_amount) from season2_batch1_team4_EMS_transaction group by associate_id,expense_type,extract(year from applied_date),status having expense_type = ?1 and associate_id = ?2 and extract(year from applied_date)= ?3 and status=?4")
    List<Float> getApprovedTransactionByType(@Param("expense_type") String expenseType,@Param("associate_id") String associateId,@Param("extract(year from applied_date)")int year,@Param("status") String status);
    
    @Query(nativeQuery = true, value = "select * from season2_batch1_team4_EMS_associates where reporting_manager_id = ?1")
    public List<Associates> findEmpByManagerId(@Param("reporting_manager_id") String managerId);
    
    @Query(nativeQuery=true,value="select budget from season2_batch1_team4_EMS_associates where associate_id=?1")
    public Optional<Float> findAmountByAssociateId(@Param("associate_id") String associate_id);
}