import { Navigate, Route, Routes } from 'react-router-dom';

import {
  AccountPage,
  ErrorPage,
  LoginPage,
  ProductPage,
  RegisterPage,
  BillManagementPage,
} from '../pages/index';

import App from '../../App';

import useAuth from '../../utils/useAuth';
import ForgotPasswordPage from '../pages/ForgotPasswordPage';
import LoadingPage from '../pages/LoadingPage';
import AuthRoute from './AuthRoute';
const Routers = () => {
  const { loading } = useAuth();
  if (loading) {
    return <LoadingPage />;
  }
  return (
    <Routes>
      <Route
        path='/'
        element={
          <AuthRoute routeType='public'>
            <App />
          </AuthRoute>
        }
      >
        <Route path='/' element={<Navigate to='/bill' replace={true} />} />
        <Route path='bill' element={<BillManagementPage />} />
        <Route path='product' element={<ProductPage />} />
        <Route path='account' element={<AccountPage />} />
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
      <Route
        path='/forgot-password'
        element={
          <AuthRoute routeType='public'>
            <ForgotPasswordPage />
          </AuthRoute>
        }
      />
    </Routes>
  );
};

export default Routers;
