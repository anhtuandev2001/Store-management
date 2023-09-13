import { createSlice } from '@reduxjs/toolkit';
import { createProduct, deleteProduct, getAllProduct } from './productReduce';
import { toast } from 'react-toastify';
const initialState = {
  productList: [],
  createProduct: {},
  deleteProduct:{},
  status: {
    productInfo: '',
    createProduct: '',
    deleteProduct: '',
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
        state.createProduct = action.payload;
      })
      .addCase(createProduct.rejected, (state) => {
        toast.error('This room is used on that time');
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
      .addCase(deleteProduct.fulfilled, (state, action) => {
        state.status = {
          ...state.status,
          deleteProduct: 'success',
        };
        state.deleteProduct = action.payload;
      })
      .addCase(deleteProduct.rejected, (state) => {
        toast.error('No response from server');
        state.status = {
          ...state.status,
          deleteProduct: 'error',
        };
        state.deleteProduct = [];
      })
  }
});
export const { clearStatus } = productManagementSlice.actions;
export default productManagementSlice.reducer;
