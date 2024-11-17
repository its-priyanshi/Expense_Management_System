import { useEffect, useState } from "react"
import { Link, useNavigate, useParams } from "react-router-dom"
import { getSubordinate } from "../service/EmsService"
import AddExpenseComponent from "./AddExpenseComponent"
import adplogowhitee from '../assets/adplogowhitee.jpg'
import Icon from '@mdi/react';
import { mdiLogout } from '@mdi/js';
function SubOrdinatesComponent(){
    const [associates,setAssociates]=useState()
    const {id}=useParams()
    const {name}=useParams()
    const {jobtitle}=useParams()
    const {isManager}=useParams()
    const navigate=useNavigate()
    useEffect(()=>{
        fetchSubordinates(id)
    },[id])
    const fetchSubordinates=(id)=>{
        getSubordinate(id).then(
            (response)=>{
                setAssociates(response.data)
            }
        ).catch((error)=>{console.log(error)})
    }
    const logout=(e)=>{
        navigate(`/`)
    }
    const viewAnalytics=(associateId)=>{
        navigate(`/subAnalytics/${associateId}/${id}/${name}/${jobtitle}/${isManager}`)
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
            <div className="py-3">
                    <ul class="nav nav-tabs">
                        <li class="nav-item">
                        <Link to={`/managerDashboard/${id}/${name}/${jobtitle}/${isManager}`} exact component={AddExpenseComponent} className="nav-link"> Home </Link> 
                        </li>
                        <li class="nav-item">
                        <Link to={`/managerAddExpense/${id}/${name}/${jobtitle}/${isManager}`} exact component={AddExpenseComponent} className="nav-link"> Add Your Expense </Link> 
                        </li>
                        <li class="nav-item">
                        <Link to={`/managerAnalytics/${id}/${name}/${jobtitle}/${isManager}`} exact component={AddExpenseComponent} className="nav-link"> My Analytics </Link> 
                        </li>
                        <li class="nav-item">
                        <Link to={`/managerExpenses/${id}/${name}/${jobtitle}/${isManager}`} exact component={AddExpenseComponent} className="nav-link"> View My Expenses </Link> 
                        </li>
                        <li class="nav-item">
                        <a class="nav-link active" href="#">View My Team</a>
                        </li>
                    </ul>
                </div>
            <div className = "container mb-5">
                    <br /><br />
                    <h2 className='text-center'>Your Team</h2>
                    {/* <Link to = "/add-employee" className = "btn btn-primary mb-2" > Add Employee </Link> */}
                    <table className="table table-bordered table-striped">
                        {/* <thead className="table-dark"> */}
                        <thead>   
                            <tr>
                                <th> Associate Id </th>
                                <th> Name </th>
                                <th> Position </th>
                                <th> Email Id</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                               Array.isArray(associates) && associates.map(
                                    associate =>
                                    <tr key = {associate.associateId}> 
                                        <td>{associate.associateId}</td>
                                        <td> <button className="btn btn-link pt-0" onClick={()=>viewAnalytics(associate.associateId)}>{associate.associateName} </button></td>
                                        <td> {associate.position} </td>
                                        <td> {associate.emailId}</td>
                                    </tr>
                                )
                            }
                        </tbody>
                    </table>
                </div>
            <footer className="d-flex justify-content-end position-absolute top-100 end-0 w-100 mt-4" style={{"height" : "60px" , "background-color" : "#E3DFDA"}}>
            <div className="d-flex align-items-center justify-content-center pe-4">
                <p className="text-dark text-center pt-2">Copyright © 2023 ADP, Inc.</p>
            </div>
            </footer>
        </>
    )
}
export default SubOrdinatesComponent