import axios from 'axios'
import { useAuthStore } from '../stores/auth'

export const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL
})

// Request interceptor - adiciona token
api.interceptors.request.use((config) => {
  const authStore = useAuthStore()
  console.log(authStore)
  if (authStore.token) {
    config.headers.Authorization = `Bearer ${authStore.token}`
  }
  return config
})

// Response interceptor - trata erros
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      const authStore = useAuthStore()
      authStore.logout()
      window.location.href = '/login' // redireciona para login
    }
    return Promise.reject(error)
  }
)