<template>
    <netviewView :deviceType="deviceType"></netviewView>
</template>

<script>
import netviewView from "../components/NetWorkView.vue"

export default {
  components: {
      netviewView,
  },
  data() {
      return {
          deviceType:2
      }
  }
}
</script>
