import * as React from 'react';
import { SupportQuery } from '../../actions/types';

interface QueryItemProps {
    query: SupportQuery,
    onTableRowClick(e: React.MouseEvent<HTMLTableRowElement>): void
}

const QueryItem: React.SFC<QueryItemProps>  = (props) => {
    const query = props.query;
    return <tr onClick={props.onTableRowClick} id = {query.id}>
        <th>{query.subject}</th>
        <th>{query.username}</th>
        <th>{query.resolved}</th>
        <th>{query.creationTime}</th>
    </tr>
}

export default QueryItem