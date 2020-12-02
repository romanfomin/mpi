import { Formik, Field, Form, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import { Link } from 'react-router-dom';
import { applicationService } from '../_services';
import React, { useState } from "react";
import DatePicker from "react-datepicker";

const DatePickerField = ({ name, value, onChange }) => {
    return (
        <DatePicker
            selected={(value && new Date(value)) || null}
            onChange={val => {
                onChange(name, val);
            }}
        />
    );
};

class Thumb extends React.Component {
    state = {
        loading: false,
        thumb: undefined,
    };

    componentWillReceiveProps(nextProps) {
        if (!nextProps.file) { return; }

        this.setState({ loading: true }, () => {
            let reader = new FileReader();

            reader.onloadend = () => {
                this.setState({ loading: false, thumb: reader.result });
            };
            console.log(nextProps.file)
            reader.readAsDataURL(nextProps.file);
        });
    }

    render() {
        const { file } = this.props;
        const { loading, thumb } = this.state;

        if (!file) { return null; }

        if (loading) { return <p>loading...</p>; }

        return (<img src={thumb}
            alt={file.name}
            className="img-thumbnail mt-2"
            height={200}
            width={200} />);
    }
}

class ApplicationAddPage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            types: null,
            applications: null,
            showForm: false,
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
                })
            })
        });
    }

    render() {
        return (

            <div className="align-middle">
                <h2>ApplicationAdd</h2>
                <Formik
                    initialValues={{
                        comment: '',
                        date: new Date(),
                        file: null,
                        price: 3000,
                        type: null

                    }}
                    validationSchema={Yup.object().shape({
                        price: Yup.number().required('Please suggest a price')
                    })}
                    onSubmit={({ date, comment, price, file, type }, { setStatus, setSubmitting }) => {
                        setStatus();
                        console.log(file)
                        applicationService.create(date, comment, price, file, 'TYPE_AD')
                            .then(
                                user => {
                                    const { from } = this.props.location.state || { from: { pathname: "/application" } };
                                    this.props.history.push(from);
                                },
                                error => {
                                    setSubmitting(false);
                                    setStatus(error);
                                }
                            );
                    }}
                    render={({ values, errors, status, touched, isSubmitting, setFieldValue }) => (

                        <Form>
                            <div className="form-group">
                                <label htmlFor="date">Date</label>
                                <Field name="date" type="text" className={'form-control' + (errors.date && touched.date ? ' is-invalid' : '')} />
                                <ErrorMessage name="date" component="div" className="invalid-feedback" />
                            </div>

                            <div className="form-group">
                                <label htmlFor="price">Price</label>
                                <Field name="price" type="text" className={'form-control' + (errors.price && touched.price ? ' is-invalid' : '')} />
                                <ErrorMessage name="price" component="div" className="invalid-feedback" />
                            </div>

                            <div className="form-group">
                                <label htmlFor="comment">Comment</label>
                                <Field name="comment" type="text" className={'form-control' + (errors.comment && touched.comment ? ' is-invalid' : '')} />
                                <ErrorMessage name="comment" component="div" className="invalid-feedback" />
                            </div>

                            <div className="form-group">
                                <label htmlFor="file">File upload</label>
                                <input id="file" name="file" type="file" onChange={(event) => {
                                    setFieldValue("file", event.currentTarget.files[0]);
                                    console.log(values);
                                }} className="form-control" />
                                <Thumb file={values.file} />
                            </div>

                            {/* <div className="form-group">
                                <label for="file">File upload</label>
                                <input id="file" name="file" type="file" onChange={(event) => {
                                    setFieldValue("file", event.currentTarget.files[0]);
                                }} className="form-control" />
                                <Thumb file={touched.file} />
                            </div> */}



                            <div className="form-group">
                                <button type="submit" className="btn btn-primary" disabled={isSubmitting}>Create</button>
                                {isSubmitting &&
                                    <img src="data:image/gif;base64,R0lGODlhEAAQAPIAAP///wAAAMLCwkJCQgAAAGJiYoKCgpKSkiH/C05FVFNDQVBFMi4wAwEAAAAh/hpDcmVhdGVkIHdpdGggYWpheGxvYWQuaW5mbwAh+QQJCgAAACwAAAAAEAAQAAADMwi63P4wyklrE2MIOggZnAdOmGYJRbExwroUmcG2LmDEwnHQLVsYOd2mBzkYDAdKa+dIAAAh+QQJCgAAACwAAAAAEAAQAAADNAi63P5OjCEgG4QMu7DmikRxQlFUYDEZIGBMRVsaqHwctXXf7WEYB4Ag1xjihkMZsiUkKhIAIfkECQoAAAAsAAAAABAAEAAAAzYIujIjK8pByJDMlFYvBoVjHA70GU7xSUJhmKtwHPAKzLO9HMaoKwJZ7Rf8AYPDDzKpZBqfvwQAIfkECQoAAAAsAAAAABAAEAAAAzMIumIlK8oyhpHsnFZfhYumCYUhDAQxRIdhHBGqRoKw0R8DYlJd8z0fMDgsGo/IpHI5TAAAIfkECQoAAAAsAAAAABAAEAAAAzIIunInK0rnZBTwGPNMgQwmdsNgXGJUlIWEuR5oWUIpz8pAEAMe6TwfwyYsGo/IpFKSAAAh+QQJCgAAACwAAAAAEAAQAAADMwi6IMKQORfjdOe82p4wGccc4CEuQradylesojEMBgsUc2G7sDX3lQGBMLAJibufbSlKAAAh+QQJCgAAACwAAAAAEAAQAAADMgi63P7wCRHZnFVdmgHu2nFwlWCI3WGc3TSWhUFGxTAUkGCbtgENBMJAEJsxgMLWzpEAACH5BAkKAAAALAAAAAAQABAAAAMyCLrc/jDKSatlQtScKdceCAjDII7HcQ4EMTCpyrCuUBjCYRgHVtqlAiB1YhiCnlsRkAAAOwAAAAAAAAAAAA==" />
                                }
                            </div>
                            {status &&
                                <div className={'alert alert-danger'}>{status}</div>
                            }
                        </Form>
                    )}
                />
                <Link to="/application" className="btn btn-danger">Return to Applications</Link>

            </div>
        )
    }
}

export { ApplicationAddPage }; 