<template>
  <main class="flex min-h-screen flex-col items-center justify-center px-6 text-center">
    <span class="text-[8rem] font-black leading-none tracking-[-0.08em] text-zinc-900">
      404
    </span>

    <h1 class="mt-6 text-3xl font-bold tracking-tight text-zinc-900">
      Página não encontrada
    </h1>

    <p class="mt-3 max-w-md text-zinc-500">
      A página que você está procurando
      não existe ou foi movida.
    </p>

    <RouterLink
      to="/"
      class="mt-8 inline-flex min-h-11.5 items-center justify-center rounded-xl bg-zinc-900 px-5 font-semibold text-white transition hover:-translate-y-0.5"
    >
      Voltar para o início
    </RouterLink>
  </main>
</template>