import { useEffect, useState } from 'react'
import ADP_HomePage_logo from '../assets/ADP_HomePage_logo.PNG'
import {useParams,useNavigate} from 'react-router-dom';
import { getAssociatesAlltransactions, getExpenseById } from '../service/EmsService';
//import { type } from '@testing-library/user-event/dist/type';
const ITEMS_PER_PAGE=4;
function EmployeeComponent(){
    const [expenses,setExpenses]=useState([])
    const [dummyexpenses,setDummyExpenses]=useState([])
    const [text, setText] = useState("");
    const [loading, setLoading] = useState(false);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages,setTotalPages]=useState(1)
    const {id}=useParams()
    const {name}=useParams()
    const {jobtitle}=useParams()
    const navigate=useNavigate()
    
    useEffect(()=>{
        fetchTransactions(id);
    },[id,currentPage])
    const fetchTransactions=(id)=>{
        console.log(id)
        console.log(currentPage+" "+ITEMS_PER_PAGE)
        setLoading(true)
        getAssociatesAlltransactions(id,currentPage,ITEMS_PER_PAGE).then(
            (response)=>{
                console.log(response.data)
                setExpenses(response.data.content);
                setDummyExpenses(response.data.content);
                setTotalPages(response.data.totalPages)
                setLoading(false)
            }
        ).catch((error)=>{console.log(error)})
    }


    const search = (e) => {
        console.log(e.target.value)
        setText(e.target.value);
        const searchResult = dummyexpenses.filter((post) => {
        return (
        // String(post.pid).toLowerCase().includes(e.target.value.toLowerCase()) ||
        post.expenseType.toLowerCase().includes(e.target.value.toLowerCase()) ||
        post.status.toLowerCase().includes(e.target.value.toLowerCase()) ||
        post.associateId.toLowerCase().includes(e.target.value.toLowerCase()) ||
        // post.expenseId.toLowerCase().includes(e.target.value.toLowerCase()) ||
        post.appliedDate.toLowerCase().includes(e.target.value.toLowerCase()) ||
        post.endDate.toLowerCase().includes(e.target.value.toLowerCase()) ||
        post.startDate.toLowerCase().includes(e.target.value.toLowerCase())
        
        // post.description.toLowerCase().includes(e.target.value.toLowerCase())
        );
        });
        setExpenses(searchResult);
        
        
        
        }
    
    const logout=()=>{
        navigate(`/`)
    }
    const addExpense=(e)=>{
        e.preventDefault(e)
        navigate(`/addExpense/${id}/${name}/${jobtitle}`)
    }
    const getAnalytics=(e)=>{
        e.preventDefault()
        console.log(id)
        navigate(`/employeeAnalytics/${id}/${name}/${jobtitle}`)
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
    const handlePageChange=(newPage)=>{
        
        setCurrentPage(newPage)
    }
    /*const indexOfLastExpense = currentPage * expensesPerPage;
    const indexOfFirstExpense = indexOfLastExpense - expensesPerPage;
    const currentExpenses = expenses.slice(indexOfFirstExpense,indexOfLastExpense);
    const totalExpenses = expenses.length;


    const pageNumbers = [];


    for(let i=1; i<= Math.ceil(totalExpenses / expensesPerPage); i++){
        pageNumbers.push(i);
    }


    const paginate = (pageNumber) => setCurrentPage(pageNumber);


    const paginatePrevPage = () => {
        if(currentPage === 1) return false;
        setCurrentPage(currentPage - 1);
    };


    const paginateNextPage =()=>{
        if(indexOfLastExpense >= totalExpenses) return false;
        setCurrentPage(currentPage + 1);
    };*/
    return(
        <>
            <div>
                <div class="container-fluid d-flex align-items-center justify-content-between pb-0 mt-0 mb-0 border-bottom text-white" style={{"background-color" : "#121C4E"}}>
                    <img src={ADP_HomePage_logo} className="img-fluid pt-3 "  width={100} height={30}/>
                    <h2 className="text-center">Expense Management System</h2>
                    <ul class="nav navbar-nav">
                        <button type="button" class="btn btn-primary text-white" onClick={(e)=>logout(e)}>Log Out</button>
                    </ul>
                </div>
            </div>
            <div class="container-fluid d-flex align-items-center justify-content-between pt-2 pb-2 border-bottom " style={{"background-color" : "#E3DFDA"}}>
                <div className="d-flex align-items-center justify-content-center">
                    <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-person-circle" viewBox="0 0 16 16">
                        <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                        <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                    </svg>
                    <h5 className="text-center ps-2 mb-1">{name}</h5>
                </div>
                
                <h5>Job Title : {jobtitle}</h5>
            </div>
            <div className = "container mb-5">
                    <br /><br />
                    <h2 className='text-center'>Your expenses</h2>
                    <div style={{display:'flex', justifyContent:'space-between'}}>
                        <nav class="navbar navbar-light bg-light">
                            <form class="container-fluid justify-content-start">
                                <button class="btn btn-secondary me-2" type="button" onClick={(e)=>addExpense(e)}>Add Expense</button>
                                <button class="btn btn-secondary me-2" type="button" onClick={(e)=>getAnalytics(e)}>My Analytics</button>
                            </form>
                        </nav>
                        {/* <Link to = "/add-employee" className = "btn btn-primary mb-2" > Add Employee </Link> */}
                        <div>
                        <textarea style={{height:'35px', border:'' , width:'200px'}} placeholder='Search here...' onChange = {(e) => search(e) }></textarea>
                            {/* <input type="input" onChange = {(e) => search(e)} placeholder="Search" className='form-control'></input>  */}
                        </div>
                        </div>
                    {/* <Link to = "/add-employee" className = "btn btn-primary mb-2" > Add Employee </Link> */}
                    
                    {loading ? (
                    <div class="d-flex justify-content-center ">
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
                        <th> EID</th>
                        <th> Expense Type </th>
                        <th> Start Date </th>
                        <th> End Date </th>
                        <th> Claimed Amount</th>
                        <th> Receipt</th>
                        <th> Comments </th>
                        <th> Status </th>
                        <th> Apply Date</th>
                        <th> Approved Date</th>
                    </tr>
                </thead>
                <tbody>
                    {
                       Array.isArray(expenses) && expenses.map(
                            expense =>
                            <tr key = {expense.expenseId}> 
                                <td> {expense.expenseId} </td>
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
                                <td>{expense.appliedDate} </td>
                                <td>{expense.approvedDate}</td>
                            </tr>
                        )
                    }
                </tbody>
            </table>
            <div class="justify-content-center">
                <button className="btn btn-outline-primary" disabled={currentPage===0} onClick={()=>handlePageChange(currentPage-1)}>Previous</button>
                <span>Page {currentPage+1} of {totalPages}</span>
                <button className="btn btn-outline-primary"  disabled={currentPage===totalPages-1} onClick={()=>handlePageChange(currentPage+1)}>Next</button>
            </div></>):<h2 class="text-center mt-5" style={{color: 'grey'}}>No expenses made</h2>}</>)}   
                </div>
            <footer className="d-flex justify-content-end position-absolute top-100 end-0 w-100 mt-4" style={{"height" : "60px" , "background-color" : "#E3DFDA"}}>
            <div className="d-flex align-items-center justify-content-center pe-4">
                <p className="text-dark text-center pt-2">Copyright © 2023 ADP, Inc.</p>
            </div>
            </footer>
        </>
    )
}
export default EmployeeComponent;