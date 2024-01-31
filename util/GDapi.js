import {
	AMAPKEY
} from "@/util/api.js" //这个就是你在第一步申请的key值

const amapFile = require('./amap-wx.130.js') //引入高德地图 ，根据自己放置的文件路径来决定
const myAmapFun = new amapFile.AMapWX({
	key: AMAPKEY
}); //创建一个实例化对象

export default function wxGetAddress({
	longitude,
	latitude
}) {
	//根据传递进来经纬度进行反解析，调用的是高德给的方法
	return new Promise((resolve, reject) => {
		myAmapFun.getRegeo({
			location: `${longitude},${latitude}`,
			success: (res) => {
				resolve(res[0])
			},
			fail: (err) => {
				resolve(null)
			}
		})
	})
}
