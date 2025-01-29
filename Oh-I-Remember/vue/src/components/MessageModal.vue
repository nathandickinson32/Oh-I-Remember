<template>
    <div class="modal-backdrop">
      <div class="modal">
        <div class="modal-content">
          
          <header class="modal-header">
            <slot name="header">{{type}}</slot>
          </header>
  
          <section class="modal-body">
            <slot name="body"></slot>
            <p class="prompt">{{ message }}</p>
        
          </section>
  
          <footer class="modal-footer">
            <slot name="footer"></slot>
            <span id="btn-container">
              <button type="button" class="btn-green" @click="deleteModal">
                YES
              </button>
              <button type="button" class="btn-green" @click="cancelModal">
                NO
              </button>
            </span>
          </footer>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    props: {
        type: {
            type: String,
            required: true
        },
        message: {
            type: String,
            required: true
        }
    },
    name: "MessageModal",
    data() {
      return {
      };
    },
    methods: {
     
      deleteModal() {
        this.$emit("deleteModal");
      },
      cancelModal() {
        this.$emit("cancelModal");
      },
    },
  };
  </script>
  
  <style scoped>
  .modal-backdrop {
    position: fixed;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    background-color: rgba(0, 0, 0, 0.3);
    display: flex;
    justify-content: center;
    align-items: center;
  }
  
  .modal {
    background: #ffffff;
    box-shadow: 2px 2px 20px 1px;
    overflow-x: auto;
    display: flex;
    flex-direction: column;
    height: 15rem;
    width: 20rem;
    margin: auto;
  }
  
  .modal-content {
    display: flex;
    flex-direction: column;
    height: 40%;
  }
  
 
  
  .modal-header {
    position: relative;
    top: 1rem;
    border-bottom: 1px solid #eeeeee;
    color: #4aae9b;
    justify-content: center;
    align-items: flex-start;
    width: 70%;
    margin: auto;
  }
  
 
  
  .modal-body {
    position: relative;
    padding: 20px 10px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 80%;
  }
  
  .prompt {
    margin-top: 10px;
  }
  
 
  
 
  
  .btn-green {
    color: white;
    background: #4aae9b;
    border: 1px solid #4aae9b;
    border-radius: 2px;
    cursor: pointer;
    width: fit-content;
    margin-top: 10px;
  }
  
  .btn-disabled {
    color: rgba(255, 255, 255, 0.822);
    background: #4aae9c54;
    border: 1px solid #4aae9b;
    border-radius: 2px;
    cursor: pointer;
    width: fit-content;
  }
  

  </style>
  