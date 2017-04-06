import React from 'react'

class Social extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            mouseOver: false,
            isEditing: false,
            selected: null,
            data: null,
            value: null,

        };

    }
    toggleEditModeOff(){
        this.setState({
            isEditing: false
        })
    }
    toggleEditModeOn(){
        this.setState({
            data: this.props.social,
            isEditing: true,
            selected: 'facebook',
            value: this.state.data.facebook
        });


    }
    componentWillMount(){
        this.setState({
            data: {
                facebook: this.props.social.facebook,
                twitter: this.props.social.twitter,
                google: this.props.social.google,
                linkedin: this.props.social.linkedin
            },
            value: this.props.social.facebook
        })
    }
    toggleEditButton(){
        this.setState({mouseOver: !this.state.mouseOver});
    }
    select(event){
        console.log('new Value: ', this.state.data[this.state.selected]);
        this.setState({
            value: this.state.data[this.state.selected],
            selected: event.currentTarget.id
        })
    }
    saveChange(event) {
        let newData = this.state.data;
        console.log('to save: ', event.target.value);
        newData[this.state.selected] = event.target.value;
        this.setState({
            data: newData,
        })
    }
    render() {
        console.log("social in Social: ", this.props.social);
        let socialLink = [];
        for(var key in this.props.social) {
            if (this.props.social.hasOwnProperty(key) && key != 'video') {
                if (this.state.selected == key) {
                    socialLink.push(
                        <a onClick={(e) => this.select(e)} id={key} className='selected'>
                            <i className={"fa fa-" + key}/>
                        </a>
                    );
                } else if (this.state.isEditing){
                    socialLink.push(
                        <a onClick={(e) => this.select(e)} id={key}>
                            <i className={"fa fa-" + key}/>
                        </a>
                    )
                } else {
                    socialLink.push(
                        <a href={this.props.social[key]} id={key}>
                            <i className={"fa fa-" + key}/>
                        </a>
                    );
                }

            }
        }
        return (
                <div className="social-links" onMouseEnter={() => this.toggleEditButton()} onMouseLeave={() => this.toggleEditButton()}>
                    {this.state.isEditing &&
                    <div className="col-md-12 row">
                        <input className="col-md-12 socialInput" type="text" id="socialInput" defaultValue={this.state.value} placeholder={this.state.value} onChange={(e) => this.saveChange(e)}/>
                    </div>
                    }
                    {socialLink}
                    {this.state.mouseOver  &&
                    <button type="submit"  onClick={(this.state.isEditing? () => this.toggleEditModeOff(): () => this.toggleEditModeOn())}>{this.state.isEditing? 'Done': 'Edit'}</button>
                    }
                </div>
        );
    }
}
export default Social
