import {combineReducers} from "redux";
import { portfolioReducer, portfolioTransactionsReducer, supportReducer} from "./portfolioReducer";
import {PortfolioState, TransactionsState, SupportState} from '../actions/types'

export interface ApplicationState {
    portfolio : PortfolioState;
    portfolioTransactions : TransactionsState;
    support: SupportState
}

export default combineReducers<ApplicationState>({
    portfolio:portfolioReducer,
    portfolioTransactions: portfolioTransactionsReducer,
    support: supportReducer
});


