import * as React from 'react';
import { AuthService } from '../../services/AuthService';

interface LoginFormProps {
    login?: Function;
}

interface LoginFormState {
}

class LoginForm extends React.Component<LoginFormState, any> {

    private authService: AuthService;

    constructor(props:LoginFormProps) {
        super(props);
        this.authService = AuthService.getInstance();
        this.state = {

        }
        this.handleSubmit=this.handleSubmit.bind(this);
    }

    handleSubmit(e: React.FormEvent<HTMLFormElement>) {
        e.preventDefault();
        this.authService.login();
    }

  public render() {
    return (
        <div className="container add_trans_div">
            <form onSubmit={this.handleSubmit}>
				    <button name="loginFormBtn" type="submit">Login</button>               
            </form>
	    </div>
    );
  }
}

export default LoginForm;
