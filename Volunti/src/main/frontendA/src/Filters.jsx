"use strict";
const React = require('react');

const Filters = React.createClass({

    handleChange: function (e) {
        this.props.onFilterChange(e);
    },

    render: function() {
        const filtersToDisplay = this.props.filters;
        return(
            <div>
                <input type="date" ref="from" id="from" onChange={this.handleChange} onBlur={this.handleChange}/>
                <input type="date" ref="to" id="to" onChange={this.handleChange} onBlur={this.handleChange}/>
                <select ref="skills" id="skills" onChange={this.handleChange} onBlur={this.handleChange}>
                    <option></option>
                    <option>Programming</option>
                    <option>Cooking</option>
                </select>
                <div>Filter: {Object.keys(filtersToDisplay).map(function (filter, i) {
                                console.log(filter + ": " + filtersToDisplay[filter]);
                                })
                }</div>
            </div>
        )
    }
});

export default Filters;