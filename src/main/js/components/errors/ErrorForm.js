'use strict';

import React, {Component} from "react";
import LoadingIcon from "../loadingIcon/LoadingIcon";
import {Redirect} from "react-router-dom";

class ClientForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: true,
            fireRedirect: false,
            errorMsg: "",
            client: {
                "appVirtualDir": "",
                "appParentDir": "",
                "appName": "",
                "domains": "",
                "forcePrimaryDomain": false,
                "clientClasses": null,
                "contentTemplate": "",
                "siteId": 0,
                "devNumber": 0,
                "datasourceName": null,
                "datasourceReportName": null,
                "databaseName": null,
                "clientName": "",
                "clientAddress": "",
                "clientLogo": "",
                "clientPhone": "",
                "clientFax": null,
                "status": 0,
                "createdDate": "",
                "updatedDate": "",
                "createdUserId": 1,
                "updatedUserId": 1,
                "version": null
            }
        }
    }

    componentDidMount() {
        if (this.props.match.params.id != null) {
            const headers = new Headers();
            headers.append("Content-Type", "application/json");
            headers.append("ENV", this.props.match.params.env);
            fetch("/api/clients/" + this.props.match.params.id, {
                method: "GET",
                accept: "application/json",
                headers: headers,
            }).then(res => {
                if (res.status === 200) {
                    res.json().then(data => {
                        delete data.id;
                        this.setState({ client: data });
                        this.setState({ loading: false });
                    });
                } else {
                    console.log("SOMETHING WENT WRONG");
                    this.setState({ loading: false });
                }
            });
        } else {
            this.setState({ loading: false });
        }
    }

    handleChange(e) {
        if (e.target.getAttribute("id") === "appVirtualDir") {
            this.state.client["appVirtualDir"] = e.target.value;
        } else if (e.target.getAttribute("id") === "appParentDir") {
            this.state.client["appParentDir"] = e.target.value;
        } else if (e.target.getAttribute("id") === "appName") {
            this.state.client["appName"] = e.target.value;
        } else if (e.target.getAttribute("id") === "domains") {
            this.state.client["domains"] = e.target.value;
        } else if (e.target.getAttribute("id") === "forcePrimaryDomain") {
            this.state.client["forcePrimaryDomain"] = e.target.type === 'checkbox' ? e.target.checked : false;
        } else if (e.target.getAttribute("id") === "contentTemplate") {
            this.state.client["contentTemplate"] = e.target.value;
        } else if (e.target.getAttribute("id") === "siteId") {
            this.state.client["siteId"] = e.target.value;
        } else if (e.target.getAttribute("id") === "devNumber") {
            this.state.client["devNumber"] = e.target.value;
        } else if (e.target.getAttribute("id") === "datasourceName") {
            this.state.client["datasourceName"] = e.target.value;
        } else if (e.target.getAttribute("id") === "datasourceReportName") {
            this.state.client["datasourceReportName"] = e.target.value;
        } else if (e.target.getAttribute("id") === "databaseName") {
            this.state.client["databaseName"] = e.target.value;
        } else if (e.target.getAttribute("id") === "clientName") {
            this.state.client["clientName"] = e.target.value;
        } else if (e.target.getAttribute("id") === "clientClasses") {
            this.state.client["clientClasses"] = e.target.value;
        } else if (e.target.getAttribute("id") === "clientAddress") {
            this.state.client["clientAddress"] = e.target.value;
        } else if (e.target.getAttribute("id") === "clientLogo") {
            this.state.client["clientLogo"] = e.target.value;
        } else if (e.target.getAttribute("id") === "clientPhone") {
            this.state.client["clientPhone"] = e.target.value;
        } else if (e.target.getAttribute("id") === "clientFax") {
            this.state.client["clientFax"] = e.target.value;
        } else if (e.target.getAttribute("id") === "status") {
            this.state.client["status"] = e.target.checked ? 1 : 0;
        }

        this.setState({ client: this.state.client });
    }

    handleSubmit(e) {
        e.preventDefault();
        const headers = new Headers();
        headers.append("Content-Type", "application/json");
        headers.append("Accept", "application/json");
        headers.append("ENV", this.props.match.params.env);
        const body = JSON.stringify(this.state.client);
        if (this.props.match.params.id != null) {
            fetch("/api/clients/" + this.props.match.params.id, {
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
            fetch("/api/clients", {
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
                        <div className="row">
                            <div className="col form-group">
                                <label htmlFor="appVirtualDir">App Virtual Dir</label>
                                <input id="appVirtualDir" value={this.state.client.appVirtualDir}
                                       onChange={e => this.handleChange(e)} type="text" maxLength="50"
                                       className="form-control" placeholder="App Virtual Dir" required/>
                            </div>
                            <div className="col form-group">
                                <label htmlFor="appParentDir">App Parent Dir</label>
                                <input id="appParentDir" value={this.state.client.appParentDir}
                                       onChange={e => this.handleChange(e)} type="text" maxLength="50"
                                       className="form-control" placeholder="App Parent Dir" required/>
                            </div>
                        </div>
                        <div className="form-group">
                            <label htmlFor="appName">App Name</label>
                            <input id="appName" value={this.state.client.appName} onChange={e => this.handleChange(e)}
                                   type="text" maxLength="100"
                                   className="form-control" placeholder="App Name" required/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="domains">Domains</label>
                            <textarea id="domains" value={this.state.client.domains}
                                      onChange={e => this.handleChange(e)} maxLength="1000"
                                      className="form-control" placeholder="Domains" required/>
                        </div>
                        <div className="row">
                            <div className="col form-group">
                                <label htmlFor="siteId">Site ID</label>
                                <input id="siteId" value={this.state.client.siteId} onChange={e => this.handleChange(e)}
                                       type="number"
                                       className="form-control" required/>
                            </div>
                            <div className="col form-group">
                                <label htmlFor="devNumber">Dev Number</label>
                                <input id="devNumber" value={this.state.client.devNumber}
                                       onChange={e => this.handleChange(e)} type="number"
                                       className="form-control" required/>
                                <small className="form-text text-muted">This field has to be unique.</small>
                            </div>
                            <div className="col">
                                <div className="form-group custom-control custom-checkbox">
                                    <input type="checkbox" value={this.state.client.forcePrimaryDomain}
                                           checked={this.state.client.forcePrimaryDomain}
                                           onChange={e => this.handleChange(e)}
                                           className="custom-control-input" id="forcePrimaryDomain"/>
                                    <label className="custom-control-label" htmlFor="forcePrimaryDomain">Force Primary
                                        Domain</label>
                                </div>
                                <div className="form-group custom-control custom-checkbox">
                                    <input type="checkbox" value={this.state.client.status}
                                           checked={this.state.client.status} onChange={e => this.handleChange(e)}
                                           className="custom-control-input" id="status"/>
                                    <label className="custom-control-label" htmlFor="status">Status</label>
                                </div>
                            </div>
                        </div>
                        <div className="form-group">
                            <label htmlFor="clientClasses">Client Classes</label>
                            <textarea id="clientClasses"
                                      value={this.state.client.clientClasses == null ? "" : this.state.client.clientClasses}
                                      onChange={e => this.handleChange(e)} maxLength="1000"
                                      className="form-control" placeholder="Client Classes"/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="contentTemplate">Content Template</label>
                            <input id="contentTemplate" value={this.state.client.contentTemplate}
                                   onChange={e => this.handleChange(e)}
                                   type="text" maxLength="100"
                                   className="form-control" placeholder="Content Template" required/>
                        </div>

                        <div className="form-group">
                            <label htmlFor="datasourceName">Datasource Name</label>
                            <input id="datasourceName"
                                   value={this.state.client.datasourceName == null ? "" : this.state.client.datasourceName}
                                   onChange={e => this.handleChange(e)} type="text" maxLength="100"
                                   className="form-control" placeholder="Datasource Name"/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="datasourceReportName">Datasource Report Name</label>
                            <input id="datasourceReportName"
                                   value={this.state.client.datasourceReportName == null ? "" : this.state.client.datasourceReportName}
                                   onChange={e => this.handleChange(e)} type="text" maxLength="100"
                                   className="form-control" placeholder="Datasource Report Name"/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="databaseName">Database Name</label>
                            <input id="databaseName"
                                   value={this.state.client.databaseName == null ? "" : this.state.client.databaseName}
                                   onChange={e => this.handleChange(e)} type="text" maxLength="100"
                                   className="form-control" placeholder="Database Name"/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="clientName">Client Name</label>
                            <textarea id="clientName" value={this.state.client.clientName}
                                      onChange={e => this.handleChange(e)} maxLength="255"
                                      className="form-control" placeholder="Client Name" required/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="clientAddress">Client Address</label>
                            <textarea id="clientAddress"
                                      value={this.state.client.clientAddress == null ? "" : this.state.client.clientAddress}
                                      onChange={e => this.handleChange(e)} maxLength="255"
                                      className="form-control" placeholder="Client Address" required/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="clientLogo">Client Logo</label>
                            <input id="clientLogo" value={this.state.client.clientLogo}
                                   onChange={e => this.handleChange(e)} type="text" maxLength="100"
                                   className="form-control" placeholder="Client Logo" required/>
                        </div>
                        <div className="row">
                            <div className="col form-group">
                                <label htmlFor="clientPhone">Client Phone</label>
                                <input id="clientPhone" value={this.state.client.clientPhone}
                                       onChange={e => this.handleChange(e)} type="text" maxLength="50"
                                       className="form-control" placeholder="Client Phone" required/>
                            </div>
                            <div className="col form-group">
                                <label htmlFor="clientFax">Client Fax</label>
                                <input id="clientFax"
                                       value={this.state.client.clientFax == null ? "" : this.state.client.clientFax}
                                       onChange={e => this.handleChange(e)} type="text" maxLength="50"
                                       className="form-control" placeholder="Client Fax"/>
                            </div>
                        </div>

                        <button type="submit" className="btn btn-primary btn-lg btn-block">Submit</button>
                    </form>
                    {fireRedirect && (
                        <Redirect to={"/"+this.props.match.params.env}/>
                    )}
                </div>
            );
        }
    }
}

export default ClientForm;