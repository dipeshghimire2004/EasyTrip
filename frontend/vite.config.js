// vite.config.ts
import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'

export default defineConfig({
  plugins: [
    react(),
    tailwindcss(),
  ],
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8021',  // Your backend URL (change this as needed)
        changeOrigin: true,
        secure: false,  // Set to false if your backend is HTTP instead of HTTPS
        rewrite: (path) => path.replace(/^\/api/, ''),  // Optional: rewrite if needed
      },
    },
  },
})
