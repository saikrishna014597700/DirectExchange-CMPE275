import React, { Component } from "react";
import axios from "axios";
import { Redirect } from "react-router";
import { backendServer } from "../../webconfig.js";
import "./addBank.css";
import { Card, Button, ButtonGroup, Form } from "react-bootstrap";

class AddBank extends Component {
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
        var data = {
            accountNumber: this.state.accountNumber,
            bankName: this.state.bankName,
            userName: localStorage.getItem("userName"),
            country: this.state.country,
            ownerName: this.state.ownerName,
            ownerAddress: this.state.ownerAddress,
            primaryCurrency: this.state.primaryCurrency,
            sendOrReceive: this.state.sendOrReceive,

        };
        console.log("handleSubmit:::", data);

        axios
            .post(`${backendServer}/bankAccount/register`, data)
            .then(response => {
                console.log("Pro are::", response.data);
                if (response.data === "Successfully Registered") {
                    alert("Bank Account Added");
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
                <div className="lease-application" style={{ marginTop: "40px" }}>
                    <div className="container lease-app-form">
                        <div className="card applications-end">
                            <div className="card-head">
                                <h2 className="page-title">Add Bank Account Form</h2>
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
                                                    name="accountNumber"
                                                    class="form-control"
                                                    id="exampleInputEmail1"
                                                    aria-describedby="emailHelp"
                                                    placeholder="Enter Account Number"
                                                    onChange={this.handleChange}
                                                />
                                                {/* <small id="emailHelp" class="form-text text-muted">
                    We'll never share your email with anyone else.
                  </small> */}
                                            </div>
                                        </div>
                                        <div class="col">
                                            <div class="form-group">
                                                {/* <label for="exampleInputPassword1">Last Name:</label> */}
                                                <input
                                                    type="text"
                                                    name="bankName"
                                                    class="form-control"
                                                    id="exampleInputPassword1"
                                                    placeholder="Enter Bank name"
                                                    onChange={this.handleChange}
                                                />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        {/* <label for="exampleInputEmail1">Credit Score:</label> */}
                                        <input
                                            type="text"
                                            name="country"
                                            class="form-control"
                                            id="exampleInputEmail1"
                                            aria-describedby="emailHelp"
                                            placeholder="Enter Bank country"
                                            onChange={this.handleChange}
                                        />
                                    </div>
                                    <div class="form-group">
                                        {/* <label for="exampleInputEmail1">Employer Name:</label> */}
                                        <input
                                            type="text"
                                            name="ownerName"
                                            class="form-control"
                                            id="exampleInputEmail1"
                                            aria-describedby="emailHelp"
                                            placeholder="Enter Owner name"
                                            onChange={this.handleChange}
                                        />
                                    </div>
                                    <div class="form-group">
                                        {/* <label for="exampleInputEmail1">Yearly Income:</label> */}
                                        <input
                                            type="text"
                                            name="ownerAddress"
                                            class="form-control"
                                            id="exampleInputEmail1"
                                            aria-describedby="emailHelp"
                                            placeholder="Enter Owner Address"
                                            onChange={this.handleChange}
                                        />
                                    </div>
                                    <div class="form-group">
                                        {/* <label for="exampleInputEmail1">Yearly Income:</label> */}
                                        <input
                                            type="text"
                                            name="primaryCurrency"
                                            class="form-control"
                                            id="exampleInputEmail1"
                                            aria-describedby="emailHelp"
                                            placeholder="Enter Primary Currency"
                                            onChange={this.handleChange}
                                        />
                                    </div>
                                    <div class="form-group">
                                        {/* <label for="exampleInputEmail1">Yearly Income:</label> */}
                                        <input
                                            type="text"
                                            name="sendOrReceive"
                                            class="form-control"
                                            id="exampleInputEmail1"
                                            aria-describedby="emailHelp"
                                            placeholder="Enter Send or Receive"
                                            onChange={this.handleChange}
                                        />
                                    </div>
                                    <br></br>
                                    <button
                                        type="submit"
                                        class="btn btn-primary"
                                        style={{ marginLeft: "40%", marginRight: "40%", width: "20%" }}
                                        onClick={this.handleSubmit}
                                    >
                                        Submit
                  </button>
                                </form>
                                {/* </div> */}
                            </div>
                        </div>
                    </div>
                </div>
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

export default AddBank;
