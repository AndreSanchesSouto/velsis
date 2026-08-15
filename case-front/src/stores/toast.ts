import { ref } from 'vue'
import { defineStore } from 'pinia'

// Tipagem para os quatro estilos visuais possíveis do balão de notificação
export type ToastType = 'success' | 'error' | 'info' | 'warning'

// Interface que define a estrutura de dados que cada objeto Toast precisa ter
export interface Toast {
  id: string
  message: string
  type: ToastType
  duration?: number
}

// Criação da store global 'toast' usando a sintaxe de Setup Store do Pinia
export const useToastStore = defineStore('toast', () => {
  // Estado reativo: Array que armazena todos os toasts ativos na fila
  const toasts = ref<Toast[]>([])

  // Função interna para varrer o objeto de erro da API e extrair a mensagem correta
  function processErrorMessage(errorOrMessage: any): string {
    // Se o argumento já for uma string simples, retorna ela mesma direto
    if (typeof errorOrMessage === 'string') {
      return errorOrMessage
    }

    // Tenta ler estruturas comuns de resposta de erro vindas do Axios/Fetch
    if (errorOrMessage?.response?.data) {
      const data = errorOrMessage.response.data

      // Se o backend retornou um objeto de validação de campos (Laravel/Nest padrão)
      if (data.errors && typeof data.errors === 'object') {
        // Transforma o objeto de arrays de erros em uma lista única (flat list) de strings
        const fieldErrors = Object.values(data.errors)
          .flat() as string[]
        
        // Retorna o primeiro erro de validação encontrado na fila
        if (fieldErrors.length > 0) {
          return fieldErrors[0]
        }
      }

      // Se o backend retornou uma propriedade 'message' explícita na raiz do erro
      if (data.message) {
        return data.message
      }
    }

    // Texto padrão caso o erro não coincida com nenhuma das estruturas mapeadas acima
    return 'Ocorreu um erro'
  }

  // Função base para registrar um toast na memória e agendar sua destruição
  function add(message: string, type: ToastType = 'info', duration = 3000) {
    // Gera um ID simples usando o timestamp atual em milissegundos
    const id = Date.now().toString()
    const toast: Toast = { id, message, type, duration }

    // Adiciona o novo toast no final do array reativo para renderizar na tela
    toasts.value.push(toast)

    // Se o tempo de duração for maior que zero, inicia o cronômetro para fechamento
    if (duration > 0) {
      setTimeout(() => {
        remove(id)
      }, duration)
    }

    // Retorna o ID gerado para controle ou manipulação manual externa se necessário
    return id
  }

  // Remove um toast específico da tela filtrando o array pelo ID
  function remove(id: string) {
    toasts.value = toasts.value.filter((t) => t.id !== id)
  }

  // Atalho para disparar uma notificação de sucesso
  function success(message: string, duration?: number) {
    return add(message, 'success', duration)
  }

  // Atalho para tratar e disparar uma notificação de erro vinda de requisições
  function error(messageOrError: any, duration = 3000) {
    const message = processErrorMessage(messageOrError)
    return add(message, 'error', duration)
  }

  // Atalho para disparar uma notificação informativa geral
  function info(message: string, duration?: number) {
    return add(message, 'info', duration)
  }

  // Atalho para disparar um alerta visual de atenção
  function warning(message: string, duration?: number) {
    return add(message, 'warning', duration)
  }

  // Exportação pública de todas as variáveis e funções para serem usadas nos componentes
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
