<template>
    <el-select @change="handlerChange" multiple v-model="roleId" placeholder="请选择">
        <el-option
            v-for="item in options"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
    </el-select>
</template>

<script>
  import {
    getAllRoles
  } from 'api/admin/role';

  export default {
    name: 'roleTree',
    props: {
        value: {
            type: Array,
            default: []
        }
    },
    created() {
        this.roleId = this.value;
        this.getAllRoles()
    },
    watch: {
        value: function(val) {
            this.roleId = val
        }
    },
    methods: {
        getAllRoles() {
            getAllRoles().then(response => {
                this.options = response.result;
            })
        },
        handlerChange(value) {
            this.$emit('update:value', value)
        }
    },
    data() {
      return {
        options: undefined,
        roleId: []
      }
    }
  }
</script>

<style scoped>
</style>