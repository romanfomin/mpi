import React from 'react';
import { Formik, Field, Form, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import { applicationService, authenticationService } from '../_services';
import { Route, Link } from 'react-router-dom';
import MultiSelect from "@khanacademy/react-multi-select";
import Select from 'react-select';


class ApplicationsTable extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            selected: {},
            currentUser: authenticationService.currentUserValue
        };
    }

    render_head() {
        if (!this.props.applications){
            return null;
        }
        console.log('applications', this.props.applications)
        return (Object.keys(this.props.applications[0]).map((key) => <th className={key === 'state' ? "App-width-25" : ""}>{key}</th>))
    }

    renderSelect(statuses, id, applicationId) {
        if (!statuses){
            return null;
        }
        let options = statuses.map(status => {
            return { value: status.id, label: status.name }
        })
        return <Select
            options={options}
            defaultValue = {options.filter((item) => {return item.value === id})}
            onChange={selected => {
                console.log(`select: ${applicationId}`)
                console.log(selected)
                applicationService.updateStates(applicationId, selected.label)
            }}
        />
    }

    render() {
        console.log(this.props.statuses)
        return (
            <table class="table table-striped">
                <thead>
                    <tr>
                        {this.props.applications && this.render_head()}
                    </tr>
                </thead>
                <tbody>
                    {this.props.applications && this.props.applications.map((application) =>
                        <tr>
                            <td>{application.id}</td>
                            <td>{application.appDate}</td>
                            <td>{application.name}</td>
                            <td>{application.price}</td>
                            {this.state.currentUser.roles && this.state.currentUser.roles.filter((item) => {return item === 'ROLE_MANAGER' || item === 'ROLE_OPERATOR'}).length > 0 ?
                            <td className>{this.renderSelect(this.props.statuses, application.state.id, application.id)}</td> :                            
                            <td>{application.state.name}</td>
                            }
                            <td>{application.type.name}</td>
                            <th>{application.files && application.files.map((file) => <a id={file.id} href={`${process.env.REACT_APP_API_URL}/api/files/${file.id}`}>{file.name}</a>)}</th>
                        </tr>
                    )}
                </tbody>
            </table>
        )
    }
}

class ApplicationPage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            types: null,
            applications: null,
            showForm: false,
            currentUser: authenticationService.currentUserValue,
            statuses: null

        };
    }
    componentDidMount() {
        applicationService.getAllTypes().then(types => {
            this.setState({ types })
            this.state.types.forEach(element => {
                applicationService.getAll(element.name).then(applications => {
                    if (!this.state.applications) {
                        this.setState({ applications: applications })
                    } else {
                        this.setState({ applications: this.state.applications.concat(applications) })
                    }
                    console.log(this.state.applications)
                    applicationService.getAllStates().then(statuses => {
                        this.setState({ statuses: statuses })
                    })
                })
            })
        });
    }

    render() {
        return (
            <div className="align-middle">
                <h2>Application</h2>
                {this.state.statuses !== null && this.state.applications !== null && this.state.applications.length !== 0 ? <ApplicationsTable applications={this.state.applications} statuses={this.state.statuses}/> : <div>No applications found</div>}
                {this.state.currentUser.roles.includes('ROLE_ADVERTISER') || this.state.currentUser.roles.includes('ROLE_OPERATOR') ? <Link to="/application_add" className="btn btn-primary">Add Application</Link> : null}
            </div>
        )
    }
}

export { ApplicationPage }; 