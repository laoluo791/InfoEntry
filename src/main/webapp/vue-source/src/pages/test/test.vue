<template>
  <Tabs type="card">
    <TabPane v-for="tab in tabs" :key="tab" :label="'标签' + tab">标签{{ tab }}</TabPane>
    <Button @click="handleTabsAdd" size="small" slot="extra">增加</Button>
  </Tabs>
</template>
<script>
  export default {
    data () {
      return {
        tabs: 2
      }
    },
    methods: {
      handleTabsAdd () {
        this.tabs ++;
      }
    }
  }
</script>
