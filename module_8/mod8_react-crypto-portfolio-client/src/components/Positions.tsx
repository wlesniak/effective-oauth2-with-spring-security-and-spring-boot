import * as React from "react";
import { Position } from "../actions/types";
import PositionItem from "./PositionItem";
import AddTransactionForm from "./AddTransactionForm";

interface PositionsProps {
  positions: Position[];
}

class Positions extends React.Component<PositionsProps> {
  public render() {
    return (
      <React.Fragment>
        <table className="table table-hover">
          <thead>
            <tr>
              <th>Symbol</th>
              <th>Quantity</th>
              <th>Value</th>
            </tr>
          </thead>
          <tbody>
            {this.props.positions.map(position => (
              <PositionItem
                key={position.cryptoCurrency.symbol}
                position={position}
              />
            ))}
          </tbody>
        </table>
        <AddTransactionForm />
      </React.Fragment>
    );
  }
}

export default Positions;
