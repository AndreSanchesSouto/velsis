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

      <form
        class="mt-7 flex flex-col gap-5"
        @submit.prevent="handleLogin"
      >
        <label class="flex flex-col gap-2 text-sm font-semibold text-zinc-700">
          Login

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

          <input
            v-model="password"
            type="password"
            placeholder="••••••••"
            required
            class="w-full rounded-xl border border-zinc-300 bg-white px-3.5 py-3.5 text-zinc-900 placeholder:text-zinc-400 focus:border-zinc-900"
          />
        </label>

        <button
          type="submit"
          class="inline-flex min-h-11.5 items-center justify-center rounded-xl bg-zinc-900 px-5 font-semibold text-white transition hover:-translate-y-0.5"
        >
          Entrar
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

import { useAuthStore } from '../../stores/auth.ts'
import { authenticationService, type AuthenticationLogin } from '../../services/authentication.ts'

const router = useRouter()
const authStore = useAuthStore()

const login = ref('')
const password = ref('')

async function handleLogin() {
    await authenticationService.login({
      login: login.value,
      password: password.value
    }).then((response: AuthenticationLogin) => {
      authStore.login(response.token, {
        name: response.name,
        login: response.login,
      })
      console.log(response)

    })

    await router.push({
      name: 'Dashboard'
    })

}
</script>