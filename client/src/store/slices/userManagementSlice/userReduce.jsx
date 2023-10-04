// @ts-nocheck
import { createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';
const baseURL = 'http://localhost:3000/users';

const getAllAccount = createAsyncThunk('GET_ACCOUNT_LIST', async () => {
  const result = await axios.get(`${baseURL}/user/getAll`);
  return result.data.data;
});

const createUser = createAsyncThunk('CREATE_USER', async (requestData) => {
  const response = await axios.post(`${baseURL}/register`, requestData);
  return response.data;
});

const loginUser = createAsyncThunk('LOGIN', async (requestData) => {
  const response = await axios.post(`${baseURL}/login`, requestData);
  return response.data;
});

export { createUser, getAllAccount, loginUser };
