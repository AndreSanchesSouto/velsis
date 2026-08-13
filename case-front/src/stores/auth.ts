import { computed, ref } from 'vue'
import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', () => {
  const token = ref<string | null>(
    localStorage.getItem('access_token')
  )

  const user = ref<{
    name: string
    email: string
  } | null>(null)

  const isAuthenticated = computed(() => !!token.value)

  function login(
    accessToken: string,
    authenticatedUser?: {
      name: string
      email: string
    }
  ) {
    token.value = accessToken

    localStorage.setItem(
      'access_token',
      accessToken
    )

    if (authenticatedUser) {
      user.value = authenticatedUser

      localStorage.setItem(
        'authenticated_user',
        JSON.stringify(authenticatedUser)
      )
    }
  }

  function logout() {
    token.value = null
    user.value = null

    localStorage.removeItem('access_token')
    localStorage.removeItem('authenticated_user')
  }

  function restoreSession() {
    const storedUser = localStorage.getItem(
      'authenticated_user'
    )

    if (storedUser) {
      try {
        user.value = JSON.parse(storedUser)
      } catch {
        user.value = null
      }
    }
  }

  return {
    token,
    user,
    isAuthenticated,
    login,
    logout,
    restoreSession
  }
})