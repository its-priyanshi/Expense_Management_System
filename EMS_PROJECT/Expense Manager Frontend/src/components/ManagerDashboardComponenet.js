import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import ADP_HomePage_logo from '../assets/ADP_HomePage_logo.PNG'
import { applyAction, getBudget, getExpenseById, getPendingExpenses, getStatusWiseApprovedAmount } from "../service/EmsService";
import AddExpenseComponent from "./AddExpenseComponent";
import adplogowhitee from '../assets/adplogowhitee.jpg'
import Icon from '@mdi/react';
import { mdiLogout } from '@mdi/js';
function ManagerDashboardComponenet(){
    const {id}=useParams()
    const {name}=useParams()
    const {jobtitle}=useParams()
    const {isManager}=useParams()
    const navigate=useNavigate()
    const [expenses,setExpenses]=useState([])
    const [loading, setLoading] = useState(false);
    const [status,setStatus]=useState('')
    const [budget,setBudget]=useState(0)
    const [acceptAmount,setAcceptAmount]=useState(0)
    const [pendingAmount,setPendingAmount]=useState(0)
    const [comment,setComment]=useState('')
    useEffect(()=>{
        fetchTransactions(id);
        fetchStatusWiseAmount()
    },[id])
    useEffect(()=>{
        fetchBudget()
    },[budget])
    useEffect(()=>{
        fetchStatusWiseAmount()
    },[pendingAmount,acceptAmount])
    const fetchTransactions=(id)=>{
        setLoading(true)
        console.log(id)
        getPendingExpenses(id).then(
            (response)=>{
                console.log(response.data)
                setExpenses(response.data)
                setLoading(false)
            }
        ).catch((error)=>{console.log(error)})
    }
    const fetchBudget=()=>{
        getBudget(id).then(
            (response)=>{
                setBudget(response.data)
            }
        )
    }
    const fetchStatusWiseAmount=()=>{
        getStatusWiseApprovedAmount(id).then(
            (response)=>{
                setAcceptAmount(response.data.accept)
                setPendingAmount(response.data.pending)
            }
        )
    }
    const doActionOnTransaction=(expenseId,status,comment)=>{
        applyAction(expenseId,status,comment).then(
            (response)=>{
                console.log(response.data)
                fetchTransactions(id)
                fetchStatusWiseAmount()
                fetchBudget()
            }
        ).catch((error)=>{console.log(error)})
    }
    const viewPdf=(e,expenseId)=>{
        console.log(typeof(expenseId))
        console.log(expenseId)
        navigate(`/viewReceipt/${id}/${name}/${jobtitle}/${expenseId}`)
        getExpenseById(expenseId).then(
            (response)=>{
                //setReceipt(response.data.receipt)
                const receipt=response.data.receipt
                const decodedReceipt=atob(receipt)
                console.log(decodedReceipt)
                const byteArrayPdf=new Uint8Array(decodedReceipt.length)
                for(let i=0;i<decodedReceipt.length;i++){
                    byteArrayPdf[i]=decodedReceipt.charCodeAt(i)
                }
                console.log(byteArrayPdf)
                const blob=new Blob([byteArrayPdf],{type:'application/pdf'})
                const pdfFile=URL.createObjectURL(blob)
                window.open(pdfFile)
            }
        ).catch((error)=>{console.log(error)})
    }
    const logout=(e)=>{
        navigate(`/`)
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
            <div class="container-fluid d-flex align-items-center justify-content-between pt-2 pb-2 border-bottom">
                <div>
                    <ul class="nav nav-tabs">
                        <li class="nav-item">
                        <a class="nav-link active">My Home</a>
                        </li>
                        <li class="nav-item">
                        <Link to={`/managerAddExpense/${id}/${name}/${jobtitle}/${isManager}`} exact component={AddExpenseComponent} className="nav-link"> Add Your Expense </Link> 
                        </li>
                        <li class="nav-item">
                        <Link to={`/managerAnalytics/${id}/${name}/${jobtitle}/${isManager}`} exact component={AddExpenseComponent} className="nav-link"> My analytics </Link> 
                        </li>
                        <li class="nav-item">
                        <Link to={`/managerExpenses/${id}/${name}/${jobtitle}/${isManager}`} exact component={AddExpenseComponent} className="nav-link"> View My Expenses </Link> 
                        </li>
                        <li class="nav-item">
                        <Link to={`/getSubordinates/${id}/${name}/${jobtitle}/${isManager}`} exact component={AddExpenseComponent} className="nav-link"> View My Team </Link> 
                        </li>
                    </ul>
                </div>
                <div className="d-flex align-items-center justify-content-center">
                        <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-person-circle" viewBox="0 0 16 16">
                        <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                        <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                    </svg>
                    <h5 className="text-center ps-2 mb-1">{name}</h5>
                </div>
            </div>
            <div className="row row-cols-1 row-cols-md-3 mb-1 text-center">
                <div className='col'>
                    <div className='card mb-4 rounded-3 shadow-sm text-secondary mt-2 ms-1 text-white' style={{"background-color" : "#CD7F32"}}>
                        <div className='card-header py-1'>
                            <h4 className='my-0 fw-normal'>Budget</h4>
                        </div>
                        <div className='card-body'>
                            <h1 className='card-title pricing-card-title'>
                                <small className='text-body-secodary fw-light'><p>&#8377;{budget}</p></small> 
                            </h1> 
                            </div>
                    </div>
                </div>
                <div className='col'>
                    <div className='card mb-4 rounded-3 shadow-sm text-secondary mt-2 ms-2 text-white' style={{"background-color" : "#228B22"}}>
                        <div className='card-header py-1'>
                            <h4 className='my-0 fw-normal'>Approved Amount</h4>
                        </div>
                        <div className='card-body'>
                            <h1 className='card-title pricing-card-title'>
                                <small className='text-body-secodary fw-light'><p>&#8377;{acceptAmount}</p></small> 
                            </h1> 
                            </div>
                    </div>
                </div>
                <div className='col'>
                    <div className='card mb-4 rounded-3 shadow-sm text-secondary mt-2 ms-2 text-white' style={{"background-color" : "#800080"}}>
                        <div className='card-header py-1'>
                            <h4 className='my-0 fw-normal'>Pending Amount</h4>
                        </div>
                        <div className='card-body'>
                            <h1 className='card-title pricing-card-title'>
                                <small className='text-body-secodary fw-light'><p>&#8377;{pendingAmount}</p></small> 
                            </h1> 
                            </div>
                    </div>
                </div>
            </div>
            <h3 className="text-center py-3">Pending Expenses of Your Team</h3>
            {loading ? (
                    <div class="d-flex justify-content-center" style={{marginTop:"100px"}}>
                        <div class="spinner-border text-primary-emphasis" style={{width: 90, height: 90}}  role="status">
                            <span class="visually-hidden">Loading...</span>
                        </div>
                    </div>
                ) : (
               <>{expenses.length>0?(<> 
               <table className="table table-bordered table-striped">
                    {/* <thead className="table-dark"> */}
                    <thead>   
                        <tr>
                                <th> Associate Id</th>
                                <th> Expense Type </th>
                                <th> Start Date </th>
                                <th> End Date </th>
                                <th> Claimed Amount</th>
                                <th> Receipt</th>
                                <th> Status </th>
                                <th> Comments </th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            expenses.map(
                                expense =>
                                <tr key = {expense.expenseId}>
                                    <td> {expense.associateId} </td>
                                    <td> {expense.expenseType} </td>
                                    <td> {expense.startDate} </td>
                                    <td> {expense.endDate}</td>
                                    <td> {expense.claimAmount} </td>
                                    <td>
                                            <div class="row justify-content-center">
                                                {console.log(expense.receipt)}
                                                <button style={{ width: "100px", height: "50px",}} class=" btn btn-link" type="submit" onClick={(e)=>viewPdf(e,expense.expenseId)}>Receipt</button>
                                            </div>
                                        </td>
                                    <td>
                                        <select name="expenseAction" id="expenseAction" className = "form-control" onChange={(e)=>setStatus(e.target.value)}>
                                        <option value="select">Select</option>
                                        <option value="accept">Accept</option>
                                        <option value="reject">Reject</option>
                                        </select>
                                    </td>
                                    <td>
                                            <input
                                                type = "text"
                                                placeholder = "Enter comments"
                                                name = "comments"
                                                className = "form-control"
                                                onChange = {(e) => setComment(e.target.value)}>
                                            </input>
                                    </td>
                                    <td>
                                    { expense.status==="pending"?<button className = "btn btn-primary" onClick = {() => doActionOnTransaction(expense.expenseId,status,comment)}
                                        style = {{marginLeft:"10px"}}> Submit</button>:null}
                                    </td>
                                </tr>
                            )
                        }
                    </tbody>
                </table></>):<h2 className='text-center mt-5' style={{color: 'grey'}}>No pending expenses</h2>}</>)}
        </>
    )
}
export default ManagerDashboardComponenet;