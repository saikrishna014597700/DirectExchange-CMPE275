import React, { Component } from 'react';
import axios from 'axios';
import { Redirect } from 'react-router';
import { Link } from 'react-router-dom';
// import bcrypt from 'bcryptjs';
// import HouseIcon from '@material-ui/icons/House';
// import landingpage from '../Landingpage';
import { backendServer } from "../../webconfig.js";
import "../Login/login.css";

export default class SignIn extends Component {
  constructor(props) {
    super(props);
    this.state = {
      invalidCredentials: false,
      email: "",
      password: "",
      invalidEmail: false
    }
    this.authenticateUser = this.authenticateUser.bind(this);
    //this.emailChangeHandler = this.emailChangeHandler.bind(this);
    //this.passwordChangeHandler = this.passwordChangeHandler.bind(this);
    //this.validateCredentials = this.validateCredentials.bind(this);
    this.handleChange = this.handleChange.bind(this);
  }

  handleChange = (e) => {
    console.log("e", e.target.name);
    console.log("e", e.target.value);
    if (this.state.showLoginError) {
      this.setState({
        showLoginError: false,
      });
    }
    this.setState({
      [e.target.name]: e.target.value,
    });
  };

  authenticateUser = (event) => {
    event.preventDefault();
    console.log(this.state.email);
    let url =`${backendServer}/user/login?userName=${this.state.email}&password=${this.state.password}`
    console.log(url)
    axios.defaults.withCredentials = true;
    axios.post(url)
      .then(response => {
        console.log(response);

       if(response.data==="success"){
        localStorage.setItem("userName",this.state.email);
        //localStorage.setItem("nickName",user.displayName);
        //localStorage.setItem("isVerified",user.email);
        this.setState({
              redirect:`/home`
         });
       }else if(response.data === "validate your token"){
          alert("Please validate your token through your email");
          this.setState({
            redirect:"/signin"
          })

       }else if(response.data === "Not a valid user"){
           alert("Invalid user name or password");
           this.setState({
            redirect:"/signin"
           })
       }
      })
      .catch((error) => {
        console.log(error)
        this.setState({
          invalidCredentials: true
        })
      });;
  }
  render() {
    let redirectVar = null;
    if (this.state.redirect) {
      redirectVar = <Redirect push to={this.state.redirect} />;
    }

      let rejct = null;
  
  
      return (
        <div>
          {redirectVar}
          
          <div>
            <a href="/login">{redirectVar}</a>
          </div>
            
  
          <div>
            <div>
              <form id="signIn">
              <div className="login-wrap">
          <div className="login-html">
            <label htmlFor="tab-1" className="tab">Sign In</label>
            
            <div className="login-form">
              <div className="sign-in-htm">
                <div className="group">
                  <label htmlFor="user" className="label">Username or Email</label>
                  <input id="user" type="text" className="input" onChange={this.handleChange}  name="email"/>
                </div>
                <div className="group">
                  <label htmlFor="pass" className="label">Password</label>
                  <input id="pass" type="password" className="input" data-type="password" onChange={this.handleChange} requied name="password"/>
                </div>
                <div className="group" style = {{paddingTop:"50px"}}>
                  <input type="submit" className="button" defaultValue="Sign In" onClick={this.authenticateUser} />
                </div>
                <div className="hr" />
              </div>
            </div>
          </div>
        </div>
              </form>
            </div>
          </div>
        </div>
      );
    }
  

}

// export default SignIn;