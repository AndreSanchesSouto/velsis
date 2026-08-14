<template>
  <section class="absolute inset-0 flex min-h-screen items-center justify-center bg-black/60 p-6 overflow-hidden">
    <div class="mx-auto w-full max-w-2xl rounded-[20px] border border-zinc-200 bg-white p-8 sm:p-10 overflow-y-auto max-h-[90vh]">
      <div class="mb-6 flex items-center justify-between gap-3">
        <h1
          v-if="mode === 'create'"
          class="text-3xl font-bold tracking-tight text-zinc-900"
        >
          Cadastrar endereço
        </h1>

        <h1
          v-else-if="mode === 'edit'"
          class="text-3xl font-bold tracking-tight text-zinc-900"
        >
          Editar endereço
        </h1>

        <h1
          v-else
          class="text-3xl font-bold tracking-tight text-zinc-900"
        >
          Visualizar endereço
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
        <div class="grid gap-5 md:grid-cols-2">
          <label class="flex w-full flex-col gap-2 text-sm font-semibold text-zinc-700 md:col-span-2">
            CEP
            <input
              v-model="zipcode"
              :readonly="mode === 'view'"
              type="text"
              maxlength="9"
              placeholder="00000-000"
              class="w-full rounded-xl border border-zinc-300 bg-white px-3.5 py-3.5 text-zinc-900 placeholder:text-zinc-400 focus:border-zinc-900"
            />
          </label>

          <label class="flex w-full flex-col gap-2 text-sm font-semibold text-zinc-700">
            Rua
            <input
              v-model="street"
              :readonly="mode === 'view'"
              type="text"
              placeholder="Digite a rua"
              class="w-full rounded-xl border border-zinc-300 bg-white px-3.5 py-3.5 text-zinc-900 placeholder:text-zinc-400 focus:border-zinc-900"
            />
          </label>

          <label class="flex w-full flex-col gap-2 text-sm font-semibold text-zinc-700">
            Número
            <input
              v-model="number"
              :readonly="mode === 'view'"
              type="text"
              placeholder="Ex: 123"
              class="w-full rounded-xl border border-zinc-300 bg-white px-3.5 py-3.5 text-zinc-900 placeholder:text-zinc-400 focus:border-zinc-900"
            />
          </label>
        </div>

        <div class="grid gap-5 md:grid-cols-2">
          <label class="flex w-full flex-col gap-2 text-sm font-semibold text-zinc-700">
            Bairro
            <input
              v-model="neighborhood"
              :readonly="mode === 'view'"
              type="text"
              placeholder="Digite o bairro"
              class="w-full rounded-xl border border-zinc-300 bg-white px-3.5 py-3.5 text-zinc-900 placeholder:text-zinc-400 focus:border-zinc-900"
            />
          </label>

          <label class="flex w-full flex-col gap-2 text-sm font-semibold text-zinc-700">
            Cidade
            <input
              v-model="city"
              :readonly="mode === 'view'"
              type="text"
              placeholder="Digite a cidade"
              class="w-full rounded-xl border border-zinc-300 bg-white px-3.5 py-3.5 text-zinc-900 placeholder:text-zinc-400 focus:border-zinc-900"
            />
          </label>
        </div>

        <div class="grid gap-5 md:grid-cols-1">
          <label class="flex w-full flex-col gap-2 text-sm font-semibold text-zinc-700">
            Estado
            <select
              v-model="state"
              :disabled="mode === 'view'"
              class="w-full rounded-xl border border-zinc-300 bg-white px-3.5 py-3.5 text-zinc-900 focus:border-zinc-900"
            >
              <option value="">Selecione o estado</option>
              <option v-for="uf in ufs" :key="uf" :value="uf">
                {{ uf }}
              </option>
            </select>
          </label>
        </div>

        <div class="flex w-full items-center justify-between gap-3 pt-2">
          <button
            type="button"
            class="inline-flex min-h-11.5 items-center justify-center rounded-xl bg-zinc-200 px-5 text-sm font-semibold text-zinc-800 transition hover:-translate-y-0.5"
            @click="close"
          >
            Fechar
          </button>

          <button
            v-if="mode !== 'view'"
            type="submit"
            class="inline-flex min-h-11.5 items-center justify-center rounded-xl bg-zinc-900 px-5 text-sm font-semibold text-white transition hover:-translate-y-0.5"
          >
            {{ mode === 'create' ? 'Cadastrar' : 'Salvar alterações' }}
          </button>
        </div>
      </form>
    </div>
  </section>
</template>

<script setup lang="ts">
  import { computed, onMounted, ref, watch } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
  import { addressService } from '../../../services/address'

  type AddressFormMode = 'create' | 'edit' | 'view'

  
  const route = useRoute()
  const router = useRouter()
  const userId = computed(() => route.params.id).value as string
  const id = computed(() => route.params.addressId).value as string

  const street = ref('')
  const number = ref('')
  const neighborhood = ref('')
  const city = ref('')
  const state = ref('')
  const zipcode = ref('')

  const ufs = [
    'AC', 'AL', 'AP', 'AM', 'BA', 'CE', 'DF', 'ES', 'GO', 'MA', 'MT', 'MS',
    'MG', 'PA', 'PB', 'PR', 'PE', 'PI', 'RJ', 'RN', 'RS', 'RO', 'RR', 'SC',
    'SP', 'SE', 'TO'
  ]

  
  onMounted(async () => {
    if (id) {
      const address = await addressService.findById(id)
        street.value = address.street
        number.value = address.number
        neighborhood.value = address.neighborhood
        city.value = address.city
        state.value = address.state
        zipcode.value = address.zipcode
    }
  })

  const mode = computed<AddressFormMode>(() => {
    if (route.name === 'NewUserAddress') {
      return 'create'
    }

    if (route.name === 'EditUserAddress') {
      return 'edit'
    }

    return 'view'
  })

  watch(zipcode, async (value) => {
    if (mode.value === 'view' || value.length < 8) {
      return
    }

    const cleaned = value.replace(/\D/g, '')

    if (cleaned.length !== 8) {
      return
    }

    try {
      const response = await fetch(`https://viacep.com.br/ws/${cleaned}/json/`)
      const data = await response.json()

      if (data.erro) {
        return
      }

      street.value = data.logradouro || ''
      neighborhood.value = data.bairro || ''
      city.value = data.localidade || ''
      state.value = data.uf || ''
    } catch { }
  })

  function close() {
    router.back()
  }

  async function handleSubmit(event: Event) {
    event.preventDefault()
    if (mode.value === 'create') {
      await addressService.create(
        userId, 
        {
          street: street.value,
          number: number.value,
          neighborhood: neighborhood.value,
          city: city.value,
          state: state.value,
          uf: state.value,
          zipcode: zipcode.value
        }
      ).then(() => {
        close()
      })

    } else if(mode.value === 'edit') {
      await addressService.update(
        id, 
        {
          street: street.value,
          number: number.value,
          neighborhood: neighborhood.value,
          city: city.value,
          state: state.value,
          uf: state.value,
          zipcode: zipcode.value
        }
      ).then(() => {
        close()
      })
    }
  }

  async function handleDelete() {
    await addressService.remove(id)
      .then(() => {
        close()
      })
  }
</script>