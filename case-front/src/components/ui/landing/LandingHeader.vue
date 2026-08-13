<template>
  <header class="fixed inset-x-0 top-0 z-50 border-b border-zinc-200/80 bg-white/80 backdrop-blur-xl">
    <nav class="mx-auto flex h-18 w-full max-w-6xl items-center justify-between px-5 sm:px-6">
      <RouterLink
        to="/"
        class="text-xl font-extrabold tracking-tight text-zinc-900"
      >
        Velsis
      </RouterLink>

      <div class="flex items-center gap-3">
        <RouterLink
          to="/login"
          class="hidden min-h-11 items-center justify-center rounded-xl bg-zinc-100 px-5 font-semibold text-zinc-900 transition hover:-translate-y-0.5 sm:inline-flex"
        >
          Entrar
        </RouterLink>

        <RouterLink
          to="/register"
          class="inline-flex min-h-11 items-center justify-center rounded-xl bg-zinc-900 px-5 font-semibold text-white transition hover:-translate-y-0.5"
        >
          Criar conta
        </RouterLink>
      </div>
    </nav>
  </header>
</template>