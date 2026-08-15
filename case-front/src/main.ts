import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import './style.css'
import { useAuthStore } from './stores/auth'

const app = createApp(App)
const pinia = createPinia()

// 1. Instala o Pinia no Vue (obrigatório antes de usar qualquer Store)
app.use(pinia)

// 2. Recupera a sessão do localStorage antes das rotas carregarem
const authStore = useAuthStore()
authStore.restoreSession()

// 3. Instala as rotas (garante que os guardas de rotas leiam o usuário já logado)
app.use(router)

// 4. Monta a aplicação no HTML
app.mount('#app')
