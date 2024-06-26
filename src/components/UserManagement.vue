<template>
    <v-data-table
        :headers="headers"
        :items="users"
        :search="search"
    >
        <!-- 表格顶部插槽 -->
        <template #top>
            <v-toolbar flat>
                <v-toolbar-title>用户列表</v-toolbar-title>
                <v-spacer></v-spacer>
                <v-btn icon @click="refresh()">
                    <v-icon>mdi-refresh</v-icon>
                </v-btn>
                <v-text-field
                    v-model="search"
                    append-icon="mdi-magnify"
                    label="搜索"
                    single-line
                    hide-details
                ></v-text-field>
            </v-toolbar>
        </template>
        <template v-slot:[`item.eye`]="{ item  }">
            <v-btn icon @click="openUserCard(item)">
                    <v-icon>mdi-eye</v-icon>
            </v-btn>
        </template>

        <template v-slot:[`item.resetPassword`]="{ item }">
            <v-btn icon @click="openChangePasswordCard(item)">
                <v-icon color="yellow">mdi-lock-reset</v-icon>
            </v-btn>
        </template>
        
        <template v-slot:[`item.delete`]="{ item }">
            <v-btn icon @click="deleteUser(item)">
                <v-icon color="red">mdi-trash-can</v-icon>
            </v-btn>
        </template>

    </v-data-table>
    
    <!-- 添加v-dialog -->
    <v-dialog v-model="dialog" persistent max-width="500px">
        <v-card-actions>
            <v-btn @click="dialog = false">
                <v-icon color="red">mdi-close</v-icon>
            </v-btn>
        </v-card-actions>
        <UserCard :uid="selectedUser.uid" :uname="selectedUser.uname" />
    </v-dialog>

    <!-- 更改密码对话框 -->
    <v-dialog v-model="dialogChangePassword" persistent max-width="290px">
        <v-card>
            <v-card-title class="text-h5">更改密码</v-card-title>
            <v-card-text>
                <v-form ref="form">
                    <v-text-field
                        v-model="newPassword"
                        label="新密码"
                        type="password"
                        required
                    > </v-text-field>
                </v-form>
            </v-card-text>
            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" text @click="resetPassword()">确认</v-btn>
                <v-btn color="red darken-1" text @click="this.dialogChangePassword = false">关闭</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>


<script>
import UserCard from '@/components/UserCard.vue';

export default {
    components: {
        UserCard
    },
    data() {
        return {
            search: '',
            headers: [
                { title: '用户ID', value: 'uid', sortable: true },
                { title: '用户名', value: 'uname', sortable: true },
                { title: '用户密码', value: 'upassword', sortable: false },
                { title: '用户信息', key: 'eye', sortable: false },
                { title: '重置密码', key: 'resetPassword', sortable: false },
                { title: '删除用户', key: 'delete', sortable: false },
            ],
            users: [],
            selectedUser: {}, // 初始化selectedUser为一个空对象
            dialog: false,     // 初始化dialog为false，控制对话框的显示
            dialogChangePassword: false,
            changePasswordUser: {},
            newPassword: ''
        };
    },
    async mounted() {
        this.refresh();
    },
    methods: {
        async refresh() {
            try {
                const response = await this.$axios.get('/admin/all-user');
                if (response.data.success) {
                  this.users = response.data.data.map(user => ({
                      uid: user.uid,
                      uname: user.uname,
                      upassword: user.upassword,
                      eye: user,
                      delete: user,
                  }));
                } else {
                  console.error('Unexpected response format:', response.data);
                }
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        },
        openUserCard(user) {
          this.selectedUser = user;
          this.dialog = true;  // 打开对话框
        },
        openChangePasswordCard(user) {
            this.changePasswordUser = user;
            this.dialogChangePassword = true;
            this.newPassword = '';
        },
        async deleteUser(user) {
            try {
                const response = await this.$axios.delete('/admin/user/' + user.uid);
                if (response.data.success) {
                    alert('删除用户 uid:'+user.uid+' uname:'+user.uname+' 成功');
                } else {
                    console.error('删除用户失败');
                }
            } 
            catch (error) {
                console.error('网络错误');
            }
        },
        async resetPassword() {
            try {
                const response = await this.$axios.put('/admin/update/password', {
                    uid: this.changePasswordUser,
                    password: this.newPassword
                });
                if (response.data.success) {
                    this.dialogChangePassword = false;
                    alert('重置密码成功');
                } else {
                    alert('重置密码失败');
                }
            } 
            catch (error) {
                alert('网络错误');
            }
        }
    }
}
</script>

  