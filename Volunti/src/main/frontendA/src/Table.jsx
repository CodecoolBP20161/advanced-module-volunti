"use strict";
const React = require('react');

const Table = React.createClass({
    render: function () {
        return(
            <div>
                Table: {this.props.opportunities}
            </div>
        )
    }
});

export default Table;