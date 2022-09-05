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
            {{ quiz.quizSubject.name }}
        </h2></v-row>

        <v-row align="center" justify="space-around"><h2 class="text-h5 warning--text pl-4">
            {{ quiz.chapter }}
        </h2></v-row>
        
        <div v-for='a in attempts' :key='a.id'>
            <br>
            <v-card
                class="mx-10"
            >

                <v-card-title>
                    {{ a.attemptedQuiz.quizSubject.name }}
                </v-card-title>

                <v-card-subtitle>
                    {{ a.attemptedQuiz.chapter }}
                </v-card-subtitle>

                <v-card-text>
                <div class="my-4 text-subtitle-1">
                    Student: {{ a.attemptedStudent.username }}
                </div>
                </v-card-text>

                <v-card-text>
                <div class="my-4 text-subtitle-1">
                    Score: {{ a.score }}/{{ a.maxScore }}
                </div>
                </v-card-text>

            
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
    name: 'attempts-page',
    data() {
      return {
        attempts: [],
        quiz: null,
      }
    },

    async created() {
      try {
        var resultAttempts = await axios.get("http://localhost:8080/api/attempt/getByQuiz/" + this.$route.params.id);
        var resQ = await axios.get("http://localhost:8080/api/quiz/" + this.$route.params.id);
        this.attempts =  resultAttempts.data;
        this.quiz = resQ.data;
        
      }
      catch (e) {
        alert("Error loading attempts for this quiz!");
      }
    },

    methods: {
    }
  }
</script>

