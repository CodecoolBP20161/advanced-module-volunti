import React from 'react'
import Social from './toplabel-social'

class TopLabel extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            editMode: false
        }
    }


  render() {
    return (
        <div className="user-info">
            <h1>{this.props.organisationName} {/*<a data-toggle="tooltip" data-placement="top" title="Verified Member"> <img src="images/icon-ver.png" alt="" /> </a>*/} </h1>
            <h6>{this.props.category}</h6>
            <p>{this.props.address}</p>
            <Social social={this.props.social} />
            {/*Stars
            <ul className="row">
                <li className="col-sm-6">
                    <div className="stars"> <i className="fa fa-star"></i> <i className="fa fa-star"></i> <i className="fa fa-star"></i> <i className="fa fa-star"></i> <i className="fa fa-star"></i> <span>(06)</span> </div>
                    </li>
                <li className="col-sm-6">
                    <p><i className="fa fa-bookmark-o"></i> 28 Bookmarks</p>
                    </li>
                </ul>

            Followers
             <div className="followr">
                 <ul className="row">
                     <li className="col-sm-6">
                        <p>Followers <span>(31)</span></p>
                    </li>
                    <li className="col-sm-6">
                        <p>Following <span>(38)</span></p>
                    </li>
                </ul>
            </div>*/}
        </div>);
  }
}
export default TopLabel
