import { createSlice } from '@reduxjs/toolkit';

const loadingSlice = createSlice({
  name: 'loading',
  initialState: {
    isLoading: false,
    isLoadingPage: false,
  },
  reducers: {
    handleLoading: (state, action) => {
      state.isLoading = action.payload;
    },
    handleLoadingPage: (state, action) => {
      state.isLoadingPage = action.payload;
    },
  },
});

export const { handleLoading, handleLoadingPage } = loadingSlice.actions;
export default loadingSlice.reducer;
