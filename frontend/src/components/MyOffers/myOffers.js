import React, { Component } from 'react';
import axios from "axios";
import { Redirect } from "react-router";
import { backendServer } from "../../webconfig";
import { Card, Button, ButtonGroup, Row, Col } from 'react-bootstrap';

class MyOffers extends Component {
  constructor(props) {
    super(props);
    this.state = {
      offers: [],
      flag1: false,
      isEdit: "True",
      amount: ""
    };
  }

  loadMatchingOffers(id) {
    this.setState({
      redirect: `/matchingOffers/${id}`
    })
  }

  handleChange(e) {
    this.setState({
      [e.target.name]: e.target.value,
    });
  }

  editOffers(id) {
    this.setState({
      isEdit: "False"
    });
  }

  saveOffers(id) {
    this.setState({
      isEdit: "True"
    });
    this.state.offers.map(offer => {
      if (offer.offerId === id) {
        offer["amount"] = this.state.amount;
      }
      axios
        .post(`${backendServer}/offer/create`, offer)
        .then(response => {
          if (response.data === "Successfully created offer") {
            alert("Saved Successfully");
          } else {
            alert("Error")
          }
        });
    });
  }

  async componentDidMount() {
    //   const data = {
    //     email: localStorage.getItem("email")
    //   };
    const data = {

    };
    var email = localStorage.getItem("userName");//"kailashnath0315@gmail.com"    
    axios
      .get(`${backendServer}/offer/userOffers/${email}`)
      .then((response) => {
        console.log("Pro are::", response.data);
        this.setState({
          offers: response.data,
        });
        console.log("Pro are::", this.state.offers);
      });
  }

  render() {
    let redirectVar = null;
    if (this.state.redirect) {
      redirectVar = <Redirect push to={this.state.redirect} />;
    }



    let candr = this.state.offers.map((msg) => {
      let editButton = null;
      if (this.state.isEdit === "True") {
        editButton = <Button style={{ marginLeft: "10px" }} onClick={(e) => this.editOffers(msg.offerId)}>Edit Offer</Button>;
      } else if (this.state.isEdit === "False") {
        editButton = <Button style={{ marginLeft: "10px" }} onClick={(e) => this.saveOffers(msg.offerId)}>Save </Button>;
      }
      let editAmount = null;
      if (this.state.isEdit === "True") {
        editAmount = <Col>
          <p style={{ color: "black" }}>Amount: {msg.amount}</p>
        </Col>
      } else if (this.state.isEdit === "False") {
        editAmount = <input
          type="text"
          name="amount"
          id="amount"
          style={{ marginLeft: "10px", marginBottom: "20px", width: "200px" }}
          placeholder="Enter Amount"
          onChange={(e) => this.handleChange(e)}
        />
      }
      return (
        <div>
          &nbsp;
                <Card style={{ display: "flex", marginLeft: "20%", marginRight: "20%", marginTop: "15px" }}>
            <Card.Header style={{ color: "white", backgroundColor: "black" }}>Expires on {msg.expiryDate}</Card.Header>
            <Card.Body>
              <Card.Text>
                <Row>
                  {editAmount}
                </Row>
                <Row>
                  <Col>
                    <p style={{ color: "black" }}>Source Country : {msg.sourceCountry}</p>

                    <p style={{ color: "black" }}>Source Currency : {msg.sourceCurrency}</p>
                    <p style={{ color: "black" }}>Exchange Rate : {msg.exchangeRate}</p>
                  </Col>
                  <Col>

                    <p style={{ color: "black" }}>Destination Country : {msg.destinationCountry}</p>

                    <p style={{ color: "black" }}>Destination Currency : {msg.destinationCurrency}</p>

                    <p style={{ color: "black" }}>Status : {msg.status}</p>
                  </Col>
                </Row>

              </Card.Text>
              <ButtonGroup style={{ marginLeft: "450px", marginTop: "20px" }} className="mb-2">
                {/* <Button>Approve</Button>
        &nbsp; */}

                &nbsp;
                <Button onClick={(e) => this.loadMatchingOffers(msg.offerId)}>Matching Offers</Button>
                {editButton}

              </ButtonGroup>
            </Card.Body>
          </Card>

          &nbsp;
              </div>
      );
    });
    return (
      <div>
        {/* <div class="card-deck"> */}
        {redirectVar}
        {candr}
        {/* </div> */}


      </div>

    );
  }
}

export default MyOffers;