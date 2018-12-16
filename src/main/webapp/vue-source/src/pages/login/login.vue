<template>
  <div id="app" class="window-center">
    <Form id="my_form" ref="formValidate" :model="formModel" :rules="ruleValidate">
      <FormItem prop="name">
        <Row align="middle" >
          <i-Col offset="2" span="4">
            <label style="text-align:right;font-size:20px">账号</label>
          </i-Col>
          <i-Col span="18">
            <i-Input size="large" v-model="formModel.name" ></i-Input>
          </i-Col>
        </Row>
      </FormItem>
      <FormItem prop="password" >
        <Row align="middle" >
          <i-Col offset="2"  span="4">
            <label style="text-align:right;font-size:20px">密码</label>
          </i-Col>
          <i-Col span="18">
            <i-Input size="large" v-model="formModel.password" ></i-Input>
          </i-Col>
        </Row>
      </FormItem>
      <FormItem prop="password" >
        <Row align="middle" >
          <i-Col offset="6">
            <Checkbox v-model="formModel.saveState">
              <label style="text-align:right;font-size:16px">&nbsp;保持登录</label>
            </Checkbox>
          </i-Col>
        </Row>
      </FormItem>
      <FormItem>
        <Row>
          <i-Col :offset="6" span="4">
            <Button type="primary" size="large" @click="handleSubmit('formValidate')">登录</Button>
          </i-Col>
          <i-Col offset="1" span="12">
            <!--提示信息-->
            <Alert v-if="info.show" :type="info.type" show-icon>{{info.message}}</Alert>
          </i-Col>
        </Row>
      </FormItem>
    </Form>
  </div>
</template>

<script>

export default {
  name:"login",
  data(){
    return{
      info:{
        show:false,
        type:"error",
        message:""
      },
      formModel:{
        name:"",
        password:"",
        saveState:false
      },
      ruleValidate:{
        name:[
          {
            required : true,
            message : "　　　　　　　　　"+"账号不能为空",
            trigger: 'blur'
          }
        ],
        password:[
          {
            required : true,
            message : "　　　　　　　　　"+"密码不能为空",
            trigger: 'blur'
          }
        ]
      }
    }
  },

  methods: {
    handleSubmit(name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          //开始登录
          this.axios.post("/InfoEntry/admin/login",this.qs.stringify(this.formModel))
            .then((response)=>{
              //console.info(response.data);
              this.info.show=true;
              if(response.data.state == 1) {
                this.info.type = "success";
              }
              this.info.message=response.data.msg;
              setTimeout(()=>{this.info.show=false}, 3000);

              if(response.data.state == 1) {
                window.location="/InfoEntry/admin";
              }
            })
        } else {
          this.info.show=true;
          this.info.type="error";
          this.info.message="请填写完整";
          setTimeout(()=>{this.info.show=false}, 3000)
        }
      })
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

  #my_form{
    /*border: 1px solid mediumvioletred;*/
    position:relative;
    top:50%;
    transform:translateY(-50%);
  }
</style>
