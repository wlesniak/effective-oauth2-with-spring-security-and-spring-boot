import * as React from "react";
import { Transaction } from "../actions/types";
import { ApplicationState } from "../reducers";
import { ThunkDispatch } from "redux-thunk";
import { getPortfolioTransactionsCreator, deleteTransactionsCreator } from "../actions/protfolioActions";
import { connect } from "react-redux";
import TransactionItem from "./TransactionItem";

export interface TransactionsProps {
  loadTransactions: Function;
  deleteTransactions: Function;
  transactions: Transaction[];
}

interface TransactionFormState {
  selectedTransactionIds : Set<string>
}

class Transactions extends React.Component<TransactionsProps,TransactionFormState> {
  
  constructor(props: TransactionsProps) {
    super(props);
    this.state = {
      selectedTransactionIds : new Set()
    }
    this.handleSubmit=this.handleSubmit.bind(this);
    this.handleCheckboxChange=this.handleCheckboxChange.bind(this);
  }
  
  componentDidMount() {
    this.props.loadTransactions();
  }

  handleCheckboxChange = (event : React.ChangeEvent<HTMLInputElement>) => {
    this.toggleCheckbox(event.target.value);
  }

  toggleCheckbox = (transactionId : string) => {
    if(this.state.selectedTransactionIds.has(transactionId)) {
        this.state.selectedTransactionIds.delete(transactionId);
    } else {
      this.state.selectedTransactionIds.add(transactionId);
    }
  }

  handleSubmit(e: React.FormEvent<HTMLFormElement>) {
    e.preventDefault();
    console.log(e.target)
    this.props.deleteTransactions(Array.from(this.state.selectedTransactionIds));
  }

  public render() {
    return (
      <React.Fragment>
        <form onSubmit={this.handleSubmit}>
        <table className="table table-hover">
          <thead>
            <tr>
              <th>#</th>
              <th>Transaction Id</th>
              <th>Symbol</th>
              <th>Type</th>
              <th>Quantity</th>
              <th>Price</th>
            </tr>
          </thead>
          <tbody>
              {this.props.transactions.map((transaction, index) => (
                <TransactionItem key={transaction.id} handleCheckboxChange = {this.handleCheckboxChange} transaction={transaction} />
              ))}
          </tbody>
        </table>
        <button name="deleteTransactionBtn" type="submit">Delete Transactions</button>    
        </form>
      </React.Fragment>
    );
  }
}

const mapStateToProps = (state: ApplicationState) => {
  return { transactions: state.portfolioTransactions.transactions };
};

const mapDispatchToProps = (dispatch: ThunkDispatch<{}, {}, any>) => ({
  loadTransactions: (transactionType?: string) =>
    dispatch(getPortfolioTransactionsCreator(transactionType)),
  deleteTransactions : (transactionIds : string[]) => 
    dispatch(deleteTransactionsCreator(transactionIds))
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Transactions);
