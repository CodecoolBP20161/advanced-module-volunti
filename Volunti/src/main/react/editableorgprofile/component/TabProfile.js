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
            hasVideoInputError: false,
            isVideoEditEnabled: true
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleVideoChange = this.handleVideoChange.bind(this);
        this.toggleEdit = this.toggleEdit.bind(this);
        this.dismissError = this.dismissError.bind(this)
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
        if (!this.state.isEditing) {
            this.setState({
                [listItem]: !isEditing
            });
        }
    }

    toggleVideoEditMode(){
        if (this.state.isEditing){
            this.props.saveVideo();
        } else {
            this.setState({
                isVideoEditEnabled: false
            })
        }
        this.setState({
            isEditing: !this.state.isEditing,
        });
    }

    handleChange(event) {
        this.props.onChange(event.target.name, event.target.value)
    }

    handleVideoChange(event) {
        if(! /<iframe[^>]+src="http[s]?:\/\/(www\.)?(youtube|player\.vimeo){1}([^"\s]+)"?[^>]*><\/iframe>/g.test(event.target.value)){
            this.setState({hasVideoInputError: true,
                isVideoEditEnabled: false,
                errorMessage: "Not gooooood! Please provide valid embed code."})
        } else {
            this.setState({hasVideoInputError: false,
                isVideoEditEnabled: true
            });
            this.props.changeVideo(event.target.value);
        }
    }

    getAttributes(text) {
        const doc = document.createElement('div');
        doc.innerHTML = text;
        const iframe = doc.getElementsByTagName('iframe')[0];

        const attributes = {};
        [].slice.call(iframe.attributes).forEach(function (element) {
            attributes[element.name] = element.value;
        });

        return attributes
    }

    getVideoCode() {
        let videoURL;
        if(!this.props.videoURL.hasOwnProperty("embedCode")) {
            videoURL = '<iframe src="https://www.youtube.com/embed/leQ8nEcYFOc"></iframe>'
        } else {
            videoURL = this.props.videoURL.embedCode;
        }
        return videoURL;
    }

    cancelVideo() {
        this.setState({
            isEditing: !this.state.isEditing,
            isVideoEditEnabled: true,
            hasVideoInputError: false
        });
        this.props.cancelVideo();
    }

    dismissError() {
        this.setState({hasVideoInputError: false});
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
                        <textarea defaultValue={this.props.description1} name="description1" onChange={this.handleChange}/>
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
        const videoURL = this.getVideoCode();
        return(
            <li className="row lined" id="editVideoUrl" onMouseEnter={this.toggleEdit} onMouseLeave={this.toggleEdit}>
                <div className="row video-input">
                    <div  className="edit col-xs-2">
                        {(this.state.hasVideoInputError || !this.state.isVideoEditEnabled) &&
                        <a type="submit" className="btn btn-small btn-success" disabled>
                            {this.state.isEditing ? 'Set video': 'Change video'}</a>
                        }
                        {!this.state.hasVideoInputError && this.state.isVideoEditEnabled &&
                        <a type="submit" className="btn btn-small btn-success"
                           onClick={() => this.toggleVideoEditMode()}>
                            {this.state.isEditing ? 'Set video' : 'Change video'}
                        </a>
                        }
                    </div>
                    <div className="col-xs-10">
                        {(this.state.isEditing && this.state.editVideoUrl) &&
                        <div className="col-xs-12">
                            <input className="col-xs-10" defaultValue={videoURL} name="video" onChange={this.handleVideoChange}/>
                            <a type="submit" className="btn btn-small btn-default col-xs-2" onClick={() => this.cancelVideo()}>Cancel</a>
                        </div>
                        }
                    </div>
                    {this.state.hasVideoInputError &&
                    <div className="alert alert-error alert-dismissible video-input-alert" role="alert">
                        <button type="button" className="close" onClick={this.dismissError}>&times;</button>
                        <strong>Upload failed! </strong>{this.state.errorMessage}</div>
                    }
                </div>
                <div className="col-md-12">
                    <iframe {...this.getAttributes(videoURL)} />
                </div>
            </li>
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
                            {this.renderDescription2()}
                        </ul>
                    </div>
                </div>
            </div>
        )
    }
}

export default TabProfile