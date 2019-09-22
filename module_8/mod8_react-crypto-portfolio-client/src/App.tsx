import React, { Component} from 'react';
import { Provider } from 'react-redux';
import './App.css';
import Portfolio from './components/Portfolio';
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Route } from "react-router-dom";
import store from "./store/store";
import LoginForm from './components/login/LoginForm';
import UserSupport from './components/support/UserSupport';

class App extends Component {

  render() {
    return (
      <Provider store = {store({})}>
      <Router>
        <div className="App">
        <Route path="/login" component={LoginForm} />
        <Route  path="/portfolio" component={Portfolio} />
        <Route path="/support" component={UserSupport}/>
        </div>
      </Router>
      </Provider>
    );
  }
}

export default App;
