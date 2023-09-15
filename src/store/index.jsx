import { configureStore } from '@reduxjs/toolkit';
import productManagementReducer from './slices/ScheduleManagementSlice/productManagementSlice';
import leftMenuReducer from './slices/leftMenuSlice';
import loadingReducer from './slices/loadingSlice';

const store = configureStore({
  reducer: {
    productManagement: productManagementReducer,
    loading: loadingReducer,
    leftMenu: leftMenuReducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: false,
    }),
});
export default store;
