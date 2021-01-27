import React, { Component } from 'react';
import axios from "axios";
import { Redirect } from "react-router";
import { backendServer } from "../../webconfig";
import { Card, Button, ButtonGroup, Row, Col } from 'react-bootstrap';
import Modal from "react-bootstrap/Modal";

class MatchingOffers extends Component {
  constructor(props) {
    super(props);
    this.state = {
      offers: {
        singleOffers: [],
        showHide: false,
        subject: "",
        message: "",
        recEmail: ""
      },
      splitTransactions: false,
      offerId: this.props.match.params.id,
    };
    this.handleInputChange = this.handleInputChange.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.makeDeal = this.makeDeal.bind(this);
  }

  handleChange(e) {
    this.setState({
      [e.target.name]: e.target.value,
    });
    console.log(this.state.message)
  }

  makeDeal(id) {
    //counterOffer
    console.log("Making deal");
    var offerId = this.state.offerId
    axios.post(`${backendServer}/offer/checkExactMatch?initialOfferId=${this.state.offerId}&acceptedOfferId=${id}`)
      .then(async function (response) {
        console.log("Pro are::", response.data);
        if (response.data === "success") {
          await axios.post(`${backendServer}/offer/acceptOffer?userOfferId=${offerId}&acceptedOfferId=${id}&userName=${localStorage.getItem("userName")}`)
            .then(function (response) {
              alert("Deal made");
              // this.setState({})
            })
        } else if (response.data === "Failed to accept") {
          var txt;
          console.log("Not Matched")
          if (window.confirm("Not an Exact match! Do you wish to proceed?")) {
            txt = "You pressed OK!";
            console.log("You pressed OK!")
            await axios.post(`${backendServer}/offer/changeAndAccept?userOfferId=${offerId}&acceptedOfferId=${id}&userName=${localStorage.getItem("userName")}`)
              .then(function (response) {
                alert("Deal made with adjustments");
                // this.setState({})
              })
          } else {
            console.log("You pressed Cancel!")
            txt = "You pressed Cancel!";
            // this.setState({})s
          }

        }
      });
  }

  handleModalShowHide(offerid) {
    console.log(this.state.showHide)
    this.setState({ showHide: !this.state.showHide })
  }

  handleModalShowHide1(recEmail) {
    this.handleModalShowHide();
    this.setState({ recEmail: recEmail })
  }

  handleModalShowHide2(email1, amt1, email2, amt2) {
    var recEmail = amt1 > amt2 ? email1 : email2;
    this.handleModalShowHide();
    this.setState({ recEmail: recEmail })
  }

  sendMessage() {
    this.handleModalShowHide();
    axios
      .post(`${backendServer}/user/sendMessage?subject=${this.state.subject}&body=${this.state.message}&receiverEmail=${this.state.recEmail}&senderEmail=${localStorage.getItem('userName')}`)
      .then((response) => {
        console.log("Pro are::", response.data);
        alert(`Successfully sent email`)
      });
    this.setState({ recEmail: "" })
    this.setState({ message: "" })
    this.setState({ subject: "" })
  }

  counterOffer(id, amount, userName) {
    console.log("countering the offer");

    var proposedAmount = prompt("Please enter your Proposed Amount", 0);

    var data = {
      offer: this.state.offerId,
      counterOffer: id,
      proposedAmount: amount,
      counterAmount: proposedAmount,
      proposedUserName: localStorage.getItem("userName"),
      counterPartyUserName: userName
    }

    axios.post(`${backendServer}/offer/counterOffer`, data)
      .then(function (response) {
        alert("Counter offer made");
        // this.setState({})
      })
  }


