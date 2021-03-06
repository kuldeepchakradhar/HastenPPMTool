import { combineReducers } from "redux";
import errorReducer from "./errorReducer";
import projectReducers from "./projectReducers";
import backlogReducer from "./backlogReducer";
import securityReducer from "./securityReducer";

export default combineReducers({
    errors: errorReducer,
    project: projectReducers,
    backlog: backlogReducer,
    security: securityReducer 
});