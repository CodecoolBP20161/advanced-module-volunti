"use strict";
const React = require('react');
const ReactDOM = require('react-dom');
const Filters = require('./Filters');
const Table = require('./Table');


var Main = React.createClass({

    getInitialState: function () {
        return {
            skills: []
        }
    },

    loadDataFromServer: function() {
        var that = this;

        $.ajax({
            url: '/api/opportunities/filters',

            success: function (response) {
                console.log(response);
                that.setState({
                    skills: response.skills
                });
            }

        })
    },

    render: function () {
       return(
           <div>
               <Filters />
               <Table data={this.loadDataFromServer}/>
           </div>
       )
    }
});

ReactDOM.render(<Main/>, document.getElementById("react-dropdown-filter"));
