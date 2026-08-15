<template>
  <!-- Renderiza dinamicamente o componente da rota atual -->
  <router-view />
  <!-- Container global para exibição de notificações/toasts -->
  <ToastContainer />
</template>

<script setup lang="ts">
import ToastContainer from './components/ui/ToastContainer.vue'
</script>

<style>
</style>