"use strict";
const React = require('react');
const ReactDOM = require('react-dom');

var url = "http://localhost:8080/api/opportunities/find/1?from=2020-10-10&to=1999-10-10&location&skills&category&pageSize=10";
var location = "";
var skill = "";
var category = "";

var updateUrl= function () {
    url = "http://localhost:8080/api/opportunities/find/1?from=2020-10-10&to=1999-10-10&pageSize=10";
    if (location != ""){
        url += "&location=" + location;
    } else {
        url += "&location";
    }
    if (skill != ""){
        url += "&skills=" + skill;
    } else {
        url += "&skills";
    }
    if (category != ""){
        url += "&category=" + category;
    } else {
        url += "&category";
    }
}

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
            <div className="field custom-select-box" id={this.props.label}>
                {/*<h5>Filter by: {this.props.label}</h5>*/}
                <select title="sort" onChange={this.handleChange}>
                    <option defaultValue={this.props.label}>{this.props.label}(optional)</option>
                    {options}</select>
            </div>
        </div>)
    },

    handleChange: function (e) {

        var name = e.target.value;
        //console.log(name);
        //console.log(e.target.parentNode.id);

        var type = e.target.parentNode.id;
        var value = e.target.value;

        switch(type) {
            case "Location":
                location = value;
                if(value.includes("optional")){
                    location = "";
                }
                break;
            case "Skill":
                skill = value;
                if(value.includes("optional")) {
                    skill = "";
                }
                break;

            case "Category":
                category = value;
                if(value.includes("optional")) {
                    category = "";
                }
                break;
            default:
                //console.log(type);
                break;
        };

        updateUrl();
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
            url: url
        }).then(function (data) {
            self.setState({opportunities: data.result});
        });
    },

    updateIfNeeded: function () {
        var isNeeded = this.state.url != url;
        this.state.url = url;
        if (isNeeded) {
            this.loadOpportunityFromServer();
        }
    },

    getInitialState: function () {
        return {opportunities: []};
    },

    componentDidMount: function () {
        this.state.url = url;
        this.loadOpportunityFromServer();
        setInterval(this.updateIfNeeded, 1000);
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
                    <h5><b>{this.props.opportunity.title}</b></h5>
                </td>
                <td className="col-xs-3 n-p-r">
                    <h5><b>{this.props.opportunity.dateAvailabilityTo}</b></h5>
                    <h5><b>{this.props.opportunity.availabilityFrom}</b></h5>
                </td>
                <td>
                    <h5><b>{this.props.opportunity.foodType}</b></h5>
                </td>
                <td>
                    <h5><b>{this.props.opportunity.skills}</b></h5>
                </td>
                <td>
                    <h5><b>{this.props.opportunity.hoursExpected}</b></h5>
                </td>
                <td>
                    <h5><b>{this.props.opportunity.costs}</b></h5>
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
        return (
            <div>
                <table className="table-hover" >
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
                </table>
                <div>
                    {console.log(rows)}
                    {rows.length == 0 ? <div>Empty</div> : null}
                </div>
            </div>
        );
    }
});

ReactDOM.render(<Table />, document.getElementById('react-table'));

ReactDOM.render(<Filters />, document.getElementById('react-dropdown-filter'));
