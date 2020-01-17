<template>
  <main-panel :visible="visible" @close="close">
    <div class="menu-table">
        <el-table
            :data="menuList"
            border
            v-loading="listLoading"
            stripe
            style="width: 100%"
            :header-cell-style="{background:'#f8f8f8',color:'#606266'}"
            >
            <el-table-column
                prop="name"
                label="模块"
                label-class-name="label"
                align="left"
                width="180"
                >
                <template slot-scope="scope">
                    <el-col :span="24">
                        <el-checkbox @change="menuChange(scope.row)" v-model="scope.row.check">{{scope.row.name}}</el-checkbox>
                    </el-col>
                </template>
            </el-table-column>

            <el-table-column
                prop="menuList.buttonList"
                align="left"
                label-class-name="label"
                label="资源"
                >
                <template slot-scope="scope">
                    <el-col :span="4" v-if="scope.row.buttonList == null" class="resource">
                        <span>暂无资源</span>
                    </el-col>
                    <el-col
                        :span="4"
                        v-for="(item,key) in scope.row.buttonList"
                        :key="key"
                        class="resource"
                        >
                        <el-checkbox
                            v-model="item.check"
                            class="title"
                            v-if="scope.row.buttonList != null"
                            :key="item.btnId"
                        >{{item.name}}</el-checkbox>
                    </el-col>
                </template>
            </el-table-column>
            <template slot="empty">
                <p>未查询到数据</p>
            </template>
        </el-table>

        <div class="dialog-footer">
            <el-button @click="close()">取消</el-button>
            <el-button type="primary" @click="handleSave">确定</el-button>
        </div>
    </div>
  </main-panel>
</template>


<script>
import MainPanel from "@/views/layout/MainPanel";
import {
    getAllMenusByRoleId,
} from 'api/admin/menu';
import {
    changeRoleResourceByRoleId,
} from 'api/admin/resource';

export default {
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    row: {
      type: Object,
      required: false
    }
  },
  data() {
    return {
      listLoading: false,
      menuList: null // 菜单列表
    };
  },
  components: { MainPanel },
  watch: {
    visible: function(val) {
        if (val) {
            this.getAllMenusByRoleId();
        }
    }
  },
  methods: {
    menuChange(row) {
      // 菜单选择
      // 选择菜单下所有的按钮
      this.menuList.forEach((item, index, array) => {
        // 获取选用状况及按钮数据
        const { check, buttonList } = item;

        if (buttonList != undefined) {
          if (item.id === row.id) {
            buttonList.forEach((it, idx, ary) => {
              // 选中其菜单下所有按钮
              if (check) {
                this.menuList[index].buttonList[idx].check = true;
              } else {
                this.menuList[index].buttonList[idx].check = false;
              }
            });
          }
        }
      });
    },
    getAllMenusByRoleId() { // 获取所有菜单
        this.listLoading = true;
        getAllMenusByRoleId({id: this.row.id}).then(response => {
            const { result } = response;
            // 菜单数据
            this.menuList = result;
        })
        .finally(response => {
            this.listLoading = false;
        });
    },
    close() {
      // 关闭弹窗
      this.$emit("update:visible", false);
    },
    handleSave() {
        // 菜单 id组
        let menuIdList = [];
        // 按钮 id组
        let buttonIdList = [];

        if (this.menuList != null) {
            this.menuList.forEach((item, index, array) => {
                const { check, buttonList } = item;
                // 选中的菜单
                if (check) {
                    menuIdList.push(item.id);
                }
                if (buttonList != undefined) {
                    // 按钮组
                    buttonList.forEach((it, idx, ary) => {
                        // 选中的按钮
                        if (it.check) {
                            buttonIdList.push(it.id);
                        }
                    });
                }
            });
        }

        // 请求参数
        let param = {
            id: this.row.id,
            menuIds: menuIdList,
            buttonIds: buttonIdList
        }

        // 修改用户角色权限
        changeRoleResourceByRoleId(param)
            .then(res => {
                this.$notify({
                  title: '成功',
                  message: '修改角色权限成功',
                  type: 'success',
                  duration: 2000
                });
                this.$emit("saveSuccess");
            })
    }
  }
};
</script>

<style scoped>
    .menu-table {
        margin-top: 20px;
        margin-left: 20px;
    }

    .label {
        color: #000000;
    }

    /* 模块 */
    .mode {
        text-align: left;
    }

    /* 资源 */
    .resource {
        text-align: left;
    }

    .title {
        margin-right: 15px;
    }

    .dialog-footer {
        text-align: center;
        margin-top: 20px;
    }
    .menu-table /deep/ .el-checkbox .el-checkbox__input{
        display: inline;
    }
</style>