import React from 'react'

class SideBar extends React.Component {
    render() {
        return (
        <div className="col-md-4">

            {/*<!-- Company Information -->*/}
            <div className="sidebar">
                <h5 className="main-title">Organisation Information</h5>
                <div className="sidebar-thumbnail"> <img src={this.props.profileSource} alt="/profile/organisation/image" /> </div>
                <div className="sidebar-information">
                    <ul className="single-category">
                        <li className="row">
                            <h6 className="title col-xs-6">Category</h6>
                            <span className="subtitle col-xs-6">{this.props.category}</span> </li>
                        <li className="row">
                            <h6 className="title col-xs-6">Location</h6>
                            <span className="subtitle col-xs-6">{this.props.city}, {this.props.country}</span> </li>
                        {/*<li className="row">*/}
                            {/*<h6 className="title col-xs-6">Number of Employees</h6>*/}
                            {/*<span className="subtitle col-xs-6">11,245</span> </li>*/}
                        {/*<li className="row">*/}
                            {/*<h6 className="title col-xs-6">Legal Entity</h6>*/}
                            {/*<span className="subtitle col-xs-6">Gesselschaft</span> </li>*/}
                        {/*<li className="row">*/}
                            {/*<h6 className="title col-xs-6">Company Registration</h6>*/}
                            {/*<span className="subtitle col-xs-6">HSD7589</span> </li>*/}
                        {/*<li className="row">*/}
                            {/*<h6 className="title col-xs-6">Operating Hours</h6>*/}
                            {/*<span className="subtitle col-xs-6">10:00 AM - 5:00 PM</span> </li>*/}
                        {/*<li className="row">*/}
                            {/*<h6 className="title col-xs-6">Contacts</h6>*/}
                            {/*<div className="col-xs-6"> <span className="subtitle">*****************<i className="fa fa-exclamation-circle"></i></span> <span className="subtitle">***************** <i className="fa fa-exclamation-circle"></i></span> <a href="#.">example@example.com</a> <a href="#.">example.com</a> </div>*/}
                        {/*</li>*/}
                    </ul>
                </div>
            </div>

            {/*/!*<!-- Company Rating -->*!/*/}
            {/*<div className="sidebar">*/}
                {/*<h5 className="main-title">Company Rating</h5>*/}
                {/*<div className="sidebar-information">*/}
                    {/*<ul className="single-category com-rate">*/}
                        {/*<li className="row">*/}
                            {/*<h6 className="title col-xs-6">Expertise:</h6>*/}
                            {/*<span className="col-xs-6"><i className="fa fa-star"></i> <i className="fa fa-star"></i> <i className="fa fa-star"></i> <i className="fa fa-star-o"></i> <i className="fa fa-star-o"></i></span> </li>*/}
                        {/*<li className="row">*/}
                            {/*<h6 className="title col-xs-6">Knowledge:</h6>*/}
                            {/*<span className="col-xs-6"><i className="fa fa-star"></i> <i className="fa fa-star"></i> <i className="fa fa-star"></i> <i className="fa fa-star-half-o"></i> <i className="fa fa-star-o"></i></span> </li>*/}
                        {/*<li className="row">*/}
                            {/*<h6 className="title col-xs-6">Quality::</h6>*/}
                            {/*<span className="col-xs-6"><i className="fa fa-star"></i> <i className="fa fa-star"></i> <i className="fa fa-star"></i> <i className="fa fa-star"></i> <i className="fa fa-star-o"></i></span> </li>*/}
                        {/*<li className="row">*/}
                            {/*<h6 className="title col-xs-6">Price:</h6>*/}
                            {/*<span className="col-xs-6"><i className="fa fa-star"></i> <i className="fa fa-star"></i> <i className="fa fa-star-o"></i> <i className="fa fa-star-o"></i> <i className="fa fa-star-o"></i></span> </li>*/}
                        {/*<li className="row">*/}
                            {/*<h6 className="title col-xs-6">Services:</h6>*/}
                            {/*<span className="col-xs-6"><i className="fa fa-star"></i> <i className="fa fa-star"></i> <i className="fa fa-star"></i> <i className="fa fa-star"></i> <i className="fa fa-star-o"></i></span> </li>*/}
                    {/*</ul>*/}
                {/*</div>*/}
            {/*</div>*/}

            {/*<!-- Company Rating -->*/}
            {/* <div className="sidebar">*/}
            {/*     <h5 className="main-title">Contact</h5>*/}
            {/*     <div className="sidebar-information form-side">*/}
            {/*         <form action="#">*/}
            {/*             <input type="text" placeholder="Name Surname" />*/}
            {/*             <input type="text" placeholder="E-mail address" />*/}
            {/*             <textarea placeholder="Your Message"></textarea>*/}
            {/*             <button className="btn btn-primary">Send message</button>*/}
                    {/*</form>*/}
                {/*</div>*/}
            {/*</div>*/}
        </div>
        );
    }
}
export default SideBar





