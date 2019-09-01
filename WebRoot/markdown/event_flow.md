### **<font color='salmon'>#事件流：</font>**

- 冒泡流：事件“**<font color='salmon'>由底向上</font>**”传播。

- 捕获流：事件“**<font color='salmon'>由上向底</font>**”传播。

### <font color='salmon'>**#阶段**：</font>

- 捕获阶段
- 目标阶段
- 冒泡阶段

![这是图片](event_flow_1.jpg)

content-title

```javascript
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <div id="a" style="width: 500px;height: 500px;background-color: aliceblue;margin: 0 auto">
        <div id="b" style="width: 400px;height: 400px;background-color: antiquewhite;margin: 0 auto">
            <div id="c" style="width: 300px;height: 300px;background-color: aqua;margin: 0 auto">
                <div id="d" style="width: 200px;height: 200px;background-color: aquamarine;margin: 0 auto">
                    <div id="e" style="width: 100px;height: 100px;background-color: azure;margin: 0 auto">

                    </div>
                </div>
            </div>
        </div>
    </div>
    <script>
        document.getElementById('a').addEventListener('click',function () {
            console.log("这是a捕获阶段");
        },true);
        document.getElementById('a').addEventListener('click',function () {
            console.log("这是a冒泡阶段");
        },false);
        document.getElementById('b').addEventListener('click',function () {
            console.log("这是b捕获阶段");
        },true);
        document.getElementById('b').addEventListener('click',function () {
            console.log("这是b冒泡阶段");
          }
        );

        document.getElementById('c').addEventListener('click',function () {
            console.log("这是c捕获阶段");
        },true);
        document.getElementById('c').addEventListener('click',function () {
            console.log("这是c冒泡阶段");
        },false);

        document.getElementById('d').addEventListener('click',function () {
            console.log("这是d捕获阶段");
        },true);
        document.getElementById('d').addEventListener('click',function () {
            console.log("这是d冒泡阶段");
        },false);

        document.getElementById('e').addEventListener('click',function () {
            console.log("这是捕e获阶段");
        },true);
        document.getElementById('e').addEventListener('click',function () {
            console.log("这是e冒泡阶段");
        },false);
    </script>
</body>
</html>
```

点击id=“c”时候的效果：
（蓝绿色那块为id=“c”）
addEventListener( , , ):第三个参数为：

- true时：在捕获阶段处理调用事件处理程序

- false时：在冒泡阶段处理调用事件处理程序
  
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20190605105304216.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTY4MjgyOA==,size_16,color_FFFFFF,t_70)
  
  我们点击id=“c”的div标签时，事件从最外层的id=“a”向id=“c”开始传播，此时为捕获阶段。当捕获到id=“c”时，事件开始从最里层的id="c"开始向id="a"传播，此时为冒泡阶段。
  
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20190605005650425.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTY4MjgyOA==,size_16,color_FFFFFF,t_70)捕获流是“**由外向内**”传播。而冒泡流是：“**由内向外**”
