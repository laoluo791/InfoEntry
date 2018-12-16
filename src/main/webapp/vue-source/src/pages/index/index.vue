<template>
  <div id="app" class="window-center">
    <my-entry :TypeData="TypeData"></my-entry>
  </div>
</template>

<script>
  export default {
    data(){
      return{
        TypeData:{
          content:[]
        }
      }
    },
    created(){
      //获取数据
      this.getItem();
    },
    methods:{
      getItem(){
        let data={
          typeId:this.getUrlParam("v")
        };
        this.axios.post("/InfoEntry/type/getTypeById",this.qs.stringify(data))
          .then((response)=>{
            //将content的json字符串转为json对象 便于后面的操作
            response.data.content=JSON.parse(response.data.content);
            this.TypeData=response.data;
          })
      },
      /*
　　  调用方法：GetUrlParam("id");
　　  举例说明：
　　  假如当网页的网址有这样的参数 test.htm?id=896&s=q&p=5，则调用 GetUrlParam("p")，返回 5。
      * */
      getUrlParam(paraName) {
        var url = document.location.toString();
        var arrObj = url.split("?");
        if (arrObj.length > 1) {
          var arrPara = arrObj[1].split("&");
          var arr;
          for (var i = 0; i < arrPara.length; i++) {
            arr = arrPara[i].split("=");
            if (arr != null && arr[0] == paraName) {
              return arr[1];
            }
          }
          return "";
        }
        else {
          return "";
        }
      }
    }
  }
</script>

<style scoped>
  .window-center {
    /* 将position设置为fixed，使元素相对于浏览器窗口定位 */
    position: fixed;
    /* 将margin设置为auto，使浏览器自动推算元素外边距 */
    margin: auto;
    /* 将上下左右边距（相对于浏览器窗口边缘）全设为0，使浏览器推算出的外边距上下、左右对应相等
       可以设置任何其他相等的值，但如果窗口小到不能容下任意一个方向的两侧边距，元素也将不居中，所以推荐设为0 */
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    /* 使用这种方法的元素必须有绝对或相对大小，
    否则浏览器推算出的margin将为0，
    元素将被拉伸，以适应窗口大小和四个边距值 */
    width: 400px;
    height: 100%;
    overflow-y:auto;
    /*border: 1px solid mediumvioletred;*/
  }
</style>
