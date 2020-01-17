<template>
  <div class="app-container calendar-list-container">
    <el-card class="box-card">
      <!-- 查询区域 -->
      <div class="filter-container">
          <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="请输入登录名" v-model="listQuery.username"></el-input>
          <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="请输入真实名称" v-model="listQuery.realname"></el-input>
          <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="请输入描述" v-model="listQuery.description"></el-input>
          <el-button class="filter-item" type="primary" v-waves icon="search" @click="handleFilter">搜索</el-button>
          <el-button class="filter-item" v-if="user_btn_add" style="margin-left: 10px;" @click="handleCreate" type="primary" icon="edit">添加</el-button>
          <el-button class="filter-item" v-if="user_btn_remove" style="margin-left: 10px;" @click="handleDeleteBatch" type="danger" icon="delete">删除</el-button>
      </div>

      <!-- table区域-begin -->
      <el-table
        :key='tableKey'
        :data="list"
        v-loading.body="listLoading"
        @sort-change="sortChange"
        @selection-change="handleSelectionChange"
        border fit highlight-current-row
        style="width: 100%">
        <el-table-column
            type="selection"
            width="55">
          </el-table-column>
        <el-table-column
            label="序号"
            sortable="custom"
            type="index"
            :index="(index)=>{return (index+1) + (listQuery.pageNo-1)*listQuery.pageSize}"
            align="center"
            width="50"
          />
        <el-table-column align="center" sortable="custom" prop="username" label="登录名"> <template slot-scope="scope">
              <span>{{scope.row.username}}</span>
            </template> </el-table-column>
        <el-table-column align="center" sortable="custom" prop="realname" label="真实名称"> <template slot-scope="scope">
              <span>{{scope.row.realname}}</span>
            </template> </el-table-column>
        <el-table-column align="center" prop="roleName" label="角色名称"> <template slot-scope="scope">
              <span>{{scope.row.roleName}}</span>
            </template> </el-table-column>
        <el-table-column align="center" sortable="custom" prop="description" label="描述"> <template slot-scope="scope">
              <span>{{scope.row.description}}</span>
            </template> </el-table-column>
        <el-table-column align="center" label="操作"> <template slot-scope="scope">
            <el-button size="small" v-if="user_btn_edit" type="success" @click="handleUpdate(scope.row)">编辑
            </el-button>
            <el-button size="small" v-if="user_btn_remove" type="danger" @click="handleDelete(scope.row)">删除
            </el-button>
            <el-button size="small" v-if="user_btn_editPassword" type="info" @click="handleUpdatePassword(scope.row)">修改密码
            </el-button>
          </template> </el-table-column>
      </el-table>

      <!-- 表单区域 -->
      <div v-show="!listLoading" class="pagination-container">
          <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="listQuery.pageNo" :page-sizes="[10,20,30, 50]" :page-size="listQuery.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total"> </el-pagination>
      </div>
      <el-dialog :before-close="cancel" :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
        <el-form :model="form" :rules="rules" ref="form" label-width="100px">
          <el-form-item label="登录名" v-if="dialogStatus!='updatePassword'" prop="username">
            <el-input v-model="form.username" placeholder="请输入登录名"></el-input>
          </el-form-item>
          <el-form-item label="真实名称" v-if="dialogStatus!='updatePassword'" prop="realname">
            <el-input v-model="form.realname" placeholder="请输入真实名称"></el-input>
          </el-form-item>
          <el-form-item label="密码" v-if="dialogStatus!='update'" prop="password">
            <el-input v-model="form.password" placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item label="重复密码" v-if="dialogStatus!='update'" prop="passwordSecond">
            <el-input v-model="form.passwordSecond" placeholder="请再次输入密码"></el-input>
          </el-form-item>
          <el-form-item label="选择角色" v-if="dialogStatus!='updatePassword'" prop="roleId">
            <role-tree :value.sync="form.roleId" />
          </el-form-item>
          <el-form-item label="描述" v-if="dialogStatus!='updatePassword'" prop="description">
            <el-input v-model="form.description" placeholder="请输入描述"></el-input>
          </el-form-item>
        </el-form>
        <div scope="footer" class="dialog-footer">
          <el-button @click="cancel()">取 消</el-button>
          <el-button v-if="dialogStatus=='create'" type="primary" @click="create('form')">确 定</el-button>
          <el-button v-else-if="dialogStatus=='update'" type="primary" @click="update('form')">确 定</el-button>
          <el-button v-else-if="dialogStatus=='updatePassword'" type="primary" @click="updatePassword('form')">确 定</el-button>
        </div>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
  import {
    getUserPageList,
    generateUser,
    changeUserById,
    changePasswordById,
    expurgateUserById,
    expurgateUserBatch,
    getUserById
  } from 'api/admin/user';
  import { mapGetters } from 'vuex';
  import RoleTree from "@/views/admin/role/components/RoleTree"

  export default {
    name: 'adminUser',
    components: {
      RoleTree
    },
    data() {
      let validatePass = (rule, value, callback) => {
        // 验证密码是否合法
        let reg = /^[0-9a-zA-Z]\w{5,17}$/;
        if (!reg.test(value)) {
          callback(
            new Error("密码以数字或字母开头,不得包含非法字符,长度在6-18之间")
          );
        } else {
          callback();
        }
      };

      let validatePassTwo = (rule, value, callback) => {
        // 验证2次密码是否相等
        // 修改时不进行密码验证
        if (value !== this.form.password) {
          callback(new Error("两次输入密码不一致!"));
        } else {
          let reg = /^[0-9a-zA-Z]\w{5,17}$/;
          if (!reg.test(value)) {
            callback(
              new Error("密码以数字或字母开头,不得包含非法字符,长度在6-18之间")
            );
          } else {
            callback();
          }
        }
      };
      return {
        form: {
          username: undefined,
          password: undefined,
          passwordSecond: undefined,
          realname: undefined,
          roleId: [],
          description: undefined,
        },
        rules: {
          username: [
            {
              required: true,
              message: '请输入登录名',
              trigger: 'blur'
            },
            {
              min: 1,
              max: 64,
              message: '长度在 1 到 64 个字符',
              trigger: 'blur'
            }
          ],
          password: [
            {
              required: true,
              message: '请输入密码',
              trigger: 'blur'
            },
            {
              min: 1,
              max: 64,
              message: '长度在 1 到 64 个字符',
              trigger: 'blur'
            },
            { validator: validatePass, trigger: ["blur", "change"] }
          ],
          passwordSecond: [
            {
              required: true,
              message: '请输入密码',
              trigger: 'blur'
            },
            {
              min: 1,
              max: 64,
              message: '长度在 1 到 64 个字符',
              trigger: 'blur'
            },
            { validator: validatePassTwo, trigger: ["blur", "change"] }
          ],
          realname: [
            {
              required: true,
              message: '请输入真实名称',
              trigger: 'blur'
            },
            {
              min: 1,
              max: 64,
              message: '长度在 1 到 64 个字符',
              trigger: 'blur'
            }
          ],
          roleId: [
            {
              required: true,
              message: '请选择一个角色',
              trigger: 'blur'
            }
          ]
        },
        list: undefined,
        total: undefined,
        selections: undefined,
        listLoading: true,
        listQuery: {
          pageNo: 1,
          pageSize: 10,
          sortProp: undefined,
          sortType: undefined
        },
        user_btn_add: false,
        user_btn_edit: false,
        user_btn_remove: false,
        user_btn_editPassword: false,
        dialogFormVisible: false,
        dialogStatus: '',
        textMap: {
          update: '编辑',
          create: '创建',
          updatePassword: '修改密码'
        },
        tableKey: 0
      }
    },
    created() {
      this.getList();
      this.user_btn_edit = this.buttons['/admin/user/edit'] || false;
      this.user_btn_remove = this.buttons['/admin/user/remove'] || false;
      this.user_btn_add = this.buttons['/admin/user/add'] || false;
      this.user_btn_editPassword = this.buttons['/admin/user/editPassword'] || false;
    },
    computed: {
      ...mapGetters([
        'buttons'
      ])
    },
    methods: {
      getList() {
        this.listLoading = true;
        getUserPageList(this.listQuery).then(response => {
          this.list = response.result.records;
          this.total = response.result.total;
          this.listLoading = false;
        })
      },
      handleFilter() {
        this.listQuery.pageNo = 1;
        this.getList();
      },
      sortChange(data) {
        const { prop, order } = data;
        this.listQuery.sortProp = prop;
        this.listQuery.sortType = order;
        this.handleFilter();
      },
      handleCreate() {
        this.resetTemp();
        this.dialogStatus = 'create';
        this.dialogFormVisible = true;
      },
      handleUpdate(row) {
        getUserById({id: row.id})
          .then(response => {
            this.form = response.result;
            this.dialogFormVisible = true;
            this.dialogStatus = 'update';
          });
      },
      handleUpdatePassword(row) { // 修改密码
          this.form.id = row.id;
          this.dialogFormVisible = true;
          this.dialogStatus = 'updatePassword';
      },
      handleDelete(row) {
        this.$confirm('此操作将永久删除, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            expurgateUserById({id: row.id})
              .then(() => {
                this.$notify({
                  title: '成功',
                  message: '删除成功',
                  type: 'success',
                  duration: 2000
                });
                const index = this.list.indexOf(row);
                this.list.splice(index, 1);
              });
          });
      },
      handleSelectionChange(val) {
        this.selections = val;
      },
      handleDeleteBatch() {
        if (this.selections == undefined || this.selections.length == 0) {
          this.$notify({
            title: '批量删除',
            message: '请选择列',
            type: 'warning',
            duration: 2000
          });
          return
        }
        var ids = '';
        this.selections.forEach((item, index, array) => {
          ids += item.id + ",";
        });
        ids = ids.substring(0, ids.length -1);
        this.$confirm('此操作将永久删除选择列, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            expurgateUserBatch({ids: ids})
              .then(() => {
                this.$notify({
                  title: '成功',
                  message: '删除成功',
                  type: 'success',
                  duration: 2000
                });
                this.getList();
            });
        });
      },
      handleSizeChange(val) {
        this.listQuery.limit = val;
        this.getList();
      },
      handleCurrentChange(val) {
        this.listQuery.page = val;
        this.getList();
      },
      cancel() {
        this.dialogFormVisible = false;
        this.$refs['form'].resetFields();
      },
      create(formName) {
        const set = this.$refs;
        // 移除验证
        set[formName].validate(valid => {
          if (valid) {
            delete this.form.passwordSecond
            generateUser(this.form)
              .then(() => {
                this.dialogFormVisible = false;
                  this.getList();
                  this.$notify({
                  title: '成功',
                  message: '创建成功',
                  type: 'success',
                  duration: 2000
                });
              })
          } else {
            return false;
          }
        });
      },
      update(formName) {
        const set = this.$refs;
        set[formName].clearValidate("passwordSecond");
        set[formName].clearValidate("password");
        set[formName].validate(valid => {
          if (valid) {
            delete this.form.passwordSecond
            delete this.form.password
            changeUserById(this.form).then(() => {
              this.dialogFormVisible = false;
              this.getList();
              this.$notify({
                title: '成功',
                message: '修改成功',
                type: 'success',
                duration: 2000
              });
            });
          } else {
            return false;
          }
        });
      },
      updatePassword(formName) { // 修改密码
        const set = this.$refs;
        set[formName].clearValidate("username");
        set[formName].clearValidate("realname");
        set[formName].clearValidate("roleId");
        set[formName].validate(valid => {
          if (valid) {
            delete this.form.passwordSecond
            changePasswordById(this.form).then(() => {
              this.dialogFormVisible = false;
              this.getList();
              this.$notify({
                title: '成功',
                message: '修改成功',
                type: 'success',
                duration: 2000
              });
            });
          } else {
            return false;
          }
        });
      },
      resetTemp() {
        this.form = {
          username: undefined,
          password: undefined,
          passwordSecond: undefined,
          realname: undefined,
          roleId: [],
          description: undefined,
        };
      }
    }
  }
</script>

<style scoped>
</style>