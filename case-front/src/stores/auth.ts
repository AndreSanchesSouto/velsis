import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { api } from '../services/api.ts'

export const useAuthStore = defineStore('auth', () => {
  const token = ref<string | null>(localStorage.getItem('token') || null)
  const user = ref<any>(null)

  const isAuthenticated = computed(() => !!token.value)

  async function login(email: string, password: string) {
    try {
      const response = await api.post('/auth/login', { email, password })
      const { token: accessToken, user: userData } = response.data
      token.value = accessToken
      user.value = userData
      localStorage.setItem('token', accessToken)
      return { success: true }
    } catch (error: any) {
      return { success: false, error: error.response?.data?.message || 'Erro ao fazer login' }
    }
  }

  function logout() {
    token.value = null
    user.value = null
    localStorage.removeItem('token')
  }

  return { token, user, isAuthenticated, login, logout }
})