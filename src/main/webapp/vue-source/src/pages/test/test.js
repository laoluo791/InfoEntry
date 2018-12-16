import Vue from 'vue'
import Login from './test.vue'
import iView from 'iview';
import 'iview/dist/styles/iview.css';

//axios
import axios from 'axios';
import qs from 'qs';
Vue.prototype.qs= qs;
Vue.prototype.axios= axios;

Vue.use(iView);

new Vue({
  el: '#app',
  render: h => h(Login)
});
