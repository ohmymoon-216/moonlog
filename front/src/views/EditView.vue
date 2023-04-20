<template>
  <div>
    <el-input v-model="post.title" type="text" placeholder="제목을 입력해 주세요" />
  </div>
  <div>
    <el-input v-model="post.content" type="textarea" rows="15" />
  </div>
  <div class="mt-2">
    <el-button type="primary" @click="update">글 수정완료</el-button>
  </div>
</template>

<script setup lang="ts">
import {defineProps, onMounted, ref} from 'vue'
import axios from "axios";
import router from "@/router";
const post = ref({
  title: "",
  content: ""
});

const props = defineProps({
  postId: {
    type: [Number, String],
    require: true,
  }
});

onMounted(() => {
  axios.get(`/api/posts/${props.postId}`).then((response) => {
    if(response.data){
      post.value =response.data;
    }
  });
})

const update = function (){
  axios.patch(`/api/posts/${props.postId}`, post.value)
      .then(() => {
        router.replace("/");
      });
}
</script>

<style scoped>

</style>