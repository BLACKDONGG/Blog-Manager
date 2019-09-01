### <font color="salmon">#Blob对象：</font>

**前言在刷牛课的时候遇到了关于Blob对象，在此特意记一下。**
本文将采用：是什么？为什么？怎么做？的方式来阐述Blob对象。

### <font color="salmon">#什么是Blob对象：</font>

    Blob对象全称是--Binary Large Object，二进制大对象。是一个可以存放二进制文件的容器。

### <font color="salmon">#如何创建Blob对象：</font>

1. 使用new关键字：
   
   ```
   var blob = new Blob(array[optional], options[optional]);
   ```
* 第一个参数：为数据序列，可以是任意格式的值。

* 第二个参数：数据的类型(MIME)

* 示例：
  
  ```
  <script>    let bolb = new Blob(['<h4>HELLO WORLD</h4>'],{type:'text/xml'});</script>
  ```

2.已知有一Blob对象，为blob，使用<font color="salmon">blob.slice(startend,content-type)</font>，复制到新的Blob对象。

* 注意，当start+length超出源Blob对象的大小时，将返回从start到结尾的数据。

* 有些浏览器在使用slice时可能需要加前缀。如: blob.mozSlice()，blob.webkitSlice()

### <font color="salmon">#属性:</font>

* .size:`Blob` 对象中所包含数据的大小（字节）。
  
  ```
  只读
  ```

* .type:一个**字符串**，表明该`Blob`对象所包含数据的MIME类型。如果类型未知，则该值为空字符串。
  
  ```
  只读
  ```

### <font color="salmon">#示例:</font>

- ```javascript
  <script>
   let arr = [
   '<h4>HELLO WORLD</h4>',
   '<h3>HELLO WORLD</h3>',
   '<h2>HELLO WORLD</h2>',
   '<h1>HELLO WORLD</h1>',
   ];
   let blob = new Blob(arr,{type:'text/xml'});
   let newBlob = blob.slice(0,4);
   console.log(typeof newBlob);
   </script>
  ```
