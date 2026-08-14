import { api } from './api'

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
    const { data } = await api.post(`/addresses/user/${userId}`, payload)
    return data
  },

  async update(id: string, payload: Partial<Address>): Promise<Address> {
    const { data } = await api.patch(`/addresses/${id}`, payload)
    return data
  },

  async remove(id: number): Promise<void> {
    await api.delete(`/addresses/${id}`)
  },
}