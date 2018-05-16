'use strict';

import React, {Component} from "react";
import LoadingIcon from "../loadingIcon/LoadingIcon";
import {Redirect} from "react-router-dom";

class EndpointForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: true,
            fireRedirect: false,
            errorMsg: "",
            endpoint: {
                "httpMethod": "GET",
                "name": ""
            }
        }
    }

    componentDidMount() {
        if (this.props.match.params.id != null) {
            const headers = new Headers();
            headers.append("Content-Type", "application/json");
            headers.append("ENV", this.props.match.params.env);
            fetch("/api/endpoints/" + this.props.match.params.id, {
                credentials: 'same-origin',
                method: "GET",
                accept: "application/json",
                headers: headers,
            }).then(res => {
                if (res.status === 200) {
                    res.json().then(data => {
                        delete data.id;
                        const endpointArray = data["name"].split(" ");
                        this.state.endpoint["httpMethod"] = endpointArray[0];
                        this.state.endpoint["name"] = endpointArray[1];
                        this.setState({ endpoint: this.state.endpoint });
                        this.setState({ loading: false });
                    });
                } else {
                    console.log(res);
                    this.setState({ errorMsg: "Could not get the endpoint." });
                    this.setState({ loading: false });
                }
            });
        } else {
            this.setState({ loading: false });
        }
    }

    handleChange(e) {
        if (e.target.getAttribute("id") === "httpMethod") {
            this.state.endpoint["httpMethod"] = e.target.value;
        } else if (e.target.getAttribute("id") === "name") {
            this.state.endpoint["name"] = e.target.value;
        }

        this.setState({ endpoint: this.state.endpoint });
    }

    handleSubmit(e) {
        e.preventDefault();
        const headers = new Headers();
        headers.append("Content-Type", "application/json");
        headers.append("Accept", "application/json");
        headers.append("ENV", this.props.match.params.env);
        const jsonBody = {"name": this.state.endpoint["httpMethod"] + " " + this.state.endpoint["name"]};
        const body = JSON.stringify(jsonBody);
        if (this.props.match.params.id != null) {
            fetch("/api/endpoints/" + this.props.match.params.id, {
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
            fetch("/api/endpoints", {
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
            const methods = ["GET","POST","PUT","PATCH","DELETE"];
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
                                <label htmlFor="httpMethod">HTTP Method</label>
                                <select value={this.state.endpoint.httpMethod} onChange={e => {this.handleChange(e)}} id="httpMethod" className="custom-select"
                                        required>
                                    {methods.map(elem => {
                                        return <option key={elem} value={elem}>{elem}</option>
                                    })}
                                </select>
                            </div>
                            <div className="col form-group">
                                <label htmlFor="name">Endpoint</label>
                                <input id="name" value={this.state.endpoint.name}
                                       onChange={e => this.handleChange(e)} type="text" maxLength="50"
                                       className="form-control" placeholder="Endpoint" required/>
                            </div>
                        </div>
                        <button type="submit" className="btn btn-primary btn-lg btn-block">Submit</button>
                    </form>
                    {fireRedirect && (
                        <Redirect to={"/"+this.props.match.params.env+"/endpoints"}/>
                    )}
                </div>
            );
        }
    }
}

export default EndpointForm;