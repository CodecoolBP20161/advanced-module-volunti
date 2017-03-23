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
        }
    },

    loadDataFromServer: function(e) {
        const self = this;

        var newObj = {};
        newObj[e.target.id] = e.target.value;

        var newState = update(this.state.filters, {
                $merge: newObj
        });
        self.setState({opportunities: [], filters: newState});

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
            type: "GET",
            success: function (response) {
                console.log(response.result);
                self.setState({opportunities: response.result});
            },
            error: function(msg) {
                console.log(msg);
            }
        });
    },

    render: function () {
        return(
            <div>
                <button value="hah" onClick={this.loadDataFromServer}>Fuck</button>
                <Filters filters={this.state.filters} onFilterChange={this.loadDataFromServer}
                />
                <Table opportunities={this.state.opportunities}/>
            </div>
        )
    }
});

ReactDOM.render(<Main/>, document.getElementById("react-dropdown-filter"));