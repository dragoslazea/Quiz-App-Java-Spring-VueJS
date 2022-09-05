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

      <template v-slot:extension>
        <v-tabs
          align-with-title
          v-model="tab">
          <v-tab>My Account</v-tab>
          <v-tab>All Subjects</v-tab>
          <v-tab v-if="type === 'Student'">My Grades</v-tab>
          <v-tab v-if="type === 'Teacher'">Manage Quizzes</v-tab>
          <v-tab v-if="type === 'Teacher'">All Users</v-tab>
        </v-tabs>
      </template>
    </v-app-bar>
    <v-sheet
      id="scrolling-techniques-3"
      class="overflow-y-auto"
      max-height="880"
    >
      <v-container style="height: 1000px;">
        <br><br><br><br><br><br><br><br><br><br>
        <v-tabs-items v-model="tab">
          <v-tab-item>
            <br><br>
              <v-row align="center" justify="space-around"><h2 class="text-h4 warning--text pl-4">
                My personal information
              </h2></v-row>
            <v-form
              ref="form"
              class="ml-6 mr-6"
            >
              <v-text-field
                v-model="username"
                label="Username"
                required
              >
              </v-text-field>

              <v-text-field
                v-model="password"
                label="Password"
                type="password"
                required
              >
              </v-text-field>

              <v-text-field
                v-model="type"
                label="Type"
                readonly
                required
              >
              </v-text-field>

              <v-btn
                class="mr-4"
                color="success"
                @click="update"
              >
                <v-icon left>
                  mdi-account-edit
                </v-icon>
                Update
              </v-btn>

              <v-btn
                color="error"
                class="mr-4"
                @click="del"
              >
                <v-icon left>
                  mdi-account-remove
                </v-icon>
                Delete
              </v-btn>

              <v-btn
                color="warning"
                class="mr-4"
                @click="$router.push({ name: 'login' })"
              >
                <v-icon left>
                  mdi-arrow-left
                </v-icon>
                Log Out
              </v-btn>
              <br><br>
          </v-form>
          
          <br><br>
          <v-row v-if="type === 'Student'" align="center" justify="space-around"><h2 class="text-h4 warning--text pl-4">
              My courses
          </h2></v-row>

          <br><br>
          <v-row v-if="type === 'Teacher'" align="center" justify="space-around"><h2 class="text-h4 warning--text pl-4">
              My quizzes
          </h2></v-row>

          <div v-for='enrolment in myEnrolments' :key='enrolment.id'
            v-if="type === 'Student'">
            <br>
            <v-card
              class="mx-10"
            >

              <v-card-title>
                {{ enrolment.subject.name }}
              </v-card-title>

              <v-card-subtitle>
                {{ enrolment.subject.description }}
              </v-card-subtitle>

              <v-card-actions>
                <v-btn
                  color="orange lighten-2"
                  text
                  @click="deleteEnrollment(enrolment)"
                > 
                  Leave
                </v-btn>

                <v-btn
                  color="orange lighten-2"
                  text
                  @click="seeQuizzesStudent(enrolment.subject)"
                > 
                  See quizzes
                </v-btn>                

                <v-spacer></v-spacer>
              </v-card-actions>

            
            </v-card>
          </div>

          <div v-for='quiz in myQuizzes' :key='quiz.id'
            v-if="type === 'Teacher'">
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
                  @click="selectQuiz(quiz)"
                > 
                  See attempts
                </v-btn>

                <v-btn
                  color="orange lighten-2"
                  text
                  @click="manageQuiz(quiz)"
                > 
                  Manage quiz
                </v-btn>

                <v-btn
                  color="orange lighten-2"
                  text
                  @click="deleteQuiz(quiz)"
                > 
                  Delete quiz
                </v-btn>

                <v-spacer></v-spacer>

                
              </v-card-actions>

            
            </v-card>
          </div>
      </v-tab-item>

      <v-tab-item>
        <br><br>
          <v-row align="center" justify="space-around"><h2 class="text-h4 warning--text pl-4">
              All courses
          </h2></v-row><br>
        <v-card v-if="type === 'Teacher'">
          <v-card-title class="blue white--text text-h5">Add a new subject section</v-card-title>
      
          <v-img
            max-height="210"
            :aspect-ratio="24/9"
            src="https://www.newcastle.edu.au/__data/assets/image/0010/559378/geospatial-engineering-header.jpg"
          >
          </v-img>

          <v-form
            ref="form"
            class="ml-6 mr-6"
          >


            <v-text-field
              v-model="newSubjName"
              label="Subject Name"
              required
            ></v-text-field>

            <v-text-field
              v-model="newSubjDescription"
              label="Course Description"
              required
            ></v-text-field>

            <v-btn
              color="warning"
              class="mr-4"
              @click="createSubject"
            >
              <v-icon left>
                mdi-pencil
              </v-icon>
              Create
            </v-btn>

            <br>

          </v-form>
          <br>
        </v-card>
        
        <div v-for='subject in allSubjects' :key='subject.id'>
          <br>
          <v-card
            class="mx-10"
          >

            <v-card-title>
              {{ subject.name }}
            </v-card-title>

            <v-card-subtitle>
              {{ subject.description }}
            </v-card-subtitle>

            <v-card-actions>
              <v-btn
                color="orange lighten-2"
                text
                v-if="type === 'Teacher'"
                @click="selectSubject(subject)"
              > 
                Edit
              </v-btn>

              <v-btn
                color="orange lighten-2"
                text
                v-if="type === 'Student'"
                @click="createEnrollment(subject)"
              > 
                Enroll
              </v-btn>

              <v-spacer></v-spacer>
            </v-card-actions>

            
          </v-card>
        </div>
      </v-tab-item>


      <v-tab-item v-if="type === 'Student'">
        <br><br>
          <v-row v-if="type === 'Student'" align="center" justify="space-around"><h2 class="text-h4 warning--text pl-4">
              My grades
          </h2></v-row>
        <div v-for='a in myAttempts' :key='a.id'>
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
                Score: {{ a.score }}/{{ a.maxScore }}
              </div>
            </v-card-text>

            
          </v-card>
        </div>
      </v-tab-item>

      <v-tab-item v-if="type === 'Teacher'">
        <br><br>
          <v-row align="center" justify="space-around"><h2 class="text-h4 warning--text pl-4">
              Manage quizzes
          </h2></v-row><br>
        <v-card>
          <v-card-title class="blue white--text text-h5">Create a new answear option</v-card-title>
          <v-form>
            <v-text-field
              v-model="newOptionText"
              label="Answear Option"
              required
            ></v-text-field>

            <v-btn
              color="warning"
              class="mr-4"
              @click="createOption"
            >
              <v-icon left>
                mdi-pencil
              </v-icon>
              Create
            </v-btn>
          </v-form>
        </v-card>

        <br><br>

        <v-card>
          <v-card-title class="blue white--text text-h5">Add a new question</v-card-title>
          <v-form>
            <v-text-field
              v-model="newQuestionText"
              label="Question"
              required
            ></v-text-field>

            <v-autocomplete
              v-model="correctOptionText"
              :items="optionsText"
              outlined
              dense
              chips
              small-chips
              label="Select the correct answer"
          ></v-autocomplete>

            <v-btn
              color="warning"
              class="mr-4"
              @click="createQuestion"
            >
              <v-icon left>
                mdi-pencil
              </v-icon>
              Create
            </v-btn>
          </v-form>
        </v-card>

        <br><br>

        <v-card>
          <v-card-title class="blue white--text text-h5">Set options</v-card-title>
          <v-form>
            <br>
            <v-autocomplete
              v-model="selQTxt"
              :items="questionsText"
              outlined
              dense
              chips
              small-chips
              label="Select the question"
            ></v-autocomplete>

            <v-autocomplete
              v-model="selOTxt"
              :items="optionsText"
              outlined
              dense
              chips
              small-chips
              label="Select an option"
            ></v-autocomplete>

            <v-btn
              color="warning"
              class="mr-4"
              @click="createQuestionOption"
            >
              <v-icon left>
                mdi-pencil
              </v-icon>
              Add option
            </v-btn>
          </v-form>
        </v-card>

        <br><br>

        <v-card>
          <v-card-title class="blue white--text text-h5">Create a new quiz section</v-card-title>
          <v-form>
            <br>
            <v-autocomplete
              v-model="selSubjTxt"
              :items="subjectsTxt"
              outlined
              dense
              chips
              small-chips
              label="Select the subject"
            ></v-autocomplete>

             <v-text-field
              v-model="newChapter"
              label="Chapter"
              required
            ></v-text-field>

            <v-btn
              color="warning"
              class="mr-4"
              @click="createQuiz"
            >
              <v-icon left>
                mdi-pencil
              </v-icon>
              Create
            </v-btn>
          </v-form>
        </v-card>

        <br><br>

        <v-card>
          <v-card-title class="blue white--text text-h5">Add questions to a quiz</v-card-title>
          <v-form>
            <br>
            <v-autocomplete
              v-model="selChTxt"
              :items="chapters"
              outlined
              dense
              chips
              small-chips
              label="Select the quiz"
            ></v-autocomplete>

             <v-autocomplete
              v-model="selQuTxt"
              :items="questionsText"
              outlined
              dense
              chips
              small-chips
              label="Select the question"
            ></v-autocomplete>

            <v-btn
              color="warning"
              class="mr-4"
              @click="createQuizQuestion"
            >
              <v-icon left>
                mdi-pencil
              </v-icon>
              Add to quiz
            </v-btn>
          </v-form>
        </v-card>

      </v-tab-item>

      <v-tab-item v-if="type === 'Teacher'">
        <br><br>
          <v-row align="center" justify="space-around"><h2 class="text-h4 warning--text pl-4">
              Users personal information
          </h2></v-row><br>
        <v-card>
          <v-card-title class="blue white--text text-h5">
            User Directory
          </v-card-title>
          <v-img
            max-height="510"
            :aspect-ratio="24/9"
            src="https://epe.brightspotcdn.com/a2/0d/caff29434c629bda509712a3e27b/tsj-remote-teacher-1253007851-02.jpg"
          >
          </v-img>
          <v-row
            class="pa-4"
            justify="space-between"
          >
            <v-col cols="5">
              <v-treeview
                :active.sync="active"
                :items="items"
                :load-children="fetchUsers"
                :open.sync="open"
                activatable
                color="warning"
                open-on-click
                transition
              >
                <template v-slot:prepend="{ item }">
                  <v-icon v-if="!item.children" @click="selectUser(item)">
                    mdi-account
                    {{ item.username }}
                  </v-icon>
                </template>
                <template v-slot:label="{ item }">
                  <div @click="selectUser(item)">
                    {{ item.username }}
                  </div>
                </template>
              </v-treeview>
            </v-col>

            <v-divider vertical></v-divider>

            <v-col
              class="d-flex text-center"
            >
              <v-scroll-y-transition mode="out-in">
                <div
                  v-if="!selectedUser"
                  class="text-h6 grey--text text--lighten-1 font-weight-light"
                  style="align-self: center;"
                >
                  Select a User
                </div>
                <v-card
                  v-else-if="selectedUser.username !== 'Users'"
                  :key="selectedUser.idUser"
                  class="pt-6 mx-auto"
                  flat
                  max-width="400"
                >
                  <v-avatar v-if="selectedUser.username !== 'Users'" size="88" :color="'#'+(Math.random()*0xFFFFFF<<0).toString(16)">
                    <span class="white--text text-h3">{{ selectedUser.username[0] + selectedUser.username[selectedUser.username.indexOf(".") + 1] }}</span>
                  </v-avatar>
                  <v-card-text>
                    <h3 class="text-h5 mb-2">
                      {{ selectedUser.username }}
                    </h3>
                    <div class="blue--text mb-2" v-if="selectedUser.type === 1">
                      Teacher
                    </div>
                    <div class="blue--text mb-2" v-else-if="selectedUser.username !== 'Users'">
                      Student
                    </div>
                  </v-card-text>
                  <v-divider></v-divider>
                </v-card>
              </v-scroll-y-transition>
            </v-col>
          </v-row>
        </v-card>
      </v-tab-item>

    </v-tabs-items>

    
      </v-container>
    </v-sheet>

    
  </v-card>
