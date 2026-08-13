<template>
  <main class="min-h-screen bg-zinc-50 p-6 sm:p-10">
    <header class="flex items-center justify-between gap-4">
      <div>
        <h1 class="text-3xl font-bold tracking-tight text-zinc-900">
          Dashboard
        </h1>

        <p class="mt-1 text-zinc-500">
          Bem-vindo de volta!
        </p>
      </div>

      <button
        class="inline-flex min-h-11 items-center justify-center rounded-xl bg-zinc-100 px-5 font-semibold text-zinc-900 transition hover:-translate-y-0.5"
        @click="handleLogout"
      >
        Sair
      </button>
    </header>

    <section class="mt-10">
      <div class="rounded-2xl border border-zinc-200 bg-white p-7 shadow-sm flex items-center justify-between">
        <h2 class="text-2xl font-semibold text-zinc-900">
          Cadastro de usuários
        </h2>
        <button class="bg-zinc-900 text-white px-4 py-2 rounded-lg hover:bg-zinc-800 text-sm transition hover:-translate-y-0.5"
              @click="openNewUser">
          Cadastrar novo usuário
        </button>
      </div>
    </section>
  </main>
  <RouterView />
</template>

<script setup lang="ts">
  import { useRouter } from 'vue-router'
  import { useAuthStore } from '../../stores/auth.ts'

  const router = useRouter()
  const authStore = useAuthStore()

  function handleLogout() {
    authStore.logout()

    router.push({
      name: 'Landing'
    })
  }

  function openNewUser() {
    router.push({
      name: 'NewUser'
    })
  }
</script>