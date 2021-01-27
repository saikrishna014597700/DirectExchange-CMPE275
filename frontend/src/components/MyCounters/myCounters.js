import React,{Component} from 'react';
import axios from "axios";
import { Redirect } from "react-router";
import { backendServer } from "../../webconfig";
import {Card,Button,ButtonGroup, Row, Col} from 'react-bootstrap';

class  MyCounters extends Component {
    constructor(props){
        super(props);
        this.state = {
         offers:[],
         flag1:false
        };
        this.loadMatchingOffers= this.loadMatchingOffers.bind(this)
    }

    loadMatchingOffers(msg){
        axios.post(`${backendServer}/offer/acceptCounter`,msg)
        .then( function (response){
            alert("Deal made with adjustments");
            //this.setState({})
        })
    }

    async componentDidMount() {
    //   const data = {
    //     email: localStorage.getItem("email")
    //   };
    const data = {
       
      };
     var email = localStorage.getItem("userName");//"kailashnath0315@gmail.com"    
      axios
          .get(`${backendServer}/offer/getMyCounters/${email}`)
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
                <Card.Header style={{color:"black"}}>Counter Offer ID {msg.counterOfferId}</Card.Header>
                <Card.Body>
                  <Card.Text>
                  <Row>
                     <Col>
                  <p style={{color:"black"}}>Amount: {msg.amount}</p> 
                  </Col>
                </Row>
                 <Row>
                     <Col>
                  <p style={{color:"black"}}>Source offer : {msg.offer}</p>
                  
                  <p style={{color:"black"}}>Original amount : {msg.proposedAmount}</p>
                  <p style={{color:"black"}}>Exchange Rate : {msg.exchangeRate}</p>
                  </Col>
                  <Col>
                 
                  <p style={{color:"black"}}>Destination Offer : {msg.counterOffer}</p>
                  
                  <p style={{color:"black"}}>Countered Amount : {msg.counterAmount}</p>
                  <p style={{color:"black"}}>counterPartyUserName: {msg.counterPartyUserName}</p>
                  
                  
                  </Col>
                </Row>

                  </Card.Text>
                  <ButtonGroup style={{marginLeft:"450px",marginTop:"20px"}} className="mb-2">
        {/* <Button>Approve</Button>
        &nbsp; */}
        
        &nbsp;
        <Button onClick={(e)=> this.loadMatchingOffers(msg)}>Accept offer</Button>

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

export default MyCounters;