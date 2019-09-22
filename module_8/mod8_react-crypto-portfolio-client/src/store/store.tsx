import {createStore,applyMiddleware,compose, Store, StoreCreator} from "redux";
import thunk from "redux-thunk";
import rootReducer from "../reducers";
import { composeWithDevTools } from "redux-devtools-extension";

export default function configureStore(initialState: any) {

    return createStore(rootReducer,initialState,
        compose(applyMiddleware(thunk)));

};
    