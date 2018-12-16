<template>
  <form id="entry_form">
    <Form v-if="TypeData.content.length!=0" :rules="ruleValidate"  ref="formModel" :model="formModel">
      <!--输入学号-->
      <FormItem  v-if="!showInput.show">
        <Row>
          <Col>
            <Tooltip placement="top-start">
              <span style="font-size: 16px;color:skyblue">你的班级序号</span>
              <div slot="content">
                <p>刘老师给你分的序号</p>
                <p><i>职教云你的账号:1601 XX , XX就是你的序号</i></p>
              </div>
            </Tooltip>
          </Col>
        </Row>
        <Row>
          <Col span="8">
            <InputNumber style="width: 100%" :min="1" :max="32" size="large"
                         placeholder="序号1-32"  v-model="showInput.val" @on-change="getUserName"></InputNumber>
          </Col>
          <Col span="4">
            <Input size="large" v-model="showInput.userName" disabled  />
          </Col>
        </Row>
      </FormItem>
      <FormItem v-if="!showInput.show" >
        <Row>
          <Col span="6">
            <Button type="primary" size="large" @click="InputClick" long>确定</Button>
          </Col>
        </Row>
      </FormItem>

      <FormItem v-if="showInput.show" v-for="(item,index) in TypeData.content" :prop="'val'+index" :key="'val'+index">
        <Row align="middle" >
          <i-Col span="6">
            <label style="text-align:right;font-size:20px;margin-left:15px;">{{item.name}}</label>
          </i-Col>
          <i-Col span="18">
            <i-Input size="large" v-model="formModel['val'+index]" ></i-Input>
          </i-Col>
        </Row>
      </FormItem>
      <FormItem v-if="showInput.show" >
        <Row>
          <i-Col :offset="6">
            <Button type="primary" size="large" @click="handleSubmit('formModel')">提交</Button>
          </i-Col>
        </Row>
      </FormItem>

    </Form>
    <Row v-else>
      <i-Col :lg="{span:24}">
        <span style="font-size:20px;text-align:center">未检测到填写项 , 请检查链接是否有误</span>
      </i-Col>
    </Row>
  </form>
</template>

<script>

  export default {
    props:["TypeData"],//prop用于父组件向子组件传递数据
    name: "my-entry",
    data(){
      return{
        formModel:{},
        ruleValidate:{},
        showInput:{
          show:false,
          val:0,
          userName:"姓名",
          userList:[]
        }
      }
    },
    created(){
      //获取用户
      this.axios.post("/InfoEntry/user/getAllUser")
        .then((response)=>{
          this.showInput.userList=response.data;
          console.info("用户",this.showInput.userList);
        });

      //获取到值才继续
      var exit = setInterval(()=>{
        if(this.TypeData != null){
          this.generateInput();
          clearInterval(exit);
        }
      },50);
    },
    methods:{
      InputClick(){
        if(this.showInput.val!=null && this.showInput.val!=0){
          //如果填写过则把之前的内容设置到输入框去
          let data={
            typeId:this.TypeData.id,
            userId:this.showInput.val
          };
          this.axios.post("/InfoEntry/content/getContentByTypeIdAndUserId",
            this.qs.stringify(data))
            .then((response)=>{
              console.info(response);
              if(response.data==""){
                response.data={};
              }
              console.info("shtInputVal",response.data);
              //设置输入框的值
              this.formModel=response.data;
            });
          //显示输入框
          this.showInput.show=true;
        }
      },
      getUserName(){
        for(let item of this.showInput.userList){
          if(item.no==this.showInput.val){
            this.showInput.userName=item.name;
          }
        }
      },
      handleSubmit (name) {
        this.$refs[name].validate((valid) => {
          if (valid) {
            let data={
              type:this.TypeData.id,
              user:this.showInput.val,
              content:JSON.stringify(this.formModel)
            };
            this.axios.post("/InfoEntry/content/saveByContent",
              this.qs.stringify(data))
              .then((response)=>{
                if(response.data.state == 1){
                  this.$Modal.confirm({
                    title: "保存成功",
                    content: "您提交的数据已成功保存 , 您现在可以关闭浏览器了",
                    onOk:()=>{
                      //关闭当前页
                      this.closeCurrentPage();
                    }
                  });
                }
                else{
                  this.$Modal.error({
                    title: "保存失败",
                    content: response.data.msg
                  });
                }
              })
          } else {
            this.$Message.error('请填写完整再提交');
          }
        })
      },
      generateInput(){
        //生成输入框
        let formModelVal = {};
        let ruleValidateVal ={};
        this.TypeData.content.forEach(function (v,k) {
          //console.info(v,k);
          //生成v-model
          formModelVal["val"+k]="";
          //只有在非空验证为true时,才添加验证规则
          ruleValidateVal['val'+k]=[
            {
              required : v.required,
              message : "　　　　　　　　　"+v.name+"不能为空",
              trigger: 'blur'
            }
          ];
        });
        this.formModel=formModelVal;
        this.ruleValidate=ruleValidateVal;
      },
      /*关闭当前页*/
      closeCurrentPage() {
        var userAgent = navigator.userAgent;
        if (userAgent.indexOf("Firefox") != -1 || userAgent.indexOf("Chrome") !=-1) {
          window.location.href="about:blank";
          window.close();
        } else {
          window.opener = null;
          window.open("", "_self");
          window.close();
        }
      }
    }
  }
</script>

<style scoped>

  #entry_form{
    /*border: 1px solid mediumvioletred;*/
    position:relative;
    top:50%;
    transform:translateY(-50%);
  }
</style>
