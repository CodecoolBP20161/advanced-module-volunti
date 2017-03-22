"use strict";
const React = require('react');

const Filters = React.createClass({

    handleChange: function (e) {
        this.props.onFilterChange(e);
    },

    render: function() {
        return(
            <div>
                <input type="date" ref="fromDate" onChange={this.handleChange} onBlur={this.handleChange}/>
                <input type="date" ref="toDate" onChange={this.handleChange} onBlur={this.handleChange}/>
                <select ref="skills" onChange={this.handleChange} onBlur={this.handleChange}>
                    <option></option>
                    <option>Programming</option>
                    <option>Cooking</option>
                </select>
                <div>Filter:{this.props.filters}</div>
            </div>
        )
    }
});

export default Filters;