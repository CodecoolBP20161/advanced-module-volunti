"use strict";
const React = require('react');
const Main = require('./Main');

var Filters = React.createClass({
    getInitialState: function() {
        return {
            skills: '',
            fromDate: '',
            toDate: ''
        };
    },

    componentWillUpdate: function (nextProps, nextState) {
        console.log('will update');
        console.log(nextState);

        Main.loadDataFromServer();
    },


    handleChange: function () {
        console.log('before ');
        console.log(this.state)

        this.setState({
        skills: this.refs.skills.value,
          fromDate: this.refs.fromDate.value,
          toDate: this.refs.toDate.value
      });
        console.log('after ');
        console.log(this.state)
    },

    render: function() {
        console.log('render');
        console.log(this.state)
        return(
            <div>
                <input type="date" ref="fromDate" onChange={this.handleChange} onBlur={this.handleChange}/>
                <input type="date" ref="toDate" onChange={this.handleChange} onBlur={this.handleChange}/>
                <select ref="skills" onChange={this.handleChange} onBlur={this.handleChange}>
                    <option></option>
                    <option>First</option>

                </select>
            </div>
        )
    }
});

module.exports = Filters;