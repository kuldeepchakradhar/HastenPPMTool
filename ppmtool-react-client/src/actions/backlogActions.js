import axios from "axios";
import { GET_ERRORS, GET_BACKLOG, GET_PROJECT_TASK, DELETE_PROJECT_TASK } from "./types";


export const addProjectTask = (backlog_id, project_task, history) => async dispatch => {

    try {
        await axios.post(`http://localhost:8080/api/backlog/${backlog_id}`, project_task);
        history.push(`/projectBoard/${backlog_id}`);
        dispatch({
            type: GET_ERRORS,
            payload: {}
        });
    } catch (err) {
        dispatch({
            type: GET_ERRORS,
            payload: err.response.data
        });
    }
};

export const getProjectTask = backlog_id => async dispatch => {

    try {
        const res = await axios.get(`http://localhost:8080/api/backlog/${backlog_id}`);
        dispatch({
            type: GET_BACKLOG,
            payload: res.data
        })
    } catch (err) {
        dispatch({
            type: GET_ERRORS,
            payload: err.response.data
        });
    }
};

export const getProjectTaskById = (backlog_id, pt_id, history) => async dispatch => {
    try {
        const res = await axios.get(`http://localhost:8080/api/backlog/${backlog_id}/${pt_id}`);
        dispatch({
            type: GET_PROJECT_TASK,
            payload:res.data
        });
    } catch (err) {
        history.push("/dashboard");
        // dispatch({            type: GET_ERRORS,            payload: err.response.data        });
    }
};

export const updatedProjectTask = (backlog_id, pt_id, project_task, history) => async dispatch => {
    try {
        await axios.patch(`http://localhost:8080/api/backlog/${backlog_id}/${pt_id}`, project_task);
        history.push(`/projectBoard/${backlog_id}`);
        dispatch({
            type: GET_ERRORS,
            payload: {}
        });
    } catch (err) {
        dispatch({
            type: GET_ERRORS,
            payload: err.response.data
        });
    }
};

export const deleteProjectTask = (backlog_id, pt_id) => async dispatch => {
    if(window.confirm(`do you realy want to delete this proejct ID: ${pt_id}`)){
        await axios.delete(`http://localhost:8080/api/backlog/${backlog_id}/${pt_id}`);
        dispatch({
            type: DELETE_PROJECT_TASK,
            payload: pt_id
        });
    }
}