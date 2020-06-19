import React from 'react';

import { userService, authenticationService } from '@/_services';

class HomePage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            currentUser: authenticationService.currentUserValue
        };
    }

    render() {
        const { currentUser} = this.state;
        return (
            <div>
                <h1>Hi your id is {currentUser.id}!</h1>
                <h2>Your roles:</h2>
                <ul>
                    {currentUser.roles &&
                        <ul>
                            {currentUser.roles.map(role =>
                                <li key={role}>{role}</li>
                            )}
                        </ul>
                    }
                </ul>
                
            </div>
        );
    }
}

export { HomePage };