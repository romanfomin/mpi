import React from 'react';
import { Router, BrowserRouter, Route, Link } from 'react-router-dom';

import { authenticationService } from '../_services';
import { PrivateRoute } from '../_components/PrivateRoute';
import { HomePage } from '../HomePage/index';
import { LoginPage } from '../LoginPage';
import { RegisterPage } from '../RegisterPage';
import { ScriptPage } from '../ScriptPage';
import { AdminPage } from '../AdminPage';
import { ApplicationAddPage } from '../ApplicationAddPage';
import { ApplicationPage } from '../ApplicationPage';
import {history} from '../_helpers'
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';


class App extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            currentUser: null
        };
    }

    componentDidMount() {
        authenticationService.currentUser.subscribe(x => this.setState({ currentUser: x }));
    }

    logout() {
        authenticationService.logout();
        console.log('logout')
        if (process.env.REACT_APP_SUB_PATH != undefined){
        history.push(process.env.REACT_APP_SUB_PATH.replace('http://','') + '/login');
    }
    }

    render() {
        const { currentUser } = this.state;
        console.log('env', process.env.REACT_APP_SUB_PATH.replace('http://',''))
        return (
            <BrowserRouter history={history} basename={process.env.REACT_APP_SUB_PATH.replace('http://','')}>
                <div>
                    {currentUser &&
                        <nav className="navbar navbar-expand navbar-dark bg-dark">
                            <div className="navbar-nav">
                                <Link to="/" className="nav-item nav-link">Home</Link>
                                <Link to="/script" className="nav-item nav-link">Scripts</Link>
                                <Link to="/admin" className="nav-item nav-link">Admin</Link>
                                <Link to="/application" className="nav-item nav-link">Applications</Link> 
                                <a onClick={this.logout} className="nav-item nav-link">Logout</a>
                            </div>
                        </nav>
                    }
                    <div className="container">
                        <div className="row">
                            <div className="col-md-12">
                                <PrivateRoute exact path="/" component={HomePage} />
                                <Route path="/login" component={LoginPage} />
                                <Route path="/register" component={RegisterPage} />
                                <Route path="/script" component={ScriptPage} />
                                <Route path="/admin" component={AdminPage}/>
                                <Route path="/application" component={ApplicationPage}/>
                                <Route path="/application_add" component={ApplicationAddPage}/>
                            </div>
                        </div>
                    </div>
                </div>
            </BrowserRouter>
        );
    }
}

export default App;