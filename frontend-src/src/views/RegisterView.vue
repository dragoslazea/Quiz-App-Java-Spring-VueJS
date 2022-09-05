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

          <v-text-field
            v-model="confirmpassword"
            label="Confirm password"
            type="password"
            required
          ></v-text-field>

          <v-btn
            color="warning"
            class="mr-4"
            @click="register"
          >
            <v-icon left>
              mdi-pencil
            </v-icon>
            Register
          </v-btn>

          <v-btn
            color="error"
            class="mr-4"
            @click="$router.push({ name: 'login' })"
          >
            <v-icon left>
              mdi-arrow-left
            </v-icon>
            Cancel
          </v-btn>

          <v-radio-group
            v-model="type"
            row
            required
          >
            <v-radio
              label="Teacher"
              color="warning"
              value=1
            ></v-radio>
            <v-radio
              label="Student"
              color="warning"
              value=0
            ></v-radio>
          </v-radio-group>


        </v-form>
      </v-container>
    </v-sheet>
  </v-card>
</template>

<script>
  import axios from "axios";
  export default {
    name: 'register',
    data() {
      return {
		username: "",
		password: "",
		confirmpassword: "",
		type: null
      }
    },
    methods: {
      async register() {
        try { 
            if (this.password != this.confirmpassword) {
                alert("Passwords are different!");
            }
            else if (this.username == "" || this.password == "" || this.confirmpassword == "" || this.type == null) {
                throw error;
            }
            else {
                var result = await axios.post("http://localhost:8080/api/usertable", { username: this.username, password: this.password, type: this.type });
                this.$router.push({ name: 'login' });
            }
            
        } catch (e) {
            alert("All fields are mandatory!");
        }
      }
    }
  }
</script>