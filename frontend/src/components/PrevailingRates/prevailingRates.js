import React, { Component } from 'react';
import axios from 'axios';
import { Redirect } from 'react-router';
import { Link } from 'react-router-dom';
// import bcrypt from 'bcryptjs';
// import HouseIcon from '@material-ui/icons/House';
// import landingpage from '../Landingpage';
import { backendServer } from "../../webconfig.js";
import Table from 'react-bootstrap/Table'


export default class PrevailingRates extends Component {
  constructor(props) {
    super(props);
    this.state = {
      rates: []
    }
  }

  componentDidMount() {
    axios.get(`${backendServer}/rates`).then(response => {
      console.log("Pro are::", response.data);
      this.setState({
        rates: response.data
      })

    });
  }
  render() {
    let redirectVar = null;
    if (this.state.redirect) {
      redirectVar = <Redirect push to={this.state.redirect} />;
    }

    let rates = this.state.rates.map((msg) => {
      return (

        <tr>
          <td>{msg.id}</td>
          <td>{msg.fromCurr}</td>
          <td>{msg.toCurr}</td>
          <td>{msg.exchangeRate}</td>
        </tr>

      )
    });



    return (
      <div>
        {redirectVar}
        <div style={{ marginLeft: "10%", marginRight: "10%", marginTop: "20px" }}>
          <Table striped bordered hover >
            <thead class="thead-dark">
              <tr>
                <th>#</th>
                <th>From Currency</th>
                <th>To Currency</th>
                <th>Exchnage Value</th>
              </tr>
            </thead>
            <tbody>
              {rates}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }


}

// export default SignIn;