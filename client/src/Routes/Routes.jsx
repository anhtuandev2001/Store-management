// @ts-nocheck
import { useSelector } from 'react-redux';
import { Navigate, Route, Routes } from 'react-router-dom';
import App from '../App';
import LoadingPage from '../pages/LoadingPage';
import {
  AccountPage,
  ChartManagementPage,
  ErrorPage,
  LoginPage,
  OrderManagementPage,
  ProductPage,
  RegisterPage,
} from '../pages/index';
import AuthRoute from './AuthRoute';

const Routers = () => {
  const { isLoadingPage } = useSelector((state) => state.loading);
  if (isLoadingPage) {
    return <LoadingPage />;
  }

  return (
    <Routes>
      <Route
        path='/'
        element={
          <AuthRoute routeType='private'>
            <App />
          </AuthRoute>
        }
      >
        <Route path='/' element={<Navigate to='/product' replace={true} />} />
        <Route path='product' element={<ProductPage />} />
        <Route path='account' element={<AccountPage />} />
        <Route path='order' element={<OrderManagementPage />} />
        <Route path='statistical' element={<ChartManagementPage />} />
        <Route path='*' element={<ErrorPage />} />
      </Route>

      <Route
        path='/login'
        element={
          <AuthRoute routeType='public'>
            <LoginPage />
          </AuthRoute>
        }
      />

      <Route
        path='/register'
        element={
          <AuthRoute routeType='public'>
            <RegisterPage />
          </AuthRoute>
        }
      />
    </Routes>
  );
};

export default Routers;
