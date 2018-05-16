'use strict';

import React, {Component} from "react";
import ReactDOM from "react-dom";
import {BrowserRouter, Route,} from "react-router-dom";
import AuthPage from "./components/auths/AuthPage";
import AuthForm from "./components/auths/AuthForm";
import PermissionPage from "./components/permissions/PermissionPage";
import OperatorPage from "./components/operators/OperatorPage";
import OperatorForm from "./components/operators/OperatorForm";
import VendorPage from "./components/vendors/VendorPage";
import VendorForm from "./components/vendors/VendorForm";
import EndpointPage from "./components/endpoints/EndpointPage";
import EndpointForm from "./components/endpoints/EndpointForm";

const Home = () => {
    return (
        <div>
            <div className="jumbotron">
                <div className="container">
                    <h1 className="display-3">Hello!</h1>
                    <p>This is a custom-made UI for managing MONKEY CONNECT database.</p>
                </div>
            </div>

            <div className="container">
                <div className="row">
                    <div className="col">
                        <h2>Auth</h2>
                        <p>This is where you can see MONKEY CONNECT client information along with the client's permission status.</p>
                    </div>
                    <div className="col">
                        <h2>Endpoints</h2>
                        <p>This section allows you to create/update/delete MONKEY CONNECT endpoints.</p>
                    </div>
                    <div className="col">
                        <h2>Operator</h2>
                        <p>This is where you can manage MONKEY CONNECT operator information which is used for adding new MONKEY CONNECT client.</p>
                    </div>
                    <div className="col">
                        <h2>Vendor</h2>
                        <p>This section allows you to manage MONKEY CONNECT vendor information which is used for adding new MONKEY CONNECT client.</p>
                    </div>
                </div>
            </div>
        </div>
    );
}

class App extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        return (
            <div className="container-fluid">
                <Route exact path="/" component={Home} />
                <Route exact path="/:env/auths" component={AuthPage} />
                <Route exact path="/:env/auths/add" component={AuthForm} />
                <Route exact path="/:env/auths/:id(\d+)" component={AuthForm} />
                <Route exact path="/:env/auths/:id(\d+)/permissions" component={PermissionPage} />
                <Route exact path="/:env/operators" component={OperatorPage} />
                <Route exact path="/:env/operators/add" component={OperatorForm} />
                <Route exact path="/:env/operators/:id(\d+)" component={OperatorForm} />
                <Route exact path="/:env/vendors" component={VendorPage} />
                <Route exact path="/:env/vendors/add" component={VendorForm} />
                <Route exact path="/:env/vendors/:id(\d+)" component={VendorForm} />
                <Route exact path="/:env/endpoints" component={EndpointPage} />
                <Route exact path="/:env/endpoints/add" component={EndpointForm} />
                <Route exact path="/:env/endpoints/:id(\d+)" component={EndpointForm} />
            </div>
        )
    }
}

ReactDOM.render(
    <BrowserRouter>
        <App />
    </BrowserRouter>,
    document.getElementById('react')
)