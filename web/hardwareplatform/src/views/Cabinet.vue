<template>
    <CabinetView :deviceType="deviceType"></CabinetView>
</template>

<script>
import CabinetView from "../components/CabinetView.vue"

export default {
  components: {
    CabinetView,
  },
  data() {
      return {
          deviceType:''
      }
  }
}
</script>
