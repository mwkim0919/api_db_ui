'use strict';

import React, {Component} from "react";
import LoadingIcon from "../loadingIcon/LoadingIcon";
import VendorTable from "./VendorTable";

class VendorPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: true,
            vendors: [],
            message: ""
        };
    }

    componentDidMount() {
        const headers = new Headers();
        headers.append("Content-Type", "application/json");
        headers.append("ENV", this.props.match.params.env);
        fetch("/api/vendors", {
            credentials: 'same-origin',
            method: "GET",
            accept: "application/json",
            headers: headers,
        }).then(res => {
            if (res.status === 200) {
                res.json().then(data => {
                    this.setState({ vendors: data });
                    this.setState({ loading: false });
                    console.log(data);
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
            this.setState({ vendors: [...prevState, data] });
        });
    }

    // handleEdit(data) {
    //     this.setState((prevState) => ({
    //         client: prevState.operators.map(client =>
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
            vendors: prevState.vendors.filter(elem => {return elem["id"] != id})
        }));
    }

    render() {
        if (this.state.loading) {
            return(
                <LoadingIcon/>
            )
        } else {
            const vendors = this.state.vendors;
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
                    { this.state && this.state.vendors && <VendorTable env={this.props.match.params.env} vendors={vendors} handleDelete={this.handleDelete.bind(this)}/> }
                    <a href={"/" + this.props.match.params.env + "/vendors/add"} className="btn btn-lg btn-success" id="fixedbutton">
                        <i className="fa fa-plus" id="fixedIcon"/>
                    </a>
                </div>
            );
        }
    }
}

export default VendorPage;