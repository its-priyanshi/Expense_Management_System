import axios from "axios"

const EMS_USER_URL="http://localhost:8085/user"
const EMS_EMP_URL="http://localhost:8085/employee"
const EMS_MNGR_URL="http://localhost:8085/manager"

export const getUser=async (empId,password)=>{
    const response=await axios.get(EMS_USER_URL+"/getcredentials/"+empId+"/"+password)
    return response;
}

export const getUserId=async (empId)=>{
    const response=await axios.get(EMS_USER_URL+"/getAssociateById/"+empId)
    return response;
}

export const updatePassword=async (empId,password)=>{
    const response=await axios.put(EMS_USER_URL+"/updateCredentials/"+empId+"/"+password)
    return response;
}

export const getAssociateById=async (empId)=>{
    const response=await axios.get(EMS_EMP_URL+"/getEmployee/"+empId)
    return response
}

export const getAssociatesAlltransactions=async (empId,currPage,maxItems)=>{
    console.log(maxItems)
    console.log(EMS_EMP_URL+"/viewStatus/"+empId+"?page="+currPage+"&size="+maxItems)
    const response=await axios.get(EMS_EMP_URL+"/viewStatus/"+empId+"?page="+currPage+"&size="+maxItems)
    return response
}

export const uploadTransaction=async (transaction)=>{
    console.log(transaction)
    const response=await axios.post(EMS_EMP_URL+"/addExpense",transaction,{headers: { 'content-type': 'multipart/form-data'}})
    return response
}

export const getSubordinate=async (mngrId)=>{
    console.log(mngrId)
    const response=await axios.get(EMS_MNGR_URL+"/getEmpByMngId/"+mngrId)
    return response
}

export const getPendingExpenses=async (mngrId)=>{
    console.log(mngrId)
    const response=await axios.get(EMS_MNGR_URL+"/getExpenseByEmpId/"+mngrId)
    return response
}

export const applyAction=async (expId,action,comments)=>{
    console.log(action)
    const response =await axios.post(EMS_MNGR_URL+"/actionOnExpense/+"+expId+"/"+action+"/"+comments)
    return response
}

export const getStatusAnalytics=async (empId,month,type)=>{
    console.log(empId+" "+month+" "+type)
    const response = await axios.get(EMS_EMP_URL+"/getByEmpStatus/"+empId+"/"+month+"/"+type)
    return response
}

export const getTypeAnalytics=async (empId,month)=>{
    console.log("Entered service method")
    console.log(empId+" "+month)
    const response=await axios.get(EMS_EMP_URL+"/getByExptype/"+empId+"/"+month)
    return response
}

export const getExpenseById=async (expenseId)=>{
    console.log(expenseId)
    const response=await axios.get(EMS_EMP_URL+"/getExpenseById/"+expenseId)
    return response
}

export const mngrActions=async (empId,month)=>{
    console.log(empId+" "+month)
    const response=await axios.get(EMS_MNGR_URL+"/getMngAmountChart/"+empId+"/"+month)
    return response
}

export const getTransactionByStatuswise=async (empId,status,currPage,maxItems)=>{
    console.log(empId+" "+status)
    console.log(EMS_EMP_URL+"/getTransactionsStatusWise/"+empId+"/"+status+"?page="+currPage+"&size="+maxItems)
    const response=await axios.get(EMS_EMP_URL+"/getTransactionsStatusWise/"+empId+"/"+status+"?page="+currPage+"&size="+maxItems)
    return response
}

export const getAmountByStatus=async (empId)=>{
    console.log(empId);
    const response=await axios.get(EMS_EMP_URL+"/getAmountByStatus/"+empId)
    return response
}

export const getCountByStatus=async (empId)=>{
    console.log(empId)
    const response=await axios.get(EMS_EMP_URL+"/getCountByStatus/"+empId)
    return response
}

export const getBudget=async (mngrId)=>{
    console.log(mngrId)
    const response=await axios.get(EMS_MNGR_URL+"/getBudget/"+mngrId)
    return response
}

export const getStatusWiseApprovedAmount=async (mngrId)=>{
    console.log(mngrId)
    const response=await axios.get(EMS_MNGR_URL+"/getStatuWiseAmount/"+mngrId)
    return response
}