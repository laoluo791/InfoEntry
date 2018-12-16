<template>
  <div>
    <!--菜单-->
    <div style="margin-bottom: 40px;">
      <Row>
        <Col span="10" :gutter="16">
          <Select v-model="selectCate.model" placeholder="请先选择项目.."
                  @on-change="changeSelect" not-found-text="暂无项目" filterable >
            <Option v-for="item in selectCate.cateList" :value="item.id" :key="item.id">
              <span>{{ item.name }}</span>
              <span style="float:right;color:#ccc">[{{item.inputList.join()}}]</span>
            </Option>
          </Select>
        </Col>
        <Col offset="2" v-if="selectCate.model != ''" >
          <Button type="info" shape="circle" @click="exportData"
                  :loading="loading" icon="ios-cloud-download-outline">导出数据</Button>
        </Col>
      </Row>
      <Row v-if="selectCate.model != ''" class-name="row-class">
        <Col>
          <Checkbox size="large" v-model="tableFilter.user" @on-change="changeCheck('user')">
            <span style="font-size: 15px">显示用户列</span>
          </Checkbox>
          <Checkbox size="large"  v-model="tableFilter.yesInput" @on-change="changeCheck('yesInput')">
            <span style="font-size: 15px">已填写用户</span>
          </Checkbox>
          <Checkbox size="large"  v-model="tableFilter.noInput" @on-change="changeCheck('noInput')">
            <span style="font-size: 15px">未填写用户</span>
          </Checkbox>
        </Col>
      </Row>
      <Row>
        <Col>
        </Col>
      </Row>
    </div>
    <!--表格-->
    <Table border :columns="columns" :data="data"  ref="table" no-data-text="暂无数据"></Table>
  </div>
</template>

<script>
  export default {
    name: "my-admin-dataList",
    data(){
      return{
        selectCate:{
          model:"",
          cateList:[]
        },
        loading:false,
        tableFilter:{
          user:true,
          yesInput:true,
          noInput:true,
          allTableList:[],
          yesTableList:[],
          noTableList:[]
        },
        columns:[],
        data:[]
      }
    },
    created(){
      this.getTypeData();
    },
    mounted(){
    },
    methods:{
      changeSelect(value){
        this.getTableData(value);
        //每次切换都置空
        this.tableFilter={
          user:true,
            yesInput:true,
            noInput:true,
            allTableList:[],
            yesTableList:[],
            noTableList:[]
        }
      },
      changeCheck(name){
        switch (name){
          case "user":
            this.tableFilter.user ?
              this.columns.splice(0,0,{title:'用户[学号,姓名]',key:'user'}) : this.columns.splice(0,1);
            break;
          default:
            if(this.tableFilter.yesInput){
              this.data = this.tableFilter.yesTableList;
            }
            if(this.tableFilter.noInput){
              this.data = this.tableFilter.noTableList;
            }
            if(this.tableFilter.yesInput && this.tableFilter.noInput){
              this.data = this.tableFilter.allTableList;
            }
            else if(!this.tableFilter.yesInput && !this.tableFilter.noInput){
              this.data = [];
            }
        }
      },
      exportData(){
        this.loading = true;
        this.$refs.table.exportCsv({
          filename: "table_data"+"_"+new Date().toLocaleString()
        });
        setTimeout(()=>{
          this.loading = false;
        },1000);
      },
      getTypeData(){
        //获取项目列表
        this.axios.post("/InfoEntry/type/getAllType")
          .then((response)=>{
            //提取填写列表
            response.data.forEach((value,index,array)=>{
              //转换为JSON对象
              array[index].content = JSON.parse(value.content);
              //提取列表
              array[index]["inputList"]=[];
              for (let content of array[index].content){
                array[index]["inputList"].push(content.name)
              }
            });
            this.selectCate.cateList = response.data;
          })
      },
      getTableData(typeId){
        let data ={
          typeId:typeId
        };
        //获取格式化后的表格数据
        this.axios.post("/InfoEntry/content/getTableData",this.qs.stringify(data))
          .then((response)=>{
            //设置table值
            this.columns = response.data.columns;
            this.data = response.data.data;
            //生成三种情况的[数据] 全有的 , 已填写的 , 未填写的
            this.tableFilter.allTableList = this.data;
            this.tableFilter.yesTableList = this.data.filter((row)=>{
              return row.hasData
            });
            this.tableFilter.noTableList = this.data.filter((row)=>{
              return !row.hasData
            })
          })
      }
    }
  }
</script>

<style scoped>
  .row-class{
    margin-top: 25px;
  }
</style>
