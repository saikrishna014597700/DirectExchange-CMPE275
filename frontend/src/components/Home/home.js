import React, { Component } from "react";
import axios from "axios";
import { Redirect } from "react-router";
import { backendServer } from "../../webconfig";
import { Card, Button, ButtonGroup, Row, Col } from "react-bootstrap";
import { Link } from "react-router-dom";

class Home extends Component {
  constructor(props) {
    super(props);
    this.state = {
      offers: [],
      flag1: false,
      pageNum: 1,
      srcCurr: "USD",
      destCurr: "USD",
      minsrcPrize: 0,
      maxsrcPrize: 10000,
      mindestPrize: 0,
      maxdestPrize: 10000,
    };
  }

  handleChange(e) {
    this.setState({
      [e.target.name]: e.target.value,
    });
    console.log(this.state.srcCurr);
  }

  handlePagination(e, page) {
    var pageNumber = this.state.pageNum;
    if (page == 0 && pageNumber > 1) {
      pageNumber = pageNumber - 1;
      this.setState({
        pageNum: pageNumber,
      });
    } else if (page == 1) {
      pageNumber = pageNumber + 1;
      this.setState({
        pageNum: pageNumber,
      });
    }
    axios
      .get(`${backendServer}/offer/getOffers/${pageNumber}`)
      .then((response) => {
        this.setState({
          offers: response.data,
        });
      });
  }

  handleFilter(e) {
    console.log(
      "params are::",
      this.state.pageNum,
      this.state.srcCurr,
      this.state.destCurr,
      this.state.minsrcPrize,
      this.state.maxsrcPrize,
      this.state.mindestPrize,
      this.state.maxdestPrize
    );
    axios
      .post(
        `${backendServer}/offer/getOffers?minSourceAmount=${this.state.minsrcPrize}&maxSourceAmount=${this.state.maxsrcPrize}&sourceCurrency=${this.state.srcCurr}&minDestinationAmount=${this.state.mindestPrize}&maxDestinationAmount=${this.state.maxdestPrize}&destinationCurrency=${this.state.destCurr}&pageNumber=${this.state.pageNum}`
      )
      .then((response) => {
        this.setState({
          offers: response.data,
        });
      });
  }

  async componentDidMount() {
    //   const data = {
    //     email: localStorage.getItem("email")
    //   };
    const data = {};
    var email = localStorage.getItem("userName"); //"kailashnath0315@gmail.com"
    axios.get(`${backendServer}/offer/getOffers`).then((response) => {
      this.setState({
        offers: response.data,
      });
    });
    axios
      .get(`${backendServer}/offer/getOffers/${this.state.pageNum}`)
      .then((response) => {
        this.setState({
          offers: response.data,
        });
      });
  }

