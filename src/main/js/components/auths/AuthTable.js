'use strict';

import React, {Component} from "react";

class AuthTable extends Component {

    constructor(props) {
        super(props);
        this.state = {
            auths: []
        }
    }

    handleDelete(e) {
        e.preventDefault();
        const id = e.currentTarget.getAttribute("id");
        const isDelete = confirm("Are you sure you want to delete?");
        const headers = new Headers();
        headers.append("Content-Type", "application/json");
        headers.append("ENV", this.props.env);
        if (isDelete) {
            fetch("/api/auths/" + id , {
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
        const auths = this.props.auths.sort((a, b) => {
            return a["apiKey"].localeCompare(b["apiKey"]);
        });
        const keys = ["API Key","Client Secret","Operator ID","Vendor ID","Action"];
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
                    {auths.map(auth => {
                        return(
                            <tr key={auth["id"]}>
                                <td><a href={"/" + env + "/auths/" + auth["id"] + "/permissions"}>{auth["apiKey"]}</a></td>
                                <td>{auth["clientSecret"]}</td>
                                <td>{auth["operatorId"]}</td>
                                <td>{auth["vendorId"]}</td>
                                <td>
                                    <a href={"/" + env + "/auths/" + auth["id"]} id={auth["id"]} type="button"
                                       className="btn btn-info btn-sm" role="button">
                                        <i className="fa fa-pencil" aria-hidden="true"/>
                                    </a>
                                    <button id={auth["id"]} onClick={e => this.handleDelete(e)} type="button" className="btn btn-danger btn-sm">
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

export default AuthTable;