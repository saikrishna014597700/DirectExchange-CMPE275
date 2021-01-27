import React, { Component } from "react";
import Card from "react-bootstrap/Card";

import axios from 'axios';
import { Redirect } from 'react-router';

import { backendServer } from "../../webconfig.js";

export default class Report extends Component {
  constructor(props) {
    super(props);
    this.state = {
      month: "", 
      userTransactions :[{
     sourceAmount:0,
    sourceCurrency:0,
       destinationAmount:0,
        destinationCurrency:0,
        exchange_rate:0,
       serviceFee:0,
        transactionDate:0
      }],
      systemTransactions:{}
    };
  }

  monthChangeHandler = (event) => {
    this.setState({ month: event.target.value });
  };

  submitHandler = (event) => {
    //alert("You have selected the month: " + this.state.month);
    event.preventDefault();
      axios.get(`${backendServer}/offer/getUserTransactions/${localStorage.getItem("userName")}/${this.state.month}/2020`)
        .then(response => {
                let payload_arr = response.data

                axios.get(`${backendServer}/offer/systemFinancials/${this.state.month}/2020`)
                .then(res => {
                this.setState({
                        userTransactions : payload_arr,
                        systemTransactions:res.data
                    })
                
            })
        })
  };

  componentWillMount(){
   
    // this.setState({
    //     userTransactions:[{
    //         amount:"30",
    //         source_currency:"USD",
    //         destination_amount:"2100",
    //         destination_currency:"70",
    //         service_fee:"0.05 USD",
    //         creation_date:"10-01-2020"
    //     }]
    // })
}


  render() {
    //console.log(this.state)
    let redirectVar;
    
    return (
      <form onSubmit={this.submitHandler}>
        <div className="dropdown">
          <label>Select month:</label>
            <select value={this.state.month} onChange={this.monthChangeHandler}>
            <option value="1">January</option>
            <option value="2">February</option>
            <option value="3">March</option>
            <option value="4">April</option>
            <option value="5">May</option>
            <option value="6">June</option>
            <option value="7">July</option>
            <option value="8">August</option>
            <option value="9">September</option>
            <option value="10">October</option>
            <option value="11">November</option>
            <option value="12">December</option>
          </select>
          <input type="Submit" value="Go" />

          <div class="txncard">
            <h3>User Transaction Report</h3>
            <Card style={{ backgroundColor: "white" }}>
              <Card.Body>
                <Card.Text>
                    <table className="table table-striped">
                      <thead>
                        <tr>
                          <td>Remitted Amount</td>
                          <td>Source Currency</td>
                          <td>Destination Amount</td>
                          <td>Destination Currency</td>
                          <td>Exchange Rate</td>
                          <td>Service Fee</td>
                          <td>Transaction Date</td>
                        </tr>
                      </thead>
                      <tbody>
                        {
                           this.state.userTransactions.map(
                             rec =>
                             <tr>
                               <td>{rec.sourceAmount}</td>
                               <td>{rec.sourceCurrency}</td>
                               <td>{rec.destinationAmount}</td>
                               <td>{rec.destinationCurrency}</td>
                               <td>{rec.exchange_rate}</td>
                               <td>{rec.serviceFee}</td>
                               <td>{rec.transactionDate}</td>
                             </tr>
                           )
                        }
                      </tbody>
                    </table>
                </Card.Text>
              </Card.Body>
            </Card>
          </div>
        </div>

        <div class="sysreportcard">
          <h3>System Report</h3>
          <Card style={{ backgroundColor: "white" }}>
            <Card.Body>
              <Card.Text>
              <table className="table table-striped">
                      <thead>
                        <tr>
                          <td>Completed Transactions</td>
                          <td>Incomplete Transactions</td>
                          <td>Total Remitted USD</td>
                          <td>Total Service Fee</td>
                          
                        </tr>
                      </thead>
                      <tbody>
                        {
                             <tr>
                               <td>{this.state.systemTransactions.completedTransactions}</td>
                               <td>{this.state.systemTransactions.incompleteTransactions}</td>
                               <td>{this.state.systemTransactions.totalRemittedUSD}</td>
                               <td>{this.state.systemTransactions.totalServiceFee}</td>
                              
                             </tr>
                           
                        }
                      </tbody>
                    </table>
              </Card.Text>
            </Card.Body>
          </Card>
        </div>
      </form>
      
    );
    }
}

 