<template>
  <div class="fixed top-4 right-4 z-50 space-y-2">
    <transition-group name="toast">
      <div
        v-for="toast in toastStore.toasts"
        :key="toast.id"
        :class="[
          'min-w-80 rounded-lg px-4 py-3 text-white shadow-lg animate-in fade-in slide-in-from-right-full duration-300',
          toastClasses[toast.type]
        ]"
      >
        <div class="flex items-center justify-between">
          <span>{{ toast.message }}</span>
          <button
            @click="toastStore.remove(toast.id)"
            class="ml-4 text-white/70 hover:text-white transition"
          >
            ✕
          </button>
        </div>
      </div>
    </transition-group>
  </div>
</template>

<script setup lang="ts">
import { useToastStore } from '../../stores/toast.ts'

const toastStore = useToastStore()

const toastClasses: Record<string, string> = {
  success: 'bg-green-500',
  error: 'bg-red-500',
  info: 'bg-blue-500',
  warning: 'bg-yellow-500'
}
</script>

<style scoped>
.toast-enter-active,
.toast-leave-active {
  transition: all 0.3s ease;
}

.toast-enter-from {
  opacity: 0;
  transform: translateX(100%);
}

.toast-leave-to {
  opacity: 0;
  transform: translateX(100%);
}
</style>
