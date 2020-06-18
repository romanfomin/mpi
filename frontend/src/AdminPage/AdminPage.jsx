import React from 'react';
import { authenticationService, roleService, userService } from '@/_services';
import Calendar from 'react-calendar';
import moment from 'moment';
// import MultiSelect from "react-multi-select-component";
import { useState } from 'react';
import MultiSelect from "@khanacademy/react-multi-select";

class AdminPage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            roles: null,
            users: null,
            selected: null,
        };

    }

    componentDidMount() {
        roleService.getAll().then(roles => this.setState({ roles })).then(() => {
            userService.getAll().then(users => {
                this.setState({ users })
                let selected = {}
                users.map(user => {
                    let roles = user.roles.map(role => {
                        return role.id
                        // return { value: role.id, label: role.name }
                    })
                    selected[user.id] = roles
                })
                this.setState({ selected: selected })
                console.log(this.state.selected)
            }
            )
        }
        )
    }

    renderTableData() {
        return this.state.roles.map((role, index) => {
            const { id, name } = role
            return (
                <tr key={id}>
                    <td>{id}</td>
                    <td>{name}</td>
                </tr>
            )
        })
    }

    renderMultiselect(roles, id) {
        let options = this.state.roles.map(role => {
            return { value: role.id, label: role.name }
        })
        const selected = this.state.selected[id]
        return <MultiSelect
            options={options}
            selected={selected}
            onSelectedChanged={selected => {
                let s = this.state.selected
                s[id] = selected
                this.setState({ selected: s })
            }}
        />
    }


    update = () => {
        // console.log(event.target.value)
        // console.log(this.state.selected[event.target.value])
        console.log(this.state.roles)
        let roles = this.state.roles.filter(role => {
            console.log('Role')
            console.log(role.id)
            console.log('Event')
            console.log(this.state.selected[event.target.value])
            console.log(this.state.selected[event.target.value].find((element, index, array) => {
                return element === role.id
            }))
            console.log('Result')
            console.log(role.id in this.state.selected[event.target.value])
            console.log(this.state.selected[event.target.value].includes(role.id))
            return this.state.selected[event.target.value].includes(role.id)
        })
        roles = roles.map(role => {return {name: role.name}})
        console.log(roles)
        userService.update(event.target.value, roles)
    }
    renderUsersTable() {
        let style = {
            width: '10000px'
        }
        return this.state.users.map(user => {
            const { id, username, email, roles } = user
            return (
                <tr scope="row" key={id}>
                    <th>{id}</th>
                    <td>{username}</td>
                    <td>{email}</td>
                    <td className="col-6">
                        {this.renderMultiselect(roles, id)}
                    </td>
                    <td>
                        <button className="btn btn-primary" onClick={this.update} value={id}>Update</button>
                    </td>
                </tr>
            )
        })
    }
    render() {
        return (
            <div>
                {this.state.users != null && this.state.selected != null ?
                    <table id='users' className="table mt-5">
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Username</th>
                                <th scope="col">Email</th>
                                <th scope="col">Roles</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            {this.renderUsersTable()}
                        </tbody>
                    </table>
                    : null
                }
            </div>
        );
    }
}

export { AdminPage };