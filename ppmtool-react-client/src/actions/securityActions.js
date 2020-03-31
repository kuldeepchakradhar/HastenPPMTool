import axios from "axios";
import { GET_ERRORS, SET_CURRENT_USER } from "./types";
import jwt_decode from "jwt-decode";
import setJWTToken from "../securityUtils/setJWTToken";

export const createNewUser = (newUser, history) => async dispatch => {
    try {
        await axios.post("http://localhost:8080/api/user/register", newUser);
        history.push("/login");
        dispatch({
            type: GET_ERRORS,
            payload: {}
        });

    } catch (err) {
        dispatch({
            type: GET_ERRORS,
            payload: err.response.data
        })
    }
};

export const login = (LoginRequest) => async dispatch => {
    try {
        //post to the request
        const res = await axios.post("http://localhost:8080/api/user/login", LoginRequest);
        const { token } = res.data;
        localStorage.setItem("jwtToken", token);

        //setting token in our header
        setJWTToken(token);

        const decodeToken = jwt_decode(token);
        dispatch({
            type: SET_CURRENT_USER,
            payload: decodeToken
        });
    } catch(err) {
        dispatch({
            type:GET_ERRORS,
            payload: err.response.data
        });
    }
};

export const logout = () => dispatch => {
    localStorage.removeItem("jwtToken");
    setJWTToken(false);
    dispatch({
        type: SET_CURRENT_USER,
        payload: {}
    });
};