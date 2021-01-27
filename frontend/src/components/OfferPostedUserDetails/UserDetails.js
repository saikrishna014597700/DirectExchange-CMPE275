import React, { Component } from 'react';
import axios from "axios";
import { Redirect } from "react-router";
import { backendServer } from "../../webconfig";
import { Card, Button, ButtonGroup, Row, Col } from 'react-bootstrap';

class UserDetails extends Component {
    constructor(props) {
        super(props);
        this.state = {
            userDetails: {}
        }
    }

    async componentDidMount() {
        var offerId = this.props.match.params.id;
        await axios
            .get(`${backendServer}/offer/getOffer/${offerId}`)
            .then((response) => {
                this.setState({
                    userDetails: response.data,
                });
                console.log(response.data)
            });
    }

    render() {
        let redirectVar = null;
        if (this.state.redirect) {
            redirectVar = <Redirect push to={this.state.redirect} />;
        }
        let msg = this.state.userDetails
        let temp = msg.user
        let user = null;
        let allowCountersHTML = null;
        let allowSplitHTML = null;
        if (msg.allowCounters) {
            allowCountersHTML = <Col>
                <p style={{ color: "black" }}>Allows Counter: True</p>
            </Col>;
        } else {
            allowCountersHTML = <Col>
                <p style={{ color: "black" }}>Allows Counter: False</p>
            </Col>;
        }
        if (msg.allowCounters) {
            allowSplitHTML = <Col>
                <p style={{ color: "black" }}>Allows Split: True</p>
            </Col>;
        } else {
            allowSplitHTML = <Col>
                <p style={{ color: "black" }}>Allows Split: False</p>
            </Col>;
        }
        if (temp) {
            user =
                <div>
                    <Row>
                        <Col>
                            <p style={{ color: "blue" }}> User Details:</p>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <p style={{ color: "black" }}>User Name : {temp.userName}</p>
                            <p style={{ color: "black" }}>NickName : {temp.nickName}</p>

                            <p style={{ color: "black" }}>Rating : {temp.rating}</p>

                        </Col>
                    </Row>
                </div>
        }
        let userDetails =
            <div>
                &nbsp;
                <Card style={{ width: "50%", marginLeft: "20%", marginTop: "8%", height: "80%", float: "center" }}>
                    <Card.Header style={{ color: "blue" }}>User Name: {msg.userName}</Card.Header>
                    <Card.Body>
                        <Card.Text>
                            <Row>
                                {allowCountersHTML}
                                {allowSplitHTML}
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
                            <Row>
                                <Col>
                                    <p style={{ color: "black" }}>Amount: {msg.amount}</p>
                                </Col>
                                <Col>
                                    <p style={{ color: "black" }}>Expiry Date: {msg.expiryDate}</p>
                                </Col>
                            </Row>
                            {user}


                        </Card.Text>
                    </Card.Body>
                </Card>

                &nbsp;
              </div>
        return (
            <div>
                {redirectVar}
                {userDetails}


            </div>

        );
    }
}

export default UserDetails;