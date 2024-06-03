<template>
    <div class="welcome-page">
      <v-container>
        <v-row justify="center">
          <v-col cols="12" md="8" class="text-center">
            <v-card>
              <v-card-title>Welcome to Our Application</v-card-title>
              <v-card-text>
                <p>
                  This is the welcome page. Here you can find some basic information about our application and how to use it.
                </p>
                <p>
                  Use the sidebar to navigate to different sections of the application.
                </p>
              </v-card-text>
              <v-card-actions>
                <v-btn color="primary" @click="goToUserPage">Go to User Page</v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </div>
  </template>
  
  <script>
  export default {
    name: 'WelcomePage',
    methods: {
      goToUserPage() {
        this.$emit('navigate', 'UserPage');
      },
    },
  };
  </script>
  
  <style scoped>
  .welcome-page {
    padding: 20px;
  }
  </style>
  