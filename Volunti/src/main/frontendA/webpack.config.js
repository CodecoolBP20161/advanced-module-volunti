var webpack = require('webpack')

module.exports = {
    context: __dirname,

    entry: {
        myReactApp: './src/main.jsx',

    },

    output: {
        path: '../resources/static/assets/js/',
        filename: "[name].js",
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
        ],
    },

    resolve: {
        modulesDirectories: ['node_modules'],
        extensions: ['', '.js', '.jsx', '.css']
    },
}