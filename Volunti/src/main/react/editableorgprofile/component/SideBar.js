import React from 'react'

class SideBar extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            mouseOver: false,
            isEditing: false,
            countries: [],
            categories: []
        };
        this.handleChange = this.handleChange.bind(this);
    }

    fetchData() {
        const csrfHeader = $("meta[name='_csrf_header']").attr("content");
        const csrfToken = $("meta[name='_csrf']").attr("content");
        const headers = {};
        headers[csrfHeader] = csrfToken;

        $.ajax({
        url: "/api/organisation/profile-data",
        cache: false,
        type: "GET",
        headers: headers,
        dataType: "json",
        success: function (response) {
            this.setState({
                countries: response.countries,
                categories: response.categories
                })
            }.bind(this)
        });
    }

    componentDidMount() {
        this.fetchData();
    }

    toggleEditMode(){
        if (this.state.isEditing){
            this.props.saveData();
        }
        this.setState({
            isEditing: !this.state.isEditing
        });
    }

    toggleEditButton(){
        this.setState({mouseOver: !this.state.mouseOver});
    }

    handleChange(event) {
        this.props.onChange(event.target.name, event.target.value);
    }

    render() {
        return (
        <div className="col-md-4"
             onMouseEnter={() => this.toggleEditButton()}
             onMouseLeave={() => this.toggleEditButton()}>

            {/*<!-- Company Information -->*/}
            <div className="sidebar">
                <div className="filter-flower">
                <div className="row">
                    <ul>
                        <li><h5 className="main-title">Organisation Information</h5></li>
                        {(this.state.mouseOver  || this.state.isEditing) &&
                        <button type="submit" className="btn btn-small btn-success" onClick={() => this.toggleEditMode()}>
                            {this.state.isEditing? 'Done': 'Edit'}</button>
                        }
                    </ul>
                </div>
                </div>

                {!this.state.isEditing &&
                    <div>
                        <div className="sidebar-thumbnail"> <img src={this.props.profilePicture} /> </div>
                        <div className="sidebar-information">
                            <ul className="single-category">
                                <li className="row">
                                    <h6 className="title col-xs-4">Name</h6>
                                    <span className="subtitle col-xs-6">{this.props.name}</span>
                                </li>
                                <li className="row">
                                    <h6 className="title col-xs-4">Category</h6>
                                    <span className="subtitle col-xs-6">{this.props.category}</span>
                                </li>
                                <li className="row">
                                    <h6 className="title col-xs-4">Location</h6>
                                    <span className="subtitle col-xs-6">{this.props.city}, {this.props.country}</span>
                                </li>
                            </ul>
                        </div>
                    </div>
                }

                {this.state.isEditing &&
                <div>
                    <div className="sidebar-thumbnail"> <img src={this.props.profilePicture} /> </div>
                    <div className="sidebar-information">
                        <ul className="single-category">
                            <li className="row">
                                <h6 className="title col-xs-4">Name</h6>
                                <span className="subtitle col-xs-8">
                                    <input defaultValue={this.props.name} name="name"
                                           required="required"
                                           onChange={this.handleChange}/>
                                </span>
                            </li>
                            <li className="row">
                                <h6 className="title col-xs-4">Category</h6>
                                <span className="subtitle col-xs-8">
                                    <select defaultValue={this.props.category}
                                            required="required"
                                            name="category"
                                            onChange={this.handleChange}>
                                        {this.state.categories.map(function (category, i) {
                                            return <option key={i}>{category}</option>
                                        })}
                                    </select>
                                </span>
                            </li>
                            <li className="row">
                                <h6 className="title col-xs-4">Location</h6>
                                <span className="subtitle col-xs-8">
                                    <select defaultValue={this.props.country}
                                            required="required"
                                            name="country"
                                            onChange={this.handleChange}>
                                        {this.state.countries.map(function (country, i) {
                                            return <option key={i}>{country}</option>
                                        })}
                                    </select>
                                </span>
                            </li>
                            <li className="row">
                                <h6 className="title col-xs-4">City</h6>
                                <span className="subtitle col-xs-8">
                                    <input defaultValue={this.props.city} name="city" onChange={this.handleChange}/>
                                </span>
                            </li>
                            <li className="row">
                                <h6 className="title col-xs-4">Address</h6>
                                <span className="subtitle col-xs-8">
                                    <input defaultValue={this.props.address} name="address" onChange={this.handleChange}/>
                                </span>
                            </li>
                            <li className="row">
                                <h6 className="title col-xs-4">ZipCode</h6>
                                <span className="subtitle col-xs-8">
                                    <input defaultValue={this.props.zipcode} name="zipcode"
                                           maxLength="20"
                                           type="text"
                                           onChange={this.handleChange}/>
                                </span>
                            </li>
                        </ul>
                    </div>
                </div>
                }
            </div>
        </div>
        );
    }
}
export default SideBar





