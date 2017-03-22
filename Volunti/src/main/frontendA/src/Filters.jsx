"use strict";
const React = require('react');

const Filters = React.createClass({
    componentWillUpdate: function (nextProps, nextState) {
        console.log('will update');
        console.log(nextState);
    },


    handleChange: function (e) {
        this.props.onFilterChange(e);
    },

    render: function() {
        console.log('render');
        console.log(this.state);
        return(
            <div>
                <input type="date" ref="fromDate" onChange={this.handleChange} onBlur={this.handleChange}/>
                <input type="date" ref="toDate" onChange={this.handleChange} onBlur={this.handleChange}/>
                <select ref="skills" onChange={this.handleChange} onBlur={this.handleChange}>
                    <option></option>
                    <option>First</option>
                </select>
                <div>Filter:{this.props.filters}</div>
            </div>
        )
    }
});

export default Filters;