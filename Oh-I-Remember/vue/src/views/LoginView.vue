<template>
  <div class="content">
    <div id="login" class="form-container">
      <h1>Please Sign In</h1>
      <form @submit.prevent="login">
        <!-- Error message for invalid credentials -->
        <div role="alert" v-if="invalidCredentials" class="alert">
          Invalid username and password!
        </div>

        <!-- Success message for registration -->
        <div role="alert" v-if="$route.query.registration" class="alert">
          Thank you for registering, please sign in.
        </div>

        <!-- Username input field -->
        <div class="form-input-group">
          <label for="username">Username</label>
          <input
            type="text"
            id="username"
            v-model="user.username"
            required
            autofocus
          />
        </div>

        <!-- Password input field -->
        <div class="form-input-group">
          <label for="password">Password</label>
          <input
            type="password"
            id="password"
            v-model="user.password"
            required
          />
        </div>

        <button type="submit">Sign in</button>

        <!-- Link to the registration page -->
        <p>
          <router-link v-bind:to="{ name: 'register' }" class="signup-link">
            Need an account? Sign up.
          </router-link>
        </p>
      </form>
    </div>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    }
  }
};
</script>

<style scoped>
#login {
  background: white;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.1);
  max-width: 500px;
  margin: 0 auto;
}

h1 {
  font-size: 24px;
  color: #444;
  text-align: center;
}

.alert {
  color: #f44336;
  background-color: #fce4e4;
  padding: 10px;
  border-radius: 6px;
  text-align: center;
}

.form-input-group {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

label {
  font-size: 18px;
  color: #444;
  font-weight: bold;
  margin-bottom: 5px;
}

input[type="text"], input[type="password"] {
  width: 100%;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 16px;
  background-color: #fafafa;
  transition: border-color 0.3s ease;
}

input[type="text"]:focus, input[type="password"]:focus {
  border-color: #4a6fa5;
  outline: none;
}

button {
  background-color: #ffb74d;
  color: white;
  border: none;
  padding: 12px 18px;
  font-size: 18px;
  border-radius: 8px;
  cursor: pointer;
  transition: background 0.3s ease;
}

button:hover {
  background-color: #ff9800;
}

.signup-link {
  font-size: 16px;
  color: #4a6fa5;
  text-decoration: none;
}

.signup-link:hover {
  color: #ffb74d;
}

@media (max-width: 768px) {
  #login {
    padding: 15px;
  }

  h1 {
    font-size: 22px;
  }

  button {
    font-size: 16px;
  }

  input[type="text"], input[type="password"] {
    font-size: 16px;
  }
}
</style>