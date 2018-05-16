'use strict';

import React, {Component} from "react";
import LoadingIcon from "../loadingIcon/LoadingIcon";
import {Redirect} from "react-router-dom";

class AuthForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: true,
            fireRedirect: false,
            errorMsg: "",
            auth: {
                "apiKey": "",
                "resourceIds": "",
                "scope": "trust,read,write",
                "authorizedGrantTypes": "client_credentials,authorization_code,implicit,password,refresh_token",
                "webServerRedirectUri": null,
                "authorities": "ROLE_USER",
                "accessTokenValidity": 45000,
                "refreshTokenValidity": 45000,
                "additionalInformation": null,
                "autoApprove": null,
                "operatorId": 1,
                "vendorId": 1
            },
            operators: [],
            vendors: []
        }
    }

    componentDidMount() {
        const headers = new Headers();
        headers.append("Content-Type", "application/json");
        headers.append("ENV", this.props.match.params.env);
        if (this.props.match.params.id != null) {
            fetch("/api/auths/" + this.props.match.params.id, {
                credentials: 'same-origin',
                method: "GET",
                accept: "application/json",
                headers: headers,
            }).then(res => {
                if (res.status === 200) {
                    res.json().then(data => {
                        delete data.id;
                        this.setState({ auth: data });
                    });
                } else {
                    console.log(res);
                    this.setState({ errorMsg: "Could not get the auth information." });
                }
            });
        }

        fetch("/api/operators?orderby=dsnname", {
            credentials: 'same-origin',
            method: "GET",
            accept: "application/json",
            headers: headers,
        }).then(res => {
            if (res.status === 200) {
                res.json().then(data => {
                    this.setState({ operators: data });
                });
            } else {
                console.log("SOMETHING WENT WRONG");
            }
        });

        fetch("/api/vendors", {
            credentials: 'same-origin',
            method: "GET",
            accept: "application/json",
            headers: headers,
        }).then(res => {
            if (res.status === 200) {
                res.json().then(data => {
                    this.setState({ vendors: data });
                });
            } else {
                console.log("SOMETHING WENT WRONG");
            }
        });

        this.setState({ loading: false });
    }

    handleChange(e) {
        if (e.target.getAttribute("id") === "apiKey") {
            this.state.auth["apiKey"] = e.target.value;
        } else if (e.target.getAttribute("id") === "resourceIds") {
            this.state.auth["resourceIds"] = e.target.value;
        } else if (e.target.getAttribute("id") === "scope") {
            this.state.auth["scope"] = e.target.value;
        } else if (e.target.getAttribute("id") === "authorizedGrantTypes") {
            this.state.auth["authorizedGrantTypes"] = e.target.value;
        } else if (e.target.getAttribute("id") === "webServerRedirectUri") {
            this.state.auth["webServerRedirectUri"] = e.target.value;
        } else if (e.target.getAttribute("id") === "authorities") {
            this.state.auth["authorities"] = e.target.value;
        } else if (e.target.getAttribute("id") === "accessTokenValidity") {
            this.state.auth["accessTokenValidity"] = e.target.value;
        } else if (e.target.getAttribute("id") === "refreshTokenValidity") {
            this.state.auth["refreshTokenValidity"] = e.target.value;
        } else if (e.target.getAttribute("id") === "additionalInformation") {
            this.state.auth["additionalInformation"] = e.target.value;
        } else if (e.target.getAttribute("id") === "autoApprove") {
            this.state.auth["autoApprove"] = e.target.value;
        } else if (e.target.getAttribute("id") === "operatorId") {
            this.state.auth["operatorId"] = e.target.value;
        } else if (e.target.getAttribute("id") === "vendorId") {
            this.state.auth["vendorId"] = e.target.value;
        }

        this.setState({ client: this.state.client });
    }

    handleSubmit(e) {
        e.preventDefault();
        const headers = new Headers();
        headers.append("Content-Type", "application/json");
        headers.append("Accept", "application/json");
        headers.append("ENV", this.props.match.params.env);
        const body = JSON.stringify(this.state.auth);
        if (this.props.match.params.id != null) {
            fetch("/api/auths/" + this.props.match.params.id, {
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
            fetch("/api/auths", {
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
        const operators = this.state.operators;
        const vendors = this.state.vendors;
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
                        <div className="row">
                            <div className="col form-group">
                                <label htmlFor="apiKey">API Key</label>
                                <input id="apiKey" value={this.state.auth.apiKey == null ? "" : this.state.auth.apiKey}
                                       onChange={e => this.handleChange(e)} type="text" maxLength="50"
                                       className="form-control" placeholder="API Key" required/>
                            </div>
                            <div className="col form-group">
                                <label htmlFor="resourceIds">Resource IDs</label>
                                <input id="resourceIds" value={this.state.auth.resourceIds == null ? "" : this.state.auth.resourceIds}
                                       onChange={e => this.handleChange(e)} type="text" maxLength="50"
                                       className="form-control" placeholder="Resource IDs"/>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col form-group">
                                <label htmlFor="operatorId">Operator ID</label>
                                <select value={this.state.auth.operatorId} onChange={e => {this.handleChange(e)}}
                                        className="form-control" id="operatorId">
                                    {operators.map(operator => {
                                        return (
                                            <option key={operator["id"]} value={operator["id"]}>
                                                {operator["id"] + " - " + operator["dsnName"]}
                                            </option>
                                        );
                                    })}
                                </select>
                            </div>
                            <div className="col form-group">
                                <label htmlFor="vendorId">Vendor ID</label>
                                <select value={this.state.auth.vendorId} onChange={e => {this.handleChange(e)}}
                                        className="form-control" id="vendorId">
                                    {vendors.map(vendor => {
                                        return (
                                            <option key={vendor["id"]} value={vendor["id"]}>
                                                {vendor["id"] + " - " + vendor["name"]}
                                            </option>
                                        );
                                    })}
                                </select>
                            </div>
                        </div>
                        <div className="form-group">
                            <label htmlFor="scope">Scope</label>
                            <input id="scope" value={this.state.auth.scope == null ? "" : this.state.auth.scope}
                                   onChange={e => this.handleChange(e)}
                                   type="text" maxLength="100"
                                   className="form-control" placeholder="Scope"/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="authorizedGrantTypes">Authorized Grant Types</label>
                            <input id="authorizedGrantTypes" value={this.state.auth.authorizedGrantTypes == null ? "" : this.state.auth.authorizedGrantTypes}
                                   onChange={e => this.handleChange(e)} type="text" maxLength="1000"
                                   className="form-control" placeholder="Authorized Grant Types"/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="webServerRedirectUri">Web Server Redirect URI</label>
                            <input id="webServerRedirectUri" value={this.state.auth.webServerRedirectUri == null ? "" : this.state.auth.webServerRedirectUri}
                                   onChange={e => this.handleChange(e)} type="text"
                                   className="form-control" placeholder="Web Server Redirect URI"/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="authorities">Authorities</label>
                            <input id="authorities" value={this.state.auth.authorities == null ? "" : this.state.auth.authorities}
                                   onChange={e => this.handleChange(e)} type="text"
                                   className="form-control" placeholder="Authorities"/>
                        </div>
                        <div className="row">
                            <div className="col form-group">
                                <label htmlFor="accessTokenValidity">Access Token Validity</label>
                                <input id="accessTokenValidity"
                                       value={this.state.auth.accessTokenValidity == null ? 0 : this.state.auth.accessTokenValidity}
                                       onChange={e => this.handleChange(e)} type="number"
                                       className="form-control"/>
                            </div>
                            <div className="col form-group">
                                <label htmlFor="refreshTokenValidity">Refresh Token Validity</label>
                                <input id="refreshTokenValidity"
                                       value={this.state.auth.refreshTokenValidity == null ? 0 : this.state.auth.refreshTokenValidity}
                                       onChange={e => this.handleChange(e)} type="number"
                                       className="form-control"/>
                            </div>
                        </div>
                        <div className="form-group">
                            <label htmlFor="additionalInformation">Additional Information</label>
                            <textarea id="additionalInformation"
                                      value={this.state.auth.additionalInformation == null ? "" : this.state.auth.additionalInformation}
                                      onChange={e => this.handleChange(e)} maxLength="4096"
                                      className="form-control" placeholder="Additional Information"/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="autoApprove">Auto Approve</label>
                            <textarea id="autoApprove"
                                      value={this.state.auth.autoApprove == null ? "" : this.state.auth.autoApprove}
                                      onChange={e => this.handleChange(e)} maxLength="4096"
                                      className="form-control" placeholder="Auto Approve"/>
                        </div>

                        <button type="submit" className="btn btn-primary btn-lg btn-block">Submit</button>
                    </form>
                    {fireRedirect && (
                        <Redirect to={"/"+this.props.match.params.env+"/auths"}/>
                    )}
                </div>
            );
        }
    }
}

export default AuthForm;