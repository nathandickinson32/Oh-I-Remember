<template>
  <div class="friend-card">
    <h3>{{ friend.firstName }} {{ friend.lastName }}</h3>
    <div class="button-container">
      <button @click="askQuestion">Ask Question</button>
      <button @click="deleteFriend">Remove Friend</button>
    </div>
  </div>
</template>

<script>
import FriendService from "../services/FriendService";

export default {
  data() {
    return {
      idDto: {
        id: "",
      },
    };
  },
  props: {
    friend: {
      type: Object,
      required: true,
    },
  },
  methods: {
    askQuestion() {
      this.$router.push({
        name: "ask-question-by-id-view",
        params: { receiverId: Number(this.friend.id) },
      });
    },
    deleteFriend() {
      if (
        !window.confirm(
          `Are you sure you want to remove ${this.friend.firstName}?`
        )
      ) {
        return;
      }

      this.idDto.id = Number(this.friend.id);
      FriendService.deleteFriend(this.idDto)
        .then((response) => {
          if (response.status === 200) {
            this.$emit("friendRemoved", this.friend.id);
            this.$router.push({ name: "home" });
          }
        })
        .catch((error) => {
          console.error("Error removing friend:", error);
          alert("Failed to remove friend. Please try again.");
        });
    },
  },
};
</script>

<style scoped>
.button-container {
  display: flex;
  gap: 10px;
}

button {
  padding: 8px 16px;
  cursor: pointer;
}
</style>
