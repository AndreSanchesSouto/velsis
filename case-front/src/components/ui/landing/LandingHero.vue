<template>
  <section class="flex min-h-180 items-center justify-center bg-[radial-gradient(circle_at_top,#f4f4f5,transparent_60%)] px-5 pb-20 pt-30 text-center">
    <div class="w-full max-w-3xl">
      <span class="mb-5 inline-flex rounded-full bg-zinc-100 px-3.5 py-2 text-sm font-semibold text-zinc-700">
        Velsis - Case técnico.
      </span>

      <h1 class="m-0 text-5xl font-black leading-[0.95] tracking-tighter text-zinc-900 sm:text-6xl lg:text-[5.5rem]">
        Cadastro de Usuários
        <span class="block">simples e seguro.</span>
      </h1>

      <p class="mx-auto mt-7 max-w-2xl text-lg leading-7 text-zinc-500">
        Sistema desenvolvido para a Velsis com foco em gestão de usuários, autenticação e boas práticas de desenvolvimento.
      </p>

      <div class="mt-8 flex flex-wrap items-center justify-center gap-3">
        <RouterLink
          to="/register"
          class="inline-flex min-h-11 items-center justify-center rounded-xl bg-zinc-900 px-5 font-semibold text-white transition hover:-translate-y-0.5"
        >
          Começar agora
        </RouterLink>

        <RouterLink
          to="/login"
          class="inline-flex min-h-11 items-center justify-center rounded-xl bg-zinc-100 px-5 font-semibold text-zinc-900 transition hover:-translate-y-0.5"
        >
          Já tenho uma conta
        </RouterLink>
      </div>
    </div>
  </section>
</template>