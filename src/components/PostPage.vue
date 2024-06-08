<template>
  <v-container>
    <v-row>
      <v-col cols="12">
        <v-text-field
          label="Title"
          outlined
          v-model="title"
          class="mb-4 styled-input"
        ></v-text-field>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12">
        <v-text-field
          label="Text"
          outlined
          v-model="text"
          class="mb-4 styled-input"
          textarea
          rows="5"
        ></v-text-field>
      </v-col>
    </v-row>
    <v-row>
      <v-col
        cols="12"
        sm="6"
        md="3"
        v-for="(item, index) in filePreviews"
        :key="index"
        class="mb-4"
      >
        <v-img
          :src="item.url"
          aspect-ratio="1"
          class="image-preview"
        ></v-img>
      </v-col>
      <v-col cols="12" sm="6" md="3" class="mb-4">
        <v-file-input
          multiple
          small-chips
          label="Add Images"
          prepend-icon="mdi-camera"
          @change="previewFiles"
          class="file-input"
        ></v-file-input>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12">
        <v-btn
          color="primary"
          @click="submitPost"
          class="post-btn"
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
      title: '',
      text: '',
      pid: -1, // 示例pid，可以根据需要动态设置
      files: [],
      filePreviews: [],
    };
  },
  methods: {
    previewFiles(event) {
      const newFiles = Array.from(event.target.files);
      const newFilePreviews = newFiles.map(file => ({
        file,
        url: URL.createObjectURL(file)
      }));

      this.files = this.files.concat(newFiles);
      this.filePreviews = this.filePreviews.concat(newFilePreviews);
    },
    async uploadImage(file, pid) {
      const formData = new FormData();
      formData.append('file', file);
      formData.append('pid', pid);

      try {
        const response = await this.$axios.post('/post/images/upload', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        });
        console.log('Image uploaded successfully:', response.data);
      } catch (error) {
        console.error('Error uploading image:', error);
      }
    },
    async submitPost() {
      try {
        // 先提交post数据
        const postResponse = await this.$axios.post('/post/post', {
          title: this.title,
          text: this.text
        });
        console.log('Post submitted successfully:', postResponse.data);
        // 获取返回的pid
        const pid = postResponse.data.data.pid;
        // 上传图片
        const uploadPromises = this.files.map(file => this.uploadImage(file, pid));
        await Promise.all(uploadPromises);
        console.log('All images uploaded successfully');
        // 重置表单
        this.title = '';
        this.text = '';
        this.files = [];
        this.filePreviews = [];
        alert('帖子发送成功');
      } catch (error) {
        console.error('Error submitting post or uploading images:', error);
      }
    }
  } 
}
</script>

<style scoped>
.v-text-field {
  margin-bottom: 16px;
}

.styled-input .v-input__control .v-input__slot {
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.image-preview {
  height: 100%;
  width: 100%;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.image-preview:hover {
  transform: scale(1.05);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
}

.file-input .v-file-input {
  background-color: #f5f5f5;
  border-radius: 8px;
  padding: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: background-color 0.3s ease;
}

.file-input:hover .v-file-input {
  background-color: #e0e0e0;
}

.post-btn {
  width: 100%;
  background: linear-gradient(to right, #42a5f5, #478ed1);
  color: #fff;
  padding: 12px 0;
  border-radius: 8px;
  font-weight: bold;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  transition: background 0.3s ease, transform 0.3s ease;
}

.post-btn:hover {
  background: linear-gradient(to right, #478ed1, #42a5f5);
  transform: translateY(-2px);
}
</style>
