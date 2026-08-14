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
        <h2 class="tex-sm md:text-2xl font-semibold text-zinc-900">
          Cadastro de usuários
        </h2>
        <button
          v-if="isAuthorized(null)"
          class="bg-zinc-900 text-white px-4 py-2 rounded-lg hover:bg-zinc-800 text-sm transition hover:-translate-y-0.5"
          @click="openNewUser"
        >
          Cadastrar novo usuário
        </button>
      </div>
    </section>
    <section class="rounded-2xl border border-zinc-200 bg-white p-3 shadow-sm gap-5 flex items-center mt-3">
      <label class="font-bold">Prucurar: </label>
      <input
        v-model="search"
        type="text"
        placeholder="Buscar usuário..."
        class="w-full md:w-72 rounded-lg border border-zinc-200 px-4 py-2 text-sm outline-none focus:border-zinc-400"
      />
      <select
        v-model="size"
        class="rounded-xl border border-zinc-200 px-3 py-1"
      >
        <option :value="5">5</option>
        <option :value="10">10</option>
        <option :value="25">25</option>
        <option :value="50">50</option>
        <option :value="100">100</option>
      </select>
      <div
        v-if="totalPages > 1"
        class="flex items-center justify-center gap-2 p-5"
      >
        <button
          v-for="pageNumber in totalPages"
          :key="pageNumber"
          class="h-9 w-9 rounded-lg text-sm font-semibold transition"
          :class="
            page === pageNumber - 1
              ? 'bg-zinc-900 text-white'
              : 'border border-zinc-200 text-zinc-700 hover:bg-zinc-50'
          "
          @click="changePage(pageNumber - 1)"
        >
          {{ pageNumber }}
        </button>
      </div>
    </section>
    <section class="mt-10 overflow-hidden rounded-2xl border border-zinc-200 bg-white shadow-sm">
      <div class="overflow-x-auto">
        <table class="min-w-full border-collapse text-left">
          <thead class="bg-zinc-50">
            <tr>
              <th class="px-4 py-3 text-sm font-semibold text-zinc-700">Nome</th>
              <th class="px-4 py-3 text-sm font-semibold text-zinc-700">Login</th>
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
              <td class="px-4 py-3 text-sm text-zinc-600">{{ user.login }}</td>
              <td class="px-4 py-3 text-sm text-zinc-600">{{ user.role }}</td>
              <td class="px-4 py-3 text-sm text-zinc-600">{{ user.createdAt }}</td>
              <td class="px-4 py-3">
                <div class="flex items-center gap-2">
                  <button
                    v-if="isAuthorized(user.id!)"
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
import { computed, onMounted, ref, watch } from 'vue'

const router = useRouter()
const authStore = useAuthStore()
const route = useRoute()
const users = ref<User[]>([])
const search = ref('')
const page = ref(0)
const size = ref(10)

const totalPages = ref(0)
const isAdmin = computed(() => authStore.user?.role === 'ADMIN')

const isAuthorized = (targetUserId: string | null) => {
  return isAdmin.value
    ? true
    : targetUserId === authStore.user?.id
}

onMounted(loadUsers)

watch(route, () => {
  loadUsers()
})

watch(search, () => {
  page.value = 0
  loadUsers()
})

watch(size, () => {
  page.value = 0
  loadUsers()
})

async function loadUsers() {
  const response = await userService.list(
    search.value,
    page.value,
    size.value
  )

  users.value = response.content
  totalPages.value = response.totalPages
}

function changePage(newPage: number) {
  page.value = newPage
  loadUsers()
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