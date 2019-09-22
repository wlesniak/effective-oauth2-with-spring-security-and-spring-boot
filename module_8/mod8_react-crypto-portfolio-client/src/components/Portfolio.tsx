import * as React from "react";
import { connect } from "react-redux";
import { Route, Switch } from "react-router";
import { ThunkDispatch } from "redux-thunk";
import { getPortfolioPositionsCreator } from "../actions/protfolioActions";
import { Position } from "../actions/types";
import { ApplicationState } from "../reducers";
import Positions from "./Positions";
import Transactions from "./Transactions";
import Header from './Header';
import LoginForm from "./login/LoginForm";

export interface AppProps {
  loadPositions: Function;
  positions: Position[];
  authenticated?: boolean;
}

class Portfolio extends React.Component<AppProps> {
  componentDidMount() {
    this.props.loadPositions();
  }

  public render() {
    if(!this.props.authenticated) {
      return (
        <div className="container">
      <Header/>
        <Route component={LoginForm} />
        </div>
      );
    }
    return (
      <div className="container">
      <Header/>
      <Switch>
        <Route path="/portfolio/transactions" component={Transactions} />
        <Route path="/portfolio" component={() => <Positions positions={this.props.positions} />}/>
        </Switch>
      </div>
    );
  }
}

const mapStateToProps = (state: ApplicationState) => {
  return { positions: state.portfolio.positions, authenticated : state.portfolio.isAuthenticated};
};

const mapDispatchToProps = (dispatch: ThunkDispatch<{}, {}, any>) => ({
  loadPositions: () => dispatch(getPortfolioPositionsCreator())
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Portfolio);
