import { Action } from "redux";

interface CryptoCurrency {
  readonly symbol : string;
  readonly name : string;
}

export interface Position {
  readonly cryptoCurrency: CryptoCurrency;
  readonly quantity : number;
  readonly value : number;
}

export interface SupportQuery {
  readonly id: string;
  readonly subject: string;
  readonly username: string;
  readonly resolved: boolean;
  readonly creationTime: string;
  readonly posts: SupportQueryPost[];
}

export interface SupportQueryPost {
  readonly queryId: string;
  readonly content: string;
  readonly resolve: boolean;
}

export interface PortfolioState {
  readonly positions : Position[];
  readonly isAuthenticated?: boolean;
}

export interface AuthenticationState {
  readonly isAuthenticated: boolean;
  readonly username: string;
}

export interface SupportState {
  readonly queries: SupportQuery[];
  readonly isAuthenticated?: boolean;
}

export interface Transaction {
  id: string,
  symbol: string,
  transactionType: string,
  quantity: number,
  price: number
}

export interface TransactionsState {
  readonly transactions: Transaction[]
  readonly isAuthenticated?: boolean;
}

export interface GettingPositionsAction extends Action<'GettingPositions'> {};

export interface GotPositionsAction extends Action<'GotPositions'>{
  positions : Position[];
  isAuthenticated: boolean;
}

export interface GetUserSupportQueriesAction extends Action<'GetUserSupportQueries'> {};

export interface GotSupportQueriesAction extends Action<'GotSupportQueries'> {
  queries: SupportQuery[];
}

export interface AddQueryAction extends Action<'AddQuery'> {
  subject : string;
  content : string;
}

export interface GetPortfolioTransactionsAction extends Action<'GetTransactions'> {
  transactionSymbol ?: string;
}

export interface GotPortfolioTransactionsAction extends Action<'GotTransactions'> {
  transactions: Transaction[];
  isAuthenticated: boolean;
}

export interface AddPortfolioTransaction extends Action<'AddTransaction'> {
  cryptoSymbol: string;
  quantity: number;
  price: number;
  transactionType: string;
}

export interface DeletePortfolioTransactions extends Action<'DeleteTransactions'> {
  transactionIds: string[]
}

export interface RegisterNewUser extends Action<'RegisterNewUser'> {
  firstname: string;
  lastname: string;
  username: string;
  email: string;
  password: string;
  confirmPassword: string;
}

export type PortfolioActions = GettingPositionsAction | GotPositionsAction | AddPortfolioTransaction;
export type PortfolioTransactionsAction = GetPortfolioTransactionsAction | GotPortfolioTransactionsAction;
export type SupportActions = GotSupportQueriesAction | AddQueryAction | GetUserSupportQueriesAction;