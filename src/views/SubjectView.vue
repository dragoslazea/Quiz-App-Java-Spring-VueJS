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
            v-model="subject"
            label="Subject"
            required
          ></v-text-field>

          <v-text-field
            v-model="description"
            label="Description"
            required
          ></v-text-field>

          <v-btn
                class="mr-4"
                color="success"
                @click="updateSubject"
              >
                <v-icon left>
                  mdi-account-edit
                </v-icon>
                Update
              </v-btn>

              <v-btn
                color="error"
                class="mr-4"
                @click="deleteSubject"
              >
                <v-icon left>
                  mdi-account-remove
                </v-icon>
                Delete
              </v-btn>

              <v-btn
                color="warning"
                class="mr-4"
                @click="$router.push({ name: 'home' })"
              >
                <v-icon left>
                  mdi-arrow-left
                </v-icon>
                Home
              </v-btn>
        </v-form>
      </v-container>
    </v-sheet>

    
  </v-card>
</template>

<script>
  import axios from "axios";
  export default {
    name: 'subjectpage',
    data() {
      return {
        subject: "",
        description: ""
      }
    },

    async created() {
      try {
          console.log("here");
        var resultSubject = await axios.get("http://localhost:8080/api/subject/" + this.$route.params.id);
        this.subject = resultSubject.data.name;
        this.description = resultSubject.data.description;
      }
      catch (e) {
        alert("Error loading subject information!");
      }
    },

    methods: {
    async deleteSubject(){
        try { 
          var result = await axios.delete("http://localhost:8080/api/subject/" + this.$route.params.id);
          this.$router.push({ name: 'home' });
          alert("Subject was deleted!");
          
        } catch (e) {
          alert("Failed to delete subject!");
        }
      },
      async updateSubject() {
        try { 
          if (this.subject == "" || this.description == "") {
            throw error;
          }
          else {
            var result = await axios.put("http://localhost:8080/api/subject/" + this.$route.params.id, { name: this.subject, description: this.description });
            this.$router.push({ name: 'home' });
            alert("Subject information updated!");
          }
        } catch (e) {
          alert("Error updating subject information! All fields are mandatory!\n");
        }
      },
    }
  }
</script>

