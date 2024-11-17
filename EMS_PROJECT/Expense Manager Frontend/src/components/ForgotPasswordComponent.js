import { useState } from "react"
import AdpLogo from '../assets/AdpLogo.PNG'

function ForgotPasswordComponent(){
    const [userName,setUserName]=useState('')
    const [newPassword,setNewPassword]=useState('')
    const [conformPassword,setConformPassword]=useState('')
    const [uerror,setUerror]=useState('')
    const [nperror,setNperror]=useState('')
    const [cperror,setCperror]=useState('')
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
    const confrimPassword=()=>{
        if(conformPassword.length===0){
            setCperror("Password feild must not be empty");
        }
        else if(/[A-Z]/.test(conformPassword)&& /[0-9]/.test(conformPassword) && /^[$@][A-Za-z0-9]{8,14}$/.test(conformPassword)===true && newPassword===conformPassword){
            setCperror('')
        }
        else if(/[A-Z]/.test(conformPassword)===false){
            setCperror("Password Must contain capitals");
        }
        else if(/[0-9]/.test(conformPassword)===false){
            setCperror("Password must contain atleast on digit")
        }
        else if(/^[$@][A-Za-z0-9]{8,14}$/.test(conformPassword)===false){
            setCperror("Password must be atlease of 8 charachers or atmost of 14 characters")
        }
        if(newPassword!==conformPassword){
            setCperror("Both passwords does'nt match")
        }
    }
    const updatePassword=()=>{
        console.log("Dummy")
    }
    return(
        <>
        <div>
            <header>
                <div class="mr-3">
                    <img src={AdpLogo} class="float-start width='10%'"  width={100} height={60} alt="adp logo"/>
                </div>
            </header>
           <br /><br />
           <div className = "container">
                <div className = "row">
                <div className = "d-flex align-items-center h-100 pt-5">
                    <div className = "card col-md-6 offset-md-3 offset-md-3">
                       <h2  className="text-center">Reset Password</h2>
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
                                    <label className = "form-label"> Password:<br/><small><small>[Min length:8 , Must include (A-Z,a-z,0-9,@,$) , Max length:14]</small></small></label>
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
                                <div className = "form-group mb-2">
                                    <label className = "form-label">Conform password:<br/><small><small>[Min length:8 , Must include (A-Z,a-z,0-9,@,$) , Max length:14]</small></small></label>
                                    <input
                                        type = "password"
                                        placeholder = "Enter password"
                                        name = "password"
                                        className = "form-control"
                                        value = {conformPassword}
                                        onChange = {(e) => setConformPassword(e.target.value)}
                                        onBlur={confrimPassword}
                                    >
                                    </input>
                                    <div role="alert" style={{color: 'red'}}>
                                        {cperror}
                                    </div>
                                </div>
                                <div class="row justify-content-center">
                                    <button type="submit" class="btn btn-primary" onClick={(e)=>updatePassword(e)}>Reset Password</button>
                                </div>
                            </form>
                            <br/>
                            <p className="text-center"><small>EMS © 2023 ADP.inc</small></p>
                        </div>
                    </div>
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
export default ForgotPasswordComponent