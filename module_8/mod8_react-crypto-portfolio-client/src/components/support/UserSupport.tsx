import * as React from "react";
import { connect } from "react-redux";
import { Route, Switch } from "react-router";
import { ThunkDispatch } from "redux-thunk";

import { SupportQuery, SupportState } from "../../actions/types";
import Header from "../Header";
import Queries from "./Queries";
import { getSupportQueriesCreator } from "../../actions/supportActions";
import { Link } from "react-router-dom";
import AddQueryForm from "./AddQueryForm";
import { ApplicationState } from "../../reducers";

export interface AppProps {
  loadQueries: Function;
  queries: SupportQuery[];
}

class UserSupport extends React.Component<AppProps> {
  componentDidMount() {
    this.props.loadQueries();
  }

  public render() {    
    return (
      <div className="container">
      <Header/>
      <Link className="nav-link" to="/support/compose_query">Add New</Link>
      <Switch>
        <Route path ="/support/compose_query" component = {AddQueryForm}/>
        <Route path="/support" component={() => <Queries queries={this.props.queries} />}/>
      </Switch>
      </div>
    );
  }
}

const mapStateToProps = (state: ApplicationState) => {
  return { queries: state.support.queries };
};

const mapDispatchToProps = (dispatch: ThunkDispatch<{}, {}, any>) => ({  
  loadQueries: () => dispatch(getSupportQueriesCreator())
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UserSupport);
