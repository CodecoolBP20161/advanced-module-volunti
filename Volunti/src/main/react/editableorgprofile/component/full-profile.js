import React from 'react'

import TopLabel from './top-label'
import SideBar from './side-bar'
import Profile from './tab-profile'
import Services from './tab-services'

class FullProfile extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            name: null,
            category: null,
            country: null,
            city: null,
            zipcode: null,
            address: null,
            profilePicture: "/profile/organisation/image/profile",
            backgroundPicture: "/profile/organisation/image/background",
            mission: null,
            description1: null,
            description2: null,
            social: {
                facebook: "valamiLink",
                twitter: "valamiLink",
                google: "valamiLink",
                linkedin: "valamiLink",
                video: "valamiLink"
            },
            selectedSocial: 'facebook'
        }

    }fetchData(){
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
                    mission: response.mission,
                    description1: response.description1,
                    description2: response.description2,
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
    componentWillMount(){
        this.fetchData()
    }
    saveData(){
        console.log("Full-Profile: Post AJAX sent, state saved.");
        // let csrfHeader = $("meta[name='_csrf_header']").attr("content");
        // let csrfToken = $("meta[name='_csrf']").attr("content");
        // let headers = {};
        // $.ajax({
        //     url: "/profile/organisation/text",
        //     cache: false,
        //     type: "GET",
        //     headers: headers,
        //     dataType: "json",
        //     success: function (response) {
        //         this.setState({
        //             name: response.name,
        //             category: response.category,
        //             country: response.country,
        //             city: response.city,
        //             zipcode: response.zipcode,
        //             address: response.address,
        //             mission: response.mission,
        //             description1: response.description1,
        //             description2: response.description2,
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

    saveSocial(value, selected){
        console.log("full-profile: Social values are saved.");
        let newSocial = this.state.social;
        newSocial[this.state.selectedSocial] = value;
        let newSelected = selected == null? this.state.selectedSocial : selected;
        this.setState({
            social: newSocial,
            selectedSocial: newSelected
        })
    }

    render() {
        const divStyle = {
            background: 'url(' + "/profile/organisation/image/background" + ')',
            'background-size': 'cover'
        };
        return(
            <div className="compny-profile">
                {/*<!-- SUB Banner -->*/}
                <div className="profile-bnr" style={divStyle}>
                    <div className="container">
                        {console.log("social in Profile: ", this.state.social)}
                        {/*<!-- User Info -->*/}
                        <TopLabel
                            organisationName={this.state.name}
                            category={this.state.category}
                            address={this.state.country + ", " + this.state.zipcode + ", " + this.state.city + ", " + this.state.address}
                            social={this.state.social}
                            saveSocial={(value, newSelected) => this.saveSocial(value, newSelected)}
                            saveState={() => this.saveData()}
                            selectedSocial={this.state.selectedSocial}

                        />
                        {/*<!-- Place of Top Right Buttons -->*/}
                    </div>
                </div>


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
                                    <Profile mission={this.state.mission}
                                             description1={this.state.description1}
                                             videoURL={this.state.social.video}
                                             description2={this.state.description2} />

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
