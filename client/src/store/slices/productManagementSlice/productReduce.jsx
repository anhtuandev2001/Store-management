// @ts-nocheck
import { createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';
import { baseURL } from '../../../assets/enum/constants';
const token = localStorage.getItem('jwtToken');

const getAllProduct = createAsyncThunk('GET_PRODUCT_LIST', async () => {
  const result = await axios.get(`${baseURL}products`, {
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
    const response = await axios.post(`${baseURL}products`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
        Authorization: `Bearer ${token}`,
      },
    });
  }
);

const deleteProduct = createAsyncThunk('DELETE_PRODUCT', async (productId) => {
  const response = await axios.delete(`${baseURL}products/${productId}`, {
    headers: {
      // Thêm token vào header
      Authorization: `Bearer ${token}`,
    },
  });
});

const updateProduct = createAsyncThunk(
  'UPDATE_PRODUCT',
  async (requestData) => {
    const formData = new FormData();
    for (const key in requestData) {
      formData.append(key, requestData[key]);
    }
    const response = await axios.post(`${baseURL}products`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  }
);

const getAllCategory = createAsyncThunk('GET_CATEGORY_LIST', async () => {
  const result = await axios.get(`${baseURL}category`, {
    headers: {
      // Thêm token vào header
      Authorization: `Bearer ${token}`,
    },
  });
  return result.data.data;
});

const createCategory = createAsyncThunk(
  'CREATE_CATEGORY',
  async (requestData) => {
    const response = await axios.post(`${baseURL}category`, requestData, {
      headers: {
        // Thêm token vào header
        'Content-Type': 'multipart/form-data',
        Authorization: `Bearer ${token}`,
      },
    });
  }
);

const updateCategory = createAsyncThunk(
  'UPDATE_CATEGORY',
  async (requestData) => {
    const response = await axios.post(`${baseURL}category`, requestData, {
      headers: {
        'Content-Type': 'multipart/form-data',
        Authorization: `Bearer ${token}`,
      },
    });
  }
);

const deleteCategory = createAsyncThunk(
  'DELETE_CATEGORY',
  async (requestData) => {
    const response = await axios.delete(`${baseURL}category/${requestData}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
  }
);

const getAllOrder = createAsyncThunk('GET_ORDER_LIST', async () => {
  const result = await axios.get(`${baseURL}order`, {
    headers: {
      Authorization: `Bearer ${token}`
    },
  });
  console.log(result.data.data);
  return result.data.data;
});

const changeStatusOrder = createAsyncThunk(
  'CHANGE_STATUS_ORDER',
  async (requestData) => {
    const response = await axios.post(`${baseURL}order`, requestData);
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
