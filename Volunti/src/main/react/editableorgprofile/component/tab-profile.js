import React from 'react'
import update from 'react-addons-update';


class TabProfile extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            mouseOver: false,
            isEditing: false,
            mission: null,
            description1: null,
            description2: null,
            isVideoEditing: false,
            isOpen: false,
            videoUrl: null,
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleVideo = this.handleVideo.bind(this);
    }


    fetchData() {
        let csrfHeader = $("meta[name='_csrf_header']").attr("content");
        let csrfToken = $("meta[name='_csrf']").attr("content");
        let headers = {};

        headers[csrfHeader] = csrfToken;
        $.ajax({
            url: "/profile/organisation/text",
            cache: false,
            type: "GET",
            headers: headers,
            dataType: "json",
            success: function (response) {
                this.setState({
                    mission: response.mission,
                    description1: response.description1,
                    description2: response.description2,
                })
            }.bind(this)
        });
    }

    componentDidMount() {
        this.fetchData();
    }

    toggleEditMode(){
        if (this.state.isEditing){
           this.saveData();
        }
        this.setState({
            isEditing: !this.state.isEditing
        })
    }

    toggleVideoEditMode(){
        this.setState({
            isVideoEditing: !this.state.isVideoEditing
        })
        if (this.state.isVideoEditing){

            this.saveVideoData(this.state.videoUrl);
            this.props.changeVideo(this.state.videoUrl);
        }
    }

    toggleEditButton(){
        this.setState({mouseOver: !this.state.mouseOver});
    }

    mergeState(newObj) {
        const newState = update(this.state, {
            $merge: newObj
        });

        this.setState(newState);
    }

    handleChange(event) {
        const newObj = {};
        newObj[event.target.name] = event.target.value;

        this.mergeState(newObj);
    }

    handleVideo(event) {
        this.setState({videoUrl: event.target.value })
    }

    saveData() {
        const csrfHeader = $("meta[name='_csrf_header']").attr("content");
        const csrfToken = $("meta[name='_csrf']").attr("content");
        const headers = {};

        headers[csrfHeader] = csrfToken;
        const formData = {};
        formData["mission"] = this.state.mission;
        formData["description1"] = this.state.description1;
        formData["description2"] = this.state.description2;

        $.ajax({
            url: "/profile/organisation/saveText",
            cache: false,
            type: "POST",
            headers: headers,
            data : JSON.stringify(formData),
            dataType : 'json',
            contentType: 'application/json',
            processData: false,
            async: true,
            // data: formData,
            success: function () {
            }.bind(this)
        });
    }

    saveVideoData(embedcode) {
        const csrfHeader = $("meta[name='_csrf_header']").attr("content");
        const csrfToken = $("meta[name='_csrf']").attr("content");
        const headers = {};

        headers[csrfHeader] = csrfToken;
        const formData = {};
        formData["embedCode"] = embedcode;

        $.ajax({
            url: "/profile/organisation/saveVideo",
            cache: false,
            type: "POST",
            headers: headers,
            data : JSON.stringify(formData),
            dataType : 'json',
            contentType: 'application/json',
            processData: false,
            async: true,
            // data: formData,
            success: function () {
            }.bind(this)
        });
    }

    render() {
        const videoURL = this.props.videoURL ? this.props.videoURL:  "https://www.youtube.com/embed/leQ8nEcYFOc";

        return (

        <div id="profile" className="tab-pane fade in active"
             onMouseEnter={() => this.toggleEditButton()}
             onMouseLeave={() => this.toggleEditButton()}>
            <div className="profile-main">
                <div className="filter-flower">
                    <div className="row">
                        <h3 className="col-sm-7">About the Company</h3>
                        {(this.state.mouseOver  || this.state.isEditing) &&
                        <button type="submit" className="col-sm-4" onClick={() => this.toggleEditMode()}>
                            {this.state.isEditing? 'Done': 'Edit'}</button>
                        }
                    </div>
                </div>

                {!this.state.isEditing &&
                    <div className="profile-in">
                        <p>{this.state.mission}</p>
                        <p>{this.state.description1}</p>
                        {/*<!-- Video -->*/}
                        {(this.state.mouseOver || this.state.isVideoEditing) &&
                        <button type="submit" className="col-sm-4" onClick={() =>
                            this.toggleVideoEditMode()
                        }>
                            {this.state.isVideoEditing ? 'Done' : 'Edit'}</button>
                        }
                        {this.state.isVideoEditing &&
                        <input type="text" value={this.state.videoUrl} onChange={this.handleVideo}/>
                        }
                        <iframe src={videoURL} ></iframe>
                        <p>{this.state.description2}</p>
                    </div>
                }

                {this.state.isEditing &&
                    <div className="profile-in">
                        <p>
                            <textarea value={this.state.mission}
                                      name="mission"
                                      onChange={this.handleChange}/>
                        </p>
                        <p>
                            <textarea value={this.state.description1}
                                      name="description1"
                                      onChange={this.handleChange}/>
                        </p>
                        {/*<!-- Video -->*/}
                        <iframe src={videoURL} ></iframe>
                        <p>
                            <textarea value={this.state.description2}
                                      name="description2"
                                      onChange={this.handleChange}/>
                        </p>
                    </div>

                }
                {/*<div className="col-md-12 row">*/}
                    {/*<input className="col-md-12 socialInput" type="text" id="socialInput"*/}
                           {/*value={inputValue}*/}
                           {/*ref={(input) =>this.textInput = input}*/}
                           {/*onChange={() => this.saveChange()}/>*/}
                {/*</div>*/}


            </div>

        </div>

        )
        }
}
export default TabProfile