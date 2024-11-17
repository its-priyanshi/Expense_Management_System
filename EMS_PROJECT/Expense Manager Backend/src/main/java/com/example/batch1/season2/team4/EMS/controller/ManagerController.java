package com.example.batch1.season2.team4.EMS.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.batch1.season2.team4.EMS.model.Associates;
import com.example.batch1.season2.team4.EMS.model.ExpenseTransaction;
import com.example.batch1.season2.team4.EMS.service.ManagerService;
import com.example.batch1.season2.team4.EMS.service.ManagerServiceImpl;

@RestController
@RequestMapping("/manager")
public class ManagerController {
	@Autowired
	ManagerService service;
	@PostMapping("/actionOnExpense/{expId}/{action}/{comments}")
	@CrossOrigin("*")
	public ResponseEntity<ExpenseTransaction> approveExpense(@PathVariable int expId,@PathVariable String action,@PathVariable String comments) {
		System.out.println(action);
		return new ResponseEntity<> (service.actionOnExpenseRequest(expId,action,comments),HttpStatus.OK);
	}


	/*@GetMapping("/getFile/{expId}")
	@CrossOrigin("*")
	public ResponseEntity<byte[]> viewReceipt(@PathVariable int expId) {
	byte[] image = service.getReceiptByExpId(expId);
	return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(image);
	}


	@GetMapping("/getAllExpenses")
	@CrossOrigin("*")
	public ResponseEntity<Optional<List<ExpenseTransaction>>> getAllExpenses() {
	return new ResponseEntity<> (service.getAllExpenses(),HttpStatus.OK);
	}


	@GetMapping("/getExpenseById/{expId}")
	@CrossOrigin("*")
	public ResponseEntity<Optional<ExpenseTransaction>> getExpenseById(@PathVariable int expId){
	return new ResponseEntity<> (service.getExpensebyId(expId),HttpStatus.OK);
	}
	 */

	@GetMapping("/getExpenseByEmpId/{mngrId}")
	@CrossOrigin("*")
	public ResponseEntity<Optional<List<ExpenseTransaction>>> getExpensesByEmpId(@PathVariable String mngrId) {
	return new ResponseEntity<> (service.getAllPendingExpensesByAssociateId(mngrId),HttpStatus.OK);
	}

	
	@GetMapping("/getEmpByMngId/{mngId}")
	@CrossOrigin("*")
	public ResponseEntity<List<Associates>> getEmpByManagerId(@PathVariable String mngId){
	return new ResponseEntity<> (service.getEmpByManagerId(mngId).get(),HttpStatus.OK);
	}

	/*
	@GetMapping("/getByCategory/{expType}")
	@CrossOrigin("*")
	public ResponseEntity<Optional<List<ExpenseTransaction>>> getByCategory(@PathVariable String expType){
	return new ResponseEntity<> (service.getExpensesByCategory(expType),HttpStatus.OK);
	}
	*/
	
	 @GetMapping("/getMngAmountChart/{associate_id}/{month}")
    @CrossOrigin("*")
    public ResponseEntity<Map<String,Float>> getAmountChart(@PathVariable String associate_id,@PathVariable("month") int month){
        Map<String, Float> mp = new LinkedHashMap<>();
         
        float pendingSum=0,approvedSum=0,rejectedSum=0;
        
        List<Associates> associates=service.getEmpByManagerId(associate_id).get();
        for(Associates emp : associates)
        {
            System.out.println(associates.toString());
            pendingSum+=service.getAmount("pending",emp.getAssociateId(),month).stream().reduce(0f,Float::sum);
            approvedSum+=service.getAmount("accept",emp.getAssociateId(),month).stream().reduce(0f,Float::sum);
            rejectedSum+=service.getAmount("reject",emp.getAssociateId(),month).stream().reduce(0f,Float::sum);
            System.out.println(pendingSum+" "+approvedSum+" "+rejectedSum);
        }
        mp.put("Pending", pendingSum);
        mp.put("Approved", approvedSum);
        mp.put("Rejected", rejectedSum);
    
        return new ResponseEntity<>(mp,HttpStatus.OK);
    }
	 
	@GetMapping("/getBudget/{mngrId}")
	@CrossOrigin("*")
	public ResponseEntity<Float> getBudgetForManager(@PathVariable String mngrId){
		return new ResponseEntity(service.getBudgetById(mngrId),HttpStatus.OK);
	}
	 
	@GetMapping("/getStatuWiseAmount/{mngrId}")
	@CrossOrigin("*")
	public ResponseEntity<Map<String,Integer>> getStatusWiseAmount(@PathVariable String mngrId){
		service.getAllPendingExpensesByAssociateId(mngrId);
		Map<String,Integer> statusWiseMap=new LinkedHashMap<>();
		statusWiseMap.put("accept", ManagerServiceImpl.approvedAmount);
		statusWiseMap.put("pending",ManagerServiceImpl.pendingAmount);
		return new ResponseEntity(statusWiseMap,HttpStatus.OK);
	}
}