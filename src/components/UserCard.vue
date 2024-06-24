<template>
  <v-container class="fill-height" fluid>
    <v-row justify="center">
      <v-col cols="12" sm="8" md="6">
        <v-card class="pa-5" :style="{ backgroundImage: 'url(' + UserCard.backgroundUrl + ')' }">
          <v-avatar size="120">
            <v-img :src="UserCard.avatarUrl"></v-img>
          </v-avatar>
          <v-card-title>{{ UserCard.name }}</v-card-title>
          <v-card-subtitle>{{ UserCard.signature }}</v-card-subtitle>
          <v-card-text>
            <v-chip>{{ UserCard.gender }}</v-chip>
            <v-chip>{{ UserCard.age }} years old</v-chip>
          </v-card-text>
          <v-list>
            <v-list-item>
              <v-list-item-icon>
                <v-icon>mdi-phone</v-icon>
              </v-list-item-icon>
              <v-list-item-content>{{ UserCard.phone }}</v-list-item-content>
            </v-list-item>
            <v-list-item>
              <v-list-item-icon>
                <v-icon>mdi-email</v-icon>
              </v-list-item-icon>
              <v-list-item-content>{{ UserCard.email }}</v-list-item-content>
            </v-list-item>
          </v-list>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: 'UserCard',
  props: {
    // 定义一个名为 "message" 的 prop
    uid: {
      type: Number, // 指定数据类型
      required: true, // 表示这个 prop 是必须的
    },
    uname: {
      type: String, // 指定数据类型
      required: true, // 表示这个 prop 是必须的
    }
  },
  data() {
    return {
      UserCard: {
        uid: -1,
        avatarUrl: '',  // 初始化为空，稍后动态设置
        backgroundUrl: '',  // 初始化为空，稍后动态设置
        name: '',
        signature: '',
        gender: '',
        age: 0,
        phone: '',
        email: ''
      }
    }
  },
  created() {
    // 组件创建完毕后，从props同步数据到data
    this.UserCard.uid = this.uid;
    this.UserCard.name = this.uname;
  },
  mounted() {
    this.loadInfo();
    this.loadImages();
  },
  methods: {
    async loadImages() {
      // 使用全局函数下载图像，并设置到data属性中
      const avatar = await this.$downloadImage('/user/avatar/' + this.uid);
      const background = await this.$downloadImage('/user/background/'+ this.uid);
      if (avatar) {
        this.UserCard.avatarUrl = URL.createObjectURL(avatar);
      }
      if (background) {
        this.UserCard.backgroundUrl = URL.createObjectURL(background);
      }
    },
    async loadInfo() {
      const response = await this.$axios.get("/user/userinfo/" + this.uid);
      if (response.data.success) {
        this.UserCard.signature = response.data.data.signature;
        this.UserCard.gender = response.data.data.gender;
        this.UserCard.age = response.data.data.age;
        this.UserCard.phone = response.data.data.phone;
        this.UserCard.email = response.data.data.email;
      }
    }
  }
}
</script>

<style scoped>
.v-card {
color: white;
text-shadow: 1px 1px 2px rgba(0,0,0,0.7);
}
</style>
