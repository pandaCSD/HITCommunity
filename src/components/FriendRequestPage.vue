<template>
  <v-container>
    <v-row justify="center">
      <v-col cols="12" sm="8" md="6">
        <!-- <h1>好友请求</h1> -->
        <v-btn color="primary" @click="fetchRequests">刷新请求列表</v-btn>
        <v-list>
          <v-list-item v-for="request in requests" :key="request.suid">
            <v-list-item-content>
              <v-list-item-title>用户ID: {{ request.uid }}</v-list-item-title>
              <v-list-item-subtitle>用户名: {{ request.uname }}</v-list-item-subtitle>
              <!-- <v-list-item-subtitle>请求时间: {{ request.requestTime }}</v-list-item-subtitle> -->
            </v-list-item-content>
            <v-list-item-action>
              <v-btn color="green" @click="acceptRequest(request.uid)">接受</v-btn>
              <v-btn color="red" @click="rejectRequest(request.uid)">拒绝</v-btn>
            </v-list-item-action>
          </v-list-item>
        </v-list>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: 'FriendRequestPage',
  data() {
    return {
      requests: []
    };
  },
  mounted() {
    this.fetchRequests();
  },
  methods: {
    async fetchRequests() {
      try {
        const response = await this.$axios.get('/user/friend-requests');
        if (response.data.success) {
          this.requests = response.data.data;
        } else {
          console.error('Failed to fetch requests:', response.data.message);
          alert('Failed to fetch requests: ' + response.data.message);
        }
      } catch (error) {
        console.error('Error fetching requests:', error);
        alert('An error occurred while fetching requests.');
      }
    },
    async acceptRequest(suid) {
      // 发送接受请求的API调用
      try {
        const response = await this.$axios.post('/user/friend-requests/accept/'+suid);
        if (response.data.success) {
          this.requests = this.requests.filter(f => f.suid !== suid);
          console.log('Accept request:', suid);
        } else {
          console.error('Failed to accept request:', response.data.message);
          alert('Failed to accept request:' + response.data.message);
        }
      } catch (error) {
        console.error('Error accepting request:', error);
        alert('An error occurred while accepting request.');
      }
    },
    async rejectRequest(suid) {
      // 发送拒绝请求的API调用
      try {
        const response = await this.$axios.post('/user/friend-requests/reject/'+suid);
        if (response.data.success) {
          this.requests = this.requests.filter(f => f.suid !== suid);
          console.log('Reject request:', suid);
        } else {
          console.error('Failed to reject request:', response.data.message);
          alert('Failed to reject request:' + response.data.message);
        }
      } catch (error) {
        console.error('Error rejecting request:', error);
        alert('An error occurred while rejecting request.');
      }
    }
  }
};
</script>

<style scoped>
</style>
