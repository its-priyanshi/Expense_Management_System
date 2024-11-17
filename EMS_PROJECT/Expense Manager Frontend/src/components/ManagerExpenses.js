import { Link, useNavigate, useParams } from "react-router-dom"
import { getAmountByStatus, getCountByStatus, getExpenseById, getTransactionByStatuswise } from "../service/EmsService"
import ADP_HomePage_logo from '../assets/ADP_HomePage_logo.PNG'
import { useEffect, useState } from "react";
import AddExpenseComponent from "./AddExpenseComponent";
import adplogowhitee from '../assets/adplogowhitee.jpg'
import Icon from '@mdi/react';
import { mdiLogout } from '@mdi/js';
const ITEMS_PER_PAGE=2;
function ManagerExpense(){
    const navigate=useNavigate()
    const {id}=useParams()
    const {name}=useParams()
    const {jobtitle}=useParams()
    const {isManager}=useParams()
    //const [pendingAmount,setPendingAmount]=useState(45000);
    const [expenses,setExpenses]=useState([])
    const [loading, setLoading] = useState(false);
    const [totalPages,setTotalPages]=useState(1)
    const [currentPage, setCurrentPage] = useState(0);
    const [status,setStatus]=useState('')
    const [acceptCount,setAcceptCount]=useState(0)
    const [rejectCount,setRejectCount]=useState(0)
    const [pendingCount,setPendingCount]=useState(0)
    const [acceptAmount,setAcceptAmount]=useState(0)
    const [rejectAmount,setRejectAmount]=useState(0)
    const [pendingAmount,setPendingAmount]=useState(0)
    
    useEffect(()=>{
        getStatusWiseAmount();
        getStatusWiseCount();
        if(status!==''){
            getAllPendingTransaction(status)
        }
    },[acceptAmount,acceptCount,rejectAmount,rejectCount,pendingAmount,pendingCount,currentPage])
    
    const getStatusWiseAmount=()=>{
        getAmountByStatus(id).then(
            (response)=>{
                setAcceptAmount(response.data.accept)
                setRejectAmount(response.data.reject)
                setPendingAmount(response.data.pending)
            }
        )
    }

    const getStatusWiseCount=()=>{
        getCountByStatus(id).then(
            (response)=>{
                setAcceptCount(response.data.accept)
                setRejectCount(response.data.reject)
                setPendingCount(response.data.pending)
            }
        )
    }
    
    const logout=(e)=>{
        navigate(`/`)
    }
    const getAllPendingTransaction=(status)=>{
        setLoading(true)
        getTransactionByStatuswise(id,status,currentPage,ITEMS_PER_PAGE).then(
            (response)=>{
                setExpenses([])
                setExpenses(response.data.content)
                setLoading(false)
                setTotalPages(response.data.totalPages)
            }
        )
    }
    const viewPdf=(e,expenseId)=>{
        /*console.log(typeof(expenseId))
        console.log(expenseId)
        navigate(`/viewReceipt/${id}/${name}/${jobtitle}/${expenseId}`)*/
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
    const pageNumbers = [];
    for(let i=0; i< totalPages; i++){
        pageNumbers.push(i);
    }
    return(
        <>
            <div class="container-fluid d-flex align-items-center justify-content-between pb-0 mt-0 mb-0 border-bottom text-white" style={{"background-color" : "#121C4E"}}>
                    <img src={adplogowhitee} className="img-fluid"  width={90} />
                    <h2 className="text-center pt-2">Expense Management System</h2>
                    <ul class="nav navbar-nav">
                        <button type="button" class="btn text-white" onClick={(e)=>logout(e)}><Icon path={mdiLogout} size={1} /></button>
                    </ul>
            </div> 
            <div class="container-fluid d-flex align-items-center justify-content-between pt-2 pb-2 border-bottom">
            <div>
                    <ul class="nav nav-tabs">
                        <li class="nav-item">
                        <Link to={`/managerDashboard/${id}/${name}/${jobtitle}/${isManager}`} exact component={AddExpenseComponent} className="nav-link"> Home </Link> 
                        </li>
                        <li class="nav-item">
                        <Link to={`/managerAddExpense/${id}/${name}/${jobtitle}/${isManager}`} exact component={AddExpenseComponent} className="nav-link"> Add Your Expense </Link> 
                        </li>
                        <li class="nav-item">
                        <Link to={`/managerAnalytics/${id}/${name}/${jobtitle}/${isManager}`} exact component={AddExpenseComponent} className="nav-link"> My analytics </Link> 
                        </li>
                        <li class="nav-item">
                        <a class="nav-link active" href="#">View My expenses</a></li>
                        <li class="nav-item">
                        <Link to={`/getSubordinates/${id}/${name}/${jobtitle}/${isManager}`} exact component={AddExpenseComponent} className="nav-link"> View My Team </Link> 
                        </li>
                    </ul>
                </div>
            </div>
            <div className="row row-cols-1 row-cols-md-3 mb-1 text-center">
                <div className='col'>
                    <div className='card mb-4 rounded-3 shadow-sm text-secondary mt-2 ms-1 text-white' style={{"background-color" : "#CD7F32"}}>
                        <div className='card-header py-1'>
                            <h4 className='my-0 fw-normal'>Pending Amount</h4>
                        </div>
                        <div className='card-body'>
                            <h1 className='card-title pricing-card-title'>
                                <small className='text-body-secodary fw-light'>{pendingAmount}</small> 
                            </h1> 
                            <ul className='list-unstyled mt-3 mb-4'>
                                <li className='fw-light'>Total pending transactions : {pendingCount}</li>
                            </ul>
                            <button className='w-100 btn btn-lg btn-outline-light' type="button" onClick={()=>{setStatus("pending");getAllPendingTransaction("pending")}}>View pending expenses</button>
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
                                <small className='text-body-secodary fw-light'>{acceptAmount}</small> 
                            </h1> 
                            <ul className='list-unstyled mt-3 mb-4'>
                                <li className='fw-light'>Total approved transactions : {acceptCount}</li>
                            </ul>
                            <button className='w-100 btn btn-lg btn-outline-light' type="button" onClick={()=>{setStatus("accept");getAllPendingTransaction("accept")}}>View approved expenses</button>
                        </div>
                    </div>
                </div>
                <div className='col'>
                    <div className='card mb-4 rounded-3 shadow-sm text-secondary mt-2 ms-2 text-white' style={{"background-color" : "#D22B2B"}}>
                        <div className='card-header py-1'>
                            <h4 className='my-0 fw-normal'>Rejected Amount</h4>
                        </div>
                        <div className='card-body'>
                            <h1 className='card-title pricing-card-title'>
                                <small className='text-body-secodary fw-light'>{rejectAmount}</small> 
                            </h1> 
                            <ul className='list-unstyled mt-3 mb-4'>
                                <li className='fw-light'>Total rejected transactions : {rejectCount}</li>
                            </ul>
                            <button className='w-100 btn btn-lg btn-outline-light' type="button" onClick={()=>{setStatus("reject");getAllPendingTransaction("reject")}}>View rejected expenses</button>
                        </div>
                    </div>
                </div>
            </div>
            { expenses.length!==0?(<>{loading ? (
                    <div class="d-flex justify-content-center" style={{marginTop:"100px"}}>
                        <div class="spinner-border text-primary-emphasis" style={{width: 90, height: 90}}  role="status">
                            <span class="visually-hidden">Loading...</span>
                        </div>
                    </div>
                ) : (
                    <>
                    {expenses.length>0?(<><table className="table table-bordered table-striped">
                {/* <thead className="table-dark"> */}
                <thead>   
                    <tr>
                        <th> Expense Type </th>
                        <th> Start Date </th>
                        <th> End Date </th>
                        <th> Claimed Amount</th>
                        <th> Receipt</th>
                        <th> Comments </th>
                        <th> Status </th>
                        <th> Approved Date</th>
                    </tr>
                </thead>
                <tbody>
                    {
                       Array.isArray(expenses) && expenses.map(
                            expense =>
                            <tr key = {expense.expenseId}> 
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
                                <td>{expense.managerComments} </td>
                                <td>{expense.status} </td>
                                <td>{expense.approvedDate}</td>
                            </tr>
                        )
                    }
                </tbody>
            </table>
            <div className="justify-content-center">
                <nav aria-label="Page navigation example" className='align-items-center'>
                    <ul class="pagination justify-content-center">
                        <li class="page-item" onClick={()=>{setCurrentPage((currentPage>0)?(currentPage-1):(0))}} disabled={currentPage===0}>
                            <a class="page-link" aria-label="Previous">
                                <span aria-hidden="true">Previous </span>
                            </a>
                        </li>
                        {pageNumbers.map(number => (
                            <li key={number} className="page-item">
                                <span className="page-link" onClick={()=>{setCurrentPage(number)}}> {number + 1} </span>
                            </li>
                        ))}
                        <li class="page-item">
                            <a class="page-link" aria-label="Next" onClick={()=>{setCurrentPage((currentPage<totalPages-1)?(currentPage+1):(totalPages-1))}} disabled={currentPage===totalPages-1}>
                                <span aria-hidden="true">Next</span>
                            </a>
                        </li>
                    </ul>
                </nav>


            </div>
            </>):<h2 class="text-center mt-5" style={{color: 'grey'}}>No expenses made</h2>}</>)}</>):(<></>)}
            <footer className="d-flex justify-content-end position-absolute top-100 end-0 w-100 mt-4" style={{"height" : "40px" , "background-color" : "#E3DFDA"}}>
            <div className="d-flex align-items-center justify-content-center pe-4">
                <p className="text-dark text-center pt-2">Copyright © 2023 ADP, Inc.</p>
            </div>
            </footer>
        </>
    )
}
export default ManagerExpense
