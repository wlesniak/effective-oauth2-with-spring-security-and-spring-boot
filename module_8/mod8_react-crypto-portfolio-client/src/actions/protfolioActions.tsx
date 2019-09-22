import axios from "axios";
import {GotPositionsAction, PortfolioState,AddPortfolioTransaction, GotPortfolioTransactionsAction, Transaction, DeletePortfolioTransactions} from "./types";
import { ActionCreator, Dispatch } from "redux";
import { ThunkAction, ThunkDispatch } from "redux-thunk";
import { AuthService } from "../services/AuthService";

const PORTFOLIO_SERVICE_DOMAIN = "http://localhost:8185";

export const getPortfolioPositionsCreator: ActionCreator<
ThunkAction<
    void,
    undefined,
    null,
    GotPositionsAction
    >
> = () => {
    return async (dispatch : Dispatch) => {
        let user = await AuthService.getInstance().getUser();
        if(user && user.access_token != null && !user.expired) {
            const header = {'Authorization': 'Bearer ' +user.access_token};
            const response = await axios.get(PORTFOLIO_SERVICE_DOMAIN+'/portfolio',
            {headers:header});
            const portfolioState : PortfolioState = response.data;
            const gotPositionsAction : GotPositionsAction = {
                type : "GotPositions",
                positions: portfolioState.positions,
                isAuthenticated: true
            };  
            dispatch(gotPositionsAction);
        }
    };
};


export const getPortfolioTransactionsCreator: ActionCreator<
    ThunkAction<
        void,
        undefined,
        null,
        GotPortfolioTransactionsAction
    >
> = (transactionType : string = "") => {
    return async (dispatch : Dispatch) => {
        let user = await AuthService.getInstance().getUser();
        if(user && user.access_token != null && !user.expired) {
            const header = {'Authorization': 'Bearer ' +user.access_token};
            const response = await axios.get(PORTFOLIO_SERVICE_DOMAIN+'/portfolio/transactions/'+transactionType,
            {headers: header});
            const transactionsList : Transaction[] = response.data;
            const gotTransactionsAction : GotPortfolioTransactionsAction = {
                type: 'GotTransactions',
                transactions : transactionsList,
                isAuthenticated : true
            }
            dispatch(gotTransactionsAction)
        } 
    }
}

export const addPortfolioTransactionCreator : ActionCreator<
    ThunkAction<
        void,
        undefined,
        null,
        AddPortfolioTransaction
    >
    > = (transaction: AddPortfolioTransaction) => {
        return async (dispatch :  ThunkDispatch<{},{},any>) => {
            let user = await AuthService.getInstance().getUser();
            if(user) {
                const header = {'Authorization': 'Bearer ' +user.access_token};
                const response = await axios.post(PORTFOLIO_SERVICE_DOMAIN+'/portfolio/transactions', transaction,
                {headers: header});
                dispatch(getPortfolioPositionsCreator());
            }
        }
    }

export const deleteTransactionsCreator : ActionCreator<
    ThunkAction<
        void,
        undefined,
        null,
        DeletePortfolioTransactions
    >
> = (transactionIds : string[]) => {
    return async (dispatch :  ThunkDispatch<{},{},any>) => {
        let user = await AuthService.getInstance().getUser();
        if(user) {
            const header = {'Authorization': 'Bearer ' +user.access_token};
            const response = await axios.delete(PORTFOLIO_SERVICE_DOMAIN+'/portfolio/transactions/',{data: {id: transactionIds},headers: header})
            dispatch(getPortfolioTransactionsCreator());
            dispatch(getPortfolioPositionsCreator());
        }
    }
}
