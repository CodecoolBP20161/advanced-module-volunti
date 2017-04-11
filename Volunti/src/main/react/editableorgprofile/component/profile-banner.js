import React from 'react'

import TopLabel from './top-label'

class ProfileBanner extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            mouseOver: false,
            isEditing: false
        }
    }
    savePicture(e){
        e.preventDefault();
        this.props.savePicture(this.backGroundInput.files[0]);
        console.log('forceUpdate');
    }
    toggleEditButton(){
        this.setState({
            mouseOver: !this.state.mouseOver
        })
    }
    toggleEditMode(){
        this.setState({
            isEditing: !this.state.isEditing
        })
    }

    render(){
        return (
            <div className="profile-bnr" style={this.props.divStyle} key={this.props.key} onMouseEnter={() => this.toggleEditButton()} onMouseLeave={() => this.toggleEditButton()}>
                <div className="container">
                    {this.state.isEditing &&
                    <div className="btn-group btn-group-justified pull-right col-xs-12">
                        <form method="POST" encType="multipart/form-data" action="/profile/organisation/saveBackgroundImage">
                            <input className="btn btn-default btn-sm col-xs-6" ref={(input) => this.backGroundInput = input} type="file" required="required" name="file" />
                            <input className="btn btn-default btn-sm col-xs-6" type="submit" onClick={(e) => this.savePicture(e)} value="upload" />
                        </form>
                    </div>}
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
                    {/*<!-- Place of Top Right Buttons -->*/}
                </div>
                { this.state.mouseOver &&
                    <button type="submit" className="btn btn-default btn-sm col-xs-12"
                            onClick={() => this.toggleEditMode()}>{this.state.isEditing? 'Done': 'Edit'}</button>
                }
            </div>
        )
    }
}

export default ProfileBanner
