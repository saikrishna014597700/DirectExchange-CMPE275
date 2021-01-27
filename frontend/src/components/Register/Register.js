import React, { Component } from 'react';
import axios from 'axios';
import { Redirect } from 'react-router';
import { Link } from 'react-router-dom';
// import bcrypt from 'bcryptjs';
// import HouseIcon from '@material-ui/icons/House';
// import landingpage from '../Landingpage';
import firebase from "firebase"
import StyledFirebaseAuth from "react-firebaseui/StyledFirebaseAuth"
import { backendServer } from "../../webconfig.js";
import "../Login/login.css";
import { ThemeConsumer } from 'react-bootstrap/esm/ThemeProvider';

firebase.initializeApp({
    apiKey: "AIzaSyBroa1CUnkX2486BraKX9av9NPIwrd2MXw",
    authDomain: "direct-exchange-834da.firebaseapp.com"
  })
  

export default class Register extends Component {
  constructor(props) {
    super(props);
    this.state = {
      invalidCredentials: false,
      email: "",
      password: "",
      invalidEmail: false
    }

    this.uiConfig = {
        signInFlow: "popup",
        signInOptions: [
          firebase.auth.GoogleAuthProvider.PROVIDER_ID,
          firebase.auth.FacebookAuthProvider.PROVIDER_ID
        ],
        callbacks: {
          signInSuccess: (user) => {
              console.log("jaffa",user);
              
          }
        }
      }
      this.signUp = this.signUp.bind(this);
  }

  componentDidMount = () => {
    firebase.auth().onAuthStateChanged(user => {
      
      console.log("user", user)
      if(user){
      var data = {
          userName:user.email,
          type:"OAuth",
          nickName:user.displayName
      }
      axios
      .post(`${backendServer}/user/register`,data)
      .then((response) => {
        console.log("Pro are::", response.data);

        if(response.data === "User Already Exists"){
            localStorage.setItem("userName",user.email);
            localStorage.setItem("nickName",user.displayName);
            //localStorage.setItem("isVerified",user.email);
            this.setState({
                  redirect:`/home`,
                  isSignedIn: !!user
             });
        }else if(response.data === "success"){
            alert("Please check you email and Authorize yourself")
            this.setState({
            })
        }
        
      });
    }
    })
  }

  signUp = (event) => {
    event.preventDefault();
    console.log(this.state.email);
    let url =`${backendServer}/user/register`
    var data = {
        userName: this.state.email,
        password:this.state.password,
        nickName:this.state.nickName
    }
    console.log(data)
    axios.defaults.withCredentials = true;
    axios.post(`${backendServer}/user/register`,data)
      .then(response => {
        console.log(response);

       if(response.data==="success"){
        alert("Please check you email and Authorize yourself")
            this.setState({
                redirect:"/signin"
            })
       }else if(response.data === "User Already Exists"){
          alert("Already exists");
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

  render() {
    let redirectVar = null;
    if (this.state.redirect) {
        redirectVar = <Redirect push to={this.state.redirect} />;
      }
    return (
      <div className="App">
          {redirectVar}

          <div>
            <div>
              <form id="signIn">
              <div className="login-wrap">
          <div className="login-html">
            <label htmlFor="tab-1" className="tab">Register</label>
            
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
                
                <div className="group">
                  <label htmlFor="nickName" className="label">Nickname</label>
                  <input id="nickName" type="text" className="input" onChange={this.handleChange}  name="nickName" style = {{backgroundColor:"rgb(232, 240, 254) !important"}}/>
                </div>
                <div className="group" style = {{marginBottom:"50px"}}>
                  <input type="submit" className="button" defaultValue="Sign Up" onClick={this.signUp} />
                </div>
                <div className="hr" />
              </div>
            </div>
          </div>
        </div>
              </form>
            </div>
          </div>


        {this.state.isSignedIn ? (
          <span>
            <div>Signed In!</div>
            <button onClick={() => firebase.auth().signOut()}>Sign out!</button>
            <h1>Welcome {firebase.auth().currentUser.displayName}</h1>
            <img
              alt="profile picture"
              src={firebase.auth().currentUser.photoURL}
            />
          </span>
        ) : (
          <StyledFirebaseAuth 
            uiConfig={this.uiConfig}
            firebaseAuth={firebase.auth()}
          />
        )}
      </div>
    )
  }

}

//== export default SignIn;