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
        <button 
          class="bg-zinc-900 text-white px-4 py-2 rounded-lg hover:bg-zinc-800 text-sm transition hover:-translate-y-0.5"
          @click="openNewUser"
        >
          Cadastrar novo usuário
        </button>
      </div>
    </section>
    <section class="mt-10 overflow-hidden rounded-2xl border border-zinc-200 bg-white shadow-sm">
      <div class="overflow-x-auto">
        <table class="min-w-full border-collapse text-left">
          <thead class="bg-zinc-50">
            <tr>
              <th class="px-4 py-3 text-sm font-semibold text-zinc-700">Nome</th>
              <th class="px-4 py-3 text-sm font-semibold text-zinc-700">Função</th>
              <th class="px-4 py-3 text-sm font-semibold text-zinc-700">Data de Criação</th>
              <th class="px-4 py-3 text-sm font-semibold text-zinc-700">Ações</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="user in users"
              :key="user.id"
              class="border-t border-zinc-200"
            >
              <td class="px-4 py-3 text-sm text-zinc-900 hover:text-blue-400 cursor-pointer" @click="openUser(user.id!)">
                {{ user.name }}
              </td>
              <td class="px-4 py-3 text-sm text-zinc-600">{{ user.role }}</td>
              <td class="px-4 py-3 text-sm text-zinc-600">{{ user.createdAt }}</td>
              <td class="px-4 py-3">
                <div class="flex items-center gap-2">
                  <button
                    class="rounded-lg border border-zinc-200 px-3 py-1.5 text-xs font-semibold text-zinc-700 transition hover:border-zinc-300 hover:bg-zinc-50"
                    @click="openEditUser(user.id!)"
                  >
                    Editar
                  </button>
                  <button
                    class="rounded-lg border border-zinc-200 px-3 py-1.5 text-xs font-semibold text-zinc-700 transition hover:border-zinc-300 hover:bg-zinc-50"
                    @click="openAddresses(user.id!)"
                  >
                    Endereços
                  </button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>
  </main>
  <RouterView />
</template>

<script setup lang="ts">
  import { useRoute, useRouter } from 'vue-router'
  import { useAuthStore } from '../../stores/auth.ts'
  import { userService, type User } from '../../services/users.ts'
  import { onMounted, ref, watch } from 'vue'

  const router = useRouter()
  const authStore = useAuthStore()
  const route = useRoute()
  const users = ref<User[]>([])

  onMounted(loadUsers)

  watch(route, () => {
    loadUsers()
  })

  async function loadUsers() {
    users.value = await userService.list()
  }

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

  function openEditUser(userId: string) {
    router.push({
      name: 'EditUser',
      params: { id: userId }
    })
  }

  function openUser(userId: string) {
    router.push({
      name: 'ViewUser',
      params: { id: userId }
    })
  }

  function openAddresses(userId: string) {
    router.push({
      name: 'UserAddresses',
      params: { id: userId }
    })
  }
</script>