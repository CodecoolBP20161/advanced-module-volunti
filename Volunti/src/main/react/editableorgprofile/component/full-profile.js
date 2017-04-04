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
            profilePicture: null,
            backgroundPicture: null,
            mission: null,
            description1: null,
            description2: null,
            social:{
                facebook: null,
                twitter: null,
                google: null,
                linkedin: null,
                video: null
            }

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
                console.log("data", response);
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
                    backgroundPicture: response.backgroundPicture,
                    profilePicture: "/profile/organisation/image",
                         social: {
                             facebook: null,
                             twitter: null,
                             google: null,
                             linkedin: null,
                             video: null,
                         }

                })
            }.bind(this)
         });
}
    componentDidMount(){
        this.fetchData()
    }

    render() {
        const divStyle = {
            backgroundImage: 'url(' + this.state.backgroundPicture + ')',
        };
        return(
            <div className="compny-profile">
                {/*<!-- SUB Banner -->*/}
                <div className="profile-bnr" style={{background: divStyle}}>
                    <div className="container">

                        {/*<!-- User Info -->*/}
                        <TopLabel
                            organisationName={this.state.name}
                            category={this.state.category}
                            address={this.state.country + ", " + this.state.zipcode + ", " + this.state.city + ", " + this.state.address}
                            social={this.state.social}
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
