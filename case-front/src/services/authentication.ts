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
  token: string
  id: string
  role: string
}

export const authenticationService = {
  // Realiza o cadastro de um novo usuário formatando o payload para a estrutura exigida pelo backend
  async register(payload: Authentication): Promise<boolean> {
    try {
      // Envia a requisição POST agrupando as credenciais dentro do objeto 'authentication'
      const { data } = await api.post('/authentication/register', {
        name: payload.name,
        authentication: {
          login: payload.login,
          password: payload.password,
          confirmPassword: payload.confirmPassword
        }
      })
      // Exibe um feedback visual positivo caso o registro seja bem-sucedido
      useToastStore().success('Cadastro realizado com sucesso!')
      return data
    } catch (error) {
      // Repassa a falha para o gerenciador global de mensagens de erro
      useToastStore().error(error)
      throw error
    }
  },

  // Autentica o usuário enviando o login/senha e inicializa a sessão global no ecossistema da aplicação
  async login(payload: Partial<Authentication>): Promise<AuthenticationLogin> {
    try {
      // Envia as credenciais de acesso para a rota de autenticação
      const { data } = await api.post<AuthenticationLogin>(`/authentication/login`, payload)
      
      // Sincroniza automaticamente com o store
      // Obtém a instância da store de autenticação para injetar os dados recém-recebidos
      const authStore = useAuthStore()
      authStore.login(data.token, {
        id: data.id,
        role: data.role
      })
      
      // Exibe um feedback de boas-vindas ao usuário
      useToastStore().success('Login realizado com sucesso!')
      return data
    } catch (error) {
      useToastStore().error(error)
      throw error
    }
  }
}
