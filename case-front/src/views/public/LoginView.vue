<template>
  <main class="flex min-h-screen items-center justify-center bg-zinc-100 p-7">
    <section class="w-full max-w-md rounded-[20px] border border-zinc-200 bg-white p-8 sm:p-10">
      <RouterLink
        to="/"
        class="mb-10 block text-xl font-extrabold tracking-tight text-zinc-900"
      >
        Velsis
      </RouterLink>

      <div>
        <h1 class="m-0 text-3xl font-bold tracking-tight text-zinc-900">
          Bem-vindo de volta
        </h1>

        <p class="mt-2 text-zinc-500">
          Entre na sua conta para continuar.
        </p>
      </div>

      <!-- @submit.prevent: Intercepta o envio do form e previne o recarregamento da página -->
      <form
        class="mt-7 flex flex-col gap-5"
        @submit.prevent="handleLogin"
      >
        <label class="flex flex-col gap-2 text-sm font-semibold text-zinc-700">
          Login

          <!-- v-model: Vincula o input bidirecionalmente com a ref 'login' -->
          <input
            v-model="login"
            type="text"
            placeholder="Seu login"
            required
            class="w-full rounded-xl border border-zinc-300 bg-white px-3.5 py-3.5 text-zinc-900 placeholder:text-zinc-400 focus:border-zinc-900"
          />
        </label>

        <label class="flex flex-col gap-2 text-sm font-semibold text-zinc-700">
          Senha

          <!-- v-model: Vincula o input bidirecionalmente com a ref 'password' -->
          <input
            v-model="password"
            type="password"
            placeholder="••••••••"
            required
            class="w-full rounded-xl border border-zinc-300 bg-white px-3.5 py-3.5 text-zinc-900 placeholder:text-zinc-400 focus:border-zinc-900"
          />
        </label>

        <!-- :disabled: Desabilita o botão dinamicamente baseado no estado de loading -->
        <button
          type="submit"
          :disabled="isLoading"
          class="inline-flex min-h-11.5 items-center justify-center rounded-xl bg-zinc-900 px-5 font-semibold text-white transition hover:-translate-y-0.5 disabled:opacity-50 disabled:cursor-not-allowed"
        >
          <!-- Interpolação {{ }}: Altera o texto do botão de acordo com o estado reativo -->
          {{ isLoading ? 'Entrando...' : 'Entrar' }}
        </button>
      </form>

      <p class="mt-6 text-center text-zinc-500">
        Ainda não possui uma conta?

        <RouterLink
          to="/register"
          class="font-bold text-zinc-900 hover:underline"
        >
          Criar conta
        </RouterLink>
      </p>
    </section>
  </main>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { authenticationService } from '../../services/authentication.ts'

const router = useRouter()

// Estados reativos mapeados para as credenciais e controle de carregamento
const login = ref('')
const password = ref('')
const isLoading = ref(false)

// Lógica de envio e validação da autenticação
async function handleLogin() {
  isLoading.value = true // Ativa estado de carregamento na interface

  try {
    // Dispara a requisição HTTP passando os valores das referências reativas (.value)
    await authenticationService.login({
      login: login.value,
      password: password.value
    })

    // Aguarda o feedback visual antes de redirecionar para a Dashboard
    setTimeout(() => {
      router.push({ name: 'Dashboard' })
    }, 1500)

  } catch {
    console.error('Erro ao fazer login')
  } finally {
    isLoading.value = false // Desativa o carregamento independente do resultado da requisição
  }
}
</script>