</template>

<script>
  import * as SockJS from 'sockjs-client';
  import * as Stomp from 'stompjs';
  import axios from "axios";
  export default {
    name: 'home',
    data() {
      return {
        loggedInUser: null,
        username: "",
        password: "",
        type: "",
        allUsers: [],
        active: [],
        avatar: null,
        selectedUser: null,
        open: [],
        tab: 'My Account',
        allSubjects: [],
        show: [false, false, false, false, false, false],
        newSubjName: "",
        newSubjDescription: "",
        myEnrolments: [],
        newEnrollmentStudent: null,
        newEnrollmentSubject: null,
        newOptionText: "",
        options: [],
        newQuestionText: "",
        questions: [],
        newQuestionCorrectAnswer: null,
        newQuestionOption: null,
        optionsText: [],
        correctOptionText: "",
        allQuestionOptions: "",
        selQTxt: "",
        selOTxt: "",
        questionsText: [],
        quizzes: [],
        chapters: [],
        newChapter: "",
        subjectsTxt: [],
        selSubjTxt: "",
        allQuizQuestions: [],
        selChTxt: "",
        selQuTxt: "",
        myQuizzes: [],
        myAttempts: [],
      }
    },

    computed: {
      items () {
        return [
          {
            username: 'Users',
            children: this.allUsers,
          },
        ]
      },
      selected () {
        if (!this.active.length) return undefined

        const id = this.active[0]

        //return await axios.get("http://localhost:8080/api/usertable/" + window.localStorage.getItem("idUser"));
        //alert("User selected!")

        return this.items.find(user => user.idUser === id)
        
      },
    },

    watch: {
      selected: 'randomAvatar',
    },

    async created() {
      try {
        var resultUsers = await axios.get("http://localhost:8080/api/usertable");
        var result = await axios.get("http://localhost:8080/api/usertable/" + window.localStorage.getItem("idUser"));
        var resultSubjects = await axios.get("http://localhost:8080/api/subject");
        var resultOptions =  await axios.get("http://localhost:8080/api/ansoption");
        var resultQuestions =  await axios.get("http://localhost:8080/api/question");
        var resultQuestionOptions = await axios.get("http://localhost:8080/api/questionoption");
        var resultQuizzes = await axios.get("http://localhost:8080/api/quiz");
        var resMyQuizzes = await axios.get("http://localhost:8080/api/quiz/getByTeacher/" + window.localStorage.getItem("idUser"));
        var resAtt = await axios.get("http://localhost:8080/api/attempt/getByStudent/" + window.localStorage.getItem("idUser"));
        this.loggedInUser = result.data;
        this.username = this.loggedInUser.username;
        this.password = this.loggedInUser.password;
        this.allUsers = resultUsers.data;
        this.allSubjects = resultSubjects.data;
        this.options = resultOptions.data;
        this.questions = resultQuestions.data;
        this.allQuestionOptions = resultQuestionOptions.data;
        this.quizzes = resultQuizzes.data;
        this.myQuizzes = resMyQuizzes.data;
        this.myAttempts = resAtt.data;

        this.options.forEach(op => {
          this.optionsText.push(op.answer);
        });

        this.questions.forEach(q => {
          this.questionsText.push(q.questionText);
        });

        this.allSubjects.forEach(s => {
          this.subjectsTxt.push(s.name);
        });

        this.quizzes.forEach(qz => {
          this.chapters.push(qz.chapter);
        });

        //var resultEnrolments = await axios.get("http://localhost:8080/api/enrolment/getByUsername/" + this.username);
        var resultEnrolments = await axios.get("http://localhost:8080/api/enrolment/getByStudent/" + window.localStorage.getItem("idUser"));
        this.myEnrolments = resultEnrolments.data;
      
        if (this.loggedInUser.type == 0)
          this.type = "Student";
        else
          this.type = "Teacher";
      }
      catch (e) {
        alert("Error loading your personal information!");
      }
    },

    mounted() {
      var socket = new SockJS("http://localhost:8080/gs-websockets");
      var stompClient = Stomp.over(socket);
      stompClient.connect({}, frame => {
        console.log("web socket connected!");
        stompClient.subscribe("/websockets", message => {
          //console.log("am primit: " + message.body);
          if (this.type == "Teacher") {
            alert(message.body);
          }
        });
      });
    },

     methods: {
      async del(){
        try { 
          var result = await axios.delete("http://localhost:8080/api/usertable/" + window.localStorage.getItem("idUser"));
          this.$router.push({ name: 'login' });
          alert("Your account was deleted!");
          
        } catch (e) {
          alert("Failed to delete your account!");
        }
      },
      async update() {
        try { 
          if (this.username == "" || this.password == "") {
            throw error;
          }
          else {
            var result = await axios.put("http://localhost:8080/api/usertable/" + window.localStorage.getItem("idUser"), { type: this.loggedInUser.type, username: this.username, password: this.password });
            alert("Personal information updated!");
          }
        } catch (e) {
          alert("Error updating your personal information! All fields are mandatory!\n");
        }
      },

      async fetchUsers (item) {
        await pause(1500)
        return await axios.get("http://localhost:8080/api/usertable")
      },

      selectUser (item) {
        this.selectedUser = item
      },
      
      randomAvatar () {
        this.avatar = avatars[Math.floor(Math.random() * avatars.length)]
      },

      async selectQuiz (item) {
        this.$router.push({ name: 'attempts-page', params: {id: item.id} });
      },

      async manageQuiz (item) {
        this.$router.push({ name: 'quiz-manage-page', params: {id: item.id} });
      },

      async selectSubject(item) {
          
          //window.localStorage.setItem("nameSubject", item.name);
          //window.localStorage.setItem("descriptionSubject", item.description);
          this.$router.push({ name: 'subjectpage', params: {id: item.id} });
      },

      async seeQuizzesStudent(item) {
          
          this.$router.push({ name: 'quizzespage', params: {id: item.id} });
      },

      async createSubject() {
        try { 
           if (this.newSubjName == "" || this.newSubjDescription == "") {
                throw error;
            }
            else {
                var result = await axios.post("http://localhost:8080/api/subject", { name: this.newSubjName, description: this.newSubjDescription });
                var resultSubjects = await axios.get("http://localhost:8080/api/subject");
                this.allSubjects = resultSubjects.data;
                this.$refs.form.reset();
                //this.$router.push({ name: 'login' });
                
                this.subjectsTxt = [];
                
                this.allSubjects.forEach(s => {
                  this.subjectsTxt.push(s.name);
                });
            }
            
        } catch (e) {
            alert("All fields are mandatory!");
        }
      },

      async createEnrollment(subjectItem) {
        
        try { 
           this.myEnrolments.forEach(en => {
              if (en.subject.id == subjectItem.id) {
                throw error;
              }
           });

            //alert("id " + sId);
            var resUser = await axios.get("http://localhost:8080/api/usertable/" + window.localStorage.getItem("idUser"));
            var usr = resUser.data;
            var result = await axios.post("http://localhost:8080/api/enrolment", { student: usr, subject: subjectItem } );
            var resultEnrolments = await axios.get("http://localhost:8080/api/enrolment/getByStudent/" + window.localStorage.getItem("idUser"));
            this.myEnrolments = resultEnrolments.data;
            //this.$refs.form.reset();
            //this.$router.push({ name: 'login' });
          }
            
          catch (e) {
            alert("You are already enrolled in this course!");
        }
      },

      async deleteEnrollment(enrolmentItem) {
        
        try { 
            var result = await axios.delete("http://localhost:8080/api/enrolment/" + enrolmentItem.id );
            var resultEnrolments = await axios.get("http://localhost:8080/api/enrolment/getByStudent/" + window.localStorage.getItem("idUser"));
            this.myEnrolments = resultEnrolments.data;
            //this.$refs.form.reset();
            //this.$router.push({ name: 'login' });
          }
            
          catch (e) {
            alert("You are already enrolled in this course!");
        }
      },

      async createOption() {
        
        try { 
            if (this.newOptionText === "") {
              alert("The field is mandatory!");
            } else {
              this.options.forEach(op => {
                if (op.answer === this.newOptionText) {
                  throw error;
                }
              });

              var opt = await axios.post("http://localhost:8080/api/ansoption", { answer: this.newOptionText });

              var resultOptions =  await axios.get("http://localhost:8080/api/ansoption");
              this.options = resultOptions.data;

              this.optionsText = [];

              this.options.forEach(op => {
                this.optionsText.push(op.answer);
              });

            }
          }
            
          catch (e) {
            alert("Option already exists!");
        }
      },

      async createQuestion() {
        
        try { 
            if (this.correctOptionText === "" || this.newQuestionText === "") {
              alert("All fields are mandatory!");
            } else {
              this.questions.forEach(q => {
                if (q.questionText === this.newQuestionText) {
                  throw 'Question already exists!';
                }
              });

              var op = await axios.get("http://localhost:8080/api/ansoption/getAnswer/" + this.correctOptionText);
              //alert("Option is: " + op.answer);

              var resQ = await axios.post("http://localhost:8080/api/question", { questionText: this.newQuestionText, correctOption: op.data });
              var q = await axios.get("http://localhost:8080/api/question/getText/" + this.newQuestionText);
              var resQueOp = await axios.post("http://localhost:8080/api/questionoption", { question: q.data, option: op.data });

              var resultQuestionOptions = await axios.get("http://localhost:8080/api/questionoption");
              this.allQuestionOptions = resultQuestionOptions.data;

              var resultQuestions =  await axios.get("http://localhost:8080/api/question");
              this.questions = resultQuestions.data;

              this.questionsText = [];

              this.questions.forEach(q => {
                this.questionsText.push(q.questionText);
              });

            }
          }
            
          catch (e) {
            alert(e);
        }
      },

      async createQuestionOption() {
        
        try { 
            if (this.selOTxt === "" || this.selQTxt === "") {
              alert("All fields are mandatory!");
            } else {
              this.allQuestionOptions.forEach(qo => {
                if (qo.question.questionText === this.selQTxt && qo.option.answer === this.selOTxt) {
                  throw 'Option already choosen!';
                }
              });

              var op = await axios.get("http://localhost:8080/api/ansoption/getAnswer/" + this.selOTxt);
              var qs = await axios.get("http://localhost:8080/api/question/getText/" + this.selQTxt);
              
              var resQueOp = await axios.post("http://localhost:8080/api/questionoption", { question: qs.data, option: op.data });

              var resultQuestionOptions = await axios.get("http://localhost:8080/api/questionoption");
              this.allQuestionOptions = resultQuestionOptions.data;

            }
          }
            
          catch (e) {
            alert(e);
        }
      },

      async createQuiz() {
        
        try { 
            if (this.newChapter === "" || this.selSubjTxt === "") {
              alert("All fields are mandatory!");
            } else {
              this.chapters.forEach(c => {
                if (c === this.newChapter) {
                  throw 'A quiz with the same chapter name already exists!';
                }
              });
              var s = await axios.get("http://localhost:8080/api/subject/getName/" + this.selSubjTxt);
              var u = await axios.get("http://localhost:8080/api/usertable/" + window.localStorage.getItem("idUser"));
              
              var quiz = await axios.post("http://localhost:8080/api/quiz", { chapter: this.newChapter, teacher: u.data, quizSubject: s.data });

              var resultQuiz = await axios.get("http://localhost:8080/api/quiz");
              this.quizzes = resultQuiz.data;

              this.chapters = [];

              this.quizzes.forEach(qz => {
                this.chapters.push(qz.chapter);
              });

            }
          }
            
          catch (e) {
            alert(e);
        }
      },

      async createQuizQuestion() {
        
        try { 
            if (this.selChTxt === "" || this.selQuTxt === "") {
              alert("All fields are mandatory!");
            } else {
              this.allQuizQuestions.forEach(qq => {
                if (qq.subjectQuestion.questionText === this.selQuTxt && qq.quizQ.chapter === this.selChTxt) {
                  throw 'This quiz already contains this question';
                }
              });

              var qz = await axios.get("http://localhost:8080/api/quiz/getChapter/" + this.selChTxt);
              var qs = await axios.get("http://localhost:8080/api/question/getText/" + this.selQuTxt);
              
              var resQQ = await axios.post("http://localhost:8080/api/quizquestion", { subjectQuestion: qs.data, quizQ: qz.data });

              var resultQuizQuestions = await axios.get("http://localhost:8080/api/quizquestion");
              this.allQuizQuestions = resultQuizQuestions.data;

            }
          }
            
          catch (e) {
            alert(e);
        }
      },

      async deleteQuiz(item) {
        
        try { 
              var resDel = await axios.delete("http://localhost:8080/api/quiz/" + item.id);
              var resGet = await axios.get("http://localhost:8080/api/quiz/getByTeacher/" + window.localStorage.getItem("idUser"));
              this.myQuizzes = resGet.data;
          }
            
          catch (e) {
            alert(e);
        }
      },
    }
  }
  
</script>