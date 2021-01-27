import React, { Component } from "react";
import { Link } from "react-router-dom";
// import cookie from 'react-cookies';
import { Redirect } from "react-router";
import axios from "axios";
import { backendServer } from "../webconfig";
// import "./navbar.css";
import "../assets/DirectExchange.png";
import firebase from "firebase"
import "../../src/assets/DirectExchange.png";
//create the Navbar Component
class Navbar extends Component {
  constructor(props) {
    super(props);
    this.state = {
      categories: [],
      selectedCategory: "All",
      addresses: [],
      cards: [],
      cart: [],
      name: null,
      searchValue: "",
      redirect: false,
      logoutRedirect: false,
    };
    this.handleLogout = this.handleLogout.bind(this);
  }
  //handle logout to destroy the cookie
  handleLogout = () => {
    localStorage.removeItem("userName");
    localStorage.clear();
    firebase.auth().signOut()
    this.setState({
      logoutRedirect: true,
    });
    window.location.href = "/";
  };

  componeneDidUpdate() {
    if (this.stat.redirect) {
      this.setState({
        redirect: false,
      });
    }
  }
  categoriesChangeHandler = (e) => {
    this.setState({
      selectedCategory: e.target.value,
    });
  };

  searchChangeHandler = (e) => {
    this.setState({
      searchValue: e.target.value,
      redirect: false,
    });
  };

  submitSearch = () => {
    if (this.state.searchValue) {
      this.setState({
        redirect: true,
      });
    }
  };

  render() {
    var navLinks = null;
    let navLinkBottom = null;

    let logout = null;

    if (localStorage.getItem("userName")) {
      navLinks = (

        <ul className="navbar-nav mr-auto "  >

          <li class="nav-item">
            <Link style={{ color: "white" }} class="nav-link" to="/home">
              Home
            </Link>
          </li>
          <li class="nav-item">
            <Link style={{ color: "white" }} class="nav-link" to="/myOffers">
              My Offers
            </Link>
          </li>

          <li class="nav-item">
            <Link style={{ color: "white" }} class="nav-link" to="/addBank">
              Add Bank Account
            </Link>
          </li>

          <li class="nav-item">
            <Link style={{ color: "white" }} class="nav-link" to="/rates">
              Check Rates
            </Link>
          </li>

          <li class="nav-item">
            <Link style={{ color: "white" }} class="nav-link" to="/createOffer">
              Post an Offer
            </Link>
          </li>
          <li class="nav-item">
            <Link style={{ color: "white" }} class="nav-link" to="/myTransactions">
              My Pending Transactions
            </Link>
          </li>
          <li class="nav-item">
            <Link style={{ color: "white" }} class="nav-link" to="/myCounters">
              My Counter Offers
            </Link>

          </li>
          <li class="nav-item">
            <Link style={{ color: "white" }} class="nav-link" to="/transactionReport">
              Transaction Report
            </Link>
          </li>
        </ul>



      );
      logout = (<Link style={{ color: "white" }} class="nav-link" to="/" onClick={this.handleLogout}>
        Logout
      </Link>);
    } else {
      navLinks = (
        <ul className="navbar-nav mr-auto">
          <li class="nav-item">
            <Link style={{ color: "white" }} class="nav-link" to="/signin">
              Sign In
            </Link>
          </li>
          {/* <li><Link to="/carthome"><span><i className="icon-shopping-cart icon-2x"></i></span><span className="badge badge-light">{this.state.cart.length}</span></Link></li> */}
          <li class="nav-item">
            <Link style={{ color: "white" }} class="nav-link" to="/register">
              <span>
                <i style={{ color: "white" }} className="icon-shopping-cart icon-2x">Register</i>
              </span>
            </Link>
          </li>
        </ul>
      );
    }



    let redirectVar = null;

    let logoutRedirect = null;
    if (this.state.logoutRedirect) {
      logoutRedirect = <Redirect to="/" />;
    }

    return (
      <div style={{ color: "black" }}>
        <nav class="navbar navbar-expand-lg navbar-light bg-primary">
          {/* <Link class="navbar-brand" to="/search">
            Home Finder
          </Link> */}


          {/* <a class="navbar-brand " href="#"> */}
          <img src="https://imagescdn.dealercarsearch.com/dealerimages/7415/22401/logo.png" width="100"></img>
          {/* </a> */}
          {navLinks}
          {/* <li class="nav-item"> */}
          {logout}
        </nav>
        <nav class="navbar navbar-inverse">
          <div class="container-fluid">
            {/* <div class="navbar-header">
              <img 	style={{image:("../../assets/house2.jpg")}}/>
            </div> */}
            {/* <form class="navbar-form navbar-left" action="/action_page.php">
            <div class="form-group">
                <input type="text" class="form-control" placeholder="Search"/>
            </div>
            <button type="submit" class="btn btn-default">Submit</button>
            </form> */}
          </div>
        </nav>
      </div>
    );
  }
}
export default Navbar;
