import * as React from 'react';
import { SupportQueryPost } from '../../actions/types';

interface QueryPostProps {
    post: SupportQueryPost;
}

const QueryPost: React.SFC<QueryPostProps>  = (props) => {
    const post = props.post;
    return <tr>
        <th>Query</th>
        <th>{post.content}</th>
    </tr>
}

export default QueryPost;