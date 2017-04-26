import React from 'react'

import TopLabel from './top-label'
import SideBar from './side-bar'
import Profile from './tab-profile'
import Services from './tab-services'
import ProfileBanner from './profile-banner'

class FullProfile extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            key: Math.random(),
            name: null,
            category: null,
            country: null,
            city: null,
            zipcode: null,
            address: null,
            profilePicture: "/profile/organisation/image/profile",
            backgroundPicture: "/profile/organisation/image/background",
            social: {
                facebook: "valamiLink",
                twitter: "valamiLink",
                google: "valamiLink",
                linkedin: "valamiLink",
                video: "valamiLink"
            },
            selectedSocial: 'facebook'
        };

    }

    fetchData(){
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
                    name: response.name,
                    category: response.category,
                    country: response.country,
                    city: response.city,
                    zipcode: response.zipcode,
                    address: response.address,
                    // mission: response.mission,
                    // description1: response.description1,
                    // description2: response.description2,
                    social: {
                        facebook: 'facebookURL',
                        twitter: 'twitterURL',
                        google: 'googleURL',
                        linkedin: 'linkedinURL',
                        video: "https://www.youtube.com/embed/q4je9N26ouY",
                    }

                })
            }.bind(this)
         });


        // $.ajax({
        //     url: "/profile/organisation/text",
        //     cache: false,
        //     type: "GET",
        //     headers: headers,
        //     dataType: "json",
        //     success: function (response) {
        //         let social = {
        //             facebook: null,
        //             twitter: null,
        //             linkedin: null,
        //             google: null,
        //             video: null
        //         };
        //         for (let link in response){
        //             social.facebook = [link['socialLinkType']]
        //         }
        //         this.setState({
        //             social: {
        //                 facebook: 'facebookURL',
        //                 twitter: 'twitterURL',
        //                 google: 'googleURL',
        //                 linkedin: 'linkedinURL',
        //                 video: "https://www.youtube.com/embed/q4je9N26ouY",
        //             }
        //         })
        //     }.bind(this)
    }

    componentDidMount() {
        this.fetchData()
    }

    componentWillMount(){
        this.fetchData()
    }

    saveSocial(value, selected){
        let newSocial = this.state.social;
        newSocial[this.state.selectedSocial] = value;
        let newSelected = selected == null? this.state.selectedSocial : selected;
        this.setState({
            social: newSocial,
            selectedSocial: newSelected
        })
    }
    saveBackgroundPicture(picture){
        let csrfHeader = $("meta[name='_csrf_header']").attr("content");
        let csrfToken = $("meta[name='_csrf']").attr("content");
        let headers = {};

        headers[csrfHeader] = csrfToken;
        let formData = new FormData();
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
            success: function (response) {
                this.setState({
                    key: Math.random(),
                    backgroundPicture: "/profile/organisation/image/background"
                })
            }.bind(this)
        })
    }

    render() {
        const divStyle = {
            background: 'url(' + this.state.backgroundPicture + ')',
            'background-size': 'cover'
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

                            <SideBar profileSource={this.state.profilePicture}
                                     category={this.state.category}
                                     country={this.state.country}
                                     city={this.state.city}
                                     mission={this.state.mission}/>

                            {/*<!-- Tab Content -->*/}
                            <div className="col-md-8">
                                <div className="tab-content">

                                    {/*<!-- PROFILE -->*/}
                                    <Profile videoURL={this.state.social.video} />

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