  handleInputChange(e) {

    const value = e.target.value;
    const name = e.target.name;
    console.log("jaffa", e.target.checked)
    this.setState({
      splitTransactions: e.target.checked
    });
  }
  async componentDidMount() {
    var email = "kailashnath0315@gmail.com"
    axios
      .get(`${backendServer}/offer/getMatchingOffer/${this.state.offerId}`)
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
    let split = null;

    if (this.state.splitTransactions) {
      split = this.state.offers.splitOffers.map((msg) => {
        return (
          <div>
            &nbsp;

                <Card style={{ width: "60%", marginLeft: "100px", height: "80%" }}>
              <Card.Header style={{ color: "black" }}>offer 1 Posted by {msg.offer1.userName}</Card.Header>
              <Card.Body>
                <Card.Text>
                  <Col>
                    <Row>
                      <Col>
                        <p style={{ color: "black" }}>Amount: {msg.offer1.amount}</p>
                      </Col>
                    </Row>
                    <Row>
                      <Col>
                        <p style={{ color: "black" }}>Source Country : {msg.offer1.sourceCountry}</p>

                        <p style={{ color: "black" }}>Source Currency : {msg.offer1.sourceCurrency}</p>
                        <p style={{ color: "black" }}>Exchange Rate : {msg.offer1.exchangeRate}</p>
                      </Col>
                      <Col>

                        <p style={{ color: "black" }}>Destination Country : {msg.offer1.destinationCountry}</p>

                        <p style={{ color: "black" }}>Destination Currency : {msg.offer1.destinationCurrency}</p>

                        <p style={{ color: "black" }}>Status : {msg.offer1.status}</p>
                      </Col>
                    </Row>
                  </Col>
                  <p>---------------------------------------------------</p>
                  <p style={{ color: "black" }}>Offer 2 posted by : {msg.offer2.userName}</p>
                  <p>---------------------------------------------------</p>
                  <Col>

                    <Row>

                      <Col>
                        <p style={{ color: "black" }}>Amount: {msg.offer2.amount}</p>
                      </Col>
                    </Row>
                    <Row>
                      <Col>
                        <p style={{ color: "black" }}>Source Country : {msg.offer2.sourceCountry}</p>

                        <p style={{ color: "black" }}>Source Currency : {msg.offer2.sourceCurrency}</p>
                        <p style={{ color: "black" }}>Exchange Rate : {msg.offer2.exchangeRate}</p>
                      </Col>
                      <Col>

                        <p style={{ color: "black" }}>Destination Country : {msg.offer2.destinationCountry}</p>

                        <p style={{ color: "black" }}>Destination Currency : {msg.offer2.destinationCurrency}</p>

                        <p style={{ color: "black" }}>Status : {msg.offer2.status}</p>
                      </Col>

                    </Row>
                  </Col>



                  <ButtonGroup style={{ marginLeft: "450px", marginTop: "20px" }} className="mb-2">

                    &nbsp;
        <Button hidden={(msg.status == "CounterMade")} onClick={(e) => this.makeDeal(msg.offerId)}>Make a deal</Button>

                    <Button hidden={!(msg.status == "CounterMade")} onClick={(e) => this.makeDeal(msg.offerId)}>Accept the counter offer</Button>

                  </ButtonGroup>



                  <ButtonGroup style={{ marginLeft: "450px", marginTop: "20px" }} className="mb-2">

                    &nbsp;
                    <Button onClick={(e) => this.makeDeal(msg.offerId)}>Make a deal</Button>
                    <Button onClick={(e) => this.handleModalShowHide2(msg.offer1.userName, msg.offer1.amount, msg.offer2.userName, msg.offer2.amount)} style={{ marginLeft: "10px" }}>Send Email</Button>
                  </ButtonGroup>
                </Card.Text>
              </Card.Body>

            </Card>
            & nbsp;</div >

        );
      });
    }




    let candr = this.state.offers.singleOffers.map((msg) => {
      return (
        <div>
          &nbsp;
                <Card style={{ width: "60%", marginLeft: "100px", height: "80%" }}>
            <Card.Header style={{ color: "black" }}>Posted by {msg.userName}</Card.Header>
            <Card.Body>
              <Card.Text>

                <Row>
                  <Col>
                    <p style={{ color: "black" }}>Amount: {msg.amount}</p>
                  </Col>
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

        <Button diabled={!msg.allowCounters} onClick={(e) => this.counterOffer(msg.offerId, msg.amount, msg.userName)} style={{ marginRight: "8px" }} >Counter Offer</Button>
                <Button onClick={(e) => this.makeDeal(msg.offerId)}>Accept Deal</Button>
                <Button onClick={(e) => this.handleModalShowHide1(msg.userName)} style={{ marginLeft: "10px" }}>Send Email</Button>

              </ButtonGroup>
            </Card.Body>
          </Card>

          &nbsp;
              </div>
      );
    });
    return (
      <div>
        <p style={{ marginLeft: "100px" }}>Matching offers for your offer ID: {this.state.offerId}</p>
        <label style={{ marginLeft: "100px" }}>
          Split Offers{" "}
          <input
            name="isGoing"
            type="checkbox"
            checked={this.state.splitTransactions}
            onChange={(e) => this.handleInputChange(e)} />
        </label>
        {/* <div class="card-deck"> */}
        {redirectVar}
        {split}
        {candr}
        <Modal show={this.state.showHide}>
          <Modal.Header closeButton onClick={() => this.handleModalShowHide()}>
            <Modal.Title>Send Email</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <label for="fname">Subject:</label>
            <input style={{ width: "100%", height: "40px", borderRadius: "10px" }} onChange={(e) => this.handleChange(e)} type="text" id="subject" name="subject" />
            <br></br>
            <label for="fname">Message:</label>
            <input style={{ width: "100%", height: "80px", borderRadius: "10px" }} onChange={(e) => this.handleChange(e)} type="text" id="message" name="message" />
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={() => this.handleModalShowHide()}>
              Close
                    </Button>
            <Button variant="primary" onClick={(e) => this.sendMessage(e)}>
              Send Message
                    </Button>
          </Modal.Footer>
        </Modal>


      </div>

    );
  }
}

export default MatchingOffers;