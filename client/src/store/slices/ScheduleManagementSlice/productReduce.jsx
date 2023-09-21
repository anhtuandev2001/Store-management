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

const getAllAccount = createAsyncThunk('GET_ACCOUNT_LIST', async () => {
  const result = await axios.get(`${baseURL}/user/getAll`);
  return result.data.data;
});

const createUser = createAsyncThunk('CREATE_USER', async (requestData) => {
  const response = await axios.post(`${baseURL}/user/signup`, requestData);
  return response.data;
});

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

const loginUser = createAsyncThunk('LOGIN', async (requestData) => {
  const response = await axios.post(`${baseURL}/user/login`, requestData);
  return response.data;
});

export {
  changeStatusOrder,
  createCategory,
  createProduct,
  createUser,
  deleteCategory,
  deleteProduct,
  getAllAccount,
  getAllCategory,
  getAllOrder,
  loginUser,
  getAllProduct,
  updateCategory,
  updateProduct,
};
