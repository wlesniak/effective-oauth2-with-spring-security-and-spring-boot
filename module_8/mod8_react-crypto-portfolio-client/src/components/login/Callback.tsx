import * as React from 'react';
import { AuthService } from '../../services/AuthService';
import { Redirect } from 'react-router';

interface CallbackState {
}

class Callback extends React.Component<CallbackState, any> {

    componentWillMount() {
       AuthService.getInstance().completeLogin();
    }

    render() {
       return(
          <Redirect to = "/portfolio"/>
       );
    }
   
}

export default Callback;