"use strict";
const React = require('react');
const ReactDOM = require('react-dom');
import Filters from './Filters';
import Table from './Table';
import update from 'react-addons-update';
import Pagination from 'react-js-pagination';


const Main = React.createClass ({

    getInitialState: function() {
        return {
            opportunities: [],
            currentPage: 1,
            filters: {from: '1999-10-10', to: '2022-10-10', skills: '', location:'', category:'', pageSize:10},
            maxPage: 0,

        }
    },

    setFilter: function(e) {
        let newObj = {};
        newObj[e.target.id] = e.target.value;

        let newState = update(this.state.filters, {
            $merge: newObj
        });

        this.setState({opportunities: [], currentPage: 1, filters: newState});
    },


    sendRequest: function() {
        $.ajax({
            url: "/api/opportunities/find/" + this.state.currentPage,
            data: {
                "from": this.state.filters.from,
                "to": this.state.filters.to,
                "location": this.state.filters.location,
                "skills": this.state.filters.skills,
                "category": this.state.filters.category,
                "pageSize": this.state.filters.pageSize
            },
            cache: false,
            type: "GET",
            success: function (response) {
                this.setState({opportunities: response.result, maxPage: response.maxpage});
            }.bind(this),
            error: function (msg) {
                console.log(msg);
            }
        })
    },

    componentDidUpdate: function(prevProps, prevState) {
        if(this.state.filters != prevState.filters || this.state.currentPage != prevState.currentPage) {
            this.sendRequest();
        }
    },

    componentDidMount: function () {
        this.sendRequest();
    },

    handlePageChange(pageNumber) {
        console.log(`active page is ${pageNumber}`);
        this.setState({currentPage: pageNumber});
    },

    render: function () {
        return(
            <div>
                <Filters filters={this.state.filters} onFilterChange={this.setFilter}/>
                <Table opportunities={this.state.opportunities}
                       currentPage={this.state.currentPage}/>

                {this.state.opportunities.length == 0 ? <div>Did not found any match</div> :
                <Pagination className="pagination"
                    activePage={this.state.currentPage}
                    itemsCountPerPage={this.state.filters.pageSize}
                    totalItemsCount={300}
                    pageRangeDisplayed={3}
                    onChange={this.handlePageChange}
                />}
            </div>
        )
    }
});

ReactDOM.render(<Main/>, document.getElementById("react"));


