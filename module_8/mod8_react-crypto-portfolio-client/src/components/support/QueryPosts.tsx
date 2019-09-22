import * as React from "react";

import { ThunkDispatch } from "redux-thunk";
import { connect } from "react-redux";
import { SupportQueryPost } from "../../actions/types";
import QueryPost from "./QueryPost";

export interface QueriesProps {
  posts: SupportQueryPost[];
}

interface QueryFormState {
}

class Transactions extends React.Component<QueriesProps,QueryFormState> {
  
  constructor(props: QueriesProps) {
    super(props);
    this.state = {
     
    }
    this.handleSubmit=this.handleSubmit.bind(this);
  }
  
  componentDidMount() {
    //this.props.loadTransactions();
  }


  handleSubmit(e: React.FormEvent<HTMLFormElement>) {
    e.preventDefault();
    console.log(e.target)
  }

  public render() {
    return (
      <React.Fragment>
        <form onSubmit={this.handleSubmit}>
        <table className="table table-hover">
          <thead>
            <tr>
              <th>#</th>
              <th>Posts</th>
            </tr>
          </thead>
          <tbody>
              {this.props.posts.map((post, index) => (
                <QueryPost key={post.queryId} post={post} />
              ))}
          </tbody>
        </table>
        </form>
      </React.Fragment>
    );
  }
}

const mapDispatchToProps = (dispatch: ThunkDispatch<{}, {}, any>) => ({

});

export default connect(
  null,
  mapDispatchToProps
)(Transactions);
