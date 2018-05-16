'use strict';

import React, {Component} from "react";
import LoadingIcon from "../loadingIcon/LoadingIcon";
import EndpointTable from "./EndpointTable";

class EndpointPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: true,
            endpoints: [],
            message: ""
        };
    }

    componentDidMount() {
        const headers = new Headers();
        headers.append("Content-Type", "application/json");
        headers.append("ENV", this.props.match.params.env);
        fetch("/api/endpoints", {
            credentials: 'same-origin',
            method: "GET",
            accept: "application/json",
            headers: headers,
        }).then(res => {
            if (res.status === 200) {
                res.json().then(data => {
                    this.setState({ endpoints: data });
                    this.setState({ loading: false });
                });
            } else {
                res.json().then(data => {
                    this.setState({ message: data["message"]});
                });
                this.setState({ loading: false });
            }
        });
    }

    handleSubmit(data) {
        this.setState(prevState => {
            this.setState({ endpoints: [...prevState, data] });
        });
    }

    // handleEdit(data) {
    //     this.setState((prevState) => ({
    //         client: prevState.endpoints.map(client =>
    //             client["id"] === data["id"] ?
    //                 Object.assign({}, client,
    //                     {
    //
    //                     }
    //                 ) :
    //                 client
    //         )
    //     }));
    // }

    handleDelete(id) {
        this.setState((prevState) => ({
            endpoints: prevState.endpoints.filter(elem => {return elem["id"] != id})
        }));
        this.setState({ message: "Successfully deleted operator." });
    }

    render() {
        if (this.state.loading) {
            return(
                <LoadingIcon/>
            )
        } else {
            const endpoints = this.state.endpoints;
            const message = this.state.message;
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
                    { this.state && this.state.endpoints && <EndpointTable env={this.props.match.params.env} endpoints={endpoints} handleDelete={this.handleDelete.bind(this)}/> }
                    <a href={"/" + this.props.match.params.env + "/endpoints/add"} className="btn btn-lg btn-success" id="fixedbutton">
                        <i className="fa fa-plus" id="fixedIcon"/>
                    </a>
                </div>
            );
        }
    }
}

export default EndpointPage;