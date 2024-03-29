<template>
  <div>
    <el-config-provider :locale="zhCn">
      <router-view />
    </el-config-provider>
  </div>
</template>

<script>
export default {
  name: 'App',
  mounted() {
    console.log(window.Glob)
  },
  methods: {
    
  },
}
</script>

<style scoped></style>
