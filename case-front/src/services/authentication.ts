import { useAuthStore } from '../stores/auth'
import { useToastStore } from '../stores/toast'
import { api } from './api'

export interface Authentication {
  name?: string
  login: string
  password: string
  confirmPassword?: string
}

export interface AuthenticationLogin {
  name: string
  login: string
  token: string
}

export const authenticationService = {
  async register(payload: Authentication): Promise<boolean> {
    try {
      const { data } = await api.post('/authentication/register', {
        name: payload.name,
        authentication: {
          login: payload.login,
          password: payload.password,
          confirmPassword: payload.confirmPassword
        }
      })
      useToastStore().success('Cadastro realizado com sucesso!')
      return data
    } catch (error) {
      useToastStore().error(error)
      throw error
    }
  },

  async login(payload: Partial<Authentication>): Promise<AuthenticationLogin> {
    try {
      const { data } = await api.post<AuthenticationLogin>(`/authentication/login`, payload)
      
      // Sincroniza automaticamente com o store
      const authStore = useAuthStore()
      authStore.login(data.token, {
        name: data.name,
        login: data.login
      })
      
      useToastStore().success('Login realizado com sucesso!')
      return data
    } catch (error) {
      useToastStore().error(error)
      throw error
    }
  }
}