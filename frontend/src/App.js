import React, { Component } from 'react';
import './App.css';
import {BrowserRouter} from 'react-router-dom';
import Main from './components/main.js';


class App extends Component {
  render() {
    return (
      //Use Browser Router to route to different pages
      <BrowserRouter>
        <div>
        
          <Main/>
        </div>
      </BrowserRouter>
    );
  }
}

export default App;
