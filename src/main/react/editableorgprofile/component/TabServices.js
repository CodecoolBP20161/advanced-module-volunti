import React from 'react'

class TabServices extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            }

        }
    render() {
        return(
            <div className="profile-main">
                <h3>Services</h3>
                <div className="profile-in profile-serv">
                    <h6>Here’s an overview of the services we provide.</h6>
                    <div className="media">
                        <div className="media-left">
                            <div className="icon"> <img src="" alt="" /> </div>
                        </div>
                        <div className="media-body">
                            <h6>Engine diagnostics and repairs</h6>
                            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Suscipit, maxime, excepturi,
                                mollitia, voluptatibus similique aliquidautem laudantium sapiente ad enim ipsa modi
                                labo rum accusantium deleniti neque.</p>
                        </div>
                    </div>
                    <div className="media">
                        <div className="media-left">
                            <div className="icon"> <img src="" alt="" /> </div>
                        </div>
                        <div className="media-body">
                            <h6>Engine diagnostics and repairs</h6>
                            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Suscipit, maxime, excepturi,
                                mollitia, voluptatibus similique aliquidautem laudantium sapiente ad enim ipsa modi
                                labo rum accusantium deleniti neque.</p>
                        </div>
                    </div>
                    <div className="media">
                        <div className="media-left">
                            <div className="icon"> <img src="" alt="" /> </div>
                        </div>
                        <div className="media-body">
                            <h6>Engine diagnostics and repairs</h6>
                            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Suscipit, maxime, excepturi,
                                mollitia, voluptatibus similique aliquidautem laudantium sapiente ad enim ipsa modi
                                labo rum accusantium deleniti neque.</p>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}
export default TabServices

