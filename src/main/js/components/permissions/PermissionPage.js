'use strict';

import React, {Component} from "react";
import LoadingIcon from "../loadingIcon/LoadingIcon";
import PermissionTable from "./PermissionTable";

class PermissionPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: true,
            message: "",
            permissions: [],
            endpoints: [],
            addButtonEnabled: true,
            endpoint: ""
        };
    }

    componentDidMount() {
        const headers = new Headers();
        headers.append("Content-Type", "application/json");
        headers.append("ENV", this.props.match.params.env);
        fetch("/api/auths/" + this.props.match.params.id + "/permissions", {
            credentials: 'same-origin',
            method: "GET",
            accept: "application/json",
            headers: headers,
        }).then(res => {
            if (res.status === 200) {
                res.json().then(data => {
                    this.setState({ permissions: data });
                    this.setState({ loading: false });
                });
            } else {
                res.json().then(data => {
                    this.setState({ message: data["message"] });
                    this.setState({ loading: false });
                });
            }
        });
        fetch("/api/auths/" + this.props.match.params.id + "/missing_endpoints", {
            credentials: 'same-origin',
            method: "GET",
            accept: "application/json",
            headers: headers,
        }).then(res => {
            if (res.status === 200) {
                res.json().then(data => {
                    data.sort((a, b) => {
                        return a["name"].localeCompare(b["name"]);
                    });
                    this.setState({endpoints: data});
                });
            } else {
                res.json().then(data => {
                    this.setState({ message: data["message"] });
                });
            }
        });
    }

    submitPermission(e) {
        e.preventDefault();
        this.setState({ addButtonEnabled: false });
        const headers = new Headers();
        headers.append("Content-Type", "application/json");
        headers.append("ENV", this.props.match.params.env);
        const body = {
            "apiAuthId": this.props.match.params.id,
            "endPoint": this.state.endPoint,
            "active": 0
        };
        this.setState({ endPoint: "" });
        fetch("/api/permissions", {
            credentials: 'same-origin',
            method: "POST",
            accept: "application/json",
            headers: headers,
            body: JSON.stringify(body)
        }).then(res => {
            if (res.status === 201) {
                res.json().then(data => {
                    this.handleSubmit(data);
                    this.setState({message: "Endpoint has been added."});
                    this.setState({ addButtonEnabled: true });
                });
            } else {
                this.setState({ message: "Could not add the endpoint." });
                this.setState({ addButtonEnabled: true });
            }
        });
    }

    handleSubmit(data) {
        this.setState(prevState => {
            this.setState({ permissions: [...prevState.permissions, data] });
        });
        this.setState((prevState) => ({
            endpoints: prevState.endpoints.filter(elem => {
                return elem["id"] != data["endPoint"]["id"];
            })
        }));
    }

    handleChange(e) {
        if (e.target.getAttribute("id") === "endpoint-add") {
            if (e.target.value) {
                this.setState({ endPoint: JSON.parse(e.target.value) });
            } else {
                this.setState({ endPoint: "" });
            }
        }
    }

    handleEdit(data) {
        this.setState((prevState) => ({
            permissions: prevState.permissions.map(permission =>
                permission["id"] === data["id"] ?
                    Object.assign({}, permission,
                        {
                            "id": data["id"],
                            "apiAuthId": data["apiAuthId"],
                            "endPoint": data["endPoint"],
                            "active": data["active"]
                        }
                    ) :
                    permission
            )
        }));
    }

    handleDelete(id) {
        this.setState((prevState) => ({
            permissions: prevState.permissions.filter(elem => {return elem["id"] != id})
        }));
        this.setState((prevState) => ({
            endpoints: [...prevState.endpoints, JSON.parse(endpoint)].sort((a, b) => {
                return a["name"].localeCompare(b["name"]);
            })
        }));
        this.setState({ message: "Delete was successful." });
    }

    addMissingPermissions(e) {
        e.preventDefault();
        const headers = new Headers();
        headers.append("Content-Type", "application/json");
        headers.append("ENV", this.props.match.params.env);
        fetch("/api/auths/" + this.props.match.params.id + "/permissions", {
            credentials: 'same-origin',
            method: "POST",
            accept: "application/json",
            headers: headers,
        }).then(res => {
            if (res.status === 200) {
                window.location = window.location;
                this.setState({ message: "Successfully added all permissions." });
            } else {
                this.setState({ message: "Could not add missing permissions. Please try again." });
            }
        });
    }

    deleteAllPermissions(e) {
        e.preventDefault();
        const isDelete = confirm("Are you sure you want to delete all permissions?");
        if (isDelete) {
            const headers = new Headers();
            headers.append("Content-Type", "application/json");
            headers.append("ENV", this.props.match.params.env);
            fetch("/api/auths/" + this.props.match.params.id + "/permissions", {
                credentials: 'same-origin',
                method: "DELETE",
                accept: "application/json",
                headers: headers,
            }).then(res => {
                if (res.status === 204) {
                    window.location = window.location;
                    this.setState({ message: "Successfully deleted all permissions." });
                } else {
                    this.setState({ message: "Could not delete permissions. Please try again." });
                }
            });
        }
    }

    render() {
        if (this.state.loading) {
            return(
                <LoadingIcon/>
            )
        } else {
            const message = this.state.message;
            const permissions = this.state.permissions;
            const isEnabled = this.state.addButtonEnabled && this.state.endpoint.length > 0;
            return(
                <div>
                    {message.length > 0 ?
                        (<div className="alert alert-warning alert-dismissible fade show" role="alert">
                            {message}
                            <button onClick={e => this.setState({message: ""})} type="button" className="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>) :
                        (null)
                    }
                    <form className="form-inline" onSubmit={e => this.submitPermission(e)}>
                        <label className="sr-only" htmlFor="operator">Endpoints</label>
                        <div className="input-group mb-2 mr-sm-2 mb-sm-0">
                            <select id="endpoint-add" className="custom-select" onChange={e => {this.handleChange(e)}}>
                                <option value="">Select an Endpoint</option>
                                {this.state.endpoints.map(elem => {
                                    return <option key={elem["id"]} value={JSON.stringify(elem)}>{elem["name"]}</option>
                                })}
                            </select>
                        </div>
                        <div className="input-group mb-2 mr-sm-2 mb-sm-0">
                            <button disabled={!isEnabled} type="submit" className="btn btn-primary" id="permission-add-button">Add</button>
                        </div>
                    </form>
                    { this.state && this.state.permissions && <PermissionTable env={this.props.match.params.env} permissions={permissions}
                                                                               handleDelete={this.handleDelete.bind(this)}
                                                                               handleEdit={this.handleEdit.bind(this)}/> }
                    {
                        permissions.length === 0 ?
                            <button onClick={e => this.addMissingPermissions(e)} className="btn btn-lg btn-success" id="fixedbutton">
                                <i className="fa fa-plus" id="fixedIcon"/>
                            </button> :
                            <button onClick={e => this.deleteAllPermissions(e)} className="btn btn-lg btn-danger" id="fixedbutton">
                                <i className="fa fa-trash" id="fixedIcon"/>
                            </button>
                    }
                </div>
            );
        }
    }
}

export default PermissionPage;