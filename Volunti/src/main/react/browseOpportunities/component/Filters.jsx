"use strict";
const React = require('react');

const Filters = React.createClass({

    loadDataFromServer: function () {
        $.ajax({
            url: "/api/opportunities/filters",
            cache: false,
            type: "GET",
            success: function (response) {
                console.log(response.userLocation);
                var userSkill = (response.userSkills ==='') ? 'Skill' : response.userSkills;
                var userLocation = (response.userLocation === '') ? 'Location' : response.userLocation;



                this.setState({
                    userSkill: userSkill,
                    userLocation: userLocation,
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
        return {skills: [], locations: [], categories: [], userLocation:'', userSkill:''};
    },

    componentDidMount: function () {
        this.loadDataFromServer();
    },

    handleChange: function (e) {
        if (e.target.id == 'skills') { this.setState({ userSkill: e.target.value }); }
        if (e.target.id == 'location') { this.setState({ userLocation: e.target.value }); }

        this.props.onFilterChange(e);
    },

    render: function() {
        return(
            <div>
                <input type="date" ref="from" id="from" onChange={this.handleChange}/>

                <input type="date" ref="to" id="to" onChange={this.handleChange}/>

                <select value={this.state.userSkill} ref="skills" id="skills" onChange={this.handleChange}>
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

                <select value={this.state.userLocation} ref="location" id="location" onChange={this.handleChange}>
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
            </div>
        )
    }
});

export default Filters;
