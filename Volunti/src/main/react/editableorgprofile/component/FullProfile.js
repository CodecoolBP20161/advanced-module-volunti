import React from 'react'

import SideBar from './SideBar'
import Profile from './TabProfile'
import ProfileBanner from './ProfileBanner'

class FullProfile extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            key: Math.random(),
            mission: null,
            description1: null,
            description2: null,
            name: null,
            category: null,
            country: null,
            city: null,
            zipcode: 0,
            address: null,
            profilePicture: "/profile/organisation/image/profile",
            backgroundPicture: "/profile/organisation/image/background",
            social: {
                facebook: "",
                twitter: "",
                google: "",
                linkedin: "",
            },
            video: [],
            selectedSocial: 'facebook'
        };

        this.changeVideoUrl = this.changeVideoUrl.bind(this);
        this.handleDataChange = this.handleDataChange.bind(this);
        this.saveData = this.saveData.bind(this);

    }

    fetchData(){
        const csrfHeader = $("meta[name='_csrf_header']").attr("content");
        const csrfToken = $("meta[name='_csrf']").attr("content");
        const headers = {};

        headers[csrfHeader] = csrfToken;
         $.ajax({
            url: "/profile/organisation/text",
            cache: false,
            type: "GET",
            headers: headers,
            dataType: "json",
            success: function (response) {
                this.setState({
                    name: response.name,
                    category: response.category,
                    country: response.country,
                    city: response.city,
                    zipcode: response.zipcode,
                    address: response.address,
                    social: {
                        facebook: 'facebookURL',
                        twitter: 'twitterURL',
                        google: 'googleURL',
                        linkedin: 'linkedinURL',
                    },
                    video: response.organisationVideos,
                    mission: response.mission,
                    description1: response.description1,
                    description2: response.description2,

                })
            }.bind(this)
         });
    }

    componentDidMount(){
        this.fetchData()
    }

    saveSocial(value, selected){
        const newSocial = this.state.social;
        newSocial[this.state.selectedSocial] = value;
        const newSelected = selected == null? this.state.selectedSocial : selected;
        this.setState({
            social: newSocial,
            selectedSocial: newSelected
        })
    }

    changeVideoUrl(embedCode){
        this.setState({video: embedCode});
    }

    saveBackgroundPicture(picture){
        const csrfHeader = $("meta[name='_csrf_header']").attr("content");
        const csrfToken = $("meta[name='_csrf']").attr("content");
        const headers = {};

        headers[csrfHeader] = csrfToken;
        const formData = new FormData();
        formData.append("file", picture);
        $.ajax({
            url: "/profile/organisation/saveBackgroundImage",
            cache: false,
            type: "POST",
            headers: headers,
            contentType: false,
            processData: false,
            async: true,
            data: formData,
            success: function() {
                this.setState({
                    key: Math.random(),
                    backgroundPicture: "/profile/organisation/image/background"
                })
            }.bind(this)
        })
    }

    handleDataChange(name, value) {
        this.setState({
            [name]: value
        });
    }

    saveData() {
        const csrfHeader = $("meta[name='_csrf_header']").attr("content");
        const csrfToken = $("meta[name='_csrf']").attr("content");
        const headers = {};

        headers[csrfHeader] = csrfToken;
        const formData = {};
        formData["name"] = this.state.name;
        formData["category"] = this.state.category;
        formData["country"] = this.state.country;
        formData["address"] = this.state.address;
        formData["city"] = this.state.city;
        formData["zipcode"] = this.state.zipcode;
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
            async: true
        });
    }

    render() {
        const divStyle = {
            background: 'url(' + this.state.backgroundPicture + ')',
            'backgroundSize': 'cover'
        };

        return(
            <div className="compny-profile">
                {/*<!-- SUB Banner -->*/}
                <ProfileBanner
                    key={this.state.key}
                    organisationName={this.state.name}
                    category={this.state.category}
                    address={this.state.country + ", " + this.state.zipcode + ", " + this.state.city + ", " + this.state.address}
                    social={this.state.social}
                    saveSocial={(value, newSelected) => this.saveSocial(value, newSelected)}
                    saveState={() => this.saveData()}
                    savePicture={(picture) => this.saveBackgroundPicture(picture)}
                    selectedSocial={this.state.selectedSocial}
                    divStyle={divStyle}
                />


                {/*<!-- Profile Company Content -->*/}
                <div className="profile-company-content main-user" data-bg-color="f5f5f5">
                    <div className="container">
                        <div className="row">

                            {/*<!-- Nav Tabs -->*/}
                            <div className="col-md-12 ">
                                <ul className="nav nav-tabs">
                                    <li className="active"><a data-toggle="tab" href="#profile">Profile</a></li>
                                    <li><a data-toggle="tab" href="#jobs">Jobs</a></li>
                                    <li><a data-toggle="tab" href="#contact">Contact</a></li>
                                </ul>
                            </div>

                            <SideBar profilePicture={this.state.profilePicture}
                                     name={this.state.name}
                                     category={this.state.category}
                                     country={this.state.country}
                                     city={this.state.city}
                                     zipcode={this.state.zipcode}
                                     address={this.state.address}
                                     mission={this.state.mission}
                                     saveData={this.saveData}
                                     onChange={this.handleDataChange}/>

                            {/*<!-- Tab Content -->*/}
                            <div className="col-md-8">
                                <div className="tab-content">

                                    {/*<!-- PROFILE -->*/}
                                    <Profile videoURL={this.state.video}
                                             mission={this.state.mission}
                                             description1={this.state.description1}
                                             description2={this.state.description2}
                                             saveData={this.saveData}
                                             onChange={this.handleDataChange}
                                             changeVideo={this.changeVideoUrl}/>

                                    {/*<!-- Services -->*/}
                                    {/*<Services />*/}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}
export default FullProfile
