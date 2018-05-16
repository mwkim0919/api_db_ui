'use strict';

import React, {Component} from "react";
import LoadingIcon from "../loadingIcon/LoadingIcon";
import AuthTable from "./AuthTable";

class AuthPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: true,
            message: "",
            auths: []
        };
    }

    componentDidMount() {
        const headers = new Headers();
        headers.append("Content-Type", "application/json");
        headers.append("ENV", this.props.match.params.env);
        fetch("/api/auths", {
            credentials: 'same-origin',
            method: "GET",
            accept: "application/json",
            headers: headers,
        }).then(res => {
            if (res.status === 200) {
                res.json().then(data => {
                    this.setState({ auths: data });
                    this.setState({ loading: false });
                });
            } else {
                console.log("SOMETHING WENT WRONG");
                res.json().then(data => {
                    this.setState({ message: data["message"]});
                });
                this.setState({ loading: false });
            }
        });
    }

    handleSubmit(data) {
        this.setState(prevState => {
            this.setState({ auths: [...prevState, data] });
        });
        // this.setState({ message: "Successfully added API credential."});
    }

    // handleEdit(data) {
    //     this.setState((prevState) => ({
    //         auths: prevState.auths.map(auth =>
    //             auth["id"] === data["id"] ?
    //                 Object.assign({}, auth,
    //                     {
    //                         "id": 6,
    //                         "apiKey": "monkey112",
    //                         "resourceIds": "test",
    //                         "clientSecret": "monkey112",
    //                         "scope": "trust,read,write",
    //                         "authorizedGrantTypes": "client_credentials,authorization_code,implicit,password,refresh_token",
    //                         "webServerRedirectUri": null,
    //                         "authorities": "ROLE_USER",
    //                         "accessTokenValidity": "45000",
    //                         "refreshTokenValidity": "45000",
    //                         "additionalInformation": null,
    //                         "autoApprove": null,
    //                         "operatorId": 6,
    //                         "vendorId": 1
    //                     }
    //                 ) :
    //                 auth
    //         )
    //     }));
    // }

    handleDelete(id) {
        this.setState((prevState) => ({
            auths: prevState.auths.filter(elem => {return elem["id"] != id})
        }));
        this.setState({ message: "Successfully deleted API credential."});
    }

    render() {
        if (this.state.loading) {
            return(
                <LoadingIcon/>
            )
        } else {
            const message = this.state.message;
            const auths = this.state.auths;
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
                    { this.state && this.state.auths && <AuthTable env={this.props.match.params.env} auths={auths} handleDelete={this.handleDelete.bind(this)}/> }
                    <a href={"/" + this.props.match.params.env + "/auths/add"} className="btn btn-lg btn-success" id="fixedbutton">
                        <i className="fa fa-plus" id="fixedIcon"/>
                    </a>
                </div>
            );
        }
    }
}

export default AuthPage;