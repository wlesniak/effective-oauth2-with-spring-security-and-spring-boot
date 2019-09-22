import * as React from 'react';
import { Position } from '../actions/types';

interface PositionItemProps {
    position: Position
}

const PositionItem: React.SFC<PositionItemProps>  = (props) => {
    const position = props.position;
    return <tr>
        <th>{position.cryptoCurrency.symbol}</th>
        <th>{position.quantity}</th>
        <th>{position.value}</th>
    </tr>
}

export default PositionItem