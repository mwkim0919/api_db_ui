'use strict';

import React, {Component} from "react";
import LoadingIcon from "../loadingIcon/LoadingIcon";
import OperatorTable from "./OperatorTable";

class OperatorPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: true,
            operators: [],
            message: ""
        };
    }

    componentDidMount() {
        const headers = new Headers();
        headers.append("Content-Type", "application/json");
        headers.append("ENV", this.props.match.params.env);
        fetch("/api/operators", {
            credentials: 'same-origin',
            method: "GET",
            accept: "application/json",
            headers: headers,
        }).then(res => {
            if (res.status === 200) {
                res.json().then(data => {
                    this.setState({ operators: data });
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
            this.setState({ operators: [...prevState, data] });
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
            operators: prevState.operators.filter(elem => {return elem["id"] != id})
        }));
        this.setState({ message: "Successfully deleted operator." });
    }

    render() {
        if (this.state.loading) {
            return(
                <LoadingIcon/>
            )
        } else {
            const operators = this.state.operators;
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
                    { this.state && this.state.operators && <OperatorTable env={this.props.match.params.env} operators={operators} handleDelete={this.handleDelete.bind(this)}/> }
                    <a href={"/" + this.props.match.params.env + "/operators/add"} className="btn btn-lg btn-success" id="fixedbutton">
                        <i className="fa fa-plus" id="fixedIcon"/>
                    </a>
                </div>
            );
        }
    }
}

export default OperatorPage;