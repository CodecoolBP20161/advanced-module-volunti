import React from 'react';
import ReactDom from 'react-dom';
// {} means that it instantly creates a var for it, so you can use it
import {createStore} from 'redux';
import {Provider} from 'react-redux'
import allReducers from './reducers';
import App from './components/app';

const store = createStore(allReducers);

ReactDom.render(
    <Provider store={ store }>
        <App />
    </Provider>
    , document.getElementById('root'));