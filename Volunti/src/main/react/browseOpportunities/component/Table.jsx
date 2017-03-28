"use strict";
const React = require('react');

const Table = React.createClass({

    handleClick: function (e) {
        this.props.onChange(e);
    },

    render: function () {

        let rows = [];
        this.props.opportunities.map(function(opportunity, i) {

            let skills = [];
            opportunity.name.map(function(skill, j) {
                skills.push(<h6 key={j}>{skill}</h6>)
            });

            rows.push(<tr key={i}>
                        <td>
                            <h6>{opportunity.title}</h6>
                        </td>
                        <td>
                            <h6>{opportunity.availabilityFrom}</h6>
                            <h6>{opportunity.dateAvailabilityTo}</h6>
                        </td>
                        <td>
                            <h6>{opportunity.country}</h6>
                        </td>
                        <td>{skills}</td>
                        <td>
                            <h6>{opportunity.category}</h6>
                        </td>
                        {/*<td><button className="btn mb20 btn-small btn-transparent-primary">
                        <a href={'/opportunities/' + opportunity.id}>View</a></td>*/}
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