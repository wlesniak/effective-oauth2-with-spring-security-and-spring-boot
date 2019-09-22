import axios from "axios";
import {GotSupportQueriesAction, SupportState, AddQueryAction, SupportQuery} from "./types";
import { ActionCreator, Dispatch } from "redux";
import { ThunkAction, ThunkDispatch } from "redux-thunk";
import { AuthService } from "../services/AuthService";

const SUPPORT_SERVICE_DOMAIN = "http://localhost:8185";
export const getSupportQueriesCreator: ActionCreator<
ThunkAction<
    void,
    undefined,
    null,
    GotSupportQueriesAction
    >
> = () => {
    return async (dispatch : Dispatch) => {
        let user = await AuthService.getInstance().getUser();
        if(user) {
            const header = {'Authorization': 'Bearer ' +user.access_token};
            const response = await axios.get(SUPPORT_SERVICE_DOMAIN+'/support',
            {headers:header});
            const queries : SupportQuery[] = response.data;
            const gotSupportQueriesAction : GotSupportQueriesAction = {
                type : "GotSupportQueries",
                queries: queries
            };  
            dispatch(gotSupportQueriesAction);
        }
    };
};

export const addQueryCreator : ActionCreator<
    ThunkAction<
        void,
        undefined,
        null,
        AddQueryAction
    >
    > = (query: AddQueryAction) => {
        console.log(query);
        
        return async (dispatch :  ThunkDispatch<{},{},any>) => {
            let user = await AuthService.getInstance().getUser();
            if(user) {
                const header = {'Authorization': 'Bearer ' +user.access_token};
                const response = await axios.put(SUPPORT_SERVICE_DOMAIN+'/support', query,
                {headers: header});
                dispatch(getSupportQueriesCreator());
            }
        }
    }