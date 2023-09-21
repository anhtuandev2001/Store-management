import { createSlice } from '@reduxjs/toolkit';
import { toast } from 'react-toastify';
import {
  changeStatusOrder,
  createCategory,
  createProduct,
  createUser,
  deleteCategory,
  deleteProduct,
  getAllAccount,
  getAllCategory,
  getAllOrder,
  getAllProduct,
  loginUser,
  updateCategory,
  updateProduct,
} from './productReduce';
const initialState = {
  productList: [],
  productImageList: [],
  createProduct: {},
  accountList: [],
  categoryList: [],
  orderList: [],
  status: {
    productInfo: '',
    createProduct: '',
    deleteProduct: '',
    updateProduct: '',
    createCategory: '',
    updateCategory: '',
    loginUser: '',
    deleteCategory: '',
    accountList: '',
    createUser: '',
    orderList: '',
    categoryList: '',
    changeStatusOrder: '',
  },
};

const productManagementSlice = createSlice({
  name: 'productManagement',
  initialState,
  reducers: {
    clearStatus: (state) => {
      state.status = {
        productInfo: '',
        createProduct: '',
        deleteProduct: '',
        loginUser: '',
        updateProduct: '',
        createCategory: '',
        deleteCategory: '',
        accountList: '',
        updateCategory: '',
        categoryList: '',
        createUser: '',
        orderList: '',
        changeStatusOrder: '',
      };
    },
  },
  extraReducers: (builder) => {
    builder
      //createProduct
      .addCase(createProduct.fulfilled, (state, action) => {
        toast.success('Create Successfully');
        state.status = {
          ...state.status,
          createProduct: 'success',
        };
      })
      .addCase(createProduct.rejected, (state) => {
        toast.error('Create Error');
        state.status = {
          ...state.status,
          createProduct: 'error',
        };
      })

      // //get Product List
      .addCase(getAllProduct.fulfilled, (state, action) => {
        state.status = {
          ...state.status,
          productInfo: 'success',
        };
        state.productList = action.payload;
      })
      .addCase(getAllProduct.rejected, (state) => {
        toast.error('No response from server');
        state.status = {
          ...state.status,
          productInfo: 'error',
        };
        state.productList = [];
      })

      //delete Product
      .addCase(deleteProduct.fulfilled, (state) => {
        toast.success('Delete Successfully');
        state.status = {
          ...state.status,
          deleteProduct: 'success',
        };
      })
      .addCase(deleteProduct.rejected, (state) => {
        toast.error('Delete Error');
        state.status = {
          ...state.status,
          deleteProduct: 'error',
        };
      })

      //updateProduct
      .addCase(updateProduct.fulfilled, (state, action) => {
        toast.success('Update Successfully');
        state.status = {
          ...state.status,
          updateProduct: 'success',
        };
      })
      .addCase(updateProduct.rejected, (state) => {
        toast.error('Update Error');
        state.status = {
          ...state.status,
          updateProduct: 'error',
        };
      })

      //createCategory
      .addCase(createCategory.fulfilled, (state, action) => {
        toast.success('Create Successfully');
        state.status = {
          ...state.status,
          createCategory: 'success',
        };
      })
      .addCase(createCategory.rejected, (state) => {
        toast.error('Create Error');
        state.status = {
          ...state.status,
          createCategory: 'error',
        };
      })

      //updateCategory
      .addCase(updateCategory.fulfilled, (state, action) => {
        toast.success('Update Successfully');
        state.status = {
          ...state.status,
          updateCategory: 'success',
        };
      })
      .addCase(updateCategory.rejected, (state) => {
        toast.error('Update Error');
        state.status = {
          ...state.status,
          updateCategory: 'error',
        };
      })

      //deleteCategory
      .addCase(deleteCategory.fulfilled, (state, action) => {
        toast.success('Delete Successfully');
        state.status = {
          ...state.status,
          deleteCategory: 'success',
        };
      })
      .addCase(deleteCategory.rejected, (state) => {
        toast.error('Delete Error');
        state.status = {
          ...state.status,
          deleteCategory: 'error',
        };
      })

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
        if (action.payload.status == 302) {
          toast.error('Account has been registered');
          state.status = {
            ...state.status,
            createUser: 'error',
          };
        }
        if (action.payload.status == 200) {
          toast.success('Create Successfully');
          state.status = {
            ...state.status,
            createUser: 'success',
          };
        }
      })
      .addCase(createUser.rejected, (state) => {
        toast.error('Create Error');
        state.status = {
          ...state.status,
          createUser: 'error',
        };
      })

      // //get Account List
      .addCase(getAllOrder.fulfilled, (state, action) => {
        state.status = {
          ...state.status,
          orderList: 'success',
        };
        state.orderList = action.payload;
      })
      .addCase(getAllOrder.rejected, (state) => {
        toast.error('No response from server');
        state.status = {
          ...state.status,
          orderList: 'error',
        };
        state.orderList = [];
      })

      //change status order
      .addCase(changeStatusOrder.fulfilled, (state, action) => {
        toast.success('Change Successfully');
        state.status = {
          ...state.status,
          changeStatusOrder: 'success',
        };
      })
      .addCase(changeStatusOrder.rejected, (state) => {
        toast.error('Change Error');
        state.status = {
          ...state.status,
          changeStatusOrder: 'error',
        };
      })

      // //get Category List
      .addCase(getAllCategory.fulfilled, (state, action) => {
        state.status = {
          ...state.status,
          categoryList: 'success',
        };
        state.categoryList = action.payload;
      })
      .addCase(getAllCategory.rejected, (state) => {
        toast.error('No response from server');
        state.status = {
          ...state.status,
          categoryList: 'error',
        };
        state.categoryList = [];
      })

      // login
      .addCase(loginUser.fulfilled, (state, action) => {
        toast.success('Login Success');
        state.status = {
          ...state.status,
          loginUser: 'success',
        };
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
export const { clearStatus } = productManagementSlice.actions;
export default productManagementSlice.reducer;
