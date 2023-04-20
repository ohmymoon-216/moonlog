<template>
  <div class="title">{{post.title}}</div>
  <div class="content">{{ post.content }}</div>

  <el-row>
    <el-col>
      <div class="">
        <el-button type="warning" @click="moveToEdit()">수정</el-button>
      </div>

    </el-col>
  </el-row>

</template>

<script setup lang="ts">
import {defineProps, onMounted, ref} from "vue";
import axios from "axios";
import router from "@/router";

const post = ref({
  id: 0,
  title: "",
  content: ""
});

const props = defineProps({
  postId: {
    type: [Number, String],
    require: true,
  }
});

const moveToEdit = () => {
  router.push(`/edit/${props.postId}`);
}

onMounted(() => {
  axios.get(`/api/posts/${props.postId}`).then((response) => {
    if(response.data){
      post.value =response.data;
    }
  });
})
</script>

<style scoped>
.title{
  font-size: 1.6rem;
  font-weight: 600;
  color: #383838;
}
.content{
  font-size: 0.85rem;
  margin-top: 8px;
  color: #7e7e7e;
}

</style>