import Vue from 'vue'
import Admin from './admin.vue'

//iview
import iView from 'iview';
import 'iview/dist/styles/iview.css';
Vue.use(iView, {
  size: 'large'
});
//axios
import axios from 'axios';
import qs from 'qs';
Vue.prototype.qs= qs;
Vue.prototype.axios= axios;
//粘贴板
import VueClipboard from 'vue-clipboard2'
Vue.use(VueClipboard);



new Vue({
  el: '#app',
  render: h => h(Admin)
});
