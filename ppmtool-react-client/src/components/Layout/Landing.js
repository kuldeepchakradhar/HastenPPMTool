import React, { Component } from "react";
import { Link } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";

class Landing extends Component {

    componentDidMount(){
        if(this.props.security.validToken){
            this.props.history.push("/dashboard");
        }
    }
    render() {
        return (
            <div className="landing">
                <div className="light-overlay landing-inner text-dark">
                    <div className="container">
                        <div className="row">
                            <div className="col-md-12 text-center">
                                <h4 className="display-4 mb-4">Personal Project Management Tool</h4>
                                <p className="lead">
                                    Create your account to join active projects or start you own
                        </p>
                                <hr />
                                <Link to="/register" className="btn btn-lg btn-primary mr-2">
                                    Sign Up
                        </Link>
                                <Link to="/login" className="btn btn-lg btn-secondary mr-2">
                                    Login
                        </Link>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        );
    }
}

Landing.propTypes = {
    security: PropTypes.object.isRequired
}
const matStateToProps = state => ({
    security: state.security
})
export default connect(matStateToProps)(Landing);