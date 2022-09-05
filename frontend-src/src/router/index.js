import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginView from '../views/LoginView.vue'
import HomeView from '../views/HomeView.vue'
import RegisterView from '../views/RegisterView.vue'
import SubjectView from '../views/SubjectView.vue'
import QuizzesView from '../views/QuizzesView.vue'
import AttemptView from '../views/AttemptView.vue'
import AttemptsView from '../views/AttemptsView.vue'
import ManageQuizView from '../views/ManageQuizView.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'login',
    component: LoginView
  },
  {
    path: '/home',
    name: 'home',
    component: HomeView
  },
  {
    path: '/register',
    name: 'register',
    component: RegisterView
  },
  {
    path: '/subjects/:id',
    name: 'subjectpage',
    component: SubjectView
  },
  {
    path: '/quizzes/:id',
    name: 'quizzespage',
    component: QuizzesView
  },
  {
    path: '/attempt/:id',
    name: 'attempt-page',
    component: AttemptView
  },
  {
    path: '/attempts/:id',
    name: 'attempts-page',
    component: AttemptsView
  },
  {
    path: '/editquiz/:id',
    name: 'quiz-manage-page',
    component: ManageQuizView
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
