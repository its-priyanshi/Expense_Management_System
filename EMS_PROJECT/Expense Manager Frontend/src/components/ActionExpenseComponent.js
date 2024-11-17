import adplogowhitee from '../assets/adplogowhitee.jpg'
import Icon from '@mdi/react';
import { mdiLogout } from '@mdi/js';
import { useEffect, useState } from 'react'
import {useParams,useNavigate} from 'react-router-dom';
import { applyAction, getExpenseById, getPendingExpenses, getSubordinate } from '../service/EmsService';
function ActionExpenseComponent(){
    const [expenses,setExpenses]=useState([])
    const [status,setStatus]=useState('')
    const [comment,setComment]=useState('')
    const [loading,setLoading]=useState(true)
    const {id}=useParams()
    const {name}=useParams()
    const {jobtitle}=useParams()
    const {isManager}=useParams()
    const navigate=useNavigate()
    useEffect(()=>{
        fetchTransactions(id);
    },[id])
    const fetchTransactions=(id)=>{
        console.log(id)
        getPendingExpenses(id).then(
            (response)=>{
                console.log(response.data)
                setExpenses(response.data)
                setLoading(false)
            }
        ).catch((error)=>{console.log(error)})
    }

    const doActionOnTransaction=(id,status,comment)=>{
        console.log(status)
        applyAction(id,status,comment).then(
            (response)=>{
                console.log(response.data)
                fetchTransactions(id);
            }
        ).catch((error)=>{console.log(error)})
    }
    const logout=()=>{
        navigate(`/`)
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
    const home=(e)=>{
        navigate(`/manager/${id}/${name}/${jobtitle}/${isManager}`)
      }
    return(
        <>
            <div>
                <div class="container-fluid d-flex align-items-center justify-content-between pb-0 mt-0 mb-0 border-bottom text-white" style={{"background-color" : "#121C4E"}}>
                    <img src={adplogowhitee} className="img-fluid"  width={90} alt="adp logo"/>
                    <h2 className="text-center pt-2">Expense Management System</h2>
                    <ul class="nav navbar-nav">
                        <button type="button" class="btn text-white" onClick={(e)=>logout(e)}><Icon path={mdiLogout} size={1} /></button>
                    </ul>
                </div> 
            </div>
            <div className = "container">
                <br /><br />
                <h2 className = "text-center pb-3 mt-0 mb-0"> Action on Transactions</h2>
                {/* <Link to = "/add-employee" className = "btn btn-primary mb-2" > Add Employee </Link> */}
                {loading ? (
                    <div class="d-flex justify-content-center ">
                        <div class="spinner-border text-primary-emphasis" style={{width: 90, height: 90}}  role="status">
                            <span class="visually-hidden">Loading...</span>
                        </div>
                    </div>
                ) : (
               <>{expenses.length>0?(<> <table className="table table-bordered table-striped">
                    {/* <thead className="table-dark"> */}
                    <thead>   
                        <tr>
                                <th> EID</th>
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
                                    <td> {expense.expenseId} </td>
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
                                        style = {{marginLeft:"10px"}}> Apply Action</button>:null}
                                    </td>
                                </tr>
                            )
                        }
                    </tbody>
                </table></>):<h2 className='text-center mt-5' style={{color: 'grey'}}>No records to take action</h2>}</>)}
                <div class="row justify-content-center">
                                    <button style={{ width: "100px", height: "50px",}} class=" btn btn-outline-secondary mt-1" type="submit" onClick={(e)=>home(e)}>Home</button>
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
export default ActionExpenseComponent