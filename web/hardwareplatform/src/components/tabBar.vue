<template>
    <div class="tabBar">
        <div class="toEquipment" @click="toEquipment" >
            <img src="../assets/mobile/pic/shebeiguanliW.png" alt="" class="imgW" ref="iconChange" v-if="equiIcon">
            <img src="../assets/mobile/pic/shebeiguanliB.png" alt="" class="imgB" ref="iconChange" v-if="!equiIcon">
        </div>
        <div class="toUser" @click="toUser">
            <img src="../assets/mobile/pic/wodegerenxinxiW.png" alt="" class="imgW" ref="userImgW" v-if="!equiIcon">
            <img src="../assets/mobile/pic/wodegerenxinxiB.png" alt="" class="imgB" ref="userImgB" v-if="equiIcon">
        </div>
    </div>
</template>

<script>
export default {
    name: 'tabBar',
    data() {
        return {
            equiIcon: false
        }
    },
    methods: {
        toEquipment() {
            this.equiIcon = true
            this.userIcon = false
            this.$router.push('/mEquipment')
            
            console.log('当前状态', this.equiIcon)
        },
        toUser() {
            this.userIcon = true
            this.equiIcon = false
            this.$router.push('/mUser')
            
            console.log('当前状态', this.equiIcon)
        }
    },
    mounted(){
        if(this.$route.path == '/mEquipment') {
            this.equiIcon = true
        }
    }
}
</script>

<style scoped>
.tabBar {
    position: fixed;
    bottom: -1px;
    left: 0;
    width: 100vw;
    height: 300px;
    line-height: 300px;
    background: rgba(40, 113, 181, .6);
}

.tabBar .toEquipment {
    border-right: 1px solid #ffffff;
}

.tabBar .toEquipment,
.toUser {
    display: inline-block;
    text-align: center;
    width: 50%;
    height: 200px;
}

.tabBar .toEquipment .imgW,
.toUser .imgW {
    vertical-align: top;
    width: 200px;
    color: #ffffff;
}

.tabBar .toEquipment .imgB,
.toUser .imgB {
    vertical-align: top;
    width: 200px;
    color: #ffffff;
}
/* 
.tabBar .toEquipment .imgW {
    display: none;
}

.tabBar .toUser .imgB {
    display: none;
} */
</style>
