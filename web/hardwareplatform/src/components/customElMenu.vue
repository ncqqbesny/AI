<template>
  <div>
    <!--叶子级菜单-->
    <template v-if="!item.children">
      <el-menu-item :key="item.menuId" :index="item.skipUrl">
        <component :is="item.icon" class="iconSize"></component>
          <span >
            {{item.menuName}}
          </span>
      </el-menu-item>
    </template>
    <!--父级菜单-->
    <el-sub-menu v-else :index="item.skipUrl" style="text-align: left">
      <template #title>
        <component :is="item.icon" class="iconSize"></component>
        <span >
          {{item.menuName}}
        </span>
      </template>
      <template v-for="child in item.children">
        <navigation-item v-if="child.children && child.children.length>0" :key="child.id" :item="child"/>
        <el-menu-item v-else  :index="child.skipUrl">
          <component :is="child.icon" class="iconSize"></component>
          <span>
            {{child.menuName}}
          </span>
        </el-menu-item>
      </template>
    </el-sub-menu>
  </div>
</template>
 
<script>
  export default {
    name: 'NavigationItem',
    props: {
      item: {
        type: Object,
        required: true
      }
    }
  }
</script>
<style scoped>
  .iconSize{
    height: 18px;
    width: 18px;
    margin-right: 20px;
  }

  .el-menu-item.is-active{
    color: #409eff !important;
  }
</style>