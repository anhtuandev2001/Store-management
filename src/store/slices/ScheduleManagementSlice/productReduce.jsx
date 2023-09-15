// @ts-nocheck
import { createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';
const baseURL = 'https://furniturev2-1.onrender.com/api';

const getAllProduct = createAsyncThunk('GET_PRODUCT_LIST', async () => {
  const result = await axios.get(`${baseURL}/products/0`);
  return result.data;
});

const createProduct = createAsyncThunk(
  'CREATE_PRODUCT',
  async (requestData) => {
    const formData = new FormData();
    for (const key in requestData) {
      formData.append(key, requestData[key]);
    }
    try {
      const response = await axios.post(`${baseURL}/products`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      console.log('Response:', response.data);
    } catch (error) {
      console.error('Error:', error);
    }
  }
);

const deleteProduct = createAsyncThunk('DELETE_PRODUCT', async (formData) => {
  try {
    const response = await axios.delete(`${baseURL}/products`, {
      headers: {
        'Content-Type': 'application/json',
      },
      data: formData,
    });
    console.log('Response:', response.data);
  } catch (error) {
    console.error('Error:', error);
  }
});

const updateProduct = createAsyncThunk(
  'UPDATE_PRODUCT',
  async (requestData) => {
    console.log(requestData);
    const formData = new FormData();
    for (const key in requestData) {
      formData.append(key, requestData[key]);
    }
    try {
      const response = await axios.post(`${baseURL}/products`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      console.log('Response:', response.data);
    } catch (error) {
      console.error('Error:', error);
    }
  }
);

const createCategory = createAsyncThunk(
  'CREATE_CATEGORY',
  async (requestData) => {
    try {
      const response = await axios.post(`${baseURL}/category`, requestData);
      console.log('Response:', response.data);
    } catch (error) {
      console.error('Error:', error);
    }
  }
);

const deleteCategory = createAsyncThunk(
  'DELETE_CATEGORY',
  async (requestData) => {
    try {
      const response = await axios.delete(`${baseURL}/category`, requestData);
      console.log('Response:', response.data);
    } catch (error) {
      console.error('Error:', error);
    }
  }
);

const getAllAccount = createAsyncThunk('GET_ACCOUNT_LIST', async () => {
  const result = await axios.get(`${baseURL}/user/getAll`);
  return result.data.data;
});

const createUser = createAsyncThunk('CREATE_USER', async (requestData) => {
  try {
    const response = await axios.post(`${baseURL}/user/signup`, requestData);
    console.log('Response:', response.data);
    return response.data;
  } catch (error) {
    console.error('Error:', error);
  }
});

const getAllOrder = createAsyncThunk('GET_ORDER_LIST', async () => {
  const result = await axios.get(`${baseURL}/order`);
  return result.data;
});

export {
  createCategory,
  createProduct,
  createUser,
  deleteCategory,
  deleteProduct,
  getAllAccount,
  getAllProduct,
  updateProduct,
  getAllOrder,
};
