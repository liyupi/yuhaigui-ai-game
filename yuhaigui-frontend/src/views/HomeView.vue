<template>
    <a-row type="flex" justify="center" align="middle" class="home-container">
        <a-col :span="24" class="text-center">
            <h1 class="title">AI 海龟汤</h1>
            <a-button type="primary" size="large" @click="startNewGame">开始游戏</a-button>
        </a-col>
    </a-row>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const startNewGame = () => {
    const roomId = Date.now() // 生成唯一房间号
    router.push({ name: 'chat', params: { roomId } })
}
</script>

<style scoped>
.home-container {
    height: 100vh;
}
.title {
    font-size: 2.5rem;
    margin-bottom: 2rem;
}
</style>