  render() {
    let redirectVar = null;
    if (this.state.redirect) {
      redirectVar = <Redirect push to={this.state.redirect} />;
    }
    let candr = this.state.offers.map((msg) => {
      if (msg.userName === localStorage.getItem("userName")) {
        return <div></div>;
      } else {
        return (
          <div>
            &nbsp;
            <Card
              style={{
                width: "80%",
                marginLeft: "100px",
                marginBottom: "20px",
                height: "0%",
                borderBlockColor: "black",
              }}
            >
              <Card.Header style={{ color: "white", backgroundColor: "black" }}>
                Posted by {msg.userName}
              </Card.Header>
              <Card.Body>
                <Card.Text>
                  <Row>
                    <Col>
                      <p style={{ color: "black" }}>Amount: {msg.amount}</p>
                    </Col>
                  </Row>
                  <Row>
                    <Col>
                      <p style={{ color: "black" }}>
                        Source Country : {msg.sourceCountry}
                      </p>

                      <p style={{ color: "black" }}>
                        Source Currency : {msg.sourceCurrency}
                      </p>
                      <p style={{ color: "black" }}>
                        Exchange Rate : {msg.exchangeRate}
                      </p>
                    </Col>
                    <Col>
                      <p style={{ color: "black" }}>
                        Destination Country : {msg.destinationCountry}
                      </p>

                      <p style={{ color: "black" }}>
                        Destination Currency : {msg.destinationCurrency}
                      </p>

                      <p style={{ color: "black" }}>Status : {msg.status}</p>
                    </Col>
                  </Row>

                  <Button style={{ marginRight: "120px", marginTop: "20px" }}>
                    <Link
                      to={{
                        pathname: `/userDetails/${msg.offerId}`,
                      }}
                    >
                      View offer Details
                    </Link>
                  </Button>
                </Card.Text>
              </Card.Body>
            </Card>
          </div>
        );
      }
    });
    return (
      <div>
        {/* <div class="card-deck"> */}
        {redirectVar}
        <div class="row" style={{ marginLeft: "10px" }}>
          <div class="card col-3  " style={{ marginLeft: "20px" }}>
            <article class="card-group-item">
              <header class="card-header">
                <h6
                  style={{
                    color: "blue",
                    fontWeight: "bold",
                    marginTop: "15px",
                  }}
                  class="title"
                >
                  Source Filter
                </h6>
              </header>
              <div class="filter-content">
                <div class="card-body">
                  <div class="form-row">
                    <select
                      onChange={(e) => this.handleChange(e)}
                      name="srcCurr"
                      class="custom-select custom-select-sm"
                    >
                      <option value="USD">USD</option>
                      <option value="INR">INR</option>
                      <option value="EUR">EUR</option>
                      <option value="GBP">GBP</option>
                      <option value="RMB">RMB</option>
                    </select>
                    <br></br>
                    <br></br>
                    <br></br>
                    <div class="form-group col-md-6">
                      <label>Min</label>
                      <input
                        type="number"
                        class="form-control"
                        name="minsrcPrize"
                        onChange={(e) => this.handleChange(e)}
                        placeholder="0"
                      ></input>
                    </div>
                    <div class="form-group col-md-6 text-right">
                      <label>Max</label>
                      <input
                        type="number"
                        class="form-control"
                        name="maxsrcPrize"
                        onChange={(e) => this.handleChange(e)}
                        placeholder="1,0000"
                      ></input>
                    </div>
                  </div>
                </div>
              </div>
            </article>
            <article class="card-group-item">
              <header class="card-header">
                <h6
                  style={{
                    color: "blue",
                    fontWeight: "bold",
                    marginTop: "15px",
                  }}
                  class="title"
                >
                  Destination Filter
                </h6>
              </header>
              <div class="filter-content">
                <div class="card-body">
                  <div class="form-row">
                    <select
                      onChange={(e) => this.handleChange(e)}
                      name="destCurr"
                      class="custom-select custom-select-sm"
                    >
                      <option value="USD">USD</option>
                      <option value="INR">INR</option>
                      <option value="EUR">EUR</option>
                      <option value="GBP">GBP</option>
                      <option value="RMB">RMB</option>
                    </select>
                    <br></br>
                    <br></br>
                    <br></br>
                    <div class="form-group col-md-6">
                      <label>Min</label>
                      <input
                        type="number"
                        class="form-control"
                        name="mindestPrize"
                        onChange={(e) => this.handleChange(e)}
                        placeholder="0"
                      ></input>
                    </div>
                    <div class="form-group col-md-6 text-right">
                      <label>Max</label>
                      <input
                        type="number"
                        class="form-control"
                        name="maxdestPrize"
                        onChange={(e) => this.handleChange(e)}
                        placeholder="1,0000"
                      ></input>
                    </div>
                  </div>
                </div>
              </div>
            </article>
            <article>
              <Button
                variant="primary"
                onClick={(e) => this.handleFilter(e)}
                style={{
                  float: "center",
                  marginLeft: "40%",
                  marginBottom: "20px",
                }}
              >
                Filter Results
              </Button>
            </article>
          </div>
          <div class="card col-8" style={{ marginLeft: "20px" }}>
            {candr}
          </div>
          <ButtonGroup
            class="col-4"
            style={{ marginLeft: "80%", marginTop: "30px" }}
          >
            <Button
              variant="primary"
              onClick={(e) => this.handlePagination(e, 0)}
              style={{
                float: "center",
                marginLeft: "40%",
                marginBottom: "20px",
              }}
            >
              Prev
            </Button>
            <label
              style={{
                alignContent: "center",
                marginTop: "15px",
                marginLeft: "15px",
              }}
            >
              {" "}
              {this.state.pageNum}{" "}
            </label>
            <Button
              variant="primary"
              onClick={(e) => this.handlePagination(e, 1)}
              style={{
                float: "center",
                marginLeft: "10%",
                marginBottom: "20px",
              }}
            >
              Next
            </Button>
          </ButtonGroup>
        </div>
      </div>
    );
  }
}

export default Home;
