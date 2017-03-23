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
            filters: {from: '1999-10-10', to: '2022-10-10', skills: ''},
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
            url: "http://localhost:8080/api/opportunities/find/1",
            data: {
                "from": this.state.filters.from,
                "to": this.state.filters.to,
                "location": null,
                "skills": this.state.filters.skills,
                "category": null,
                "pageSize": 10
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