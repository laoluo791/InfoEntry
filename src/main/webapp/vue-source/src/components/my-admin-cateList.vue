<template>
  <div>
    <Table border :columns="column" :data="data" no-data-text="暂无数据"></Table>
  </div>
</template>

<script>
  export default {
    name: "my-admin-cateList",
    data(){
      return{
        column:[
          {
            title: '项目名',
            key: 'name',
            render:(h,params)=>{
              return h("span",
                {
                  style:{fontSize:"16px"}
                },params.row.name)
            },
            renderHeader:(h,params)=>{
              return h("span",
                {
                  style:{fontSize:"16px"}
                },"项目名")
            }
          },
          {
            title: '项目内容',
            key: 'content',
            render:(h,params)=>{
              return h("span",
                {
                  style:{fontSize:"16px"}
                },params.row.content)
            },
            renderHeader:(h,params)=>{
              return h("span",
                {
                  style:{fontSize:"16px"}
                },"项目内容")
            }
          },
          {
            title: '已填写人数',
            key: 'yesInput',
            render:(h,params)=>{
              return h("span",
                {
                  style:{fontSize:"16px"}
                },params.row.yesInput)
            },
            renderHeader:(h,params)=>{
              return h("span",
                {
                  style:{fontSize:"16px"}
                },"已填写人数")
            }
          },
          {
            title: '操作',
            key: 'opera',
            align: 'center',
            render: (h, params) => {
              return h('div', [
                h('Button', {
                  props: {
                    type: 'primary',
                    size: 'large',
                    icon:'ios-copy-outline'
                  },
                  style: {
                    marginRight: '5px'
                  },
                  on: {
                    click: () => {
                      //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
                      var curWwwPath = window.document.location.href;
                      //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
                      var pathName = window.document.location.pathname;
                      var pos = curWwwPath.indexOf(pathName);
                      //获取主机地址，如： http://localhost:8083
                      var localhostPaht = curWwwPath.substring(0, pos);
                      //复制到粘贴板
                      var clipText = localhostPaht+"/InfoEntry/index.html?v="+params.row.id;
                      this.$copyText(clipText).then((e)=>{
                        this.$Message.success("复制成功");
                      },(e)=>{
                        this.$Message.error("复制失败");
                      })
                    }
                  }
                }, '复制链接'),
                h('Button', {
                  props: {
                    type: 'error',
                    size: 'large',
                    icon: 'ios-close'
                  },
                  style: {
                    marginRight: '5px'
                  },
                  on: {
                    click: () => {
                      this.$Modal.confirm({
                        title: "警告",
                        content: "删除后所有用户填写的信息也会一并删除",
                        onOk:()=>{
                          this.deleteItem(params.row.id);
                        }
                      });
                    }
                  }
                }, '删除项目')
              ]);
            },
            renderHeader:(h,params)=>{
              return h("span",
                {
                  style:{fontSize:"16px"}
                },"操作")
            }
          },
        ],
        data: []
      }
    },
    created(){
      this.getTableData();
    },
    methods:{
      getTableData(){
        this.axios("/InfoEntry/type/getTableData")
          .then((response)=>{
            response.data.forEach((item,index,arr)=>{
              arr[index].content = "["+item.content.join()+"]";
            });
            this.data = response.data;
          })
      },
      deleteItem(typeId){
        let data = {typeId};
        this.axios.post("/InfoEntry/type/deleteTypeAndContentByTypeId",this.qs.stringify(data))
          .then((response)=>{
            if(response.data.state == 1){
              this.$Message.success(response.data.msg);
              //刷新数据
              this.getTableData();
            }
            else{
              this.$Message.error(response.data.msg);
            }
          })
      }
    }
  }
</script>

<style scoped>

</style>
