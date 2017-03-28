"use strict";
const React = require('react');
const ReactDOM = require('react-dom');
import Filters from './Filters';
import Table from './Table';
import update from 'react-addons-update';

const Main = React.createClass ({

    getInitialState: function () {
        return {
            opportunities: [],
            filters: {currentPage: 1, from: '1999-10-10', to: '2022-10-10', skills: '', location:'', category:'', pageSize:10},
            maxPage: 0
        }
    },

    loadDataFromServer: function(e) {
        let newObj = {};
        newObj[e.target.id] = e.target.value;
        console.log(e.target.id);
        console.log(e.target.value);

        let newState = update(this.state.filters, {
            $merge: newObj
        });

        this.setState({opportunities: [], filters: newState});
    },

    sendRequest: function() {
        $.ajax({
            url: "/api/opportunities/find/" + this.state.filters.currentPage,
            data: {
                "from": this.state.filters.from,
                "to": this.state.filters.to,
                "location": this.state.filters.location,
                "skills": this.state.filters.skills,
                "category": this.state.filters.category,
                "pageSize": this.state.filters.pageSize
            },
            cache: false,
            type: "GET",
            success: function (response) {
                this.setState({opportunities: response.result, maxPage: response.maxpage});
            }.bind(this),
            error: function (msg) {
                console.log(msg);
            }
        })
    },

    componentDidUpdate: function(prevProps, prevState) {
        if(this.state.filters != prevState.filters) {
            if(!(this.state.filters.currentPage < 1 || this.state.filters.currentPage > this.state.maxPage)){
                this.sendRequest();
            } else {
                this.setState({currentPage: prevState.filters.currentPage});
                console.log(this.state.filters.currentPage);
                console.log(prevState.filters.currentPage);
            }
        }
    },

    componentDidMount: function () {
        this.sendRequest();
    },

    render: function () {
        return(
            <div>
                <Filters filters={this.state.filters} onFilterChange={this.loadDataFromServer}/>
                <Table opportunities={this.state.opportunities}
                       currentPage={this.state.filters.currentPage}
                       onChange={this.loadDataFromServer}/>
            </div>
        )
    }
});

ReactDOM.render(<Main/>, document.getElementById("react"));