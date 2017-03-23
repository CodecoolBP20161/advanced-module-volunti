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
                <input type="date" ref="from" id="from" onChange={this.handleChange}/>
                <input type="date" ref="to" id="to" onChange={this.handleChange}/>
                <select ref="skills" id="skills" onChange={this.handleChange}>
                    <option></option>
                    <option>Programming</option>
                    <option>Cooking</option>
                </select>
                <select ref="category" id="category" onChange={this.handleChange}>
                    <option></option>
                    <option>TEACHING</option>
                    <option>valami</option>
                </select>
                <select ref="location" id="location" onChange={this.handleChange}>
                    <option></option>
                    <option>Hungary</option>
                    <option>Ibrany</option>
                </select>
                <select ref="pageSize" id="pageSize" onChange={this.handleChange}>
                    <option>10</option>
                    <option>20</option>
                    <option>30</option>
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