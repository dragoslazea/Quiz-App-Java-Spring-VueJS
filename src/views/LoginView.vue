<template>
  <v-card class="overflow-hidden">
    <v-app-bar
      absolute
      color="#35B5F1"
      dark
      shrink-on-scroll
      prominent
      src="https://tme.uconn.edu/wp-content/uploads/sites/1326/2016/12/cse.jpg"
      fade-img-on-scroll
      scroll-target="#scrolling-techniques-3"
    >
      <template v-slot:img="{ props }">
        <v-img
          v-bind="props"
          gradient="to top right, rgba(100,115,201,.7), rgba(25,32,72,.7)"
        ></v-img>
      </template>

      <v-app-bar-title><h2 class="text-h4 --text pl-4 ">Computer Science Quizzes</h2></v-app-bar-title>

      <v-spacer></v-spacer>
    </v-app-bar>
    <v-sheet
      id="scrolling-techniques-3"
      class="overflow-y-auto"
      max-height="880"
    >
      <v-container style="height: 1000px;">
        <br><br><br><br><br><br><br>
        <v-form
          ref="form"
          class="ml-6 mr-6"
        >
          <v-text-field
            v-model="username"
            label="Username"
            required
          ></v-text-field>

          <v-text-field
            v-model="password"
            label="Password"
            type="password"
            required
          ></v-text-field>

          <v-btn
            color="success"
            class="mr-4"
            @click="login"
          >
            <v-icon left>
              mdi-account
            </v-icon>
            Log In
          </v-btn>

          <v-btn
            color="warning"
            class="mr-4"
            @click="$router.push({ name: 'register' })"
          >
            <v-icon left>
              mdi-account-plus
            </v-icon>
            Register
          </v-btn>
        </v-form>
      </v-container>
    </v-sheet>

    
  </v-card>
</template>

<script>
  import axios from "axios";
  export default {
    name: 'login',
    data() {
      return {
        username: "",
        password: ""
      }
    },
    methods: {
      async login() {
        try { 
          var result = await axios.post("http://localhost:8080/api/usertable/username", { username: this.username, password: this.password });
          window.localStorage.setItem("idUser", result.data.id);
          window.localStorage.setItem("typeUser", result.data.type);
          this.$router.push({ name: 'home' });
                
        } catch (e) {
          alert("Incorrect username or password!");
        }
      }
    }
  }
</script>

