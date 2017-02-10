
import {combineReducers} from 'redux';
import UserReducer from './reducer_users';

// This file is to combine all our reducers and give them a reference name
const allReducers = combineReducers({
    users: UserReducer
});

export default allReducers;