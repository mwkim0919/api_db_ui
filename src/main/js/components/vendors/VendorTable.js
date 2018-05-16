'use strict';

import React, {Component} from "react";

class VendorTable extends Component {

    constructor(props) {
        super(props);
        this.state = {
            vendors: []
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
            fetch("/api/vendors/" + id , {
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
        const vendors = this.props.vendors.sort((a, b) => {
            return a["name"].localeCompare(b["name"]);
        });
        const keys = ["Name","Action"];
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
                    {vendors.map(operator => {
                        return(
                            <tr key={operator["id"]}>
                                <td>{operator["name"]}</td>
                                <td>
                                    <a href={"/" + env + "/vendors/" + operator["id"]} id={operator["id"]} type="button"
                                       className="btn btn-info btn-sm" role="button">
                                        <i className="fa fa-pencil" aria-hidden="true"/>
                                    </a>
                                    <button id={operator["id"]} onClick={e => this.handleDelete(e)} type="button" className="btn btn-danger btn-sm">
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

export default VendorTable;