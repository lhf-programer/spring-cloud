<template>
    <el-menu mode="vertical"  unique-opened theme="dark" :default-active="$route.path" :collapse="isCollapse"  background-color="#304156" text-color="#bfcbd9" active-text-color="#409EFF">
      <sidebar-item :routes='permissionMenus'></sidebar-item>
    </el-menu>
</template>


<script>
  import { mapGetters } from 'vuex';
  import SidebarItem from './SidebarItem';
  export default {
    components: { SidebarItem },
    computed: {
      ...mapGetters([
        'sidebar',
        'permissionMenus'
      ]),
      isCollapse() {
        return !this.sidebar.opened
      }
    }
  }
</script>
