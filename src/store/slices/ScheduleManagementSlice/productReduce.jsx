// @ts-nocheck
import axios from 'axios';
import { createAsyncThunk } from '@reduxjs/toolkit';
const baseURL = 'https://furniturev2-1.onrender.com/api';

const getAllProduct = createAsyncThunk('GET_PRODUCT_LIST', async () => {
  const result = await axios.get(`${baseURL}/products/0`);
  return result.data;
});

const createProduct = createAsyncThunk('CREATE_PRODUCT', async (payload) => {
  try {
    const response = await axios.post(`${baseURL}/products`, payload, {
      headers: {
        'Content-Type': 'application/form-data',
      },
    });

    return response.data;
  } catch (error) {
    // Xử lý lỗi nếu có
    throw error;
  }
});

const deleteProduct = createAsyncThunk('DELETE_PRODUCT', async (payload) => {
  const response = await axios.delete(`${baseURL}/products`, payload);
  return response.data;
});

export { getAllProduct, createProduct, deleteProduct };
