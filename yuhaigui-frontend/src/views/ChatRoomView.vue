<template>
  <a-layout class="chat-container">
    <!-- 聊天区域 -->
    <a-layout-content class="chat-content">
      <div class="room-id">房间号: {{ roomId }}</div>
      <div class="message-list" ref="messageListRef">
        <div v-for="(msg, index) in messages" :key="index" class="message-item">
          <!-- AI 消息 -->
          <a-comment v-if="msg.isAI" class="ai-message">
            <template #avatar>
              <a-avatar src="ai-avatar.png" />
            </template>
            <template #content>
              <div class="message-bubble ai">{{ msg.content }}</div>
            </template>
          </a-comment>

          <!-- 用户消息 -->
          <a-comment v-else class="user-message">
            <template #avatar>
              <a-avatar src="user-avatar.png" />
            </template>
            <template #content>
              <div class="message-bubble user">{{ msg.content }}</div>
            </template>
          </a-comment>
        </div>
      </div>
    </a-layout-content>

    <!-- 输入区域 -->
    <a-layout-footer class="input-area">
      <div class="action-buttons">
        <a-button
          type="primary"
          :disabled="isGameStarted"
          @click="handleStart"
        >
          开始
        </a-button>
        <a-button
          danger
          :disabled="isGameEnded"
          @click="handleEnd"
        >
          结束
        </a-button>
      </div>
      <a-input-group compact>
        <a-input
          v-model:value="inputMessage"
          placeholder="输入你的问题..."
          @pressEnter="sendMessage"
        />
        <a-button type="primary" @click="sendMessage">发送</a-button>
      </a-input-group>
    </a-layout-footer>
  </a-layout>
</template>

<script setup>
import { ref, computed, onMounted, nextTick } from 'vue'
import { useRoute } from 'vue-router'
import axios from 'axios'

const route = useRoute()
const roomId = ref(route.params.roomId)
const messages = ref([])
const inputMessage = ref('')
const messageListRef = ref(null)
const isGameStarted = ref(false)
const isGameEnded = ref(false)

// 自动滚动到底部
const scrollToBottom = () => {
  nextTick(() => {
    if (messageListRef.value) {
      messageListRef.value.scrollTop = messageListRef.value.scrollHeight
    }
  })
}

// 发送消息
const sendMessage = async () => {
  if (!inputMessage.value.trim()) return

  try {
    // 用户消息
    messages.value.push({
      content: inputMessage.value,
      isAI: false
    })

    // AI 回复
    const response = await axios.post(
      `http://localhost:8080/api/chat/${roomId.value}/send`,
      null,
      { params: { message: inputMessage.value } }
    )

    messages.value.push({
      content: response.data,
      isAI: true
    })

    // 检查游戏结束
    if (response.data.includes('游戏已结束')) {
      isGameEnded.value = true
    }

    inputMessage.value = ''
    scrollToBottom()
  } catch (error) {
    console.error('发送消息失败:', error)
  }
}

// 处理开始游戏
const handleStart = async () => {
  isGameStarted.value = true
  await sendMessage()
}

// 处理结束游戏
const handleEnd = () => {
  isGameEnded.value = true
  messages.value.push({
    content: '游戏已结束',
    isAI: true
  })
}
</script>

<style scoped>
.chat-container {
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.chat-content {
  flex: 1;
  padding: 20px;
  overflow: hidden;
}

.room-id {
  font-size: 1.2rem;
  margin-bottom: 1rem;
}

.message-list {
  height: calc(100% - 60px);
  overflow-y: auto;
}

.message-item {
  margin-bottom: 1rem;
}

.message-bubble {
  max-width: 70%;
  padding: 12px;
  border-radius: 8px;
  word-break: break-word;
}

.ai-message {
  display: flex;
  justify-content: flex-start;
}

.user-message {
  display: flex;
  justify-content: flex-end;
}

.ai .message-bubble {
  background: #f0f2f5;
}

.user .message-bubble {
  background: #1890ff;
  color: white;
}

.input-area {
  padding: 16px;
  background: #fff;
  box-shadow: 0 -2px 8px rgba(0,0,0,0.1);
}

.action-buttons {
  margin-bottom: 8px;
}
</style>
