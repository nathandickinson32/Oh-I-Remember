<template>
  <div class="content">
    <div class="document-container">
      <h4>Weather Forecast</h4>
      
      <!-- Buttons to fetch weather for different cities -->
      <div class="buttons">
        <button @click="fetchWeatherForecast('Columbiana')">Columbiana</button>
        <button @click="fetchWeatherForecast('Cleveland')">Cleveland</button>
        <button @click="fetchWeatherForecast('Akron')">Akron</button>
        <button @click="fetchWeatherForecast('Youngstown')">Youngstown</button>
      </div>

      <!-- Display forecast data once it's fetched -->
      <div v-if="forecastData.length" class="forecast-data">
        <h5>Weather Forecast for {{ cityName }}</h5>
        <div
          v-for="period in forecastData"
          :key="period.number"
          class="forecast-item"
        >
          <hr />
          <span class="label">Day/Time: </span>{{ period.name }} <br />
          <span class="label">Temperature: </span>{{ period.temperature }}°{{ period.temperatureUnit }}
          <br />
          <span class="label">Detailed Forecast: </span> <br />
          {{ period.detailedForecast }}
        </div>
      </div>

      <!-- Loading state -->
      <div v-if="loading" class="loading">
        <span>Loading forecast...</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      forecastData: [], // Initially empty
      loading: false,    // Initially not loading
      cityName: '',      // City name for the current forecast
    };
  },
  methods: {
    fetchWeatherForecast(city) {
      this.cityName = city; // Set the city name
      this.loading = true;   // Start loading
      const cityCoordinates = this.getCityCoordinates(city);

      if (!cityCoordinates) {
        this.loading = false;
        console.error('Invalid city name');
        return;
      }

      fetch(`https://api.weather.gov/gridpoints/${cityCoordinates.gridId}/${cityCoordinates.x},${cityCoordinates.y}/forecast`, {
        headers: {
          "User-Agent": "(myweatherapp.com, contact@myweatherapp.com)",
        },
      })
        .then((response) => response.json())
        .then((data) => {
          this.forecastData = data.properties.periods; // Store forecast data
        })
        .catch((error) => {
          console.error("Error fetching forecast:", error);
        })
        .finally(() => {
          this.loading = false; // Stop loading once done
        });
    },
    getCityCoordinates(city) {
      // Return coordinates based on city name (for example purposes)
      const cityCoordinates = {
        Columbiana: { gridId: "PBZ", x: 30, y: 60 },
        Cleveland: { gridId: "CLE", x: 40, y: 50 },
        Akron: { gridId: "PBZ", x: 32, y: 58 },
        Youngstown: { gridId: "PBZ", x: 29, y: 61 },
      };

      return cityCoordinates[city] || null;
    },
  },
};
</script>

<style scoped>
.document-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
  background-color: #f0f8ff;
  border: 1px solid #dcdcdc;
  border-radius: 8px;
}

h4 {
  text-align: center;
  font-size: 1.5em;
  color: #007acc;
}

.buttons {
  display: flex;
  justify-content: space-around;
  margin-bottom: 20px;
}

button {
  background-color: #007acc;
  color: #fff;
  font-size: 1em;
  padding: 10px 15px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #005fa3;
}

.forecast-item {
  background-color: #fff;
  padding: 15px;
  border: 1px solid #dcdcdc;
  border-radius: 5px;
}

.label {
  font-size: 1em;
  color: #555;
  font-weight: bold;
}

hr {
  margin: 10px 0;
}

.loading {
  text-align: center;
  font-size: 1.2em;
  color: #555;
}
</style>
