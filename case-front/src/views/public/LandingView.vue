<template>
  <div class="min-h-screen bg-white">
    <LandingHeader />

    <main>
      <LandingHero />
      <LandingFeatures />
    </main>

    <LandingFooter />
  </div>
</template>

<script setup lang="ts">
  import LandingHeader from '../../components/ui/landing/LandingHeader.vue'
  import LandingHero from '../../components/ui/landing/LandingHero.vue'
  import LandingFeatures from '../../components/ui/landing/LandingFeatures.vue'
  import LandingFooter from '../../components/ui/landing/LandingFooter.vue'
</script>