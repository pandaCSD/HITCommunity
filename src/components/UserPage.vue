<template>
    <v-container>
      <v-row>
        <v-col cols="12" md="6">
          <v-card>
            <v-card-title>User Information</v-card-title>
            <v-card-text>
              <v-form ref="form" v-model="valid" lazy-validation>
                <v-text-field
                  v-model="user.id"
                  label="User ID"
                  readonly
                ></v-text-field>
                <v-text-field
                  v-model="user.username"
                  label="Username"
                  :rules="[v => !!v || 'Username is required']"
                  required
                ></v-text-field>
                <v-text-field
                  v-model="user.password"
                  label="Password"
                  :type="showPassword ? 'text' : 'password'"
                  :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
                  @click:append="showPassword = !showPassword"
                  :rules="[v => !!v || 'Password is required']"
                  required
                ></v-text-field>
              </v-form>
            </v-card-text>
            <v-card-actions>
              <v-btn color="primary" @click="save" :disabled="!valid">Save</v-btn>
              <v-btn color="secondary" @click="reset">Reset</v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </template>
  
  <script>
  export default {
    name: 'UserPage',
    data() {
      return {
        valid: false,
        showPassword: false,
        user: {
          id: '12345',
          username: 'john_doe',
          password: 'password123',
        },
      };
    },
    methods: {
      save() {
        if (this.$refs.form.validate()) {
          // Save user data (e.g., send to server)
          console.log('User data saved:', this.user);
        }
      },
      reset() {
        this.$refs.form.reset();
      },
    },
  };
  </script>
  
  <style scoped>
  /* Add any custom styles here */
  </style>
  