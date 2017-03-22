"use strict";
const React = require('react');

const Table = React.createClass({
    render: function () {
        let rows = [];
        this.props.opportunities.map(function(opportunity, i) {
            rows.push(<tr key={i}>
                        <td>
                            <h6>{opportunity.title}</h6>
                        </td>
                        <td>
                            <h6>{opportunity.dateAvailabilityTo}</h6>
                            <h6>{opportunity.availabilityFrom}</h6>
                        </td>
                        <td>
                            <h6>{opportunity.country}</h6>
                        </td>
                            <h6>{opportunity.name}</h6>
                        <td>
                            <h6>{opportunity.category}</h6>
                        </td>
                        {/*<td>{opportunity.id}</td>*/}
                    </tr>);
        });

        return(
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
        </div>
        )
    },

});

export default Table;