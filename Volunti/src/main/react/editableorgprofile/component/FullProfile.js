import React from 'react'

import SideBar from './SideBar'
import Profile from './TabProfile'
import ProfileBanner from './ProfileBanner'

class FullProfile extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            key: Math.random(),
            name: null,
            category: null,
            country: null,
            city: null,
            zipcode: 0,
            address: null,
            profilePicture: "/profile/organisation/image/profile",
            backgroundPicture: "/profile/organisation/image/background",
            social: {
                video: "valamiLink"
            },
            selectedSocial: 'facebook'
        };

        this.changeVideoUrl = this.changeVideoUrl.bind(this);
        this.handleSideBarDataChange = this.handleSideBarDataChange.bind(this);
        this.saveSideBarData = this.saveSideBarData.bind(this);

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
                        video: response.organisationVideos,
                    }

                })
            }.bind(this)
         });
    }

    componentDidMount(){
        this.fetchData()
    }

    changeVideoUrl(embedCode){
        this.setState({social:{video: embedCode}});
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

    handleSideBarDataChange(name, value) {
        this.setState({
            [name]: value
        });
    }

    saveSideBarData() {
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
                                     saveData={this.saveSideBarData}
                                     onChange={this.handleSideBarDataChange}/>

                            {/*<!-- Tab Content -->*/}
                            <div className="col-md-8">
                                <div className="tab-content">

                                    {/*<!-- PROFILE -->*/}
                                    <Profile videoURL={this.state.social.video} changeVideo={this.changeVideoUrl}/>

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
