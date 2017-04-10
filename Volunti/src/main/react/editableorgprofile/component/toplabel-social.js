import React from 'react'

class Social extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            mouseOver: false,
            isEditing: false
        };

    }
    toggleEditModeOff(){
        this.props.socialEditOff();
        this.setState({
            isEditing: false
        })
    }
    toggleEditModeOn(){
        this.setState({
            isEditing: true
        });

    }
    toggleEditButton(){
        this.setState({mouseOver: !this.state.mouseOver});
    }
    select(event){
        this.props.saveSocial(this.textInput.value, event.currentTarget.id);
    }
    saveChange() {
        this.props.saveSocial(this.textInput.value);
    }
    render() {
        // console.log("Social: ", this.props.social);
        let socialLink = [];
        for(var key in this.props.social) {
            if (this.props.social.hasOwnProperty(key) && key != 'video') {
                if (this.props.selected == key && this.state.isEditing) {
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
        let inputValue = this.props.social[this.props.selected];
        return (

                <div className="social-links" onMouseEnter={() => this.toggleEditButton()} onMouseLeave={() => this.toggleEditButton()}>
                    {this.state.isEditing &&
                    <div className="col-md-12 row">
                        <input className="col-md-12 socialInput" type="text" id="socialInput"
                               value={inputValue}
                               ref={(input) =>this.textInput = input}
                               onChange={() => this.saveChange()}/>
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

const MyInput = (props) => (
    <div className="col-md-12 row">
        <input className="col-md-12 socialInput" type="text" id="socialInput"
               value={props.inputValue}
               ref={(input) => props.textInput.textInput = input}
               onChange={()=>props.saveChange()}/>
    </div>
)