<!-- RecursiveMenu.vue -->
<template>
    <v-list>
      <!-- 循环渲染有子项的菜单项 -->
      <v-list-group
        v-for="item in itemsWithChildren"
        :key="item.title"
        v-model="item.open"
        :prepend-icon="item.icon"
      >
        <template v-slot:activator>
          <v-list-item-content>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </template>
        <recursive-menu :items="item.children" @select="select"></recursive-menu>
      </v-list-group>
  
      <!-- 循环渲染没有子项的菜单项 -->
      <v-list-item
        v-for="item in itemsWithoutChildren"
        :key="item.title"
        @click="$emit('select', item.component)"
      >
        <v-list-item-content>
          <v-list-item-title>{{ item.title }}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>
  </template>
  
  <script>
  export default {
    name: 'RecursiveMenu',
    props: {
      items: {
        type: Array,
        required: true,
      },
    },
    computed: {
      itemsWithChildren() {
        return this.items.filter(item => item.children);
      },
      itemsWithoutChildren() {
        return this.items.filter(item => !item.children);
      },
    },
    methods: {
      select(component) {
        this.$emit('select', component);
      },
    },
  };
  </script>
  