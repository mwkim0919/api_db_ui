'use strict';

import React, {Component} from "react";

class ClientTable extends Component {

    constructor(props) {
        super(props);
        this.state = {
            clients: []
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
            fetch("/api/clients/" + id , {
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
        const clients = this.props.clients.sort((a, b) => {
            return a["devNumber"] - b["devNumber"];
        });
        const keys = ["App Virtual Dir","App Parent Dir","App Name","Domains","Dev Number","Action"];
        const env = this.props.env;
        return(
            <div className="table-area">
                <table className="responsive-table table">
                    <thead>
                    <tr>
                        {keys.map(key => {
                            return(<th key={key} scope="col">{key}</th>);
                        })}
                    </tr>
                    </thead>
                    <tbody>
                    {clients.map(client => {
                        return(
                            <tr key={client["id"]}>
                                <td>{client["appVirtualDir"]}</td>
                                <td>{client["appParentDir"]}</td>
                                <td>{client["appName"]}</td>
                                <td>{client["domains"]}</td>
                                <td>{client["devNumber"]}</td>
                                <td>
                                    <a href={env + "/clients/" + client["id"]} id={client["id"]} type="button"
                                       className="btn btn-info btn-sm" role="button">
                                        <i className="fa fa-pencil" aria-hidden="true"/>
                                    </a>
                                    <button id={client["id"]} onClick={e => this.handleDelete(e)} type="button" className="btn btn-danger btn-sm">
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

export default ClientTable;