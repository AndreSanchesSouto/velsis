import { api } from './api'
import { useToastStore } from '../stores/toast'

export interface User {
  id?: string
  name: string
  role: string
  login: string
  password?: string
  createdAt?: string
}

export const userService = {
  async list(): Promise<User[]> {
    const { data } = await api.get('/users')
    return data
  },

  async findById(id: string): Promise<User> {
    const { data } = await api.get(`/users/${id}`)
    return data
  },

  async create(payload: User): Promise<User> {
    try {
      const { data } = await api.post('/users', payload)
      useToastStore().success('Usuário criado com sucesso!')
      return data
    } catch (error) {
      useToastStore().error(error)
      throw error
    }
  },

  async update(id: string, payload: Partial<User>): Promise<User> {
    try {
      const { data } = await api.patch(`/users/${id}`, payload)
      useToastStore().success('Usuário atualizado com sucesso!')
      return data
    } catch (error) {
      useToastStore().error(error)
      throw error
    }
  },

  async remove(id: string): Promise<void> {
    try {
      await api.patch(`/users/disable/${id}`)
      useToastStore().success('Usuário removido com sucesso!')
    } catch (error) {
      useToastStore().error(error)
      throw error
    }
  },
}