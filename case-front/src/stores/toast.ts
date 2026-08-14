import { ref } from 'vue'
import { defineStore } from 'pinia'

export type ToastType = 'success' | 'error' | 'info' | 'warning'

export interface Toast {
  id: string
  message: string
  type: ToastType
  duration?: number
}

export const useToastStore = defineStore('toast', () => {
  const toasts = ref<Toast[]>([])

  function processErrorMessage(errorOrMessage: any): string {
    if (typeof errorOrMessage === 'string') {
      return errorOrMessage
    }

    if (errorOrMessage?.response?.data) {
      const data = errorOrMessage.response.data

      if (data.errors && typeof data.errors === 'object') {
        const fieldErrors = Object.values(data.errors)
          .flat() as string[]
        
        if (fieldErrors.length > 0) {
          return fieldErrors[0]
        }
      }

      if (data.message) {
        return data.message
      }
    }

    // Fallback
    return 'Ocorreu um erro'
  }

  function add(message: string, type: ToastType = 'info', duration = 3000) {
    const id = Date.now().toString()
    const toast: Toast = { id, message, type, duration }

    toasts.value.push(toast)

    if (duration > 0) {
      setTimeout(() => {
        remove(id)
      }, duration)
    }

    return id
  }

  function remove(id: string) {
    toasts.value = toasts.value.filter((t) => t.id !== id)
  }

  function success(message: string, duration?: number) {
    return add(message, 'success', duration)
  }

  function error(messageOrError: any, duration = 3000) {
    const message = processErrorMessage(messageOrError)
    return add(message, 'error', duration)
  }

  function info(message: string, duration?: number) {
    return add(message, 'info', duration)
  }

  function warning(message: string, duration?: number) {
    return add(message, 'warning', duration)
  }

  return {
    toasts,
    add,
    remove,
    success,
    error,
    info,
    warning
  }
})
