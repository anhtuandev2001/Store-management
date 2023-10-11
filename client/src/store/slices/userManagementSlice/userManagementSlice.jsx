import { createSlice } from '@reduxjs/toolkit';
import { toast } from 'react-toastify';
import { createUser, getAllAccount, loginUser } from './userReduce';
const initialState = {
  accountList: [],
  token: localStorage.getItem('jwtToken') || null,
  login: {},
  status: {
    loginUser: '',
    accountList: '',
    createUser: '',
  },
};

const userManagementSlice = createSlice({
  name: 'userManagement',
  initialState,
  reducers: {
    clearStatus: (state) => {
      state.status = {
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
        state.status = {
          ...state.status,
          accountList: 'success',
        };
        state.accountList = action.payload;
      })
      .addCase(getAllAccount.rejected, (state) => {
        toast.error('No response from server');
        state.status = {
          ...state.status,
          accountList: 'error',
        };
        state.accountList = [];
      })

      //createUser
      .addCase(createUser.fulfilled, (state, action) => {
        toast.success('Create Successfully');
        state.status = {
          ...state.status,
          createUser: 'success',
        };
      })
      .addCase(createUser.rejected, (state) => {
        toast.error('Create Error');
        state.status = {
          ...state.status,
          createUser: 'error',
        };
      })

      // login
      .addCase(loginUser.fulfilled, (state, action) => {
        toast.success('Login Success');
        state.status = {
          ...state.status,
          loginUser: 'success',
        };
        state.login = action.payload.data;
      })
      .addCase(loginUser.rejected, (state) => {
        toast.error('Login Error');
        state.status = {
          ...state.status,
          loginUser: 'error',
        };
      });
  },
});
export const { clearStatus, actionLogin, actionLogout } =
  userManagementSlice.actions;
export default userManagementSlice.reducer;
