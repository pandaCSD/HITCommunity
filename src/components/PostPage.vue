<template>
    <v-container>
      <v-row>
        <v-col cols="12">
          <v-text-field
            label="Text"
            outlined
            v-model="text"
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row>
        <v-col
          cols="12"
          sm="6"
          md="3"
          v-for="(item, index) in files"
          :key="index"
        >
          <v-img
            :src="item.url"
            aspect-ratio="1"
            class="grey lighten-2"
          ></v-img>
        </v-col>
        <v-col cols="12" sm="6" md="3">
          <v-file-input
            multiple
            small-chips
            label="Add Image"
            prepend-icon="mdi-camera"
            v-model="files"
            @change="previewFiles"
          ></v-file-input>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="12">
          <v-btn
            color="primary"
            @click="submitPost"
          >
            Post
          </v-btn>
        </v-col>
      </v-row>
    </v-container>
  </template>
  
  <script>
  export default {
    data() {
      return {
        text: '',
        files: [],
      };
    },
    methods: {
      previewFiles() {
        this.files = this.files.map(file => ({
          ...file,
          url: URL.createObjectURL(file)
        }));
      },
      submitPost() {
        const formData = new FormData();
        formData.append('text', this.text);
        this.files.forEach(file => {
          formData.append('images', file);
        });
  
        // // 使用axios发送POST请求
        // axios.post('/your-upload-url', formData, {
        //   headers: {
        //     'Content-Type': 'multipart/form-data'
        //   }
        // })
        // .then(response => {
        //   console.log('Post submitted successfully');
        // })
        // .catch(error => {
        //   console.error('Error submitting post: ', error);
        // });
      }
    }
  }
  </script>
  
  <style scoped>
  .v-img {
    height: 100%;
    width: 100%;
  }
  </style>