import Vue from 'vue'
import App from './index.vue'
import iView from 'iview';
import 'iview/dist/styles/iview.css';

//axios
import axios from 'axios';
import qs from 'qs';
Vue.prototype.qs= qs;
Vue.prototype.axios= axios;

import MyEntry from '../../components/my-entry.vue'
Vue.component("my-entry",MyEntry);

Vue.use(iView);


new Vue({
  el: '#app',
  render: h => h(App)
});
