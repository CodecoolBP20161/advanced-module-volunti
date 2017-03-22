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

        this.setState({filters: e.target.value});
        // rest

        this.setState({opportunities: ['1', '2', '3']});
        //console.log(this.state.filters);
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
