import React, { Component } from 'react'
import { Link } from "react-router-dom";
import Backlog from './Backlog';
import { connect } from "react-redux";
import PropTypes from "prop-types";
import { getProjectTask } from "../../actions/backlogActions";


class ProjectBoard extends Component {

    // constructor to handle errors
    constructor(){
        super();
        this.state = {
            errors: {}
        };
    }


    componentWillReceiveProps(nextProps){
        if(nextProps.errors){
            this.setState({errors: nextProps.errors});
        }
    }

    componentDidMount() {
        const { id } = this.props.match.params;
        this.props.getProjectTask(id);
    }
    render() {
        const { project_tasks } = this.props.backlog;
        const { id } = this.props.match.params;
        const { errors } = this.state;

        let BoardContainer;

        const boardAlgorithm = (errors, project_tasks) => {
            if(project_tasks.length < 1){
                if(errors.projectNotFound){
                    return(
                        <div className="alert alert-danger text-center" role="alert">
                            {errors.projectNotFound}
                        </div>
                    );
                }else{
                    return(
                        <div className="alert alert-info text-center" role="alert">
                            No Project Exist With ID: {this.props.match.params.id}
                        </div>
                    );
                }
            }
            else{
                return <Backlog project_task_props = {project_tasks} />;
            }
        };
        
        BoardContainer = boardAlgorithm(errors, project_tasks);
        return (
            <div className="container">
                <Link to={`/addProjectTask/${id}`} className="btn btn-primary mb-3">
                    <i className="fas fa-plus-circle"> Create Project Task</i>
                </Link>
                <br />
                <hr />
            {BoardContainer}
                
            </div>
        );
    }
}

ProjectBoard.propTypes = {
    backlog: PropTypes.object.isRequired,
    getProjectTask: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    backlog: state.backlog,
    errors: state.errors
});

export default connect(mapStateToProps, { getProjectTask })(ProjectBoard);