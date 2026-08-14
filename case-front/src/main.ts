import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import './style.css'
import { useAuthStore } from './stores/auth'

const app = createApp(App)
const pinia = createPinia()

// 1. Registrar pinia PRIMEIRO
app.use(pinia)

// 2. Agora sim pode usar o store
const authStore = useAuthStore()
authStore.restoreSession()

// 3. Registrar router
app.use(router)

// 4. Montar a app
app.mount('#app')



