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
      this.$emit("saveSuccess");
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