"use strict";
const React = require('react');

const Filters = React.createClass({

    loadDataFromServer: function () {
        $.ajax({
            url: "/api/opportunities/filters",
            cache: false,
            type: "GET",
            success: function (response) {
                this.setState({
                    skills: response.skills,
                    locations: response.locations,
                    categories: response.categories
                });
            }.bind(this),
            error: function (msg) {
                console.log(msg);
            }
        })
    },

    getInitialState: function () {
        return {skills: [], locations: [], categories: []};
    },

    componentDidMount: function () {
        this.loadDataFromServer();
    },

    handleChange: function (e) {
        this.props.onFilterChange(e);
    },

    render: function () {
        const filtersToDisplay = this.props.filters;

        return (
            <div>
                <input type="date" ref="from" id="from" onChange={this.handleChange}/>

                <input type="date" ref="to" id="to" onChange={this.handleChange}/>

                <select ref="skills" id="skills" onChange={this.handleChange}>
                    <option value="">Skill</option>
                    {this.state.skills.map(function (skill, index) {
                        return <option key={index}>{skill}</option>
                    })}
                </select>

                <select ref="category" id="category" onChange={this.handleChange}>
                    <option value="">Category</option>
                    {this.state.categories.map(function (category, index) {
                        return <option key={index}>{category}</option>
                    })}
                </select>

                <select ref="location" id="location" onChange={this.handleChange}>
                    <option value="">Location</option>
                    {this.state.locations.map(function (location, index) {
                        return <option key={index}>{location}</option>
                    })}
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