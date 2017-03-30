import TopLabel from './top-label'
import SideBar from './side-bar'
import Profile from './tab-profile'
import Services from './tab-services'
import React from 'react'
import ReactDOM from 'react-dom'
//Here we will send the AJAX call
let csrfHeader = $("meta[name='_csrf_header']").attr("content");
let csrfToken = $("meta[name='_csrf']").attr("content");
let headers = {};

headers[csrfHeader] = csrfToken;
let organisation =
    $.ajax({
        url: "/profile/organisation/text",
        cache: false,
        type: "GET",
        headers: headers,
        dataType: "json",
        success: function (response) {
            {
                organisation = response;
            }
        }
    });

console.log("this is it: ", organisation);


ReactDOM.render(<div className="compny-profile">
    {/*<!-- SUB Banner -->*/}
    <div className="profile-bnr">
        <div className="container">

            {/*<!-- User Info -->*/}
            <TopLabel
                organisationName={organisation["name"]}
                category={organisation["category"]}
                address={organisation["country"] + ", " + organisation["zipcode"] + ", " + organisation["city"] + ", " + organisation["address"]}
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

                <SideBar profileSource={organisation["profilePicture"]}
                         category={organisation["category"]}
                         country={organisation["country"]} />

                {/*<!-- Tab Content -->*/}
                <div className="col-md-8">
                    <div className="tab-content">

                            {/*<!-- PROFILE -->*/}
                            <Profile mission={organisation["mission"]}
                                     description1={organisation["description1"]}
                                     videoURL=""
                                     description2={organisation["description2"]} />

                            {/*<!-- Services -->*/}
                            <Services />
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
, document.getElementById("main-wrapper"));

