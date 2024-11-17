package com.example.batch1.season2.team4.EMS.controller;

import java.io.IOException;

import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.batch1.season2.team4.EMS.model.Associates;
import com.example.batch1.season2.team4.EMS.model.ExpenseTransaction;
import com.example.batch1.season2.team4.EMS.service.AssociateService;
import com.example.batch1.season2.team4.EMS.service.ManagerService;

@RestController
@RequestMapping(value="/employee")
public class EmployeeController {

	@Autowired
	AssociateService service;
	
	
	@PostMapping(value="/addExpense",headers = "content-type=multipart/*")
	@CrossOrigin("*")
	public ResponseEntity<ExpenseTransaction> addExpense(@RequestParam("associateId") String associateId,
			@RequestParam("expenseType") String expenseType,
			@RequestParam("startDate") Date startDate,
			@RequestParam("endDate") Date endDate,
			@RequestParam("claimAmount") int claimAmount,
			@RequestParam("receipt")MultipartFile receipt){
		//System.out.println(expense.toString()+" "+id+" "+receipt);
		//System.out.println(formData.toString());
		//expense.setReceipt(attachment);
		ExpenseTransaction expense=new ExpenseTransaction();
		expense.setAssociateId(associateId);
		expense.setExpenseType(expenseType);
		expense.setStartDate(startDate);
		expense.setEndDate(endDate);
		expense.setClaimAmount(claimAmount);
		try {
			expense.setReceipt(receipt.getBytes());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(expense);
		//expense.setAssociateId(id);
		return new ResponseEntity<>(service.addExpense(expense),HttpStatus.CREATED);
	}
	
	@GetMapping("/viewStatus/{associateId}")
	@CrossOrigin("*")
	public ResponseEntity<Page<ExpenseTransaction>> viewStatus(@PathVariable String associateId,@PageableDefault(size=5)Pageable pageable){

	return new ResponseEntity<>(service.viewStatus(associateId,pageable).get(),HttpStatus.OK); 
	
	}
	
	/*
	@GetMapping("/ExpenseCategory/{expenseType}")
	@CrossOrigin("*")
	public ResponseEntity<List<ExpenseTransaction>> findByExpenseType(@PathVariable String expenseType){
	
	return new ResponseEntity<>(service.findByExpenseType(expenseType).get(),HttpStatus.OK);
	}
	
	@GetMapping("/transactions")
	@CrossOrigin("*")
	public Optional<List<ExpenseTransaction>> getAllTransactions(){
	
	return service.getAllTransactions();
	}
	*/
	
	@GetMapping("/getEmployee/{associateId}")
	@CrossOrigin("*")
	public Associates getAssociate(@PathVariable String associateId) {
		return service.getAssociateById(associateId);
	}

	@GetMapping("/getByExptype/{associate_id}/{month}")
	@CrossOrigin("*")
    public ResponseEntity<Map<String,Integer>> getCountByType(@PathVariable("associate_id") String associate_id,@PathVariable("month") int month){
        Map<String, Integer> mp = new LinkedHashMap<>();
        mp.put("Internet", service.getType("internet_expense",associate_id,month).stream().mapToInt(Integer::intValue).sum());
        mp.put("Travel", service.getType("travel_expense",associate_id,month).stream().mapToInt(Integer::intValue).sum());
        mp.put("Food",service.getType("meal_expense",associate_id,month).stream().mapToInt(Integer::intValue).sum());
        mp.put("Relocation", service.getType("relocation_expense",associate_id,month).stream().mapToInt(Integer::intValue).sum());
        mp.put("Medical", service.getType("medical_expense",associate_id,month).stream().mapToInt(Integer::intValue).sum());
        
        return new ResponseEntity(mp,HttpStatus.ACCEPTED);
    }
	
	@GetMapping("/getByEmpStatus/{associate_id}/{month}/{type}")
    @CrossOrigin("*")
    public ResponseEntity<Map<String,Double>> getAmountByStatus(@PathVariable String associate_id,@PathVariable("month") int month,@PathVariable("type") String type){
        Map<String, Double> mp = new LinkedHashMap<>();
        mp.put("Pending", service.getStatus("pending",associate_id,month,type).stream().mapToDouble(Float::doubleValue).sum());
        mp.put("Approved", service.getStatus("accept",associate_id,month,type).stream().mapToDouble(Float::doubleValue).sum());
        mp.put("Rejected", service.getStatus("reject",associate_id,month,type).stream().mapToDouble(Float::doubleValue).sum());
        return new ResponseEntity(mp,HttpStatus.ACCEPTED);
    }
	
	@GetMapping("/getExpenseById/{expenseId}")
	@CrossOrigin("*")
	public ResponseEntity<ExpenseTransaction> getExpenseById(@PathVariable int expenseId){
		return new ResponseEntity(service.getExpenseById(expenseId),HttpStatus.OK);
	}
	
	@GetMapping("/getTransactionsStatusWise/{associateId}/{status}")
	@CrossOrigin("*")
	public ResponseEntity<List<ExpenseTransaction>> getStatusWiseTransactions(@PathVariable String associateId,@PathVariable String status,@PageableDefault(size=3)Pageable pageable){
		return new ResponseEntity(service.getTransactionByIdByStatus(associateId, status,pageable),HttpStatus.OK);
	}
	
	@GetMapping("/getAmountByStatus/{associateId}")
	@CrossOrigin("*")
	public ResponseEntity<Map<String,Integer>> getAmountByStatus(@PathVariable String associateId){
		Map<String,Integer> amountByStatus=new LinkedHashMap<>();
		amountByStatus.put("accept",service.getAmountByStatusWise(associateId,"accept"));
		amountByStatus.put("reject",service.getAmountByStatusWise(associateId,"reject"));
		amountByStatus.put("pending",service.getAmountByStatusWise(associateId, "pending"));
		System.out.println(amountByStatus);
		return new ResponseEntity(amountByStatus,HttpStatus.OK);
	}
	
	@GetMapping("/getCountByStatus/{associateId}")
	@CrossOrigin("*")
	public ResponseEntity<Map<String,Integer>> getCountByStatus(@PathVariable String associateId){
		Map<String,Integer> amountByStatus=new LinkedHashMap<>();
		amountByStatus.put("accept",service.getCountByStatusWise(associateId,"accept"));
		amountByStatus.put("reject",service.getCountByStatusWise(associateId,"reject"));
		amountByStatus.put("pending",service.getCountByStatusWise(associateId, "pending"));
		System.out.println(amountByStatus);
		return new ResponseEntity(amountByStatus,HttpStatus.OK);
	}
	
}