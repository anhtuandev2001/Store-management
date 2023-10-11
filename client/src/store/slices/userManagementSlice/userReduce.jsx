// @ts-nocheck
import { createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';
import { baseURL } from '../../../assets/enum/constants';
const token = localStorage.getItem('jwtToken');

const getAllAccount = createAsyncThunk('GET_ACCOUNT_LIST', async () => {
  const result = await axios.get(`${baseURL}users`,{
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return result.data.data;
});

const createUser = createAsyncThunk('CREATE_USER', async (requestData) => {
  const response = await axios.post(`${baseURL}users/register`, requestData);
});

const loginUser = createAsyncThunk('LOGIN', async (requestData) => {
  const response = await axios.post(`${baseURL}/login`, requestData);
  return response.data;
});

export { createUser, getAllAccount, loginUser };
