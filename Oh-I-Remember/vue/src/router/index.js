import { createRouter as createRouter, createWebHistory } from 'vue-router'
import { useStore } from 'vuex'

// Import components
import HomeView from '../views/HomeView.vue';
import LoginView from '../views/LoginView.vue';
import LogoutView from '../views/LogoutView.vue';
import RegisterView from '../views/RegisterView.vue';
import QuestionListView from '../views/QuestionListView.vue';
import AnswerQuestionView from '../views/AnswerQuestionView.vue'
import DoYouRememberListView from '../views/DoYouRememberListView.vue'
import UpdateMyQuestion from '../views/UpdateQuestionView.vue'
import FriendsListView from '../views/FriendsListView.vue';
import FriendRequestListView from '../views/FriendRequestListView.vue';
import AskQuestionByIdView from '../views/AskQuestionByIdView.vue';
import AskQuestionView from '../views/AskQuestionView.vue';
import QuestionHistory from '../components/QuestionHistory.vue';
/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */
const routes = [

  {
    path: '/',
    name: 'home',
    component: HomeView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/login",
    name: "login",
    component: LoginView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/logout",
    name: "logout",
    component: LogoutView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/register",
    name: "register",
    component: RegisterView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/questions",
    name: "questions",
    component: QuestionListView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/questions/:questionId",
    name: "answer-question",
    component: AnswerQuestionView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/do-you-remember",
    name: "do-you-remember",
    component: DoYouRememberListView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/questions/edit-question/:questionId",
    name: "edit-question",
    component: UpdateMyQuestion,
    meta: {
      requiresAuth: false
    }
  },

  {
    path: "/friends-list",
    name: "friends-list",
    component: FriendsListView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/friend-requests",
    name: "friend-requests",
    component: FriendRequestListView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/ask-question/:receiverId",
    name: "ask-question-by-id-view",
    component: AskQuestionByIdView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/ask-question/",
    name: "ask-question-view",
    component: AskQuestionView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/question-history/",
    name: "question-history",
    component: QuestionHistory,
    meta: {
      requiresAuth: false
    }
  },
];

// Create the router
const router = createRouter({
  history: createWebHistory(),
  routes: routes
});

router.beforeEach((to) => {

  // Get the Vuex store
  const store = useStore();

  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    return {name: "login"};
  }
  // Otherwise, do nothing and they'll go to their next destination
});

export default router;
