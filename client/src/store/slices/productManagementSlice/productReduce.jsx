// @ts-nocheck
import { createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';
const baseURL = 'http://localhost:3000/products';

const getAllProduct = createAsyncThunk('GET_PRODUCT_LIST', async () => {
  const token = localStorage.getItem('token');
  console.log(token);
  const result = await axios.get(`${baseURL}`, {
    headers: {
      // Thêm token vào header
      Authorization: `Bearer ${token}`,
    },
  });
  return result.data.data;
});

const createProduct = createAsyncThunk(
  'CREATE_PRODUCT',
  async (requestData) => {
    const formData = new FormData();
    for (const key in requestData) {
      formData.append(key, requestData[key]);
    }
    const response = await axios.post(`${baseURL}/products`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  }
);

const deleteProduct = createAsyncThunk('DELETE_PRODUCT', async (formData) => {
  const response = await axios.delete(`${baseURL}/products`, {
    headers: {
      'Content-Type': 'application/json',
    },
    data: formData,
  });
});

const updateProduct = createAsyncThunk(
  'UPDATE_PRODUCT',
  async (requestData) => {
    const formData = new FormData();
    for (const key in requestData) {
      formData.append(key, requestData[key]);
    }
    const response = await axios.post(`${baseURL}/products`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  }
);

const getAllCategory = createAsyncThunk('GET_CATEGORY_LIST', async () => {
  const result = await axios.get(`${baseURL}/category`);
  return result.data.data;
});

const createCategory = createAsyncThunk(
  'CREATE_CATEGORY',
  async (requestData) => {
    const response = await axios.post(`${baseURL}/category`, requestData);
  }
);

const updateCategory = createAsyncThunk(
  'UPDATE_CATEGORY',
  async (requestData) => {
    const response = await axios.post(`${baseURL}/category`, requestData);
  }
);

const deleteCategory = createAsyncThunk(
  'DELETE_CATEGORY',
  async (requestData) => {
    const response = await axios.delete(`${baseURL}/category/${requestData}`);
  }
);

const getAllOrder = createAsyncThunk('GET_ORDER_LIST', async () => {
  const result = await axios.get(`${baseURL}/order`);
  return result.data;
});

const changeStatusOrder = createAsyncThunk(
  'CHANGE_STATUS_ORDER',
  async (requestData) => {
    const response = await axios.post(`${baseURL}/order`, requestData);
    return response.data;
  }
);

export {
  changeStatusOrder,
  createCategory,
  createProduct,
  deleteCategory,
  deleteProduct,
  getAllCategory,
  getAllOrder,
  getAllProduct,
  updateCategory,
  updateProduct,
};
