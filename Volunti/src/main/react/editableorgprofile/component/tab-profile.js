import React from 'react'

class TabProfile extends React.Component {
    render() {
        return (
        <div id="profile" className="tab-pane fade in active">
            <div className="profile-main">
                <h3>About the Company</h3>
                <div className="profile-in">
                    <p>{this.props.mission}</p>
                    <p>{this.props.description1}</p>
                    {/*<!-- Video -->*/}
                    <iframe src={this.props.videoURL} alt="https://www.youtube.com/watch?v=flyiGMNGQyA"></iframe>
                    <p>{this.props.description2}</p>
                </div>
            </div>
        </div>)
        }
}
export default TabProfile