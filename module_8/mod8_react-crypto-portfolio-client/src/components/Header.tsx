import * as React from 'react';
import { Link } from 'react-router-dom';

export interface IAppProps {
}

class Header extends React.Component<IAppProps, any> {
  public render() {
    return (
      <nav className="navbar navbar-expand-sm  navbar-light crypt_nav">
      <div className="container-fluid" >
        <div className="navbar-header">
          <a className="navbar-brand" href="#">Spring - Crypto Portfolio</a>
        </div>
      <ul className="nav navbar-nav">		
        <li className="nav-item active">
        <Link className="nav-link" to="/portfolio">Home</Link>
        </li>
            <li className="nav-item">
            <Link className="nav-link" to="/portfolio/transactions">Transactions</Link>
            </li>
            <li className="nav-item">
            <Link className="nav-link" to="/support">Support</Link>
            </li>
          </ul>
      </div>
    </nav>
    );
  }
}

export default Header;

