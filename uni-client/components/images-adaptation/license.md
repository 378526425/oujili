# 实现朋友圈图片自适应排列

## 说明

本组件通常用于需要处理朋友圈或者类似qq空间说说的图片自动排列布局

## 用法示例

```vue
<template>
	<view>
		<imageAdaptation :imgList="imgList" :imgLayout1="imgLayout1" 
                         :imgLayout2="imgLayout2" :imgLayout3="imgLayout4"></imageAdaptation>
    </view>
</template>
```

```js
import imageAdaptation from '../../marchVoiceComponents/marchCircle/imageAdaptation.vue'
	export default {
        components: {
            imageAdaptation
		},
		data() {
			return {
                imgList: [
					'../../static/img/cat.jpg','../../static/img/cat.jpg',
					'../../static/img/cat.jpg','../../static/img/cat.jpg',
					'../../static/img/cat.jpg','../../static/img/cat.jpg',
					'../../static/img/cat.jpg','../../static/img/cat.jpg',
					'../../static/img/cat.jpg'
				],
                imgLayout1:[300,702],
                imgLayout2:[321,280],
                imgLayout3:[220,220],
            }
        }
    }
```

## 组件参数

|   属性名   |  类型  | 默认值 |                             说明                             |
| :--------: | :----: | :----: | :----------------------------------------------------------: |
|  imgList   | Arrary |  null  |                    需要展示的图片路径列表                    |
| imgLayout1 | Arrary |  null  |     当图片只有一张（imgList.length==1)时，一排一列的布局     |
| imgLayout2 | Arrary |  null  | 当图片有两张或四张（imgList.length==4\|\|imgList.length==2)，每行两列时的布局 |
| imgLayout3 | Arrary |  null  | 当图片有三张或五张（imgList.length==3\|\|imgList.length==5)，每行三列时的布局 |

### imgLayout1参数说明

注意：imgLayout2，imgLayout3参数性质与imgLayout1相同

|  类型  |                        说明                         |         参数1          |         参数2          |
| :----: | :-------------------------------------------------: | :--------------------: | :--------------------: |
| Number | 只能向属性中定义两个参数，用来表示图片宽高，单位rpx | 表示对应布局时图片宽度 | 表示对应布局时图片高度 |



补充：有任何问题联系qq：780248307 。前端小姐姐，在线求打扰~