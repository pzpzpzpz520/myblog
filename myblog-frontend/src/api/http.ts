import axios from 'axios';
import type { ApiResponse } from '@/types/blog';

const http = axios.create({
  baseURL: import.meta.env.VITE_API_BASE || '/api',
  timeout: 10000
});

http.interceptors.response.use((response) => {
  const payload = response.data as ApiResponse<unknown>;
  if (payload.code !== 200) {
    return Promise.reject(new Error(payload.message));
  }
  return response;
});

export default http;
