import { computed, ref } from 'vue'
import { defineStore } from 'pinia'

// Criação da store global 'auth' usando a sintaxe de Setup Store do Pinia
export const useAuthStore = defineStore('auth', () => {
  // Estado reativo: Armazena o token JWT de acesso ou nulo se não logado
  const token = ref<string | null>(null)
  
  // Estado reativo: Armazena as informações básicas do usuário logado (ID e perfil de acesso)
  const user = ref<{ id: string, role: string } | null>(null)
  
  // Computada: Retorna true se houver um token válido (converte a string para booleano com !!)
  const isAuthenticated = computed(() => !!token.value)
  
  // Estado reativo: Sinaliza se o processo de leitura do localStorage já foi finalizado
  const isSessionRestored = ref(false) // flag para saber se já restaurou

  // Função para injetar os dados de autenticação no estado e persistir no navegador
  function login(accessToken: string, authenticatedUser?: { id: string, role: string }) {
    token.value = accessToken
    // Salva o token bruto em formato de string no localStorage
    localStorage.setItem('access_token', accessToken)
    
    // Se os dados do usuário forem passados, guarda-os no estado e no localStorage
    if (authenticatedUser) {
      user.value = authenticatedUser
      // Transforma o objeto do usuário em string JSON para conseguir salvar no localStorage
      localStorage.setItem('authenticated_user', JSON.stringify(authenticatedUser))
    }
  }

  // Função para limpar completamente os dados de autenticação (Deslogar)
  function logout() {
    token.value = null
    user.value = null
    // Remove as chaves do armazenamento local para evitar login automático na próxima visita
    localStorage.removeItem('access_token')
    localStorage.removeItem('authenticated_user')
  }

  // Função para resgatar a sessão salva no localStorage ao recarregar a página
  function restoreSession() {
    const storedToken = localStorage.getItem('access_token')
    const storedUser = localStorage.getItem('authenticated_user')

    // Se encontrou um token salvo, restaura para o estado reativo
    if (storedToken) {
      token.value = storedToken
    }

    // Se encontrou um usuário salvo, tenta fazer o parse do JSON com segurança
    if (storedUser) {
      try {
        user.value = JSON.parse(storedUser)
      } catch {
        // Se o JSON estiver corrompido, limpa o estado do usuário por segurança
        user.value = null
      }
    }

    // Define a flag como true, indicando que a verificação inicial terminou
    isSessionRestored.value = true
  }

  // Exportação pública de todas as propriedades e métodos para uso nos componentes e guards
  return {
    token,
    user,
    isAuthenticated,
    isSessionRestored,
    login,
    logout,
    restoreSession
  }
})
