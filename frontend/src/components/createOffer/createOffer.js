import React, { Component } from "react";
import axios from "axios";
import { Redirect } from "react-router";
import { backendServer } from "../../webconfig.js";
import "../AddBank/addBank.css";
import { Card, Button, ButtonGroup, Form } from "react-bootstrap";

class CreateOffer extends Component {
    constructor(props) {
        super(props);
        this.state = {

            flag1: false,
            //listingId: this.props.match.params.id,
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }
    handleChange(e) {
        this.setState({
            [e.target.name]: e.target.value,
        });
    }

    handleSubmit(e) {
        e.preventDefault();
        console.log("Submit bank:", this.state.accountNumber);
        var allowCounters = false;
        var allowSplit = false;
        if (this.state.allowSplit === "Yes") {
            allowSplit = true;
        }
        if (this.state.allowCounters === "Yes") {
            allowCounters = true;
        }
        var data = {
            amount: this.state.amount,
            sourceCountry: this.state.sourceCountry,
            userName: localStorage.getItem("userName"),
            destinationCountry: this.state.destinationCountry,
            sourceCurrency: this.state.sourceCurrency,
            destinationCurrency: this.state.destinationCurrency,
            exchangeRate: this.state.exchangeRate,
            expiryDate: this.state.expiryDate,
            allowCounters: allowCounters,
            allowSplit: allowSplit

        };
        console.log("handleSubmit:::", data);

        axios
            .post(`${backendServer}/offer/create`, data)
            .then(response => {
                console.log("Pro are::", response.data);
                if (response.data === "Successfully created offer") {
                    alert("Offer Posted");
                    this.setState({
                        redirect: "/createOffer"
                    })
                } else if (response.data === "Two Accounts needed") {
                    alert("You need one more bank account to post offer")

                    this.setState({
                        redirect: "/addBank"
                    })
                }
            });
    }


    render() {

        let redirectVar = null;
        if (this.state.redirect) {
            redirectVar = <Redirect push to={this.state.redirect} />;
        }
        let candr =
            (
                <div className="lease-application" style={{ marginTop: "7%" }}>
                    <div className="container lease-app-form">
                        <div className="card applications-end">
                            <div className="card-head">
                                <h2 className="page-title">Create an Offer</h2>
                            </div>
                            <div className="card-body">
                                <div className="row">

                                </div>
                                {/* <div className="row"> */}
                                <form>
                                    <div class="form-row">
                                        <div class="col">
                                            <div class="form-group ">
                                                {/* <label for="exampleInputEmail1">First Name:</label> */}
                                                <input
                                                    type="text"
                                                    name="destinationCountry"
                                                    class="form-control"
                                                    id="exampleInputEmail1"
                                                    aria-describedby="emailHelp"
                                                    placeholder="Enter destination Country"
                                                    onChange={this.handleChange}
                                                />
                                                {/* <small id="emailHelp" class="form-text text-muted">
                    We'll never share your email with anyone else.
                  </small> */}
                                            </div>
                                        </div>
                                        <div class="col">
                                            <div class="form-group ">
                                                {/* <label for="exampleInputEmail1">First Name:</label> */}
                                                <input
                                                    type="text"
                                                    name="destinationCurrency"
                                                    class="form-control"
                                                    id="exampleInputEmail1"
                                                    aria-describedby="emailHelp"
                                                    placeholder="Enter destination currency"
                                                    onChange={this.handleChange}
                                                />
                                                {/* <small id="emailHelp" class="form-text text-muted">
                    We'll never share your email with anyone else.
                  </small> */}
                                            </div>

                                        </div>
                                    </div>
                                    <div class="form-group">
                                        {/* <label for="exampleInputEmail1">Credit Score:</label> */}
                                        <input
                                            type="text"
                                            name="amount"
                                            class="form-control"
                                            id="exampleInputEmail1"
                                            aria-describedby="emailHelp"
                                            placeholder="Enter Amount"
                                            onChange={this.handleChange}
                                        />
                                    </div>
                                    <div class="form-row">
                                        <div class="col">
                                            <div class="form-group">
                                                {/* <label for="exampleInputPassword1">Last Name:</label> */}
                                                <input
                                                    type="text"
                                                    name="sourceCountry"
                                                    class="form-control"
                                                    id="exampleInputPassword1"
                                                    placeholder="Enter Source country"
                                                    onChange={this.handleChange}
                                                />
                                            </div>
                                        </div>
                                        <div class="col">
                                            <div class="form-group">
                                                {/* <label for="exampleInputPassword1">Last Name:</label> */}
                                                <input
                                                    type="text"
                                                    name="sourceCurrency"
                                                    class="form-control"
                                                    id="exampleInputPassword1"
                                                    placeholder="Enter Source currency"
                                                    onChange={this.handleChange}
                                                />
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        {/* <label for="exampleInputEmail1">Employer Name:</label> */}
                                        <input
                                            type="text"
                                            name="exchangeRate"
                                            class="form-control"
                                            id="exampleInputEmail1"
                                            aria-describedby="emailHelp"
                                            placeholder="Enter Exchange rate or 'prevailing' static string"
                                            onChange={this.handleChange}
                                        />
                                    </div>
                                    <div class="form-row">
                                        <div class="col">
                                            <div class="form-group">
                                                {/* <label for="exampleInputEmail1">Yearly Income:</label> */}
                                                <select style={{ width: "400px", height: "30px" }} className="input" name="allowCounters" onChange={this.handleChange} >
                                                    <option style={{ color: "black" }} value="" >Allow Counters</option>
                                                    <option style={{ color: "black" }} value="Yes" >Yes</option>
                                                    <option style={{ color: "black" }} value="No" >No</option>

                                                </select>
                                            </div>
                                        </div>
                                        <div class="col">
                                            <div class="form-group">
                                                {/* <label for="exampleInputEmail1">Yearly Income:</label> */}
                                                <select style={{ width: "400px", height: "30px" }} className="input" name="allowSplit" onChange={this.handleChange} >
                                                    <option style={{ color: "black" }} value=""  > Allow Split </option>
                                                    <option style={{ color: "black" }} value="Yes" >Yes</option>
                                                    <option style={{ color: "black" }} value="No" >No</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        {/* <label for="exampleInputEmail1">Yearly Income:</label> */}
                                        <input
                                            type="text"
                                            name="expiryDate"
                                            class="form-control"
                                            id="exampleInputEmail1"
                                            aria-describedby="emailHelp"
                                            placeholder="Enter Expiry Date"
                                            onChange={this.handleChange}
                                        />
                                    </div>

                                    <br></br>
                                    <br></br>
                                    <button
                                        type="submit"
                                        style={{ marginLeft: "40%", marginRight: "40%", width: "20%" }}
                                        class="btn btn-primary"
                                        onClick={this.handleSubmit}
                                    >
                                        Submit
                  </button>
                                </form>
                                {/* </div> */}
                            </div>
                        </div>
                    </div>
                </div >
            );


        return (
            <div>
                {redirectVar}
                {/* <div class="card-deck"> */}
                {candr}
                {/* </div> */}
            </div >
        );
    }
}

export default CreateOffer;
