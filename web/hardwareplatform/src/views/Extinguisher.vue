<template>
    <IntelligentDevice :deviceTypeCode="deviceTypeCode"></IntelligentDevice>
</template>

<script>
import IntelligentDevice from "../components/IntelligentDevice-E.vue"

export default {
  components: {
    IntelligentDevice,
  },
  data() {
      return {
        deviceTypeCode:'0101'
      }
  }
}
</script>
