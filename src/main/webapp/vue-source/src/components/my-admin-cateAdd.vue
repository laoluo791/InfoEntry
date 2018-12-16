<template>
  <div class="div-size">
    <Form :label-width="60">
      <FormItem v-if="itemList.length == 0">
        <span style="font-size:20px;text-align:center">请添加填写项</span>
      </FormItem>
      <FormItem v-for="(item,index) in itemList" :key="'key'+index"
                :label="item.name" >
        <Row>
          <Col span="11">
            <i-Input size="large"></i-Input>
          </Col>
          <Col span="4" offset="1">
            <Checkbox v-model="itemList[index].required">
              <span style="font-size: 16px">非空</span>
            </Checkbox>
          </Col>
          <Col span="8">
            <ButtonGroup>
              <Button type="ghost" icon="ios-arrow-up" @click="itemUp(index)"></Button>
              <Button type="ghost" icon="ios-arrow-down" @click="itemDown(index)"></Button>
              <Button type="ghost" icon="ios-close-outline" @click="itemRemove(index)"></Button>
            </ButtonGroup>
          </Col>
        </Row>
      </FormItem>
      <FormItem>
        <Row>
          <Col span="6">
            <Button type="info" size="large" icon="plus-circled" @click="addCateDialogVal.dialog = true" long>添加</Button>
            <Modal
              v-model="addCateDialogVal.dialog"
              :styles="{top: '220px'}"
              title="添加类别"
              @on-ok="addCate">
              <Form :label-width="90">
                <FormItem label="类别名称" >
                  <i-Input v-model="addCateDialogVal.cateName"></i-Input>
                </FormItem>
                <FormItem label="是否非空校验" >
                  <RadioGroup v-model="addCateDialogVal.required">
                    <Radio label="true">是</Radio>
                    <Radio label="false">否</Radio>
                  </RadioGroup>
                </FormItem>
              </Form>
            </Modal>
          </Col>
          <Col span="6" offset="2">
            <Button type="success" size="large" @click="submitVal.dialog = true"
                    icon="checkmark-circled" long>提交</Button>
            <Modal
              v-model="submitVal.dialog"
              :styles="{top: '220px'}"
              title="项目名称"
              @on-ok="submit">
              <Form :label-width="90">
                <FormItem label="项目名称" >
                  <i-Input v-model="submitVal.name"></i-Input>
                </FormItem>
              </Form>
            </Modal>
          </Col>
        </Row>
      </FormItem>
    </Form>
  </div>
</template>

<script>
  export default {
    name: "my-admin-cateAdd",
    data(){
      return{
        itemList:[],
        submitVal:{
          name:"",
          dialog:false
        },
        addCateDialogVal:{
          cateName:"",
          required:"true",
          dialog:false,
          rules:{
            cateName:[
              {
                required : true,
                message : "类别名称不能为空",
                trigger: 'blur'
              }
            ]
          }
        }
      }
    },
    methods:{
      submit(){
        if(this.submitVal.name==""){
          this.$Message.error('项目名为必填项');
          return ;
        }
        if(this.itemList.length==0){
          this.$Message.error('请添加类别');
          return ;
        }
        let data = {
          content:JSON.stringify([...this.itemList]),
          name:this.submitVal.name
        };
        //console.info(data);
        this.axios.post("/InfoEntry/type/addType",this.qs.stringify(data))
          .then((response)=>{
            if(response.data.state == 1){
              this.$Modal.success({
                title: "保存成功",
                content: "您提交的数据已成功保存 , 您现在可以关闭此选项卡了"
              });
            }
            else{
              this.$Modal.error({
                title: "保存失败",
                content: "未知原因保存失败 , 请检查网络后再试"
              });
            }
        })
      },
      addCate(){
        if (this.addCateDialogVal.cateName==""){
          this.$Message.error('类别名称为必填项');
          return;
        }
        this.itemList.push({
          name:this.addCateDialogVal.cateName,
          required:this.addCateDialogVal.required == "true"?true:false
        });
        this.addCateDialogVal.cateName="";
        this.addCateDialogVal.required="true";
      },
      itemUp(index){
        if(index==0){
          this.$Message.error('已经是第一个了哦');
          return;
        }
        this.swapVal("up",index);
        this.refreshVal();
      },
      itemDown(index){
        if(index==(this.itemList.length-1)){
          this.$Message.error('已经是最后一个了哦');
          return;
        }
        this.swapVal("down",index);
        this.refreshVal();
      },
      itemRemove(index){
        this.itemList.splice(index,1);
      },
      refreshVal(){
        //交换完毕后添加值 , 让vue实现刷新
        this.itemList.push({});
        this.itemList.splice(this.itemList.length-1,1);
      },
      //交换值
      swapVal(flag,index){
        let i=1;
        if(flag=="up"){
          i=-1;
        }
        let temp = this.itemList[index+i];
        this.itemList[index+i]=this.itemList[index];
        this.itemList[index]=temp;
      }

    }
  }
</script>

<style scoped>
  .div-size{
    width: 500px;
    /*border: 1px solid skyblue;*/
  }
</style>
