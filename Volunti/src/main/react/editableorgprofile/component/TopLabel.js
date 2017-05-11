import React from 'react'
import Social from './Social'

class TopLabel extends React.Component {
    constructor(props) {
        super(props);
    }
    saveSocial(value, newSelected){
        this.props.saveSocial(value, newSelected);

    }
    saveState(){
        this.props.saveState();
    }
  render() {
    return (
        <div className="user-info">
            <h1>{this.props.organisationName} {/*<a data-toggle="tooltip" data-placement="top" title="Verified Member"> <img src="images/icon-ver.png" alt="" /> </a>*/} </h1>
            <h6>{this.props.category}</h6>
            <p>{this.props.address}</p>
            {/*{console.log("social in Toplabel: ", this.props.social)}*/}
            <Social social={this.props.social}
                    selected={this.props.selectedSocial}
                    saveSocial={(value, newSelected) => this.saveSocial(value, newSelected)}
                    socialEditOff={() => this.saveState()}/>
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
