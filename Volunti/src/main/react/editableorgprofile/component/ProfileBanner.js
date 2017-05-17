import React from 'react'

import TopLabel from './TopLabel'

class ProfileBanner extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            mouseOver: false,
            isEditing: false
        };
        this.savePicture = this.savePicture.bind(this);
    }

    savePicture(e){
        e.preventDefault();
        this.props.savePicture(this.backGroundInput.files[0]);
        if(this.props.hasErrorBackgroundImg) {
            this.props.dismissError();
        }
    }

    toggleEditButton(){
        this.setState({
            mouseOver: !this.state.mouseOver
        })
    }

    changeBackground(){
        $('#inputFile').click();
    }

    renderBackgroundImage() {
        return(
            <div className="right-top-bnr">
                <div className="connect">
                    <form method="POST" encType="multipart/form-data" action="/profile/organisation/saveBackgroundImage">
                        <input className="inputFile" id="inputFile" ref={(input) => this.backGroundInput = input}
                               onChange={this.savePicture} type="file" required="required" name="file" accept=".png,.jpg"/>
                    </form>

                    {(this.state.mouseOver || this.props.hasErrorBackgroundImg) ?
                        <a type="submit" onClick={this.changeBackground}>Change background</a> :
                        <i className="fa fa-camera fa-2" aria-hidden="true"> </i>}
                </div>
                {this.props.hasErrorBackgroundImg &&
                <div className="alert alert-error alert-dismissible" role="alert">
                    <button type="button" className="close" onClick={this.props.dismissError}>&times;</button>
                    <strong>Upload failed! </strong>{this.props.errorMessage}</div>
                }
            </div>
        )
    }

    render(){
        return (
            <div className="profile-bnr" style={this.props.divStyle}
                 onMouseEnter={() => this.toggleEditButton()}
                 onMouseLeave={() => this.toggleEditButton()}>
                <div className="container">

                    {this.renderBackgroundImage()}
                    {/*<!-- User Info -->*/}
                    <TopLabel
                        organisationName={this.props.organisationName}
                        category={this.props.category}
                        address={this.props.address}
                        social={this.props.social}
                        saveSocial={(value, newSelected) => this.props.saveSocial(value, newSelected)}
                        saveState={() => this.props.saveState}
                        selectedSocial={this.props.selectedSocial}
                    />

                </div>
            </div>
        )
    }
}

export default ProfileBanner
