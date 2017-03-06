"use strict";
const React = require('react');
const ReactDOM = require('react-dom');


var Option = React.createClass({
    render: function () {
        return (<option>{this.props.option}</option>)
    }
});


var Filter = React.createClass({
    render: function () {
        var options = [];
        this.props.filters.forEach(function(option, i){
            options.push(<Option option={option} key={i}/>)
        });
        return(<div className="col-sm-3">
                    <div className="field custom-select-box">
                        {/*<h5>Filter by: {this.props.label}</h5>*/}
                        <select title="sort">
                            <option defaultValue={this.props.label}>{this.props.label}(optional)</option>
                            {options}</select>
                    </div>
                </div>)
    }
});


var Filters = React.createClass({

    loadDataFromServer: function () {
        var self = this;
        $.ajax({
            url: "http://localhost:8080/api/opportunities/filters"
        }).then(function (data) {
            self.setState({skills: data.skills, locations: data.locations, categories: data.categories});
        });
    },

    getInitialState: function () {
        return {skills: [], locations: [], categories: []};
    },

    componentDidMount: function () {
        this.loadDataFromServer();
    },

    render: function() {
        const filters = [this.state.skills, this.state.locations, this.state.categories];
        const labels = ["Skill", "Location", "Category"];
        const filterList = filters.map((filter, i) =>
            <Filter filters={filter} label={labels[i]} key={i} />
        );

        return ( <div>{filterList}</div> );
    }
});


var Table = React.createClass({

    loadOpportunityFromServer: function () {
        var self = this;
        $.ajax({
            url: "http://localhost:8080/api/opportunities/find/all/1"
        }).then(function (data) {
            self.setState({opportunities: data.result});
        });
    },

    getInitialState: function () {
        return {opportunities: []};
    },

    componentDidMount: function () {
        this.loadOpportunityFromServer();
    },

    render() {
        return ( <OpportunityTable opportunities={this.state.opportunities}/> );
    }
});

var Opportunity = React.createClass({
    getInitialState: function() {
        return {display: true };
    },

    render: function() {
        if (this.state.display==false) return null;
        else return (
            <tr>
                <td>
                    <h4><b>{this.props.opportunity.title}</b></h4>
                </td>
                <td className="col-xs-3 n-p-r">
                    <span><h4><b>{this.props.opportunity.dateAvailabilityTo}</b></h4></span>
                    <span><h4><b>{this.props.opportunity.availabilityFrom}</b></h4></span>
                </td>
                <td>
                    <h4><b>{this.props.opportunity.foodType}</b></h4>
                </td>
                <td>
                    <h4><b>{this.props.opportunity.skills}</b></h4>
                </td>
                <td>
                    <h4><b>{this.props.opportunity.hoursExpected}</b></h4>
                </td>
                <td>
                    <h4><b>{this.props.opportunity.costs}</b></h4>
                </td>
            </tr>
        );
    }
});

var OpportunityTable = React.createClass({
    render: function() {
        var rows = [];
        this.props.opportunities.forEach(function(opportunity, i) {
            rows.push(<Opportunity opportunity={opportunity} key={i} />);
        });
        return (<table className="table-hover" >
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Time frame</th>
                        <th>Location</th>
                        <th>Skills</th>
                        <th>Category</th>
                        <th>View</th>
                    </tr>
                </thead>
                <tbody>{rows}</tbody>
        </table>);
    }
});

ReactDOM.render(<Table />, document.getElementById('react-table'));

ReactDOM.render(<Filters />, document.getElementById('react-dropdown-filter'));
