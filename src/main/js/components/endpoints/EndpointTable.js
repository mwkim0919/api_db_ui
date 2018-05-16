'use strict';

import React, {Component} from "react";

class EndpointTable extends Component {

    constructor(props) {
        super(props);
    }

    handleDelete(e) {
        e.preventDefault();
        const id = e.currentTarget.getAttribute("id");
        const isDelete = confirm("Are you sure you want to delete?");
        const headers = new Headers();
        headers.append("Content-Type", "application/json");
        headers.append("ENV", this.props.env);
        if (isDelete) {
            fetch("/api/endpoints/" + id , {
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
        const endpoints = this.props.endpoints.sort((a, b) => {
            return a["name"].localeCompare(b["name"]);
        });
        const keys = ["HTTP Method","Endpoint","Action"];
        const env = this.props.env;
        return(
            <div>
                <table className="table table-striped">
                    <thead>
                    <tr>
                        {keys.map(key => {
                            return(<th key={key} scope="col">{key}</th>);
                        })}
                    </tr>
                    </thead>
                    <tbody>
                    {endpoints.map(endpoint => {
                        const endpointNameArr = endpoint["name"].split(" ");
                        let method;
                        if (endpointNameArr[0] === "GET") {
                            method = <span className="badge badge-primary">{endpointNameArr[0]}</span>;
                        } else if (endpointNameArr[0] === "POST") {
                            method = <span className="badge badge-success">{endpointNameArr[0]}</span>
                        } else if (endpointNameArr[0] === "PUT") {
                            method = <span className="badge badge-warning">{endpointNameArr[0]}</span>
                        } else if (endpointNameArr[0] === "DELETE") {
                            method = <span className="badge badge-danger">{endpointNameArr[0]}</span>
                        } else if (endpointNameArr[0] === "PATCH") {
                            method = <span className="badge badge-info">{endpointNameArr[0]}</span>
                        }
                        return (
                            <tr key={endpoint["id"]}>
                                <td>{method}</td>
                                <td id="endpoint-td">{endpointNameArr[1]}</td>
                                <td>
                                    <a href={"/" + env + "/endpoints/" + endpoint["id"]} id={endpoint["id"]} type="button"
                                       className="btn btn-info btn-sm" role="button">
                                        <i className="fa fa-pencil" aria-hidden="true"/>
                                    </a>
                                    <button id={endpoint["id"]} onClick={e => this.handleDelete(e)} type="button" className="btn btn-danger btn-sm">
                                        <i className="fa fa-trash" aria-hidden="true"/>
                                    </button>
                                </td>
                            </tr>
                        );
                    })}
                    </tbody>
                </table>
            </div>
        );
    }
}

export default EndpointTable;