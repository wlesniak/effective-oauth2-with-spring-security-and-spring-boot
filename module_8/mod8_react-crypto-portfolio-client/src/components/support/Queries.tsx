import * as React from "react";
import { SupportQuery } from "../../actions/types";
import QueryItem from "./QueryItem";
import QueryPosts from "./QueryPosts";
import QueryPost from "./QueryPost";


interface QueriesProps {
  queries: SupportQuery[];
}

class Queries extends React.Component<QueriesProps,any> {
  
  constructor(props:QueriesProps) {
      super(props);
      this.state = {
        displayPostsForQuery: ""
      }
      this.onTableRowClick=this.onTableRowClick.bind(this);
  }

  private onTableRowClick(e: React.MouseEvent<HTMLTableRowElement>) {
    this.setState({displayPostsForQuery : e.currentTarget.id});
  }

  public render() {
    let queriesList : SupportQuery[] = [];
    if(this.props.queries) {
      queriesList = this.props.queries;
    }
    if(this.state.displayPostsForQuery) {
        const query = queriesList.find((q) => q.id === this.state.displayPostsForQuery);
        if (query) {
          return (<React.Fragment>
            <table>
            <tr><td>Subject</td><td>{query.subject}</td></tr>
            <QueryPost post = {query.posts[0]}/>
            </table>
          </React.Fragment>);
        }
    }
    return (
      <React.Fragment>
        <table className="table table-hover">
          <thead>
            <tr>
              <th>Subject</th>
              <th>Username</th>
              <th>Resolved</th>
              <th>Created</th>
            </tr>
          </thead>
          <tbody>
            {queriesList.map(query => (
              <QueryItem onTableRowClick={this.onTableRowClick}
                key={query.creationTime}
                query={query}
              />
            ))}
          </tbody>
        </table>
      </React.Fragment>
    );
  }
}

export default Queries;
