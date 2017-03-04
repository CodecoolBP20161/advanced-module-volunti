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
        this.props.skills.forEach(function(option){
            options.push(<Option option={option}/>)
        });
        return(<div className="col-sm-5">
                    <select title="sort">{options}
                    </select>
                </div>)
    }
});


var App = React.createClass({

    loadSkillsFromServer: function () {
        var self = this;
        $.ajax({
            url: "http://localhost:8080/api/opportunities/filters"
        }).then(function (data) {
            self.setState({skills: data.skills});
        });
    },

    getInitialState: function () {
        return {skills: []};
    },

    componentDidMount: function () {
        this.loadSkillsFromServer();
    },

    render() {
        return ( <Filter skills={this.state.skills}/> );
    }
});


ReactDOM.render(<App />, document.getElementById('react-dropdown-filter'));
