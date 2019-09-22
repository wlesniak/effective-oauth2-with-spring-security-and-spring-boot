import * as React from 'react';
import { Transaction } from '../actions/types';

interface TransactionItemProps {
    transaction: Transaction;
    handleCheckboxChange : React.ChangeEventHandler;
}

const TransactionItem: React.SFC<TransactionItemProps> = (props) => {
  return <tr>
  <th><input id ={props.transaction.id} name="selectedTransaction" type="checkbox" onChange ={props.handleCheckboxChange} value={props.transaction.id} /></th>
  <th>{props.transaction.id}</th>
  <th>{props.transaction.symbol}</th>
  <th>{props.transaction.transactionType}</th>
  <th>{props.transaction.quantity}</th>
  <th>{props.transaction.price}</th>
</tr>
};

export default TransactionItem;