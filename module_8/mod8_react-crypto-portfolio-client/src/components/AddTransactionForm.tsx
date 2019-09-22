import * as React from 'react';
import {connect} from 'react-redux';
import {addPortfolioTransactionCreator} from '../actions/protfolioActions';
import { AddPortfolioTransaction } from '../actions/types';
import { ThunkDispatch } from 'redux-thunk';
import { History } from 'history';

interface AddTransactionFormProps {
    addTransaction?: Function;
}

interface AddTransactionsFormState {
    cryptoSymbol: string,
    type: string,
    quantity: number,
    price: number
}

class AddTransactionForm extends React.Component<AddTransactionFormProps, any> {

    constructor(props:AddTransactionFormProps) {
        super(props);
        this.state = {
            cryptoSymbol: "",
            type: "",
            quantity: 0,
            price: 0
        }
        this.onChange=this.onChange.bind(this);
        this.onSelectChange=this.onSelectChange.bind(this);
        this.handleSubmit=this.handleSubmit.bind(this);
    }

    onChange(e: React.ChangeEvent<HTMLInputElement>) {
        this.setState({ [e.target.name]: e.target.value});
    }

    onSelectChange(e: React.ChangeEvent<HTMLSelectElement>) {
        this.setState({ [e.target.name]: e.target.value});
    }

    handleSubmit(e: React.FormEvent<HTMLFormElement>) {
        e.preventDefault();
        const transaction = {
            cryptoSymbol: this.state.cryptoSymbol,
            price: this.state.price,
            quantity: this.state.quantity,
            transactionType: this.state.type
        };
        if(this.props.addTransaction !== undefined) {
            this.props.addTransaction(transaction);
        }
    }

  public render() {
    return (
        <div className="container add_trans_div">
            <form onSubmit={this.handleSubmit}>
                <fieldset>
                    <label>Symbol</label>
                    <select name = "cryptoSymbol" required={true} value={this.state.cryptoSymbol} onChange={this.onSelectChange}>
                        <option value="">----Select----</option>
                        <option value="BTC">BTC</option>
                        <option value="LTC">LTC</option>
                    </select>
                    <label>Symbol</label>
                    <select name="type" required={true} value = {this.state.type} onChange={this.onSelectChange}>
                        <option value="">----Select----</option>
                        <option value="BUY">BUY</option>
                        <option value="SELL">SELL</option>
                    </select>     
                    <label>Quantity</label> 
				    <input type="number" name="quantity" required={true} value={this.state.quantity} onChange={this.onChange}/>
                    <label>Price</label> 
				    <input type="number" name="price" required={true} value={this.state.price} onChange={this.onChange}/>
				    <button name="addTransactionBtn" type="submit">Add Transaction</button>               
                </fieldset>
            </form>
	    </div>
    );
  }
}

const mapDispatchToProps = (dispatch : ThunkDispatch<{},{},any>) => ({
    addTransaction: (transaction: AddPortfolioTransaction, history: History) => dispatch(addPortfolioTransactionCreator(transaction, history))
  });


export default connect(null,mapDispatchToProps)(AddTransactionForm);
