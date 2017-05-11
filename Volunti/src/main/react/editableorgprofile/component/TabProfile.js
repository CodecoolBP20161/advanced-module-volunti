import React from 'react'


class TabProfile extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            mouseOver: false,
            isEditing: false,
            editVideoUrl: false,
            editMission: false,
            editDescription1: false,
            editDescription2: false,
            isOpen: false,
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleVideo = this.handleVideo.bind(this);
        this.toggleEdit = this.toggleEdit.bind(this);
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

    toggleVideoEditMode() {
        this.setState({
            isVideoEditing: !this.state.isVideoEditing
        });
        if (this.state.isVideoEditing){

            this.saveVideoData(this.state.videoUrl);
            this.props.changeVideo(this.state.videoUrl);
        }
    }

    handleChange(event) {
        this.props.onChange(event.target.name, event.target.value)
    }

    handleVideo(event) {
        this.setState({videoUrl: event.target.value })
    }

    saveVideoData(embedCode) {
        const csrfHeader = $("meta[name='_csrf_header']").attr("content");
        const csrfToken = $("meta[name='_csrf']").attr("content");
        const headers = {};

        headers[csrfHeader] = csrfToken;
        const formData = {};
        formData["embedCode"] = embedCode;

        $.ajax({
            url: "/profile/organisation/saveVideo",
            cache: false,
            type: "POST",
            headers: headers,
            data : JSON.stringify(formData),
            dataType : 'json',
            contentType: 'application/json',
            processData: false,
            async: true
        });
    }

    render() {
        const videoURL = this.props.videoURL ? this.props.videoURL:  "https://www.youtube.com/embed/leQ8nEcYFOc";

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
                        {/*Mission*/}
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

                        {/*Description1*/}
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

                        {/*<!-- Video -->*/}
                        {(this.state.mouseOver || this.state.isVideoEditing) &&
                        <button type="submit" className="col-sm-4" onClick={() =>
                            this.toggleVideoEditMode()}>{this.state.isVideoEditing ? 'Save' : 'Edit'}</button>
                        }
                        {this.state.isVideoEditing &&
                        <input type="text" value={this.state.videoUrl} onChange={this.handleVideo}/>
                        }
                        <iframe src={videoURL} ></iframe>

                        {/*Description2*/}
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
                    </ul>
                </div>
            </div>
        </div>
        )
    }
}
export default TabProfile