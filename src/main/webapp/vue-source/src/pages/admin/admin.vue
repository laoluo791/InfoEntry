<style scoped>
  .layout{
    border: 1px solid #d7dde4;
    background: #f5f7f9;
    position: absolute;
    border-radius: 4px;
    overflow: hidden;
    width: 100%;
    height: 100%;
  }
  @font-face{
    font-family:myHeart;
    src:url('/InfoEntry/static/font/heart.ttf');
  }
  .layout-logo{
    width: 300px;
    height: 60px;
    border-radius: 3px;
    float: left;
    position: relative;
    left: 20px;
    color: #fff;
    font-size: 26px;
    font-style:italic;
    font-family: myHeart;

  }
  .layout-nav{
    width: 320px;
    margin: 0 auto;
    margin-right: 20px;
  }
</style>
<template>
  <div class="layout">
    <Layout>
      <Header>
        <Menu mode="horizontal" theme="dark" active-name="1">
          <div class="layout-logo">在线填表系统后台管理</div>
          <div class="layout-nav">
            <MenuItem name="1">
              <Avatar src="/InfoEntry/static/img/admin/cat.png" />
              {{userName}}
            </MenuItem>
            <MenuItem @on-select="selectExit" name="2">
              <Icon type="power"></Icon>
              <span @click="selectExit">注销</span>
            </MenuItem>
          </div>
        </Menu>
      </Header>
      <Layout>
        <Sider hide-trigger :style="heightStyle">
          <Menu :active-name="tabs.value" theme="light" width="auto" @on-select="selectMenu">
            <MenuItem name="main">
              <Icon type="monitor"></Icon>
              主页
            </MenuItem>
            <Submenu name="1">
              <template slot="title">
                <Icon type="compose"></Icon>
                大类管理
              </template>
              <MenuItem name="cateAdd">类别添加</MenuItem>
              <MenuItem name="cateList">类别列表</MenuItem>
            </Submenu>
            <Submenu name="2">
              <template slot="title">
                <Icon type="ios-keypad"></Icon>
                数据管理
              </template>
              <MenuItem name="dataList">数据列表</MenuItem>
              <!--<MenuItem name="dataExport">数据导出</MenuItem>-->
            </Submenu>
          </Menu>
        </Sider>
        <Layout>
          <Content :style="heightStyle">
            <Tabs type="card" @on-tab-remove="handleTabRemove" @on-click="handleTabClick"  :value="tabs.value" >
              <TabPane :label="mainLabel" name="main" >
                <my-admin-main></my-admin-main>
              </TabPane>
              <TabPane label="类别添加" v-if="tabs.cateAdd" name="cateAdd" closable>
                <my-admin-cate-add></my-admin-cate-add>
              </TabPane>
              <TabPane label="类别列表" v-if="tabs.cateList" name="cateList" closable>
                <my-admin-cate-list></my-admin-cate-list>
              </TabPane>
              <TabPane label="数据列表" v-if="tabs.dataList" name="dataList" closable>
                <my-admin-data-list></my-admin-data-list>
              </TabPane>
            </Tabs>
          </Content>
        </Layout>
      </Layout>
    </Layout>
  </div>
</template>
<script>
  import MyAdminMain from "../../components/my-admin-main";
  import MyAdminCateAdd from "../../components/my-admin-cateAdd";
  import MyAdminCateList from "../../components/my-admin-cateList";
  import MyAdminDataList from "../../components/my-admin-dataList";
  export default {
    components: {MyAdminDataList, MyAdminCateList, MyAdminCateAdd, MyAdminMain},
    data(){
      return{
        heightStyle:{
          background:"#fff",
          height:"",
          overflowY:'auto'
        },
        tabs:{
          cateAdd:false,
          cateList:false,
          dataList:false,
          value:"main"
        },
        userName:"loading..",
        mainLabel:(h)=>{
          return h('div',
            [
              h('Icon', {
                props: {
                  type: "monitor"
                }
              }),
              h('span', '主页')
            ]
          )
        }
      }
    },
    created(){
      this.getWindowHeight();
      setTimeout(()=>{
        console.info("loading name. . . .");
        this.getUserMsg();
      },2000);
    },
    methods:{
      selectMenu(menuName){
        //console.info(menuName);
        this.tabs[menuName] = true;
        this.tabs.value = menuName;
      },
      handleTabRemove (name) {
        this.tabs[name] = false;
      },
      handleTabClick(name){
        this.tabs.value=name;
      },
      selectExit(){
        this.axios.post("/InfoEntry/admin/exitUser")
          .then((response)=>{
            if (response.data.state == 1){
              window.location="/InfoEntry/login"
            }
            else{
              this.$Message.error(response.data.msg);
            }
          })
      },
      getWindowHeight(){
        setInterval(()=>{
          this.heightStyle.height=window.innerHeight-70+'px';
        },100)
      },
      getUserMsg(){
        this.axios.post("/InfoEntry/admin/getSessionUserMsg")
          .then((response)=>{
            this.userName = response.data.msg;
          })
      }
    }
  }
</script>
