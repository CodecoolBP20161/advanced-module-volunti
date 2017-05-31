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
            <Social social={this.props.social}
                    selected={this.props.selectedSocial}
                    saveSocial={(value, newSelected) => this.saveSocial(value, newSelected)}
                    socialEditOff={() => this.saveState()}/>
        </div>);
  }
}
export default TopLabel
