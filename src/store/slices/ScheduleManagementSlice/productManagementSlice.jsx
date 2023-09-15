import { createSlice } from '@reduxjs/toolkit';
import { createCategory, createProduct, deleteCategory, deleteProduct, getAllAccount, getAllProduct, updateProduct } from './productReduce';
import { toast } from 'react-toastify';
const initialState = {
  productList: [],
  productImageList: [], 
  createProduct: {},
  accountList: [],
  status: {
    productInfo: '',
    createProduct: '',
    deleteProduct: '',
    updateProduct: '',
    createCategory: '',
    deleteCategory: '',
    accountList: '',
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
        updateProduct: '',
        createCategory: '',
        deleteCategory: '',
        accountList: '',
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
  }
});
export const { clearStatus } = productManagementSlice.actions;
export default productManagementSlice.reducer;
