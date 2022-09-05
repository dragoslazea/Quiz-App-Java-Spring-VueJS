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
            {{ quiz.chapter }}
        </h2></v-row>

        <v-card-title>
          Remaining time {{ minutes }} min {{ seconds }} s
        </v-card-title>
        
        <div v-for='q in questions' :key='q.id'>
            <br>
            <v-card
              class="mx-10"
            >

                <v-card-title>
                    {{ q.questionText }}
                </v-card-title>

                <v-card-subtitle>
                    Select the correct option {{ q.correctOption.answer }}
                    <div class="my-2">
                        <v-btn
                            color="warning"
                            fab
                            x-small
                            dark
                            @click="getCurrentOpt(q)"
                        >
                            <v-icon>mdi-chevron-down</v-icon>
                        </v-btn>
                    </div>
                </v-card-subtitle>
                
                <v-radio-group
                    v-model="currentAns"
                    required
                    v-if="q.id === qId"
                    class="mx-10"
                >
                    <v-radio
                        v-for='o in currentOptions' :key='o.id'
                        :label= "o.answer"
                        color="warning"
                        :value= "o.answer"
                    ></v-radio>
                </v-radio-group>

                <v-btn
                    :disabled="clicked.includes(q.id)"
                    v-bind:key="q.id"
                    color="warning"
                    class="mx-10"
                    @click="saveAns(q)"
                >
                    <v-icon left>
                        mdi-checkbox-marked-circle-outline
                    </v-icon>
                    Save answer
                </v-btn>
              

            
            </v-card>
          </div>

            
        <v-row
            align="center"
            justify="space-around">        
            <v-btn
                color="warning"
                class="mr-10"
                @click="submit"
              >
                <v-icon left>
                  mdi-arrow-left
                </v-icon>
                Submit
            </v-btn>
        </v-row>
      </v-container>
    </v-sheet>

    
  </v-card>
</template>

<script>
  import axios from "axios";
  export default {
    name: 'attempt-page',
    data() {
      return {
        quiz: null,
        subject: null,
        attempt: null,
        score: 0,
        quizQuestions: [],
        questions: [],
        correctOptions: [],
        selectedOptions: [],
        correctOptionsTxt: [],
        currentQO: [],
        currentOptions: [],
        qId: 0,
        currentOptionsTxt: [],
        currentAns: "",
        clicked: [],
        countDown: 5,
        minutes: 0,
        seconds: 0,
        submitted: false,
      }
    },

    async created() {
      try {
        var resQuiz = await axios.get("http://localhost:8080/api/quiz/" + this.$route.params.id);
        var resQQ = await axios.get("http://localhost:8080/api/quizquestion/getByQuiz/" + this.$route.params.id);
        this.quiz = resQuiz.data;
        this.quizQuestions = resQQ.data;
        this.submitted = false;

        this.quizQuestions.forEach(q => {
            this.questions.push(q.subjectQuestion);
        });

        this.questions.forEach(q => {
            this.correctOptions.push(q.correctOption);
        });

        this.correctOptions.forEach(co => {
            this.correctOptionsTxt.push(co.answer);
        });

        this.countDown = 30 * this.questions.length;
        this.minutes = Math.floor(this.countDown / 60);
        this.seconds = this.countDown % 60;

        this.countDownTimer();

        //this.subject = this.quiz.quizSubject;
        //this.quizQuestions = this.quiz.quizQuestions;
      }
      catch (e) {
        alert("Error starting quiz!");
      }
    },

    methods: {
        async getCurrentOpt(item) {
            this.currentAns = "";
            this.currentOptions = [];
            this.currentOptionsTxt = [];
            var res = await axios.get("http://localhost:8080/api/questionoption/getByQuestion/" + item.id);
            this.currentQO = res.data;
            this.currentQO.forEach(qo => {
                this.currentOptions.push(qo.option);
            });
            this.currentOptions.forEach(co => {
                this.currentOptionsTxt.push(co.answer);
            })
            this.qId = item.id;
        },

        async saveAns(item) {
            this.clicked.push(item.id);
            if (this.currentAns === item.correctOption.answer) {
                this.score++;
            }
        },

        async submit() {
            alert("Answers submitted! Your score: " + this.score + " / " + this.questions.length);
            var usr = await axios.get("http://localhost:8080/api/usertable/" + window.localStorage.getItem("idUser"));
            var att = await axios.post("http://localhost:8080/api/attempt", { score: this.score,  attemptedQuiz: this.quiz, attemptedStudent: usr.data, maxScore: this.questions.length });
            this.$router.push({ name: 'home' });
            this.submitted = true;
        },

        async countDownTimer() {
          if (this.countDown > 0) {
            setTimeout(() => {
              this.countDown -= 1;
              this.minutes = Math.floor(this.countDown / 60);
              this.seconds = this.countDown % 60;
              this.countDownTimer();
            }, 1000);
          }
          else {
            if (this.submitted == false) {
              alert("Time is up! Your score: " + this.score + " / " + this.questions.length);
              var usr = await axios.get("http://localhost:8080/api/usertable/" + window.localStorage.getItem("idUser"));
              var att = await axios.post("http://localhost:8080/api/attempt", { score: this.score,  attemptedQuiz: this.quiz, attemptedStudent: usr.data, maxScore: this.questions.length });
              this.$router.push({ name: 'home' });
            }
            this.submitted = true;
          }
        }
    }
  }
</script>

