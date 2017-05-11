import React from 'react'

class Social extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            mouseOver: false,
            isEditing: false,
            socialLinks: {
                facebook: null,
                twitter: null,
                google: null,
                linkedin: null
            },
            selectedSocial: 'facebook'
        };

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
                    socialLinks: {
                        facebook: response.organisationSocialLinks.FACEBOOK ? response.organisationSocialLinks.FACEBOOK : '',
                        twitter: response.organisationSocialLinks.TWITTER ? response.organisationSocialLinks.TWITTER : '',
                        google: response.organisationSocialLinks.GOOGLE ? response.organisationSocialLinks.GOOGLE : '',
                        linkedin: response.organisationSocialLinks.LINKEDIN ? response.organisationSocialLinks.LINKEDIN : ''
                    }
                })
            }.bind(this)
        });
    }

    componentDidMount() {
        this.fetchData()
    }

    filterNulls(obj){
        return obj.socialLinkUrl != '';
    }

    saveSocial(value) {
        let csrfHeader = $("meta[name='_csrf_header']").attr("content");
        let csrfToken = $("meta[name='_csrf']").attr("content");
        let headers = {};
        headers[csrfHeader] = csrfToken;

        let myArray = [{socialLinkType: 'FACEBOOK', 'socialLinkUrl': this.state.socialLinks.facebook},
            {socialLinkType: 'TWITTER', socialLinkUrl: this.state.socialLinks.twitter},
            {socialLinkType: 'GOOGLE', socialLinkUrl: this.state.socialLinks.google},
            {socialLinkType: 'LINKEDIN', socialLinkUrl: this.state.socialLinks.linkedin}].filter(this.filterNulls);

        $.ajax({
            url: "/profile/organisation/process",
            cache: false,
            type: "POST",
            headers: headers,
            dataType: "json",
            contentType: "application/json",
            processData: false,
            async: true,
            data: JSON.stringify(myArray),
            success: function (response) {
                console.log("Fasza")
            }.bind(this)
        })

    }


    toggleEditMode() {
        if (this.state.isEditing) {
            this.saveSocial();
            this.props.socialEditOff();

        }
        this.setState({
            isEditing: !this.state.isEditing
        })
    }

    toggleEditButton() {
        this.setState({mouseOver: !this.state.mouseOver});
    }

    select(event) {
        let newSocial = this.state.socialLinks;
        newSocial[this.state.selectedSocial] = this.textInput.value;
        let newSelected = event.currentTarget.id == null ? this.state.selectedSocial : event.currentTarget.id;
        console.log(event.currentTarget.id)
        console.log(newSelected)
        this.setState({
            socialLinks: newSocial,
            selectedSocial: newSelected
        });
        console.log(this.state.selectedSocial)
    }


    render() {
        let socialLink = [];
        for (var key in this.state.socialLinks) {
            if (this.state.socialLinks.hasOwnProperty(key) && key != 'video') {
                if (this.state.selectedSocial == key && this.state.isEditing) {
                    socialLink.push(
                        <a onClick={(e) => this.select(e)} id={key} className='selected'>
                            <i className={"fa fa-" + key}/>
                        </a>
                    );
                } else if (this.state.isEditing) {
                    socialLink.push(
                        <a onClick={(e) => this.select(e)} id={key}>
                            <i className={"fa fa-" + key}/>
                        </a>
                    )
                } else {
                    socialLink.push(
                        <a href={this.state.socialLinks[key]} id={key}>
                            <i className={"fa fa-" + key}/>
                        </a>
                    );
                }

            }
        }
        let inputValue = this.state.socialLinks[this.state.selectedSocial];
        return (

            <div className="social-links" onMouseEnter={() => this.toggleEditButton()}
                 onMouseLeave={() => this.toggleEditButton()}>
                {this.state.isEditing &&
                <div className="col-md-12 row">
                    <input className="col-md-12 socialInput" type="text" id="socialInput"
                           value={inputValue}
                           ref={(input) => this.textInput = input}
                           onChange={() => this.select()}/>
                </div>
                }
                {socialLink}
                {this.state.mouseOver &&
                <button type="submit"
                        onClick={() => this.toggleEditMode()}>{this.state.isEditing ? 'Done' : 'Edit'}</button>
                }
            </div>
        );
    }
}
export default Social
