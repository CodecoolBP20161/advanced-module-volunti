import React from 'react';


class TabProfile extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isEditing: false,
            editVideoUrl: false,
            editMission: false,
            editDescription1: false,
            editDescription2: false,
            isOpen: false,
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleVideoInputChange = this.handleVideoInputChange.bind(this);
        this.toggleEdit = this.toggleEdit.bind(this);
        this.openModal = this.openModal.bind(this);
        this.hideModal = this.hideModal.bind(this);
    }

    toggleEditMode(){
        if (this.state.isEditing){
           this.props.saveData();
        }
        this.setState({
            isEditing: !this.state.isEditing
        })
    }

    toggleEdit(event) {
        const listItem = $(event.target).closest("li").get(0).id;
        const isEditing = this.state[listItem];
        if (!this.state.isEditing && !this.state.isOpen) {
            this.setState({
                [listItem]: !isEditing
            });
        }
    }

    openModal() {
        this.setState({
            isOpen: true
        });
    };

    hideModal() {
        this.setState({
            isOpen: false
        });
    };

    handleChange(event) {
        this.props.onChange(event.target.name, event.target.value)
    }

    handleVideoInputChange(event) {
        this.props.changeVideo(event.target.value)
    }

    cancelVideo() {
        this.hideModal();
        this.props.cancelVideo();
    }

    saveVideoData() {
        this.hideModal();
        this.props.saveVideo();
    }

    renderMission() {
        return(
            <li className="row lined" id="editMission" onMouseEnter={this.toggleEdit} onMouseLeave={this.toggleEdit}>
                <div className="col-md-10">
                    {!(this.state.isEditing && this.state.editMission) &&
                    <p>{this.props.mission}</p>
                    }

                    {(this.state.isEditing && this.state.editMission) &&
                    <p>
                        <textarea value={this.props.mission} name="mission" onChange={this.handleChange}/>
                    </p>
                    }
                </div>
                <div  className="edit col-xs-1">
                    {this.state.editMission &&
                    <a type="submit" className="btn btn-small btn-success"
                       onClick={() => this.toggleEditMode()}>
                        {this.state.isEditing ? 'Save': 'Edit'}
                    </a>}
                </div>
            </li>
        )
    }

    renderDescription1() {
        return(
            <li className="row lined" id="editDescription1" onMouseEnter={this.toggleEdit} onMouseLeave={this.toggleEdit}>
                <div className="col-md-10">
                    {!(this.state.isEditing && this.state.editDescription1) &&
                    <p>{this.props.description1}</p>
                    }

                    {(this.state.isEditing && this.state.editDescription1) &&
                    <p>
                        <textarea value={this.props.description1} name="description1" onChange={this.handleChange}/>
                    </p>
                    }
                </div>
                <div  className="edit col-xs-1">
                    {this.state.editDescription1 &&
                    <a type="submit" className="btn btn-small btn-success"
                       onClick={() => this.toggleEditMode()}>
                        {this.state.isEditing ? 'Save': 'Edit'}
                    </a>}
                </div>
            </li>
        )
    }

    renderVideo() {
        const videoURL = this.props.videoURL ? this.props.videoURL :
            <iframe width="560" height="315" src="https://www.youtube.com/embed/leQ8nEcYFOc" frameBorder="0" allowFullScreen></iframe>;
        return(
            <li className="row lined" id="editVideoUrl" onMouseEnter={this.toggleEdit} onMouseLeave={this.toggleEdit}>
                <div className="col-md-10">
                    {videoURL}
                </div>
                <div className="col-xs-2">
                    {this.state.editVideoUrl &&
                    <a type="submit" className="btn btn-small btn-success" data-toggle="modal" data-target="#myModal"
                    onClick={this.openModal}>Change video</a>
                    }
                </div>
            </li>
        )
    }

    renderModal() {
        return(
            <div className="modal" id="myModal" tabIndex="-1" role="dialog">
                <div className="modal-dialog" role="document">
                    <div className="modal-content">
                        <div className="container">
                            <h6><a type="submit" className="close" data-dismiss="modal" aria-label="Close"
                                    onClick={this.cancelVideo}><span aria-hidden="true">&times;</span></a>
                            Add your video to your profile page</h6>

                            <ul className="row">
                                <li className="col-xs-12">
                                    <input type="text" autoFocus="autoFocus" defaultValue={this.props.videoURL}
                                        onChange={this.handleVideoInputChange}/>
                                </li>
                                <li className="col-xs-12">
                                    <button type="submit" className="btn btn-default" data-dismiss="modal"
                                            onClick={this.cancelVideo}>Close</button>
                                    <button type="submit" className="btn btn-primary"
                                            onClick={this.saveVideoData}>Save changes</button>
                                </li>
                            </ul>
                    </div>
                </div>
                </div>
            </div>
        )
    }

    renderDescription2() {
        return(
            <li className="row" id="editDescription2" onMouseEnter={this.toggleEdit} onMouseLeave={this.toggleEdit}>
                <div className="col-md-10">
                    {!(this.state.isEditing && this.state.editDescription2) &&
                    <p>{this.props.description2}</p>
                    }

                    {(this.state.isEditing && this.state.editDescription2) &&
                    <p>
                        <textarea value={this.props.description1} name="description2" onChange={this.handleChange}/>
                    </p>
                    }
                </div>
                <div  className="edit col-xs-1">
                    {this.state.editDescription2 &&
                    <a type="submit" className="btn btn-small btn-success"
                       onClick={() => this.toggleEditMode()}>
                        {this.state.isEditing ? 'Save': 'Edit'}
                    </a>}
                </div>
            </li>
        )
    }

    render() {
        return (
        <div id="profile" className="tab-pane fade in active">
            <div className="profile-main">
                <div className="filter-flower">
                    <div className="row lined">
                        <h5>About the Company</h5>
                    </div>
                </div>

                <div className="profile-in">
                    <ul>
                        {this.renderMission()}

                        {this.renderDescription1()}

                        {this.renderVideo()}

                        {this.renderModal()}

                        {this.renderDescription2()}

                    </ul>
                </div>
            </div>
        </div>
        )
    }
}

export default TabProfile