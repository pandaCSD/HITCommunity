<template>
  <v-container>
    <!-- Buttons to open editing modals -->
    <v-btn color="primary" @click="showAvatarModal = true">更新头像</v-btn>
    <v-btn color="primary" @click="showBackgroundModal = true">更新背景</v-btn>
    <v-btn color="primary" @click="showInfoModal = true">更新信息</v-btn>

    <UserCard v-if="isDataLoaded" :uid="uid" :uname="uname" />

    <!-- Modal for Avatar Upload -->
    <v-dialog v-model="showAvatarModal" persistent max-width="290px">
      <v-card>
        <v-card-title>更新头像</v-card-title>
        <v-card-text>
          <v-file-input
            label="选择图片"
            prepend-icon="mdi-account-circle"
            v-model="files.avatar"
            accept="image/*"
            @change="previewAvatar"
          ></v-file-input>
          <v-img :src="filePreviews.avatar" aspect-ratio="1" class="image-preview"></v-img>
        </v-card-text>
        <v-card-actions>
          <v-btn color="green darken-1" text @click="showAvatarModal = false">关闭</v-btn>
          <v-btn color="blue darken-1" text @click="saveAvatar">保存</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Modal for Background Upload -->
    <v-dialog v-model="showBackgroundModal" persistent max-width="290px">
      <v-card>
        <v-card-title>更新背景</v-card-title>
        <v-card-text>
          <v-file-input
            label="选择图片"
            prepend-icon="mdi-image-area"
            v-model="files.background"
            accept="image/*"
            @change="previewBackground"
          ></v-file-input>
          <v-img :src="filePreviews.background" aspect-ratio="1.6" class="image-preview"></v-img>
        </v-card-text>
        <v-card-actions>
          <v-btn color="green darken-1" text @click="showBackgroundModal = false">关闭</v-btn>
          <v-btn color="blue darken-1" text @click="saveBackground">保存</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Modal for Editing Text Information -->
    <v-dialog v-model="showInfoModal" persistent max-width="600px">
      <v-card>
        <v-card-title>更新信息</v-card-title>
        <v-card-text>
          <v-text-field label="个性签名" v-model="userProfile.signature" prepend-icon="mdi-card-text-outline"></v-text-field>
          <v-select label="性别" v-model="userProfile.gender" :items="['Male', 'Female', 'Other']" prepend-icon="mdi-gender-male-female"></v-select>
          <v-text-field label="年龄" v-model="userProfile.age" type="number" prepend-icon="mdi-cake-variant"></v-text-field>
          <v-text-field label="电话" v-model="userProfile.phone" prepend-icon="mdi-phone"></v-text-field>
          <v-text-field label="邮箱" v-model="userProfile.email" prepend-icon="mdi-email"></v-text-field>
        </v-card-text>
        <v-card-actions>
          <v-btn color="green darken-1" text @click="showInfoModal = false">关闭</v-btn>
          <v-btn color="blue darken-1" text @click="saveProfile">保存</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
import UserCard from '@/components/UserCard.vue';

export default {
  name: 'UserProfile',
  components: {
    UserCard
  },
  data() {
    return {
      showAvatarModal: false,
      showBackgroundModal: false,
      showInfoModal: false,
      valid: true,
      uid: -1,
      uname: "",
      isDataLoaded: false,
      userProfile: {
        signature: '###',
        gender: 'Male',
        age: 0,
        phone: '###',
        email: '###'
      },
      files: {
        avatar: null,
        background: null
      },
      filePreviews: {
        avatar: '',
        background: ''
      },
      rules: {
        required: value => !!value || 'Required.',
        number: value => !isNaN(Number(value)) || 'Must be a number.'
      }
    }
  },
  async created() {
    const response1 = await this.$axios.get("/user/is-login");
    if (response1.data.success) {
      this.uid = response1.data.data.uid;
      this.uname = response1.data.data.uname;
    }
    const response2 = await this.$axios.get("/user/userinfo/" + this.uid);
    if (response2.data.success) {
      let data = response2.data.data;
      this.refreshProfile(data.signature, data.gender, data.age, data.phone, data.email);
    }
    this.isDataLoaded = true; // 数据加载完成，现在可以渲染子组件
  },
  methods: {
    previewAvatar(event) {
      this.filePreviews.avatar = URL.createObjectURL(event.target.files[0]);
    },
    previewBackground(event) {
      this.filePreviews.background = URL.createObjectURL(event.target.files[0]);
    },
    async refreshProfile(signature, gender, age, phone, email) {
      this.userProfile.signature = signature;
      this.userProfile.gender = gender;
      this.userProfile.age = age;
      this.userProfile.phone = phone;
      this.userProfile.email = email;
    },
    async saveAvatar() {
      const formData = new FormData();
      formData.append('file', this.files.avatar);
      try {
        await this.$axios.put('/user/avatar', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        });
      } catch (error) {
        alert("上传失败")
      }
      this.showAvatarModal = false;
    },
    async saveBackground() {
      const formData = new FormData();
      formData.append('file', this.files.background);
      try {
        await this.$axios.put('/user/background', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        });
      } catch (error) {
        alert("上传失败")
      }
      this.showBackgroundModal = false;
    },
    async saveProfile() {
      // Implement your method to upload images and save profile data
      // Close modal on save if needed
      const response = await this.$axios.put(
        '/user/userinfo',
        {
          uid: this.uid,
          signature: this.userProfile.signature,
          gender: this.userProfile.gender,
          age: this.userProfile.age,
          phone: this.userProfile.phone,
          email: this.userProfile.email
        }
      );
      if (response.data.success) {
        let data = response.data.data;
        this.refreshProfile(data.signature, data.gender, data.age, data.phone, data.email);
      } else {
        alert("更新失败");
      }
      this.showInfoModal = false;
    }
  }
}
</script>
