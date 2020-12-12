import React from 'react'
import getMuiTheme from 'material-ui/styles/getMuiTheme'
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider'
import Header from '../components/Header'
import DevTools from './DevTools'

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
      history.push('/login');
  }

  render() {
      const { currentUser } = this.state;
      return (
        <div></div>
          // <Router history={history}>
          //     <div>
          //         {currentUser &&
          //             <nav className="navbar navbar-expand navbar-dark bg-dark">
          //                 <div className="navbar-nav">
          //                     <Link to="/" className="nav-item nav-link">Home</Link>
          //                     <Link to="/script" className="nav-item nav-link">Scripts</Link>
          //                     <Link to="/admin" className="nav-item nav-link">Admin</Link>
          //                     <Link to="/application" className="nav-item nav-link">Applications</Link>
          //                     <a onClick={this.logout} className="nav-item nav-link">Logout</a>
          //                 </div>
          //             </nav>
          //         }
          //         <div className="container">
          //             <div className="row">
          //                 <div className="col-md-6 offset-md-3">
          //                     <PrivateRoute exact path="/" component={HomePage} />
          //                     <Route path="/login" component={LoginPage} />
          //                     <Route path="/register" component={RegisterPage} />
          //                     <Route path="/script" component={ScriptPage} />
          //                     <Route path="/admin" component={AdminPage}/>
          //                     <Route path="/application" component={ApplicationPage}/>
          //                     <Route path="/application_add" component={ApplicationAddPage}/>
          //                 </div>
          //             </div>
          //         </div>
          //     </div>
          // </Router>
      );
  }
}

export default (props) => (
  <MuiThemeProvider muiTheme={getMuiTheme()}>
    <App/>
  </MuiThemeProvider>
)
