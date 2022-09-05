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
        <v-row align="center" justify="space-around"><h2 class="text-h4 warning--text pl-4">
            {{ subject.name }} - Quizzes
        </h2></v-row>
        
        <div v-for='quiz in quizzes' :key='quiz.id'>
            <br>
            <v-card
              class="mx-10"
            >

              <v-card-title>
                {{ quiz.chapter }}
              </v-card-title>

              <v-card-subtitle>
                {{ quiz.quizSubject.name }}
              </v-card-subtitle>

              <v-card-actions>
                <v-btn
                  color="orange lighten-2"
                  text
                  @click="startAttempt(quiz)"
                > 
                  Start attempt
                </v-btn>

                <v-spacer></v-spacer>
              </v-card-actions>

            
            </v-card>
          </div>
            
          <v-row
            align="center"
            justify="space-around">        
            <v-btn
                color="warning"
                class="mr-10"
                @click="$router.push({ name: 'home' })"
              >
                <v-icon left>
                  mdi-arrow-left
                </v-icon>
                Home
            </v-btn></v-row>
      </v-container>
    </v-sheet>

    
  </v-card>
</template>

<script>
  import axios from "axios";
  export default {
    name: 'quizzes-page',
    data() {
      return {
        quizzes: [],
        subject: null,
        myAttempts: [],
        student: null,
      }
    },

    async created() {
      try {
        var resultQuizzes = await axios.get("http://localhost:8080/api/quiz/getBySubject/" + this.$route.params.id);
        var resSubj = await axios.get("http://localhost:8080/api/subject/" + this.$route.params.id);
        var resAttempts = await axios.get("http://localhost:8080/api/attempt/getByStudent/" + window.localStorage.getItem("idUser"));
        this.myAttempts = resAttempts.data;
        this.quizzes = resultQuizzes.data;
        this.subject = resSubj.data;
      }
      catch (e) {
        alert("Error loading quizzes!");
      }
    },

    methods: {
        async startAttempt(item) {
            try {
              var attempted = false;
              var usr = await axios.get("http://localhost:8080/api/usertable/" + window.localStorage.getItem("idUser"));
              this.student = usr.data;
              this.myAttempts.forEach(a => {
                if (a.attemptedQuiz.id === item.id) {
                  attempted = true;
                }
              });
              if (attempted === false) {
                this.$router.push({ name: 'attempt-page', params: {id: item.id } });
              }
              else {
                alert("You have already attempted this quiz!");
              }
            } catch (e) {
                alert("Error starting attempt!");
            }
            
        },
    }
  }
</script>

