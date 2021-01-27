import React,{Component} from 'react';
import axios from "axios";
import { Redirect } from "react-router";
import { backendServer } from "../../webconfig";
import {Card,Button,ButtonGroup, Row, Col} from 'react-bootstrap';

class MyTransactions extends Component {
    constructor(props){
        super(props);
        this.state = {
         offers:[],
         flag1:false
        };
    }

    loadMatchingOffers(id){
        axios
          .post(`${backendServer}/offer/transferFunds?userName=${localStorage.getItem("userName")}&transactionId=${id}`)
          .then((response) => {
            console.log("Pro are::", response.data);
            alert("Success")
            this.setState({
             redirect: "/myOffers"
            });
            console.log("Pro are::", this.state.offers);
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
          .get(`${backendServer}/offer/getMyTransaction/${email}`)
          .then((response) => {
            console.log("Pro are::", response.data);
            this.setState({
             offers: response.data,
            });
            console.log("Pro are::", this.state.offers);
          });
      }

    render()
    {
        let redirectVar = null;
        if (this.state.redirect) {
            redirectVar = <Redirect push to={this.state.redirect} />;
          }
        let candr = this.state.offers.map((msg) => {
            return (
            <div>
            &nbsp;
                <Card style={{width:"50%",marginLeft:"100px",height:"80%"}}>
                <Card.Header style={{color:"black"}}>Transaction ID: {msg.transactionId}</Card.Header>
                <Card.Body>
                  <Card.Text>
                  <Col>
                  <p>Your Offer</p>
                 <Row>
                     <Col>
                  <p style={{color:"black"}}>Source Country : {msg.offer.sourceCountry}</p>
                  
                  <p style={{color:"black"}}>Source Currency : {msg.offer.sourceCurrency}</p>
                  <p style={{color:"black"}}>Exchange Rate : {msg.offer.exchangeRate}</p>
                  </Col>
                  <Col>
                 
                  <p style={{color:"black"}}>Destination Country : {msg.offer.destinationCountry}</p>
                  
                  <p style={{color:"black"}}>Destination Currency : {msg.offer.destinationCurrency}</p>
                  
                  <p style={{color:"black"}}>Amount : {msg.offer.amount}</p>
                  </Col>
                </Row>
                </Col>
                <Col>
                <p>Accepted Offer</p>
                <Row>
                     <Col>
                  <p style={{color:"black"}}>Source Country : {msg.acceptedOffer.sourceCountry}</p>
                  
                  <p style={{color:"black"}}>Source Currency : {msg.acceptedOffer.sourceCurrency}</p>
                  <p style={{color:"black"}}>Exchange Rate : {msg.acceptedOffer.exchangeRate}</p>
                  </Col>
                  <Col>
                 
                  <p style={{color:"black"}}>Destination Country : {msg.acceptedOffer.destinationCountry}</p>
                  
                  <p style={{color:"black"}}>Destination Currency : {msg.acceptedOffer.destinationCurrency}</p>
                  
                  <p style={{color:"black"}}>Amount : {msg.acceptedOffer.amount}</p>
                  </Col>
                </Row>
                </Col>

                  </Card.Text>
                  <ButtonGroup style={{marginLeft:"450px",marginTop:"20px"}} className="mb-2">
        {/* <Button>Approve</Button>
        &nbsp; */}
        
        &nbsp;
        <Button onClick={(e)=> this.loadMatchingOffers(msg.transactionId)}>Transfer money</Button>

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

export default MyTransactions;