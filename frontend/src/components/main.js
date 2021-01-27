import React, { Component, Fragment } from "react";
import { Route } from "react-router-dom";
import SignIn from "./Login/login.js"
import Register from "./Register/Register"
import Navbar from "../components/navbar";
import MyOffers from "./MyOffers/myOffers.js"
import MatchingOffers from "./MatchingOffers/MatchingOffers"
import AddBank from "./AddBank/addBank"
import PrevailingRates from "./PrevailingRates/prevailingRates"
import CreateOffer from "./createOffer/createOffer"
import Home from "./Home/home"
import MyTransactions from "./MyTransactions/myTransactions"
import MyCounters from "./MyCounters/myCounters"
import Report from "./Reports/Reports"
import UserDetails from "./OfferPostedUserDetails/UserDetails"

class Main extends Component {
  render() {

    // let footer = <Footer />

    return (
      <div>

        <Fragment>
          <Route path="/" component={Navbar} />
          {/* <Route exact path="/search" component={Search} /> */}
          <Route path="/signin" component={SignIn} />
          <Route path="/register" component={Register} />
          <Route path="/myOffers" component={MyOffers} />
          <Route path="/matchingOffers/:id" component={MatchingOffers} />
          <Route path="/addBank" component={AddBank} />
          <Route path="/rates" component={PrevailingRates} />
          <Route path="/createOffer" component={CreateOffer} />
          <Route path="/home" component={Home} />
          <Route path="/myTransactions" component={MyTransactions} />
          <Route path="/mycounters" component={MyCounters} />
          <Route path="/transactionReport" component={Report} />
          <Route path="/userDetails/:id" component={UserDetails} />
        </Fragment>
      </div>
    );
  }
}
//Export The Main Componenta
export default Main;