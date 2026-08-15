
<template>
  <section class="absolute inset-0 flex min-h-screen items-center justify-center bg-black/60 p-6 overflow-hidden">
    <div class="mx-auto w-full max-w-md rounded-[20px] border border-zinc-200 bg-white p-8 sm:p-10 overflow-y-auto max-h-[90vh]">
      <div class="flex w-full justify-between items-center mb-6">
        <h1
          v-if="mode === 'create'"
          class="text-3xl font-bold tracking-tight text-zinc-900"
        >
          Cadastrar usuário
        </h1>
  
        <h1
          v-else-if="mode === 'edit'"
          class="text-3xl font-bold tracking-tight text-zinc-900"
        >
          Editar usuário
        </h1>
  
        <h1
          v-else
          class="text-3xl font-bold tracking-tight text-zinc-900"
        >
          Visualizar usuário
        </h1>
        <button
          v-if="mode === 'edit'" 
          class="hover:text-red-400 duration-200 transform-all text-slate-500"
          @click="handleDelete"
          >
          Deletar
        </button>
      </div>

      <form @submit="handleSubmit($event)" class="flex flex-col gap-5">
        <label class="flex w-full flex-col gap-2 text-sm font-semibold text-zinc-700">
          Nome
          <input
            v-model="name"
            :readonly="mode === 'view'"
            type="text"
            placeholder="Digite o nome"
            class="w-full rounded-xl border border-zinc-300 bg-white px-3.5 py-3.5 text-zinc-900 placeholder:text-zinc-400 focus:border-zinc-900"
          />
        </label>

        <label class="flex w-full flex-col gap-2 text-sm font-semibold text-zinc-700">
          Login
          <input
            v-model="login"
            :readonly="mode === 'view'"
            type="text"
            placeholder="Digite o login"
            class="w-full rounded-xl border border-zinc-300 bg-white px-3.5 py-3.5 text-zinc-900 placeholder:text-zinc-400 focus:border-zinc-900"
          />
        </label>

        <label 
          v-if="mode === 'create'"
          class="flex w-full flex-col gap-2 text-sm font-semibold text-zinc-700"
          >
          Senha
          <input
            v-model="password"
            :readonly="mode !== 'create'"
            type="text"
            placeholder="Digite a login"
            class="w-full rounded-xl border border-zinc-300 bg-white px-3.5 py-3.5 text-zinc-900 placeholder:text-zinc-400 focus:border-zinc-900"
          />
        </label>

        <label class="flex w-full flex-col gap-2 text-sm font-semibold text-zinc-700">
          Função
          <select
            v-model="role"
            :disabled="mode === 'view'"
            class="w-full rounded-xl border border-zinc-300 bg-white px-3.5 py-3.5 text-zinc-900 focus:border-zinc-900"
          >
            <option value="">Selecione</option>
            <option value="ADMIN">Administrador</option>
            <option value="USER">Usuário</option>
          </select>
        </label>
        <div class="w-full items-center justify-between flex ">
          <button
            type="button"
            class="mt-7 inline-flex min-h-11.5 items-center justify-center rounded-xl bg-zinc-200 px-5 text-sm font-semibold text-zinc-800 transition hover:-translate-y-0.5"
            @click="close()"
          >
            Fechar
          </button>
          <button
            v-if="mode !== 'view'"
            type="submit"
            class="mt-7 inline-flex min-h-11.5 items-center justify-center rounded-xl bg-zinc-900 px-5 text-sm font-semibold text-white transition hover:-translate-y-0.5"
          >
            {{ mode === 'create' ? 'Cadastrar' : 'Salvar alterações' }}
          </button>
        </div>
      </form>

    </div>
  </section>
</template>

<script setup lang="ts">
  import { computed, onMounted } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
  import { ref } from 'vue'
  import { userService } from '../../../services/users'

  type UserFormMode = 'create' | 'edit' | 'view'

  const route = useRoute()
  const router = useRouter()
  const id = computed(() => route.params.id)
  const name = ref('')
  const login = ref('')
  const role = ref('')
  const password = ref('')

  onMounted(async () => {
    if(id.value) {
      const user = await userService.findById(id.value as string)
      name.value = user.name
      login.value = user.login
      role.value = user.role
    }
  })

  const mode = computed<UserFormMode>(() => {
    if (route.name === 'NewUser') {
      return 'create'
    }

    if (route.name === 'EditUser') {
      return 'edit'
    }

    return 'view'
  })

  function close() {
    router.back()
  }

  async function handleSubmit(event: Event) {
    event.preventDefault()
    if (mode.value === 'create') {
      await userService
        .create({ 
          name: name.value,
          login: login.value,
          password: password.value,
          role: role.value 
        })
        .then(() => { close() })
    } else if (mode.value === 'edit') {
      await userService
        .update(
          id.value as string, 
          { 
            name: name.value,
            login: login.value,
            role: role.value
          }
        )
        .then(() => { close() })
    }
  }

  async function handleDelete(event: Event) {
    event.preventDefault()
    await userService.remove(id.value as string).then(() => {
      close()
    })
  }
</script>