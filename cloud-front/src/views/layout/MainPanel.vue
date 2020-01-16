<!-- main框中的panel，占满一个main框 -->
<template>
  <div id="main-panel" :class="classObj" v-if="visible">
    <slot name="header">
      <!-- 默认的header -->
      <transition name="el-fade-in-linear">
        <div class="main-panel-header">
          <span @click="()=>$emit('close', false)" class="close-span">返回</span>
        </div>
      </transition>
    </slot>
    <!-- 默认的slot -->
    <slot></slot>
  </div>
</template>
<script>
import { mapState } from "vuex";

export default {
  name: "MainPanel",
  props: {
    visible: {
      type: Boolean,
      default: false
    }
  },
  computed: {
    ...mapState({
      sidebar: state => state.app.sidebar
    }),
    classObj() {
      return {
        hideSidebar: !this.sidebar.opened,
        openSidebar: this.sidebar.opened
      };
    }
  }
};
</script>
<style lang="scss" scoped>
$paddingTop: 84px;
#main-panel {
  top: $paddingTop;
  right: 0;
  overflow: auto;
  z-index: 1035;
  min-height: calc(100vh - 84px);
  background-color: #fff;
  transition: width 0.28s;
  .hideSidebar & {
    transition: width 0.28s;
  }
  .main-panel-header {
    height: 44px;
    line-height: 44px;
    border-bottom: 1px solid #d2d2d2;
    padding-left: 8px;
  }
  .close-span {
    cursor: pointer;
    border: 1px solid #d2d2d2;
    font-size: 14px;
    width: 70px;
    display: inline-block;
    height: 30px;
    line-height: 30px;
    border-radius: 2px;
    text-align: center;
    &:hover {
      border: 1px solid #409eff;
      color: #409eff;
    }
  }
}
</style>


