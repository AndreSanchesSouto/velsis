import { api } from './api'
import { useToastStore } from '../stores/toast'

// Interface que define a estrutura de dados de um Usuário no sistema
export interface User {
  id?: string          // Opcional pois não existe antes de salvar no banco
  name: string
  role: string         // Perfil de acesso (ex: 'ADMIN', 'USER')
  login: string
  password?: string    // Opcional para não expor a senha em listagens
  createdAt?: string
}

// Interface que define a estrutura de paginação padrão retornada pelo backend
export interface UserPage {
  content: User[]      // Lista de usuários da página atual
  totalPages: number   // Quantidade total de páginas disponíveis
  totalElements: number // Quantidade total de registros no banco
  size: number         // Limite de registros por página
  number: number       // Número da página atual
  first: boolean       // Indica se é a primeira página
  last: boolean        // Indica se é a última página
}

// Objeto de serviço responsável pelas requisições HTTP da entidade de Usuários
export const userService = {
  // Busca a lista paginada de usuários com suporte a filtro de busca
  async list(
    search = '',
    page = 0,
    size = 10
  ): Promise<UserPage> {
    // Faz a requisição GET enviando os critérios de paginação e busca via Query Params
    const { data } = await api.get('/users', {
      params: {
        search,
        page,
        size
      }
    })

    return data
  },

  // Busca os detalhes e dados completos de um único usuário através do ID
  async findById(id: string): Promise<User> {
    const { data } = await api.get(`/users/${id}`)
    return data
  },

  // Envia os dados para a criação de um novo usuário no banco de dados
  async create(payload: User): Promise<User> {
    try {
      // Faz a requisição POST com o payload do usuário
      const { data } = await api.post('/users', payload)
      // Dispara um alerta visual de sucesso usando a store global de toasts
      useToastStore().success('Usuário criado com sucesso!')
      return data
    } catch (error) {
      // Repassa o erro para a store tratar e exibir a mensagem correta na tela
      useToastStore().error(error)
      // Lança o erro adiante para permitir tratamento local no componente se necessário
      throw error
    }
  },

  // Atualiza parcialmente os dados de um usuário existente (PATCH)
  async update(id: string, payload: Partial<User>): Promise<User> {
    try {
      // Faz o envio das propriedades alteradas para a rota específica do ID
      const { data } = await api.patch(`/users/${id}`, payload)
      useToastStore().success('Usuário atualizado com sucesso!')
      return data
    } catch (error) {
      useToastStore().error(error)
      throw error
    }
  },

  // Desativa/remove logicamente um usuário do sistema
  async remove(id: string): Promise<void> {
    try {
      // Faz uma requisição PATCH para a rota de desativação (soft delete)
      await api.patch(`/users/disable/${id}`)
      useToastStore().success('Usuário removido com sucesso!')
    } catch (error) {
      useToastStore().error(error)
      throw error
    }
  },
}
