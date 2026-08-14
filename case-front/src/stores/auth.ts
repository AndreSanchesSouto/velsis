import { computed, ref } from 'vue'
import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', () => {
  const token = ref<string | null>(null)
  const user = ref<{ id: string, role: string } | null>(null)
  const isAuthenticated = computed(() => !!token.value)
  const isSessionRestored = ref(false) // flag para saber se já restaurou

  function login(accessToken: string, authenticatedUser?: { id: string, role: string }) {
    token.value = accessToken
    localStorage.setItem('access_token', accessToken)
    if (authenticatedUser) {
      user.value = authenticatedUser
      localStorage.setItem('authenticated_user', JSON.stringify(authenticatedUser))
    }
  }

  function logout() {
    token.value = null
    user.value = null
    localStorage.removeItem('access_token')
    localStorage.removeItem('authenticated_user')
  }

  function restoreSession() {
    const storedToken = localStorage.getItem('access_token')
    const storedUser = localStorage.getItem('authenticated_user')

    if (storedToken) {
      token.value = storedToken
    }

    if (storedUser) {
      try {
        user.value = JSON.parse(storedUser)
      } catch {
        user.value = null
      }
    }

    isSessionRestored.value = true
  }

  return {
    token,
    user,
    isAuthenticated,
    isSessionRestored,
    login,
    logout,
    restoreSession
  }
})