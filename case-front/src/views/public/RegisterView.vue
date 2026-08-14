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
          Crie sua conta
        </h1>

        <p class="mt-2 text-zinc-500">
          Comece agora mesmo.
        </p>
      </div>

      <form
        class="mt-7 flex flex-col gap-5"
        @submit.prevent="handleRegister"
      >
        <label class="flex flex-col gap-2 text-sm font-semibold text-zinc-700">
          Nome

          <input
            v-model="name"
            type="text"
            placeholder="Seu nome"
            required
            class="w-full rounded-xl border border-zinc-300 bg-white px-3.5 py-3.5 text-zinc-900 placeholder:text-zinc-400 focus:border-zinc-900"
          />
        </label>

        <label class="flex flex-col gap-2 text-sm font-semibold text-zinc-700">
          Usuário

          <input
            v-model="username"
            type="text"
            placeholder="Seu usuário"
            required
            class="w-full rounded-xl border border-zinc-300 bg-white px-3.5 py-3.5 text-zinc-900 placeholder:text-zinc-400 focus:border-zinc-900"
          />
        </label>
        
        

        <label class="flex flex-col gap-2 text-sm font-semibold text-zinc-700">
          Senha

          <input
            v-model="password"
            type="password"
            placeholder="••••••••"
            required
            class="w-full rounded-xl border border-zinc-300 bg-white px-3.5 py-3.5 text-zinc-900 placeholder:text-zinc-400 focus:border-zinc-900"
          />
        </label>

        <label class="flex flex-col gap-2 text-sm font-semibold text-zinc-700">
          Confirmar senha
          <input
            v-model="passwordConfirmation"
            type="password"
            placeholder="••••••••"
            required
            class="w-full rounded-xl border border-zinc-300 bg-white px-3.5 py-3.5 text-zinc-900 placeholder:text-zinc-400 focus:border-zinc-900"
          />
        </label>

        <button
          type="submit"
          :disabled="isLoading"
          class="inline-flex min-h-11.5 items-center justify-center rounded-xl bg-zinc-900 px-5 font-semibold text-white transition hover:-translate-y-0.5 disabled:opacity-50 disabled:cursor-not-allowed"
        >
          {{ isLoading ? 'Criando conta...' : 'Criar conta' }}
        </button>
      </form>

      <p class="mt-6 text-center text-zinc-500">
        Já possui uma conta?

        <RouterLink
          to="/login"
          class="font-bold text-zinc-900 hover:underline"
        >
          Entrar
        </RouterLink>
      </p>
    </section>
  </main>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { authenticationService } from '../../services/authentication'

const router = useRouter()

const name = ref('')
const username = ref('')
const password = ref('')
const passwordConfirmation = ref('')
const isLoading = ref(false)

async function handleRegister() {
  isLoading.value = true

  try {
    await authenticationService.register({
      name: name.value,
      login: username.value,
      password: password.value,
      confirmPassword: passwordConfirmation.value
    })

    // Toast de sucesso e redirecionamento
    setTimeout(() => {
      router.push({ name: 'Login' })
    }, 1500)

  } catch {
    console.error('Erro ao criar conta')
  } finally {
    isLoading.value = false
  }
}
</script>