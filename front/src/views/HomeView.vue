<script setup lang="ts">
import axios from "axios";
import { ref } from 'vue'
import {useRouter} from "vue-router";

const posts = ref([]);
const router = useRouter();

axios.get('/api/posts?page=1&size=10').then((response) => {
  if(response.data){
    response.data.forEach((r:any) => {
      posts.value.push(r);
    });
  }
});

</script>

<template>
  <ul>
    <li v-for="post in posts" :key="post.id">
      <div class="title"><router-link :to="`/read/${post.id}`">{{ post.title }}</router-link></div>
      <div class="content">{{ post.content }}</div>
      <div class="sub d-flex">
        <div class="category">개발</div>
        <div class="regDate">2023-04-20</div>
      </div>
    </li>
  </ul>
</template>

<style scoped>
  ul {
    list-style: none;
    padding: 0;
  }
  li {
    margin-bottom: 1.3em;
  }

  li .title {
    font-size: 1.2rem;
    color: #303030;
  }

  li .content {
    font-size: 0.85rem;
    margin-top: 8px;
    color: #5d5d5d;
  }

  .sub {
    margin-top: 5px;
    font-size: 0.75rem;
    color: #6b6b6b;
  }

  .regDate {
    margin-left: 10px;
  }

  li:last-child {
    margin-bottom: 0;
  }
</style>
