# 服务器

http://47.94.161.154:8082

# API接口

## 课程：/course

### /course/all

请求方法：GET

接口作用：获取全部课程

response：

| state | message | data(json格式数据)        |
| ----- | ------- | ------------------------- |
| 200   |         | [{course}]   ==> 课程列表 |

```json
course:{
	id:number,		// 课程id
    name:string		// 课程名称 
}
```

### /course/add

请求方法：POST

请求参数：`name：String`  课程名称

接口作用：添加课程

response：

| state | message | data(json格式数据)        |
| ----- | ------- | ------------------------- |
| 200   |         | [{course}]   ==> 课程列表 |

```java
public class Course {
    Integer id;		// 课程id
    String name;	// 课程名称
}
```

### 

## PPT：/ppt

### /ppt/ppt

请求方法：POST

请求参数：`file: file  ==> ppt文件` `courseId：number ==> 课程id`  

接口作用：给课程上传一个ppt文件

response：

| state | message | data(json格式数据)              |
| ----- | ------- | ------------------------------- |
| 200   |         | {PPT}   ==> 上传成功的ppt的信息 |

```java
public class PPT {
    Integer id;				//ppt的唯一标识 id
    Integer courseId;		 //ppt对应的课程id
    String filename;		 //ppt在服务器存储时的文件名
    String originFilename;	  //ppt原本的文件名称
    String fileType;		 // 文件类型
}
```

### 

### /ppt/pdf

请求方法：POST

请求参数：`file:file ==> pdf文件` ` pptId:number ==>上传的ppt文件返回的 PPT.id`  

接口作用：上传 已上传的ppt文件对应的pdf文件

response：

| state | message | data(json格式数据) |
| ----- | ------- | ------------------ |
| 200   |         | {PDF}              |

```java
public class PDF {
    Integer id;			//pdf文件的唯一标识
    Integer pptId;		 // 对应哪一个PPT的PDF文件
    String filename;	 // 在服务器存储的文件名
    String originFilename;  // 原本的文件名
    String fileType;	 // 文件类型
}
```

### 

### /ppt/extract

请求方法：POST

请求参数：`pdfId:number ==> 已上传的pdf文件返回的 PDF.id`

接口作用：

response：

| state | message | data(json格式数据)                                |
| ----- | ------- | ------------------------------------------------- |
| 200   |         | [{Resource}]   ==> 该PPT解析出来的所有的图片/视频 |

```java
// 解析出来的实体类
public class Resource {
    Integer id;							//提取出的文件的唯一标识 id
    Integer pptId;						// 从哪一个ppt提取出来的 
    Integer page;						// 从ppt的哪一页提取出来的
    String filename;					// 存储用的文件名
    String originFilename;				 // 原本的文件名
    String type;						// 类型==>   1. page 代表是一个完整的页面  2. extract  代表从一页的内容里提取出来的
    String fileType;					 // 文件类型
    Double pageWidth;					 // extract 的所在页面的页面宽度
    Double PageHeight;					 // extract 的所在页面的页面高度
    Double x;							// extract 位于页面的左边距
    Double y;							// extract 位于页面的上边距
    Double width;						 // extract 的宽度
    Double height;						 // extract 的高度
}
```

### 

### /ppt/ppts/{courseId}

​		http://47.94.161.154:8082/ppt/ppts/3

请求方法：GET

请求参数：`curseId:number  ==> 课程id`

接口作用： 获取一个课程的所有PPT

response：

| state | message | data(json格式数据)                 |
| ----- | ------- | ---------------------------------- |
| 200   |         | [{PPT}]  ==> 课程下的所有{PPT}列表 |

### 

### /load/id/{pptId}

​		http://47.94.161.154:8082/ppt/load/id/2

请求方法：GET

请求参数：`pptId:number ==> 已上传的ppt的id`

接口作用：下载ppt文件

response：

| state | message | data(json格式数据) |
| ----- | ------- | ------------------ |
| 200   |         | 成功时下载ppt文件  |

### 

## 提取出来的资源：/resource

### /resource/{pptId}

​		http://47.94.161.154:8082/resource/2

请求方法：GET

请求参数：`pptId:number ==> 已上传的ppt的id`

接口作用：请求一个ppt的对应的提取出的所有图片/视频

response：

| state | message | data(json格式数据)                                           |
| ----- | ------- | ------------------------------------------------------------ |
| 200   |         | [{Resource}]   ==> 该PPT解析出来的所有的图片/视频 已使用页码，文件名排序 |

### /resource/load/id/{resourceId}

​		http://47.94.161.154:8082/resource/load/id/283

请求方法：GET

请求参数：`resourceId:number ==> PPT解析出的文件的文件id(Resource.id)`

接口作用：请求解析出来的文件

response：

| state | message | data(json格式数据) |
| ----- | ------- | ------------------ |
| 200   |         | 对应的文件         |

### /resource/load/name/{filename}

​		http://47.94.161.154:8082/resource/load/name/2_page1.png

请求方法：GET

请求参数：`filename:string ==> ppt解析出的文件的文件名(Resource.filename)`

接口作用：请求解析出来的文件

response：

| state | message | data(json格式数据) |
| ----- | ------- | ------------------ |
| 200   |         | 对应的文件         |











