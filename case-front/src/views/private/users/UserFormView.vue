
<template>
  <section class="absolute inset-0 flex min-h-screen items-center justify-center bg-black/60 p-6">
    <div class="mx-auto w-full max-w-md rounded-[20px] border border-zinc-200 bg-white p-8 sm:p-10">
      <h1
        v-if="mode === 'create'"
        class="mb-6 text-3xl font-bold tracking-tight text-zinc-900"
      >
        Cadastrar usuário
      </h1>

      <h1
        v-else-if="mode === 'edit'"
        class="mb-6 text-3xl font-bold tracking-tight text-zinc-900"
      >
        Editar usuário
      </h1>

      <h1
        v-else
        class="mb-6 text-3xl font-bold tracking-tight text-zinc-900"
      >
        Visualizar usuário
      </h1>

      <div class="flex flex-col gap-5">
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
          Função
          <select
            v-model="role"
            :disabled="mode === 'view'"
            class="w-full rounded-xl border border-zinc-300 bg-white px-3.5 py-3.5 text-zinc-900 focus:border-zinc-900"
          >
            <option value="">Selecione</option>
            <option value="Administrador">Administrador</option>
            <option value="Usuário">Usuário</option>
          </select>
        </label>
      </div>
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
          type="button"
          class="mt-7 inline-flex min-h-11.5 items-center justify-center rounded-xl bg-zinc-900 px-5 text-sm font-semibold text-white transition hover:-translate-y-0.5"
        >
          {{ mode === 'create' ? 'Cadastrar' : 'Salvar alterações' }}
        </button>
      </div>
    </div>
  </section>
</template>

<script setup lang="ts">
  import { computed } from 'vue'
  import { useRoute } from 'vue-router'
  import { ref } from 'vue'

  type UserFormMode = 'create' | 'edit' | 'view'

  const route = useRoute()
  const id = computed(() => route.params.id)
  const name = ref('')
  const role = ref('')

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
    window.history.back()
  }
</script>