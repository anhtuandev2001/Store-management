import { createSlice } from '@reduxjs/toolkit';
import { toast } from 'react-toastify';
import {
  changeStatusOrder,
  createCategory,
  createProduct,
  deleteCategory,
  deleteProduct,
  getAllCategory,
  getAllProduct,
  updateCategory,
  updateProduct,
} from './productReduce';
const initialState = {
  productList: [],
  productImageList: [],
  createProduct: {},
  categoryList: [],
  orderList: [],
  status: {
    productInfo: '',
    createProduct: '',
    deleteProduct: '',
    updateProduct: '',
    createCategory: '',
    updateCategory: '',
    deleteCategory: '',
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
        updateProduct: '',
        createCategory: '',
        deleteCategory: '',
        updateCategory: '',
        categoryList: '',
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
      });
  },
});
export const { clearStatus } = productManagementSlice.actions;
export default productManagementSlice.reducer;
