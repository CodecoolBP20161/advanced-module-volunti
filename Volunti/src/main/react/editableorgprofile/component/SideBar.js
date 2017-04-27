import React from 'react'

class SideBar extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            mouseOver: false,
            isEditing: false,
        }

    }

    toggleEditMode(){
        // if (this.state.isEditing){
        //     this.saveData();
        // }
        this.setState({
            isEditing: !this.state.isEditing
        })
    }

    toggleEditButton(){
        this.setState({mouseOver: !this.state.mouseOver});
    }

    render() {
        return (
        <div className="col-md-4" onMouseEnter={() => this.toggleEditButton()}
             onMouseLeave={() => this.toggleEditButton()}>

            {/*<!-- Company Information -->*/}
            <div className="sidebar">
                <div className="row">
                    <div>
                        <h5 className="main-title">Organisation Information</h5>
                        {(this.state.mouseOver  || this.state.isEditing) &&
                        <button type="submit" className="btn btn-small btn-success" onClick={() => this.toggleEditMode()}>
                            <i className="fa fa-pencil-square-o"/>
                            {this.state.isEditing? 'Done': 'Edit'}</button>
                        }
                    </div>
                </div>

                {!this.state.isEditing &&
                    <div>
                        <div className="sidebar-thumbnail"> <img src={this.props.profilePicture} /> </div>
                        <div className="sidebar-information">
                            <ul className="single-category">
                                <li className="row">
                                    <h6 className="title col-xs-6">Name</h6>
                                    <span className="subtitle col-xs-6">{this.props.name}</span>
                                </li>
                                <li className="row">
                                    <h6 className="title col-xs-6">Category</h6>
                                    <span className="subtitle col-xs-6">{this.props.category}</span>
                                </li>
                                <li className="row">
                                    <h6 className="title col-xs-6">Location</h6>
                                    <span className="subtitle col-xs-6">{this.props.city}, {this.props.country}</span>
                                </li>
                            </ul>
                        </div>
                    </div>
                }

                {this.state.isEditing &&
                <div>
                    <div className="sidebar-thumbnail"> <img src={this.props.profilePicture} /> </div>
                    <div className="sidebar-information">
                        <ul className="single-category">
                            <li className="row">
                                <h6 className="title col-xs-6">Name</h6>
                                <span className="subtitle col-xs-6">{this.props.name}</span>
                            </li>
                            <li className="row">
                                <h6 className="title col-xs-6">Category</h6>
                                <span className="subtitle col-xs-6">{this.props.category}</span>
                            </li>
                            <li className="row">
                                <h6 className="title col-xs-6">Location</h6>
                                <span className="subtitle col-xs-6">{this.props.city}, {this.props.country}</span>
                            </li>
                        </ul>
                    </div>
                </div>
                }

            </div>
        </div>
        );
    }
}
export default SideBar





