const path = require('path');
var webpack = require('webpack');

module.exports={
    entry:{
        organisationprofile: "./editableorgprofile/component/main.jsx",
        browseOpportunities: "./browseOpportunities/component/main.jsx"
    },

    output: {
        path: path.resolve(__dirname, '../resources/static/assets/js/react'),
        filename: "[name].js"
    },

    module: {
        loaders: [
            {
                test: /\.jsx?$/,
                exclude: /node_modules/,
                loader: 'babel-loader',
                query: {
                    presets: ['es2015', 'react']
                }
            },
            {
                test: /\.css$/,
                loader: "style-loader!css-loader"
            }
        ]
    },

    resolve: {
        // modulesDirectories: ['node_modules'],
        extensions: ['.js', '.jsx', '.css']
    }
};