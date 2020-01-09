<template>
	<div class='menu-wrapper'>
		<template v-for="item in routes">
			<el-submenu :index="item.name" :key="item.name">
				<template slot="title">
					<icon-svg v-if='item.icon' :icon-class="item.icon"></icon-svg>
					<span>{{item.name}}</span>
				</template>
				<template v-for="child in item.children">
					<sidebar-item class='nest-menu' v-if='child.children&&child.children.length>0' :routes='[child]' :key="child.name"> </sidebar-item>
					<a target="_blank"  v-if="child.path!=null && child.path.indexOf('http')==0" :href="child.path" :key="child.name">
						<el-menu-item :index="child.path">
							<icon-svg v-if='child.icon' :icon-class="child.icon"></icon-svg>
							<span>{{child.name}}</span>
						</el-menu-item>
					</a>
					<router-link v-if="child.path!=null && child.path.indexOf('http')!=0 && child.children==undefined" :to="child.path" :key="child.name">
						<el-menu-item :index="child.path">
							<icon-svg v-if='child.icon' :icon-class="child.icon"></icon-svg>
							<span>{{child.name}}</span>
						</el-menu-item>
					</router-link>
				</template>
			</el-submenu>
		</template>
	</div>
</template>

<script>
  export default {
    name: 'SidebarItem',
    props: {
      routes: {
        type: Array
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>

</style>

