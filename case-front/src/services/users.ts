import { api } from './api'

export interface User {
  id?: string
  name: string
  role: string
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
    const { data } = await api.post('/users', payload)
    return data
  },

  async update(id: string, payload: Partial<User>): Promise<User> {
    const { data } = await api.patch(`/users/${id}`, payload)
    return data
  },

  async remove(id: number): Promise<void> {
    await api.delete(`/users/${id}`)
  },
}