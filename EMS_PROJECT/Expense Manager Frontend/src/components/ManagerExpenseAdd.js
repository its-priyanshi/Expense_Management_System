
import {Link, useNavigate,useParams} from 'react-router-dom'
import { useState } from 'react'
import { uploadTransaction } from '../service/EmsService'
import AddExpenseComponent from './AddExpenseComponent'
import adplogowhitee from '../assets/adplogowhitee.jpg'
import Icon from '@mdi/react';
import { mdiLogout } from '@mdi/js';
function ManagerAddExpense(){
    const [expenseType,setExpenseType]=useState('')
    const [startDate,setStartdate]=useState(new Date())
    const [endDate,setEnddate]=useState(new Date())
    const [claimAmount,setClaimAmount]=useState(0)
    const [receipt,setReceipt]=useState(null)
    const [typeError,setTypeError]=useState('')
    const [amountError,setAmountError]=useState('')
    const [receiptError,setReceiptError]=useState('')
    const navigate=useNavigate()
    const {id}=useParams()
    const {name}=useParams()
    const {jobtitle}=useParams()
    const {isManager}=useParams()
    const logout=()=>{
        navigate("/")
    }
    const setFileUponchange=(e)=>{
        const fileSelected=e.target.files[0];
        if(fileSelected){
            setReceipt(fileSelected)
        }
    }
    const validateType=()=>{
        console.log(expenseType)
        if(expenseType==="select")
            setTypeError('Please select below options')
        else
            setTypeError('')
    }
    const disableFutureDate=()=>{
        const today = new Date();
        const dd = String(today.getDate()).padStart(1, "0");
        const mm = String(today.getMonth()+1).padStart(1, "0"); //January is 0!
        const yyyy = today.getFullYear();
        return yyyy + "-" + mm + "-" + dd;
    }
    const enableBetweenDates=()=>{
        //const today = new Date();
        const date=new Date(startDate)
        const dd = String(date.getDate()).padStart(1, "0");
        const mm = String(date.getMonth()+1).padStart(1, "0"); //January is 0!
        const yyyy = date.getFullYear();
        return yyyy + "-" + mm + "-" + dd;
    }
    const validateClaimAmount=()=>{
        if(claimAmount<=0)
            setAmountError("Claim amount cannot be 0 or negitive")
        else
            setAmountError('')
    }
    const validateReceipt=()=>{
        if(receipt===null)
            setReceiptError("Please select a file file selectd cannot be empty")
        else
            setReceiptError('')
    }
    const addExpense=()=>{
            console.log({id,expenseType,startDate,endDate,claimAmount,receipt})
            /*formData.append("associateId",id)
            formData.append("expenseType",expenseType)
            formData.append("startDate",startDate)
            formData.append("endDate",endDate)
            formData.append("claimAmount",claimAmount)
            formData.append("receipt",receipt)*/
            console.log({"associateId":id,"expenseType":expenseType,"startDate":startDate,"endDate":endDate,"claimAmount":claimAmount,"receipt":receipt})
            if(typeError==='' &&  receiptError==='' && amountError==='')
                uploadTransaction({"associateId":id,"expenseType":expenseType,"startDate":startDate,"endDate":endDate,"claimAmount":claimAmount,"receipt":receipt}).then(
                    (response)=>{
                        console.log(response.data)
                        navigate(`/manager/${id}/${name}/${jobtitle}`)
                    }
                ).catch((error)=>{console.log(error)})
    }
    const submitApplyExpense=(e)=>{
        e.preventDefault()
        console.log({id,expenseType,startDate,endDate,claimAmount})
        addExpense()
        
    }
    return(
        <>
            <div class="container-fluid d-flex align-items-center justify-content-between pb-0 mt-0 mb-0 border-bottom text-white" style={{"background-color" : "#121C4E"}}>
                    <img src={adplogowhitee} className="img-fluid"  width={90} alt="adp logo"/>
                    <h2 className="text-center pt-2">Expense Management System</h2>
                    <ul class="nav navbar-nav">
                        <button type="button" class="btn text-white" onClick={(e)=>logout(e)}><Icon path={mdiLogout} size={1} /></button>
                    </ul>
            </div> 
            <div>
                    <ul class="nav nav-tabs">
                        <li class="nav-item">
                        <Link to={`/managerDashboard/${id}/${name}/${jobtitle}/${isManager}`} exact component={AddExpenseComponent} className="nav-link"> Home </Link> 
                        </li>
                        <li class="nav-item">
                        <a class="nav-link active" >Add Your Expense</a>
                        </li>
                        <li class="nav-item">
                        <Link to={`/managerAnalytics/${id}/${name}/${jobtitle}/${isManager}`} exact component={AddExpenseComponent} className="nav-link"> My analytics </Link> 
                        </li>
                        <li class="nav-item">
                        <Link to={`/managerAnalytics/${id}/${name}/${jobtitle}/${isManager}`} exact component={AddExpenseComponent} className="nav-link"> View My Expenses </Link> 
                        </li>
                        <li class="nav-item">
                        <Link to={`/managerAnalytics/${id}/${name}/${jobtitle}/${isManager}`} exact component={AddExpenseComponent} className="nav-link"> View My Team </Link> 
                        </li>
                    </ul>
                </div>
            <div className = "container">
                <div className = "row">
                    <div className = "card col-md-6 offset-md-3 offset-md-3 mt-3">
                       <h2  className="text-center">Apply Expense</h2>
                        <div className = "card-body">
                            <form  encType="multipart/form-data">
                            <div className = "form-group mb-2">
                                    <label for="roles" className = "form-label">Choose Expense Type:</label>

                                        <select name="expenseType" id="expenseType" className = "form-control" onChange={(e)=>setExpenseType(e.target.value)} onBlur={validateType}>
                                        <option value="select">Select</option>
                                        <option value="internet_expense">Internet Expense</option>
                                        <option value="travel_expense">Travel Expense</option>
                                        <option value="meal_expense">Meal Expense</option>
                                        <option value="relocation_expense">Relocation Expense</option>
                                        <option value="medical_expense">Medical Expense</option>
                                        </select>
                                </div>
                                <div role="alert" style={{color: 'red'}}>
                                        {typeError}
                                </div>
                                <div className = "form-group mb-2">
                                    <label className = "form-label"> Start Date:</label>
                                    <input
                                        type = "date"
                                        placeholder = "Enter start date"
                                        name = "startDate"
                                        className = "form-control"
                                        max={disableFutureDate()}
                                        value = {startDate}
                                        onChange = {(e) =>{ setStartdate(e.target.value)}}
                                    >
                                    </input>
                                </div>
                                <div className = "form-group mb-2">
                                    <label className = "form-label"> End Date:</label>
                                    <input
                                        type = "date"
                                        placeholder = "Enter end date"
                                        name = "endDate"
                                        className = "form-control"
                                        min={enableBetweenDates()}
                                        max={disableFutureDate()}
                                        value = {endDate}
                                        onChange = {(e) =>setEnddate(e.target.value)}
                                    >
                                    </input>
                                </div>
                                <div className = "form-group mb-2">
                                    <label className = "form-label"> Claim Amount :</label>
                                    <input
                                        type = "text"
                                        placeholder = "Enter claim amount"
                                        name = "claimAmount"
                                        className = "form-control"
                                        value = {claimAmount}
                                        onChange = {(e) => setClaimAmount(e.target.value)}
                                        onBlur={validateClaimAmount}
                                    >
                                    </input>
                                </div>
                                <div role="alert" style={{color: 'red'}}>
                                        {amountError}
                                </div>
                                <div className = "form-group mb-2">
                                    <label className = "form-label" htmlFor='fileInput'> Receipt :</label>
                                    <input
                                        type = "file"
                                        placeholder = "Upload Receipt"
                                        name = "receipt"
                                        className = "form-control"
                                        accept=".pdf, .jpg, .jpeg, .png"
                                        onChange = {setFileUponchange}
                                        onBlur={validateReceipt}
                                    >
                                    </input>
                                    {console.log}
                                </div>
                                <div role="alert" style={{color: 'red'}}>
                                        {receiptError}
                                </div>
                                <button class = "btn btn-success mr-5" type="submit" onClick={(e)=>submitApplyExpense(e)} >Apply Expense </button>
                            </form>

                        </div>
                    </div>
                </div>
           </div>
            <footer className="d-flex justify-content-end position-absolute bottom-0 end-0 w-100" style={{"height" : "60px" , "background-color" : "#E3DFDA"}}>
            <div className="d-flex align-items-center justify-content-center pe-4">
                <p className="text-dark text-center pt-2">Copyright © 2023 ADP, Inc.</p>
            </div>
            </footer>
        </>
    )
}
export default ManagerAddExpense