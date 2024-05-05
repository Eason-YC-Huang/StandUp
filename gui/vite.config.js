import {defineConfig} from 'vite'
import react from '@vitejs/plugin-react-swc'

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [react()],
    build: {
        outDir: '../src/main/resources/static/',
        emptyOutDir: true,
    },

    server: {
        proxy: {
            "/api": {
                target: 'http://127.0.0.1:7040',
                changeOrigin: true,
                secure: false,
            }
        }
    }
})
