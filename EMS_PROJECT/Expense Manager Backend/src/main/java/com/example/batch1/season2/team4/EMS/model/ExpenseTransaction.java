package com.example.batch1.season2.team4.EMS.model;

import java.sql.Date;
import java.util.Arrays;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name="season2_batch1_team4_EMS_transaction")
public class ExpenseTransaction {
    
    @Id
    @Column(name="expense_id")
    int expenseId;
    @Column(name="associate_id")
    String associateId;
    @Column(name="expense_type")
    String expenseType;
    @Column(name="start_date")
    Date startDate;
    @Column(name="end_date")
    Date endDate;
    @Column(name="claim_amount")
    int claimAmount;
    @Lob
    byte[] receipt;
    @Column(name="mng_comments")
    String managerComments;
    String status;
    @Column(name="applied_date")
    Date appliedDate;
    @Column(name="approved_date")
    Date approvedDate;
    
    public ExpenseTransaction() {
        
    }

    public ExpenseTransaction(int expenseId, String associateId, String expenseType, Date startDate, Date endDate,
            int claimAmount, byte[] receipt, String managerComments, String status, Date appliedDate,
            Date approvedDate) {
        super();
        this.expenseId = expenseId;
        this.associateId = associateId;
        this.expenseType = expenseType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.claimAmount = claimAmount;
        this.receipt = receipt;
        this.managerComments = managerComments;
        this.status = status;
        this.appliedDate = appliedDate;
        this.approvedDate = approvedDate;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public String getAssociateId() {
        return associateId;
    }

    public void setAssociateId(String associateId) {
        this.associateId = associateId;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(int claimAmount) {
        this.claimAmount = claimAmount;
    }

    public byte[] getReceipt() {
        return receipt;
    }

    public void setReceipt(byte[] receipt) {
        this.receipt = receipt;
    }

    public String getManagerComments() {
        return managerComments;
    }

    public void setManagerComments(String managerComments) {
        this.managerComments = managerComments;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(Date appliedDate) {
        this.appliedDate = appliedDate;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

	@Override
	public String toString() {
		return "ExpenseTransaction [expenseId=" + expenseId + ", associateId=" + associateId + ", expenseType="
				+ expenseType + ", startDate=" + startDate + ", endDate=" + endDate + ", claimAmount=" + claimAmount
				+ ", receipt=" + Arrays.toString(receipt) + ", managerComments=" + managerComments + ", status="
				+ status + ", appliedDate=" + appliedDate + ", approvedDate=" + approvedDate + "]";
	}

    
    
}
