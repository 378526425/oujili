module.exports = {
    devServer: {
		    proxy: {
		      '/wxmapi': {
		        //target: 'http://localhost:8088',
				target: 'https://www.wxmblog.com/wxmapi',
				changeOrigin: true,
		        pathRewrite: {
		          '^/wxmapi': ''
		        }
		      }
		    },
	}
}