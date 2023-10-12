import { createSlice } from '@reduxjs/toolkit';
import { toast } from 'react-toastify';
import {
  createUser,
  getAllAccount,
  getAllAddress,
  loginUser,
} from './userReduce';
const initialState = {
  accountList: [],
  addressList: [],
  token: localStorage.getItem('jwtToken') || null,
  login: {},
  statusUser: {
    addressList: '',
    loginUser: '',
    accountList: '',
    createUser: '',
  },
};

const userManagementSlice = createSlice({
  name: 'userManagement',
  initialState,
  reducers: {
    clearStatusUser: (state) => {
      state.statusUser = {
        addressList: '',
        loginUser: '',
        accountList: '',
        createUser: '',
      };
    },
    actionLogin: (state, action) => {
      state.token = action.payload;
      localStorage.setItem('jwtToken', action.payload);
    },
    actionLogout: (state) => {
      state.token = null;
      localStorage.removeItem('jwtToken');
    },
  },
  extraReducers: (builder) => {
    builder
      // //get Account List
      .addCase(getAllAccount.fulfilled, (state, action) => {
        state.statusUser = {
          ...state.statusUser,
          accountList: 'success',
        };
        state.accountList = action.payload;
      })
      .addCase(getAllAccount.rejected, (state) => {
        toast.error('No response from server');
        state.statusUser = {
          ...state.statusUser,
          accountList: 'error',
        };
        state.accountList = [];
      })

      //createUser
      .addCase(createUser.fulfilled, (state, action) => {
        toast.success('Create Successfully');
        state.statusUser = {
          ...state.statusUser,
          createUser: 'success',
        };
      })
      .addCase(createUser.rejected, (state) => {
        toast.error('Create Error');
        state.statusUser = {
          ...state.statusUser,
          createUser: 'error',
        };
      })

      // login
      .addCase(loginUser.fulfilled, (state, action) => {
        toast.success('Login Success');
        state.statusUser = {
          ...state.statusUser,
          loginUser: 'success',
        };
        state.login = action.payload.data;
      })
      .addCase(loginUser.rejected, (state) => {
        toast.error('Login Error');
        state.statusUser = {
          ...state.statusUser,
          loginUser: 'error',
        };
      })

      // get address List
      .addCase(getAllAddress.fulfilled, (state, action) => {
        state.statusUser = {
          ...state.statusUser,
          addressList: 'success',
        };
        state.addressList = action.payload;
      })
      .addCase(getAllAddress.rejected, (state) => {
        toast.error('No response from server');
        state.statusUser = {
          ...state.statusUser,
          addressList: 'error',
        };
        state.addressList = [];
      });
  },
});
export const { clearStatusUser, actionLogin, actionLogout } =
  userManagementSlice.actions;
export default userManagementSlice.reducer;
