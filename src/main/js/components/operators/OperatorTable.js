'use strict';

import React, {Component} from "react";

class OperatorTable extends Component {

    constructor(props) {
        super(props);
        this.state = {
            operators: []
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
            fetch("/api/operators/" + id , {
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
        const operators = this.props.operators.sort((a, b) => {
            return a["identifier"].localeCompare(b["identifier"]);
        });
        const keys = ["Name","Identifier","DSN Name","CF URL","Action"];
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
                    {operators.map(operator => {
                        return(
                            <tr key={operator["id"]}>
                                <td>{operator["name"]}</td>
                                <td>{operator["identifier"]}</td>
                                <td>{operator["dsnName"]}</td>
                                <td>{operator["cfUrl"]}</td>
                                <td>
                                    <a href={"/" + env + "/operators/" + operator["id"]} id={operator["id"]} type="button"
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

export default OperatorTable;