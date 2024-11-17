import { useState } from "react"
import { useNavigate } from "react-router-dom"
import { getAssociateById, getSubordinate, getUser } from "../service/EmsService"
import AdpLogo from '../assets/AdpLogo.PNG'
import image_bg from '../assets/image_bg1.png';

function LoginComponent(){
    const [userName,setUserName]=useState('')
    const [newPassword,setNewPassword]=useState('')
    //const [empName,setEmpName]=useState('')
    //const [jobTitle,setJobTitle]=useState('')
    const [uerror,setUerror]=useState('')
    const [nperror,setNperror]=useState('')
    const [error,setError]=useState('')
    const [sub]=useState([])
    //const [isManager,setIsManager]=useState(false)
    const navigate=useNavigate()
    const validateUserName=()=>{
        if(/^[0-9 A-Z]{16}$/.test(userName)){
            setUerror('')
        }
        else if(userName.length===0){
            setUerror("Username must not be empty")
        }
        else if(!(/^[0-9 A-Z]{16}$/.test(userName))){
            setUerror("Invalid username")
        }
    }
    const validatePassword=()=>{
        if(newPassword.length===0){
            setNperror("Password feild must not be empty");
        }
        else if(/[A-Z]/.test(newPassword)&& /[0-9]/.test(newPassword) && /^[$@][A-Za-z0-9]{7,14}$/.test(newPassword)===true){
            setNperror('')
        }
        else if(/[A-Z]/.test(newPassword)===false){
            setNperror("Password Must contain capitals");
        }
        else if(/[0-9]/.test(newPassword)===false){
            setNperror("Password must contain atleast on digit")
        }
        else if(/^[$@][A-Za-z0-9]{7,14}$/.test(newPassword)===false){
            setNperror("Password must be atlease of 8 charachers or atmost of 14 characters")
        }
    }
    const login=(e)=>{
        e.preventDefault()
        getUser(userName,newPassword).then(
            (response)=>{
                console.log(response.data)
                getAssociateById(userName).then(
                    (response_associate)=>{
                        console.log(response_associate.data)
                        console.log(sub)
                        getSubordinate(userName).then(
                            (response_manager)=>{
                                if(response_manager.data.length===0)
                                    navigate(`/sampleDashboard/${userName}/${response_associate.data.associateName}/${response_associate.data.position}/${false}`)
                                else
                                    navigate(`/managerDashboard/${userName}/${response_associate.data.associateName}/${response_associate.data.position}/${true}`) 
                            }
                        ).catch((error)=>{console.log(error)})
                    }
                ).catch((error)=>{console.log(error)})
            }
        ).catch((error)=>{setError("Invalid user credentials");console.log(error)})
    }
    const forgotPassword=()=>{
        navigate("/forgotPassword")
    }
    const backgroundstyle ={
        backgroundImage: `url(${image_bg})`,
        height:'100vh',
        width:'100%',
        backgroundPosition:'center',
        backgroundRepeat:'repeat',
        backgroundSize: 'cover'
    }
    return(
        <>
        <header>
                <div class="mr-3">
                    <img src={AdpLogo} class="float-start width='10%'"  width={100} height={60} alt="adp logo"/>
                </div>
            </header>
           <div className = "container-fluid" style={backgroundstyle}>
                <div className = "row">
                <div className = "d-flex align-items-center pt-5">
                    <div className = "card col-md-6 offset-md-3 offset-md-3" style={{width:'35%',padding:"20px",margin:"auto",marginTop:"40px"}}>
                       <h2  className="text-center">Welcome to ADP!!</h2>
                       <div role="alert" style={{color: 'red'}} className="text-center">
                                        {error}
                        </div>
                        <div className = "card-body">
                            <form>
                                <div className = "form-group mb-2">
                                    <label className = "form-label"> User Name :</label>
                                    <input
                                        type = "text"
                                        placeholder = "Enter user name"
                                        name = "userName"
                                        className = "form-control"
                                        value = {userName}
                                        onChange = {(e) => setUserName(e.target.value)}
                                        onBlur={validateUserName}
                                    >
                                    </input>
                                    <div role="alert" style={{color: 'red'}}>
                                        {uerror}
                                    </div>
                                </div>


                                <div className = "form-group mb-2">
                                    <label className = "form-label"> Password:</label>
                                    <input
                                        type = "password"
                                        placeholder = "Enter password"
                                        name = "password"
                                        className = "form-control"
                                        value = {newPassword}
                                        onChange = {(e) => setNewPassword(e.target.value)}
                                        onBlur={validatePassword}
                                    >
                                    </input>
                                    <div role="alert" style={{color: 'red'}}>
                                        {nperror}
                                    </div>
                                </div>
                                <div class="row justify-content-center">
                                    <button style={{ width: "100px", height: "50px",}} class=" btn btn-primary" type="submit" onClick={(e)=>login(e)}>Submit</button>
                                </div>
                                <div class="row justify-content-center">
                                    <button type="submit" class="btn btn-link" onClick={()=>forgotPassword()}>Forgot Password?</button>
                                </div>
                            </form>
                            <br/>
                            <p className="text-center"><small>EMS © 2023 ADP.inc</small></p>
                        </div>
                    </div>
                </div>
            </div>
           </div>
        </>
    )
}
export default LoginComponent;