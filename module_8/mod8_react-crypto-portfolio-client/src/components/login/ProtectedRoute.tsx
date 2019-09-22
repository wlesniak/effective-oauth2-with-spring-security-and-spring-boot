import * as React from 'react';
import {Redirect, Route, RouteProps} from 'react-router';
import { AuthService } from '../../services/AuthService';

export interface ProtectedRouteProps extends RouteProps {
    isAuthenticated: boolean;
    authenticationPath: string;
}

export type ComponentState = {isAuthenticated: boolean}
export class ProtectedRoute extends Route<ProtectedRouteProps> {

    public render() {
        let redirectPath: string = '';
        let isAuth = true

        if (!isAuth) {
            redirectPath = this.props.authenticationPath;
        }

        if (redirectPath) {
            const renderComponent = () => (<Redirect to={{pathname: redirectPath}}/>);
            return <Route {...this.props} component={renderComponent} render={undefined}/>;
        } else {
            return <Route {...this.props}/>;
        }
    }
}