import { configureStore } from '@reduxjs/toolkit';
import userReducer from './slices/userSlice';
import errorReducer from './slices/errorSlice';
import loadingReducer from './slices/loadingSlice';
import avatarUrlReducer from './slices/avatarUrl';
import leftMenuReducer from './slices/leftMenuSlice';

const store = configureStore({
  reducer: {
    avatarUrl: avatarUrlReducer,
    loading: loadingReducer,
    user: userReducer,
    error: errorReducer,
    leftMenu: leftMenuReducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: false,
    }),
});
export default store;
