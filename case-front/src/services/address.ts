import { api } from './api'
import { useToastStore } from '../stores/toast'

export interface Address {
  id?: string
  street: string
  number: string
  neighborhood: string
  city: string
  state: string
  zipcode: string
  uf: string
}

export const addressService = {
  async list(): Promise<Address[]> {
    const { data } = await api.get('/addresses')
    return data
  },

  async findById(id: string): Promise<Address> {
    const { data } = await api.get(`/addresses/${id}`)
    return data
  },

  async findByUserId(userId: string): Promise<Address[]> {
    const { data } = await api.get(`/addresses/user/${userId}`)
    return data
  },

  async create(userId: string, payload: Address): Promise<Address> {
    try {
      const { data } = await api.post(`/addresses/user/${userId}`, payload)
      useToastStore().success('Endereço criado com sucesso!')
      return data
    } catch (error) {
      useToastStore().error(error)
      throw error
    }
  },

  async update(id: string, payload: Partial<Address>): Promise<Address> {
    try {
      const { data } = await api.patch(`/addresses/${id}`, payload)
      useToastStore().success('Endereço atualizado com sucesso!')
      return data
    } catch (error) {
      useToastStore().error(error)
      throw error
    }
  },

  async remove(id: string): Promise<void> {
    try {
      await api.patch(`/addresses/disable/${id}`)
      useToastStore().success('Endereço removido com sucesso!')
    } catch (error) {
      useToastStore().error(error)
      throw error
    }
  },
}