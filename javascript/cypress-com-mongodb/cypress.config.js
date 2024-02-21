const { defineConfig } = require("cypress");

module.exports = defineConfig({
  projectId: "3ndc8n",
  baseURL: "https://alura-fotos.herokuapp.com", 
  e2e: {
    setupNodeEvents(on, config) {
    },
  },
});