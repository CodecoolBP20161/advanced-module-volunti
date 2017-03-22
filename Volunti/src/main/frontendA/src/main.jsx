"use strict";
const React = require('react');
const ReactDOM = require('react-dom');
import Filters from './Filters';
import Table from './Table';


const Main = React.createClass ({

    getInitialState: function () {
        return {
            opportunities: [],
            filters: ''
        }
    },

    loadDataFromServer: function(e) {
        const self = this;
        self.setState({filters: e.target.value});
        $.ajax({
            url: "http://localhost:8080/api/opportunities/find/1",
            data: {
                "from": '1999-10-10',
                "to": '2022-10-10',
                "location": null,
                "skills": this.state.filters,
                "category": null,
                "pageSize": 10
            },
            type: "GET",
            success: function (response) {
                console.log(response.result);
                self.setState({opportunities: response.result});
            }
        });
    },

    render: function () {
        return(
            <div>
                <button value="hah" onClick={this.loadDataFromServer}>Fuck</button>
                <Filters filters={this.state.filters} onFilterChange={this.loadDataFromServer} />
                <Table opportunities={this.state.opportunities}/>
            </div>
        )
    }
});

ReactDOM.render(<Main/>, document.getElementById("react-dropdown-filter"));