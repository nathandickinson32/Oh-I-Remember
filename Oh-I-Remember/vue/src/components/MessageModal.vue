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
/* Modal Backdrop (Overlay) */
.modal-backdrop {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5); /* Semi-transparent background */
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
}

/* Modal Box */
.modal {
    background-color: white;
    border-radius: 12px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    width: 80%;
    max-width: 600px;
    padding: 20px;
}

/* Modal Content */
.modal-content {
    display: flex;
    flex-direction: column;
}

/* Header */
.modal-header {
    font-size: 22px;
    font-weight: bold;
    color: #444;
    margin-bottom: 15px;
    text-align: center;
}

/* Body */
.modal-body {
    font-size: 16px;
    color: #666;
    margin-bottom: 20px;
    text-align: center;
}

.prompt {
    font-size: 18px;
    color: #444;
}

/* Footer */
.modal-footer {
    display: flex;
    justify-content: center;
    gap: 15px;
    margin-top: 20px;
}

/* Buttons */
#btn-container button {
    background-color: #ffb74d; /* Button color matching theme */
    color: white;
    border: none;
    padding: 12px 18px;
    font-size: 18px;
    border-radius: 8px;
    cursor: pointer;
    transition: background 0.3s ease;
}

#btn-container button:hover {
    background-color: #ff9800; /* Darker button color on hover */
}

#btn-container button:focus {
    outline: none;
}

/* Responsiveness */
@media (max-width: 768px) {
    .modal {
        width: 90%;
        padding: 15px;
    }

    .modal-header {
        font-size: 20px;
    }

    .modal-body {
        font-size: 14px;
    }

    .modal-footer {
        flex-direction: column;
        align-items: center;
    }

    #btn-container button {
        width: 100%;
        font-size: 16px;
        margin-top: 10px;
    }
}

  </style>
  