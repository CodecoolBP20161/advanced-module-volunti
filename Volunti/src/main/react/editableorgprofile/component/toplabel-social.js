import React from 'react'

class Social extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            mouseOver: false,
            isEditing: false,
            selected: null,
            data: {
                facebook: props.facebook,
                twitter: props.twitter,
                google: props.google,
                linkedIn: props.linkedIn
            }
        };

    }
    toggleEditMode(){
        this.setState({isEditing: !this.state.isEditing});

    }
    toggleEditButton(){
        console.log("Hovered!");
        this.setState({mouseOver: !this.state.mouseOver});
    }

    render() {
        return (
                <div className="social-links" onMouseEnter={() => this.toggleEditButton()} onMouseLeave={() => this.toggleEditButton()}>
                    {this.state.isEditing &&
                        <div className="col-md-12 row"><input className="col-md-12 socialInput" type="text"/></div>
                    }
                    <a href={this.props.social.facebook} ><i className="fa fa-facebook"></i></a>
                    <a href={this.props.social.twitter}><i className="fa fa-twitter"></i></a>
                    <a href={this.props.social.google}><i className="fa fa-google"></i></a>
                    <a href={this.props.social.linkedIn}><i className="fa fa-linkedin"></i></a>
                    {this.state.mouseOver  &&
                    <button type="submit"  onClick={() => (this.toggleEditMode())}>{this.state.isEditing? 'Done': 'Edit'}</button>
                    }
                </div>
        );
    }
}
export default Social
