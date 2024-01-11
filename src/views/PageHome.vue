<template>
    <div class="home" id="wrapper">
      <ProjectLink1 /><hr>
      <ProjectLink2 /><hr>
      <ProjectLink3 />
    </div>
  </template>
  
  <script>
  import ProjectLink1 from '@/components/ProjectLink1.vue'
  import ProjectLink2 from '@/components/ProjectLink2.vue'
  import ProjectLink3 from '@/components/ProjectLink3.vue'
  
  export default {
    name: 'PageHome',
    components: {
      ProjectLink1,
      ProjectLink2,
      ProjectLink3
    }
  }
  </script>