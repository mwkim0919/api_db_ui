'use strict';

import React, {Component} from "react";
import LoadingIcon from "../loadingIcon/LoadingIcon";
import {Redirect} from "react-router-dom";

class VendorForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: true,
            fireRedirect: false,
            errorMsg: "",
            vendor: {
                "name": ""
            }
        }
    }

    componentDidMount() {
        if (this.props.match.params.id != null) {
            const headers = new Headers();
            headers.append("Content-Type", "application/json");
            headers.append("ENV", this.props.match.params.env);
            fetch("/api/vendors/" + this.props.match.params.id, {
                credentials: 'same-origin',
                method: "GET",
                accept: "application/json",
                headers: headers,
            }).then(res => {
                if (res.status === 200) {
                    res.json().then(data => {
                        delete data.id;
                        this.setState({ vendor: data });
                        this.setState({ loading: false });
                    });
                } else {
                    console.log(res);
                    this.setState({ errorMsg: "Could not get the vendor." });
                    this.setState({ loading: false });
                }
            });
        } else {
            this.setState({ loading: false });
        }
    }

    handleChange(e) {
        if (e.target.getAttribute("id") === "name") {
            this.state.vendor["name"] = e.target.value;
        }

        this.setState({ operator: this.state.operator });
    }

    handleSubmit(e) {
        e.preventDefault();
        const headers = new Headers();
        headers.append("Content-Type", "application/json");
        headers.append("Accept", "application/json");
        headers.append("ENV", this.props.match.params.env);
        const body = JSON.stringify(this.state.vendor);
        if (this.props.match.params.id != null) {
            fetch("/api/vendors/" + this.props.match.params.id, {
                credentials: 'same-origin',
                method: "PUT",
                headers: headers,
                body: body
            }).then(res => {
                if (res.status === 200) {
                    res.json().then(data => {
                        this.setState({ fireRedirect: true });
                    });
                } else {
                    res.json().then(data => {
                        console.log(data);
                        this.setState({ errorMsg: "Something went wrong. Please check your input again." });
                    })
                }
            });
        } else {
            fetch("/api/vendors", {
                credentials: 'same-origin',
                method: "POST",
                headers: headers,
                body: body
            }).then(res => {
                if (res.status === 201) {
                    res.json().then(data => {
                        this.setState({ fireRedirect: true });
                    });
                } else {
                    res.json().then(data => {
                        console.log(data);
                        this.setState({ errorMsg: "Something went wrong. Please check your input again." });
                    })
                }
            });
        }
    }

    render() {
        const loading = this.state.loading;
        const fireRedirect = this.state.fireRedirect;
        const errorMsg = this.state.errorMsg;
        if (loading) {
            return(<LoadingIcon/>);
        } else {
            return (
                <div>
                    {errorMsg.length > 0 ?
                        (<div className="alert alert-danger alert-dismissible fade show" role="alert">
                            {errorMsg}
                            <button onClick={e => this.setState({errorMsg: ""})} type="button" className="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>) :
                        (null)
                    }
                    <form onSubmit={e => this.handleSubmit(e)}>
                        <div className="form-group">
                            <label htmlFor="name">Name</label>
                            <input id="name" value={this.state.vendor.name}
                                   onChange={e => this.handleChange(e)} type="text" maxLength="50"
                                   className="form-control" placeholder="Name" required/>
                        </div>
                        <button type="submit" className="btn btn-primary btn-lg btn-block">Submit</button>
                    </form>
                    {fireRedirect && (
                        <Redirect to={"/"+this.props.match.params.env+"/vendors"}/>
                    )}
                </div>
            );
        }
    }
}

export default VendorForm;