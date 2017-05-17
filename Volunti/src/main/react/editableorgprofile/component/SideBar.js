import React from "react";

class SideBar extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isEditing: false,
            countries: [],
            categories: [],
            editName: false,
            editCategory: false,
            editLocation: false,
            mouseOver: false
        };
        this.handleChange = this.handleChange.bind(this);
        this.toggleEdit = this.toggleEdit.bind(this);
        this.savePicture = this.savePicture.bind(this);
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

    savePicture(e) {
        e.preventDefault();
        this.props.saveProfilePicture(this.profileInput.files[0]);
        if (this.props.hasErrorProfileImg) {
            this.props.dismissError();
        }
    }

    changeImage() {
        $('#profileImg').click();
    }

    toggleEditMode() {
        if (this.state.isEditing) {
            this.props.saveData()
        }
        this.setState({
            isEditing: !this.state.isEditing
        });
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

    toggleEditButton() {
        this.setState({
            mouseOver: !this.state.mouseOver
        })
    }

    handleChange(event) {
        this.props.onChange(event.target.name, event.target.value);
    }

    renderName() {
        return (
            <li className="row lined" id="editName" onMouseEnter={this.toggleEdit}
                onMouseLeave={this.toggleEdit}>
                <h6 className="title col-xs-4">Name</h6>

                {!(this.state.isEditing && this.state.editName) &&
                <span className="subtitle col-xs-5">{this.props.name}</span>
                }

                {this.state.isEditing && this.state.editName &&
                <span className="subtitle col-xs-4">
                    <input defaultValue={this.props.name} name="name"
                           required="required"
                           onChange={this.handleChange}/>
                </span>
                }

                <div className="edit col-xs-3">
                    {this.state.editName &&
                    <a type="submit" className="btn btn-small btn-success"
                       onClick={() => this.toggleEditMode()}>
                        {this.state.isEditing ? 'Save' : 'Edit'}
                    </a>}
                </div>
            </li>
        )
    }

    renderCategory() {
        return (
            <li className="row lined" id="editCategory" onMouseEnter={this.toggleEdit}
                onMouseLeave={this.toggleEdit}>
                <h6 className="title col-xs-4">Category</h6>

                {!(this.state.isEditing && this.state.editCategory) &&
                <span className="subtitle col-xs-5">{this.props.category}</span>
                }

                {this.state.isEditing && this.state.editCategory &&
                <span className="subtitle col-xs-5">
                    <select defaultValue={this.props.category}
                            required="required"
                            name="category"
                            onChange={this.handleChange}>
                        {this.state.categories.map(function (category, i) {
                            return <option key={i}>{category}</option>
                        })}
                    </select>
                </span>
                }

                <div className="edit col-xs-3">
                    {this.state.editCategory &&
                    <a type="submit" className="btn btn-small btn-success"
                       onClick={() => this.toggleEditMode()}>
                        {this.state.isEditing ? 'Save' : 'Edit'}
                    </a>}
                </div>
            </li>
        )
    }

    renderLocationWithoutDetails() {
        return (
            <li className="row" id="editLocation" onMouseEnter={this.toggleEdit}
                onMouseLeave={this.toggleEdit}>
                <h6 className="title col-xs-4">Location</h6>

                {!(this.state.isEditing && this.state.editLocation) &&
                <span className="subtitle col-xs-5">{this.props.city}, {this.props.country}</span>
                }

                {this.state.isEditing && this.state.editLocation &&
                <span className="subtitle col-xs-5">
                    <select defaultValue={this.props.country}
                            required="required"
                            name="country"
                            onChange={this.handleChange}>
                        {this.state.countries.map(function (country, i) {
                            return <option key={i}>{country}</option>
                        })}
                    </select>
                </span>
                }

                <div className="edit col-xs-3">
                    {this.state.editLocation &&
                    <a type="submit" className="btn btn-small btn-success"
                       onClick={() => this.toggleEditMode()}>
                        {this.state.isEditing ? 'Save' : 'Edit'}</a>}
                </div>

            </li>
        )
    }

    renderLocationDetails() {
        return (
            <div>
                <li className="row">
                    <h6 className="title col-xs-4">City</h6>
                    <span className="subtitle col-xs-5">
                        <input defaultValue={this.props.city} name="city" onChange={this.handleChange}/>
                    </span>
                </li>
                <li className="row">
                    <h6 className="title col-xs-4">Address</h6>
                    <span className="subtitle col-xs-5">
                        <input defaultValue={this.props.address} name="address" onChange={this.handleChange}/>
                    </span>
                </li>
                <li className="row">
                    <h6 className="title col-xs-4">ZipCode</h6>
                    <span className="subtitle col-xs-5">
                        <input defaultValue={this.props.zipcode} name="zipcode"
                               maxLength="20"
                               type="text"
                               onChange={this.handleChange}/>
                    </span>
                </li>
            </div>
        )
    }

    renderProfileImage() {
        return (
            <div className="sidebar-thumbnail" onMouseEnter={() => this.toggleEditButton()}
                 onMouseLeave={() => this.toggleEditButton()}>
                <div className="image-wrapper">
                    <img style={this.props.imgStyle}/>

                    <form method="POST" encType="multipart/form-data" action="/profile/organisation/saveProfileImage">
                        <input className="profileImg" id="profileImg" ref={(input) => this.profileInput = input}
                               onChange={this.savePicture} type="file" required="required" name="file"
                               accept=".png,.jpg"/>
                    </form>

                    {(this.state.mouseOver || this.props.hasErrorProfileImg) ?
                        <a type="submit" onClick={this.changeImage}>Change picture</a> :
                        <i className="fa fa-camera fa-2" aria-hidden="true"> </i>}
                    {this.props.hasErrorProfileImg &&
                    <div className="alert alert-error alert-dismissible" role="alert">
                        <button type="button" className="close" onClick={this.props.dismissError}>&times;</button>
                        <strong>Upload failed! </strong>{this.props.errorMessage}</div>
                    }
                </div>
            </div>
        )
    }

    render() {
        console.log(this.props.hasErrorProfileImg);
        console.log(this.state.mouseOver);
        return (
            <div className="col-md-4">

                {/*<!-- Company Information -->*/}
                <div className="sidebar">
                    <div className="filter-flower">
                        <div className="row lined">
                            <ul>
                                <li><h5>Organisation Information</h5></li>
                            </ul>
                        </div>
                    </div>

                    <div>
                        {this.renderProfileImage()}

                        <div className="sidebar-information">
                            <ul className="single-category">

                                {this.renderName()}
                                {this.renderCategory()}
                                {this.renderLocationWithoutDetails()}
                                {this.state.isEditing && this.state.editLocation &&
                                this.renderLocationDetails()
                                }

                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}
export default SideBar





