import axios from "axios";

// Create an axios instance with your backend URL
const api = axios.create({
  baseURL: "http://localhost:8021", // Your backend URL
  withCredentials: true,  // Send cookies with requests (important for sessions, authentication)
  headers: {
    "Content-Type": "application/json", // Ensure the content type is JSON
    // "Accept": "application/json", // Expect JSON response
    "Authorization":"${Bearer}"
  },
});

export default api;
