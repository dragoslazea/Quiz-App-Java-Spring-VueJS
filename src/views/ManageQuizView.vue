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
        
        <div v-for='q in quizQuestions' :key='q.id'>
            <br>
            <v-card
              class="mx-10"
            >

                <v-card-title>
                    {{ q.subjectQuestion.questionText }}
                </v-card-title>

                <v-card-subtitle>
                    Select the correct option {{ q.subjectQuestion.correctOption.answer }}
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
                
                <v-list-item v-for='o in currentOptions' :key='o.id' v-if="q.id === qId">
                    <v-btn
                        class="mx-2"
                        fab
                        dark
                        x-small
                        color="error"
                        @click="elimQO(q, o)"
                    >
                        <v-icon dark>
                            mdi-minus
                        </v-icon>
                    </v-btn>
                    <v-list-item-content>
                        <v-list-item-title>{{ o.answer }}</v-list-item-title>
                    </v-list-item-content>
                    
                </v-list-item>

                <br>

                <v-btn
                    color="warning"
                    class="mx-10"
                    @click="deleteQuizQuestion(q)"
                >
                    <v-icon left>
                        mdi-delete-circle
                    </v-icon>
                    Remove
                </v-btn>
              

            
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
                  mdi-home
                </v-icon>
                Home
            </v-btn>
        </v-row>
      </v-container>
    </v-sheet>

    
  </v-card>
</template>

<script>
  import axios from "axios";
  export default {
    name: 'quiz-manage-page',
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
        
        this.quizQuestions.forEach(q => {
            this.questions.push(q.subjectQuestion);
        });

        this.questions.forEach(q => {
            this.correctOptions.push(q.correctOption);
        });


        //this.subject = this.quiz.quizSubject;
        //this.quizQuestions = this.quiz.quizQuestions;
      }
      catch (e) {
        alert("Error loading quiz!");
      }
    },

    methods: {
        async deleteQuizQuestion(item) {
            try {
                var res = await axios.delete("http://localhost:8080/api/quizquestion/" + item.id);

                var resQuiz = await axios.get("http://localhost:8080/api/quiz/" + this.$route.params.id);
                var resQQ = await axios.get("http://localhost:8080/api/quizquestion/getByQuiz/" + this.$route.params.id);
                this.quiz = resQuiz.data;
                this.quizQuestions = resQQ.data;
        
                this.quizQuestions.forEach(q => {
                    this.questions.push(q.subjectQuestion);
                });

                this.questions.forEach(q => {
                    this.correctOptions.push(q.correctOption);
                });
            } catch(e) {
                alert("Cannot delete quiz question!");
            }
        },

        async getCurrentOpt(item) {
            try {
                this.currentAns = "";
                this.currentOptions = [];
                this.currentOptionsTxt = [];
                var res = await axios.get("http://localhost:8080/api/questionoption/getByQuestion/" + item.subjectQuestion.id);
                this.currentQO = res.data;
                this.currentQO.forEach(qo => {
                    this.currentOptions.push(qo.option);
                });
                this.currentOptions.forEach(co => {
                    this.currentOptionsTxt.push(co.answer);
                })
                this.qId = item.id;
            } catch(e) {
                alert("Cannot load answers!");
            }

        },

        async elimQO(qq, opt) {
            
            try {
                var result = await axios.get("http://localhost:8080/api/questionoption/getByQuesionAndOption/" + qq.subjectQuestion.id + "/" + opt.id);
                var qopt = result.data;
                console.log("id = " + qopt.id);
                var del = await axios.delete("http://localhost:8080/api/questionoption/" + qopt.id);
                this.currentAns = "";
                this.currentOptions = [];
                this.currentOptionsTxt = [];
                var res = await axios.get("http://localhost:8080/api/questionoption/getByQuestion/" + qq.subjectQuestion.id);
                this.currentQO = res.data;
                this.currentQO.forEach(qo => {
                    this.currentOptions.push(qo.option);
                });
                this.currentOptions.forEach(co => {
                    this.currentOptionsTxt.push(co.answer);
                })
                this.qId = qq.id;
            } catch(e) {
                alert("Cannot delete option!" + e);
            }

        },
    }
  }
</script>

