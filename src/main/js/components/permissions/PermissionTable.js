'use strict';

import React, {Component} from "react";

class PermissionTable extends Component {

    constructor(props) {
        super(props);
    }

    handleEdit(e) {
        e.preventDefault();
        const id = e.currentTarget.getAttribute("id");
        const target = this.props.permissions.filter(permission => {
            return permission["id"] == id;
        });
        const body = {
            "apiAuthId": target[0]["apiAuthId"],
            "endPoint": target[0]["endPoint"],
            "active": !target[0]["active"]
        };
        const headers = new Headers();
        headers.append("Content-Type", "application/json");
        headers.append("ENV", this.props.env);
        fetch("/api/permissions/" + id, {
            credentials: 'same-origin',
            headers: headers,
            method: "PUT",
            body: JSON.stringify(body),
            accept: "application/json"
        }).then(res => {
            if (res.status === 200) {
                res.json().then(data => {
                    this.props.handleEdit(data);
                });
            } else {
                console.log(res);
            }
        });
    }

    handleDelete(e) {
        e.preventDefault();
        const id = e.currentTarget.getAttribute("id");
        const isDelete = confirm("Are you sure you want to delete?");
        const headers = new Headers();
        headers.append("Content-Type", "application/json");
        headers.append("ENV", this.props.env);
        if (isDelete) {
            fetch("/api/permissions/" + id , {
                credentials: 'same-origin',
                method: "DELETE",
                headers: headers
            }).then(res => {
                if (res.status === 204) {
                    this.props.handleDelete(id);
                }
            });
        }
    }

    render() {
        const permissions = this.props.permissions.sort((a, b) => {
            return a["endPoint"]["name"].localeCompare(b["endPoint"]["name"]);
        });
        const keys = ["Method","URI","Status","Action"];
        return(
            <div className="">
                <table className="table table-striped">
                    <thead>
                    <tr>
                        {keys.map(key => {
                            return(<th id={key} key={key} scope="col">{key}</th>);
                        })}
                    </tr>
                    </thead>
                    <tbody>
                    {permissions.map(permission => {
                        const nameArray = permission["endPoint"]["name"].split(" ");
                        let method;
                        let status;
                        if (nameArray[0] === "GET") {
                            method = <span className="badge badge-primary">{nameArray[0]}</span>;
                        } else if (nameArray[0] === "POST") {
                            method = <span className="badge badge-success">{nameArray[0]}</span>
                        } else if (nameArray[0] === "PUT") {
                            method = <span className="badge badge-warning">{nameArray[0]}</span>
                        } else if (nameArray[0] === "DELETE") {
                            method = <span className="badge badge-danger">{nameArray[0]}</span>
                        } else if (nameArray[0] === "PATCH") {
                            method = <span className="badge badge-info">{nameArray[0]}</span>
                        }
                        if (permission["active"]) {
                            status = <span className="badge badge-success">Allowed</span>
                        } else {
                            status = <span className="badge badge-danger">Denied</span>
                        }
                        return(
                            <tr key={permission["id"]}>
                                <td id="method">{method}</td>
                                <td id="uri">{nameArray[1]}</td>
                                <td id="status">{status}</td>
                                <td>
                                    {permission["active"] ?
                                        <button id={permission["id"]} onClick={e=> this.handleEdit(e)} type="button" className="btn btn-danger btn-sm">
                                            <i className="fa fa-ban" aria-hidden="true"/>
                                        </button> :
                                        <button id={permission["id"]} onClick={e=> this.handleEdit(e)} type="button" className="btn btn-success btn-sm">
                                            <i className="fa fa-check" aria-hidden="true"/>
                                        </button>
                                    }
                                    <button id={permission["id"]} onClick={e => this.handleDelete(e)} type="button" className="btn btn-danger btn-sm">
                                        <i className="fa fa-trash" aria-hidden="true"/>
                                    </button>
                                </td>
                            </tr>
                        )
                    })}
                    </tbody>
                </table>
            </div>
        );
    }
}

export default PermissionTable;