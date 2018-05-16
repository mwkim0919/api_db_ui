'use strict';

import React, {Component} from "react";
import LoadingIcon from "../loadingIcon/LoadingIcon";
import ClientTable from "./ErrorTable";

class ClientPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: true,
            clients: []
        };
    }

    componentDidMount() {
        const headers = new Headers();
        headers.append("Content-Type", "application/json");
        headers.append("ENV", this.props.match.params.env);
        fetch("/api/clients", {
            method: "GET",
            accept: "application/json",
            headers: headers,
        }).then(res => {
            if (res.status === 200) {
                res.json().then(data => {
                    this.setState({ clients: data });
                    this.setState({ loading: false });
                });
            } else {
                console.log("SOMETHING WENT WRONG");
                this.setState({ loading: false });
            }
        });
    }

    handleSubmit(data) {
        this.setState(prevState => {
            this.setState({ clients: [...prevState, data] });
        });
    }

    handleEdit(data) {
        this.setState((prevState) => ({
            client: prevState.clients.map(client =>
                client["id"] === data["id"] ?
                    Object.assign({}, client,
                        {
                            "appVirtualDir": data["appVirtualDir"],
                            "appParentDir": data["appParentDir"],
                            "appName": data["appName"],
                            "domains": data["domains"],
                            "forcePrimaryDomain": data["forcePrimaryDomain"],
                            "clientClasses": data["clientClasses"],
                            "contentTemplate": data["contentTemplate"],
                            "siteId": data["siteId"],
                            "devNumber": data["devNumber"],
                            "datasourceName": data["datasourceName"],
                            "datasourceReportName": data["datasourceReportName"],
                            "databaseName": data["databaseName"],
                            "clientName": data["clientName"],
                            "clientAddress": data["clientAddress"],
                            "clientLogo": data["clientLogo"],
                            "clientPhone": data["clientPhone"],
                            "clientFax": data["clientFax"],
                            "status": data["status"],
                            "createdDate": data["createdDate"],
                            "updatedDate": data["updatedDate"],
                            "createdUserId": data["createdUserId"],
                            "updatedUserId": data["updatedUserId"],
                            "version": data["version"]
                        }
                    ) :
                    client
            )
        }));
    }

    handleDelete(id) {
        this.setState((prevState) => ({
            clients: prevState.clients.filter(elem => {return elem["id"] != id})
        }));
    }

    render() {
        if (this.state.loading) {
            return(
                <LoadingIcon/>
            )
        } else {
            const clients = this.state.clients;
            return(
                <div>
                    { this.state && this.state.clients && <ClientTable env={this.props.match.params.env} clients={clients} handleDelete={this.handleDelete.bind(this)}/> }
                    <a href={"/" + this.props.match.params.env + "/clients"} className="btn btn-lg btn-success" id="fixedbutton">
                        <i className="fa fa-plus" id="fixedIcon"/>
                    </a>
                </div>
            );
        }
    }
}

export default ClientPage;