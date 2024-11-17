import adplogowhitee from '../assets/adplogowhitee.jpg'
import Icon from '@mdi/react';
import { mdiLogout } from '@mdi/js';
import { useEffect, useState } from "react";
import { Doughnut, Pie } from "react-chartjs-2";
import Chart from "chart.js/auto";
import { CategoryScale } from "chart.js";
import { Link, useNavigate, useParams } from "react-router-dom";
import ADP_HomePage_logo from '../assets/ADP_HomePage_logo.PNG'
import {  getStatusAnalytics, getTypeAnalytics } from "../service/EmsService";
import AddExpenseComponent from "./AddExpenseComponent";

function InsightsComponent(){
    const {id}=useParams()
    const {name}=useParams()
    const {jobtitle}=useParams()
    const {isManager}=useParams()
    const {mngrId}=useParams()
    const[approved, setApproved] = useState(0);
    const[pending, setPending] = useState(0);
    const[rejected, setReject] = useState(0);
    const[expenseType,setExpenseType]=useState("none")
    const[monthStatus,setMonthStatus]=useState(new Date().getMonth()+1)
    const[monthType,setMonthType]=useState(new Date().getMonth()+1)
    const[internet,setInternet]=useState(0)
    const[food,setFood]=useState(0)
    const[medical,setMedical]=useState(0)
    const[travel,setTravel]=useState(0)
    const[relocation,setRelocation]=useState(0)
    const navigate=useNavigate()
    useEffect(() => {
        fetchStatusAnalytics();
        fetchTypeAnalytics();
    }, [id,monthStatus,expenseType,monthType,internet,food,medical,relocation,travel])

    const fetchStatusAnalytics = () => {
        console.log(monthStatus+" "+monthType)
        console.log(new Date().getMonth()+1)
        getStatusAnalytics(id,monthStatus,expenseType).then((response) => {
            setApproved(response.data.Approved);
            setPending(response.data.Pending);
            setReject(response.data.Rejected);
            console.log(response.data);
            //console.log('heyy')
            
        }).catch(error =>{
            console.log(error);
        })
      }
      const fetchTypeAnalytics=()=>{
        console.log("entering fetch type analytics")
        getTypeAnalytics(id,monthType).then(
            (response)=>{
                console.log(response)
                setInternet(response.data.Internet)
                setTravel(response.data.Travel)
                setFood(response.data.Food)
                setMedical(response.data.Medical)
                setRelocation(response.data.Relocation)
                console.log(internet+" "+food+" "+medical+" "+travel+" "+relocation)
            }
        ).catch((error)=>{console.log(error)})
        console.log("Exited fetch type analytics")
      }
      const statusData = {
        labels: ['Approved', 'Rejected','Pending'],
        datasets: [
          {
            label: '',
            data: [approved,rejected, pending ],
            backgroundColor: [
                'rgb(144,238,144)',
                'rgb(255,51,51)',
                'rgb(255,255,128)'                
               ],
            borderColor: 'rgba(0,0,0,1)',
            borderWidth: 1,
          },
        ],
      };
      const typeData = {
        labels: ['Internet','Travel','Food','Medical','Relocation'],
        datasets: [
          {
            label: '',
            data: [internet,travel,food,medical,relocation ],
            backgroundColor: [
                'rgb(255,153,255)',
                'rgb(153,255,153)',
                'rgb(255,255,128)' ,
                'rgb(255,204,153)',
                'rgb(102,178,255)'               
               ],
            borderColor: 'rgba(0,0,0,1)',
            borderWidth: 1,
          },
        ],
      };
      const logout=()=>{
        navigate(`/`)
      }
    return(
        <>
        <div>
            <div class="container-fluid d-flex align-items-center justify-content-between pb-0 mt-0 mb-0 border-bottom text-white" style={{"background-color" : "#121C4E"}}>
                    <img src={adplogowhitee} className="img-fluid"  width={90} alt="adp logo"/>
                    <h2 className="text-center pt-2">Expense Management System</h2>
                    <ul class="nav navbar-nav">
                        <button type="button" class="btn text-white" onClick={(e)=>logout(e)}><Icon path={mdiLogout} size={1}  /></button>
                    </ul>
                </div> 
            </div>
{
                isManager==="true"?(<>
                <div className="py-3">
                    <ul class="nav nav-tabs">
                        <li class="nav-item">
                        <Link to={`/managerDashboard/${mngrId}/${name}/${jobtitle}/${isManager}`} exact component={AddExpenseComponent} className="nav-link"> Home </Link> 
                        </li>
                        <li class="nav-item">
                        <Link to={`/managerAddExpense/${mngrId}/${name}/${jobtitle}/${isManager}`} exact component={AddExpenseComponent} className="nav-link"> Add Your Expense </Link> 
                        </li>
                        <li class="nav-item">
                        <Link to={`/managerAnalytics/${mngrId}/${name}/${jobtitle}/${isManager}`} exact component={AddExpenseComponent} className="nav-link"> My Analytics </Link> 
                        </li>
                        <li class="nav-item">
                        <Link to={`/managerExpenses/${mngrId}/${name}/${jobtitle}/${isManager}`} exact component={AddExpenseComponent} className="nav-link"> View My Expenses </Link> 
                        </li>
                        <li class="nav-item">
                        <a class="nav-link active">View My Team</a>
                        </li>
                    </ul>
                </div>
            </>):(<>
                {console.log(isManager+" "+isManager===true)}
                <div className='py-2 mb-2'>
                    <ul class="nav nav-tabs">
                        <li class="nav-item">
                            <Link to={`/sampleDashboard/${id}/${name}/${jobtitle}/${isManager}`} exact component={AddExpenseComponent} className="nav-link"> Home </Link> 
                        </li>
                        <li class="nav-item">
                            <Link to={`/addExpense/${id}/${name}/${jobtitle}/${isManager}`} exact component={AddExpenseComponent} className="nav-link">Add expense</Link> 
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active">My analytics</a>
                        </li>
                    </ul>
                </div>
            </>)
            }
                <div className="row ms-5 mt-0 ps-5 d-flex align-items-center justify-content-center">
                    <div className="card col-6 me-5 mt-0" style={{"width" : "300px" , "height" : "460px"}}>
                        <h6 className="text-center">Expense Categorization By Status</h6>
                        <small className="text-center"><small >(Default chart is for current month)</small></small>
                        <label for="roles" className = "form-label">Expense Type:</label>
                        <select name="expenseType" id="expenseType" className = "form-control" onChange={(e)=>setExpenseType(e.target.value)}>
                                        <option value="none">Select</option>
                                        <option value="internet_expense">Internet Expense</option>
                                        <option value="travel_expense">Travel Expense</option>
                                        <option value="meal_expense">Meal Expense</option>
                                        <option value="relocation_expense">Relocation Expense</option>
                                        <option value="medical_expense">Medical Expense</option>
                                        </select>
                                        <label for="roles" className = "form-label">Month:</label>
                        <select name="expenseType" id="expenseType" className = "form-control" onChange={(e)=>setMonthStatus(e.target.value)} >
                                        <option value={new Date().getMonth()+1}>Select</option>
                                        <option value={1}>Janurary</option>
                                        <option value={2}>February</option>
                                        <option value={3}>March</option>
                                        <option value={4}>April</option>
                                        <option value={5}>May</option>
                                        <option value={6}>June</option>
                                        <option value={7}>July</option>
                                        <option value={8}>August</option>
                                        <option value={9}>September</option>
                                        <option value={10}>October</option>
                                        <option value={11}>November</option>
                                        <option value={12}>December</option>
                        </select>
                        <div align-items-center class="card-body">
                            <Pie data={statusData} />
                        </div>  
                    </div>
                    <div className="card col-6 me-3 mt-1" style={{"width" : "300px" , "height" : "460px"}}>
                    <h6 className="text-center">Expense categorization by expense type</h6>
                    <small className="text-center"><small >(Default chart is for current month)</small></small>
                    <label for="roles" className = "form-label">Month:</label>
                    <select name="expenseType" id="expenseType" className = "form-control" onChange={(e)=>setMonthType(e.target.value)} >
                                        <option value={new Date().getMonth()+1}>Select</option>
                                        <option value={1}>Janurary</option>
                                        <option value={2}>February</option>
                                        <option value={3}>March</option>
                                        <option value={4}>April</option>
                                        <option value={5}>May</option>
                                        <option value={6}>June</option>
                                        <option value={7}>July</option>
                                        <option value={8}>August</option>
                                        <option value={9}>September</option>
                                        <option value={10}>October</option>
                                        <option value={11}>November</option>
                                        <option value={12}>December</option>
                    </select>
                        <div class="card-body mt-5">
                            <Pie data={typeData} />
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
export default InsightsComponent;
