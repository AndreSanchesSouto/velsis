<template>
  <main class="login">
    <section class="login-card">
      <h1>Entrar</h1>

      <form @submit.prevent="handleLogin">
        <input
          v-model="email"
          type="email"
          placeholder="E-mail"
        />

        <input
          v-model="password"
          type="password"
          placeholder="Senha"
        />

        <button type="submit">
          Entrar
        </button>
      </form>
    </section>
  </main>
</template>

<script setup>
import { ref } from 'vue'
console.log("alou")
const email = ref('')
const password = ref('')

function handleLogin() {
  console.log(email.value, password.value)
}
</script>

<style scoped>
.login {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
}

.login-card {
  width: 100%;
  max-width: 400px;
  padding: 2rem;
}

.login-card form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.login-card input {
  padding: 0.75rem;
}

.login-card button {
  padding: 0.75rem;
  cursor: pointer;
}
</style>