<template>
  <div class="fixed inset-0 z-50 flex justify-end bg-black/40 backdrop-blur-[1px]">
    <aside class="flex h-full w-full max-w-xl flex-col border-l border-zinc-200 bg-white shadow-2xl transition-transform duration-300 ease-out">
      <header class="flex items-center justify-between border-b border-zinc-200 bg-zinc-50 px-6 py-4">
        <div>
          <p class="text-xs font-semibold uppercase tracking-[0.18em] text-zinc-500">
            Usuário
          </p>
          <h2 class="mt-1 text-2xl font-bold tracking-tight text-zinc-900">
            TODO
          </h2>
        </div>

        <button
          type="button"
          class="inline-flex text-xs px-3 py-1 items-center justify-center rounded-full bg-white font-semibold text-zinc-700 shadow-sm ring-1 ring-zinc-200 transition hover:bg-zinc-100"
          aria-label="Fechar"
          @click="closePanel"
        >
          Fechar
        </button>
      </header>

      <div class="flex items-center justify-between border-b border-zinc-200 px-6 py-4">
        <div>
          <p class="text-sm font-medium text-zinc-500">Endereços cadastrados</p>
          <p class="text-xs text-zinc-400">{{ addresses.length }} registros</p>
        </div>

        <button
          type="button"
          class="inline-flex items-center justify-center rounded-xl bg-zinc-900 px-4 py-2 text-sm font-semibold text-white transition hover:bg-zinc-800"
          @click="addAddress"
        >
          Novo endereço
        </button>
      </div>

      <main class="flex-1 overflow-y-auto p-6">
        <div v-if="addresses.length" class="space-y-4">
          <article
            v-for="(address, index) in addresses"
            :key="address.id ?? index"
            class="rounded-2xl border border-zinc-200 hover:border-blue-400 bg-zinc-50 p-5 shadow-sm group cursor-pointer"
            @click="viewAddress(address.id!)"
          >
            <div class="flex items-start justify-between gap-3">
              <div>
                <p class="text-xs font-semibold uppercase tracking-[0.18em] text-zinc-500">
                  Endereço {{ index + 1 }}
                </p>
                <h3 class="mt-2 text-lg font-semibold text-zinc-900 group-hover:text-blue-400">
                  {{ address.street }}, {{ address.number }}
                </h3>
              </div>

              <button
                type="button"
                class="rounded-lg border border-zinc-200 bg-white px-3 py-1.5 text-xs font-semibold text-zinc-700 transition hover:bg-zinc-100"
                @click="$event.stopPropagation(); editAddress(address.id!)"
              >
                Editar
              </button>
            </div>

            <div class="mt-4 space-y-2 text-sm text-zinc-600">
              <p><span class="font-semibold text-zinc-700">Bairro:</span> {{ address.neighborhood }}</p>
              <p><span class="font-semibold text-zinc-700">Cidade:</span> {{ address.city }} - {{ address.state }}</p>
              <p><span class="font-semibold text-zinc-700">CEP:</span> {{ address.zipcode }}</p>
            </div>
          </article>
        </div>

        <div
          v-else
          class="flex h-full min-h-50 items-center justify-center rounded-2xl border border-dashed border-zinc-300 bg-zinc-50"
        >
          <p class="text-center text-sm text-zinc-500">
            Nenhum endereço cadastrado para este usuário.
          </p>
        </div>
      </main>
    </aside>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { addressService, type Address } from '../../../services/address'

const route = useRoute()
const router = useRouter()
const addresses = ref<Address[]>([])
const userId = computed(() => route.params.id).value as string

onMounted(loadAddresses)

watch(route, () => {
  loadAddresses()
})

async function loadAddresses() {
  addresses.value = await addressService.findByUserId(userId)
}

function closePanel() {
  router.back()
}

function addAddress() {
  router.push({
    name: 'NewUserAddress',
    params: { id: userId }
  })
}

function viewAddress(addressId: string) {
  router.push({
    name: 'ViewUserAddress',
    params: {
      id: userId,
      addressId
    }
  })
}

function editAddress(addressId: string) {
  router.push({
    name: 'EditUserAddress',
    params: {
      id: route.params.id,
      addressId
    }
  })
}
</script>