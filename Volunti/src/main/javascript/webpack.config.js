module.exports = {

    //entry point
    entry: './index.js',

    //output point
    output: {
        path: '../resources/static/js',
        filename: 'bundle.js'
    },
    // We set the babel-loader to convert all es2016 code to vanilla JS,
    // except the files in the 'node_modules' folder
    module: {
        loaders: [
            {
                test: /\.js$/,
                exclude: /(node_modules)/,
                loader: 'babel-loader',
                query: {
                    presets: ['react', 'es2016']
                }
            }
        ]
    }
};