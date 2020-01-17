import {
  login,
  logout
} from 'api/admin/login';
import {
  getUserInfoByToken
} from 'api/admin/user';
import {
  getToken,
  setToken,
  removeToken
} from 'utils/auth';

const user = {
  state: {
    user: '',
    status: '',
    code: '',
    token: getToken(),
    name: '',
    avatar: '',
    introduction: '',
    roles: '',
    menus: undefined,
    buttons: undefined,
    setting: {
      articlePlatform: []
    }
  },

  mutations: {
    SET_CODE: (state, code) => {
      state.code = code;
    },
    SET_TOKEN: (state, token) => {
      state.token = token;
    },
    SET_INTRODUCTION: (state, introduction) => {
      state.introduction = introduction;
    },
    SET_SETTING: (state, setting) => {
      state.setting = setting;
    },
    SET_STATUS: (state, status) => {
      state.status = status;
    },
    SET_NAME: (state, name) => {
      state.name = name;
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar;
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles;
    },
    SET_MENUS: (state, menus) => {
      state.menus = menus;
    },
    SET_BUTTONS: (state, buttons) => {
      state.buttons = buttons;
    },
    LOGIN_SUCCESS: () => {
      console.log('login success')
    },
    LOGOUT_USER: state => {
      state.user = '';
    }
  },

  actions: {
    // 登录
    Login({
      commit
    }, userInfo) {
      const username = userInfo.username.trim();
      commit('SET_TOKEN', '');
      commit('SET_ROLES', '');
      commit('SET_MENUS', undefined);
      commit('SET_BUTTONS', undefined);
      removeToken();
      return new Promise((resolve, reject) => {
        let param = {
          username: username,
          password: userInfo.password,
          grant_type: "password"
        }
        login(param).then(response => {
          setToken(response.access_token);
          commit('SET_TOKEN', response.access_token);
          resolve();
        }).catch(error => {
          reject(error);
        });
      });
    },

    // 获取用户信息
    GetUserInfoByToken({
      commit,
      state
    }) {
      return new Promise((resolve, reject) => {
        var param = {
          token: state.token
        }
        getUserInfoByToken(param).then(response => {
          const data = response;
          commit('SET_ROLES', data.result.roleName);
          commit('SET_NAME', data.result.username);
          commit('SET_AVATAR', 'http://git.oschina.net/uploads/42/547642_geek_qi.png?1499487420');
          commit('SET_INTRODUCTION', data.result.description);

          const menus = {}
          const buttons = {}
          data.result.menusList.forEach((item, index, array) => {
            resolverResourceList(item, menus, buttons)
          })
          
          commit('SET_MENUS', menus);
          commit('SET_BUTTONS', buttons);
          resolve(response);
        }).catch(error => {
          reject(error);
        });
      });
    },

    // 登出
    LogOut({
      commit,
      state
    }) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          commit('SET_TOKEN', '');
          commit('SET_ROLES', '');
          commit('SET_MENUS', undefined);
          commit('SET_BUTTONS', undefined);
          removeToken();
          resolve();
        }).catch(error => {
          reject(error);
        });
      });
    },

    // 前端 登出
    FrontLogOut({
      commit
    }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '');
        commit('SET_MENUS', undefined);
        commit('SET_BUTTONS', undefined);
        removeToken();
        resolve();
      });
    }
  }
};

// 解析资源列表
function resolverResourceList(data, menus, buttons) {
  menus[data.url] = true;
  data.buttonList.forEach((item, index, array) => {
    buttons[item.url] = true
  })

  if (data.children != undefined && data.children.length != 0) {
    data.children.forEach((item, index, array) => {
      resolverResourceList(item, menus, buttons)
    })
  }
}

export default user;
