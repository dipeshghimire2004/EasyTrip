import axios from "axios";

// Create an Axios instance
const api = axios.create({
  baseURL: "http://localhost:8021", // Backend URL
  withCredentials: true,            // Include cookies in requests
});

// Automatically attach token from localStorage before each request
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("accessToken");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Optional: Utility function to manually set or remove token
export const setAuthToken = (token) => {
  if (token) {
    localStorage.setItem("accessToken", token);
  } else {
    localStorage.removeItem("accessToken");
  }
};

export default api;
