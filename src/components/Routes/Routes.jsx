import { Navigate, Route, Routes, useLocation } from 'react-router-dom';

import {
  AccountPage,
  ErrorPage,
  LoginPage,
  OrderManagementPage,
  ProductPage,
  RegisterPage,
} from '../pages/index';

import App from '../../App';

import CategoryPage from '../pages/CategoryPage';

const Routers = () => {
  const loginStatus = localStorage.getItem('login');
  const location = useLocation();
  if (
    loginStatus === 'false' &&
    location.pathname !== '/register' &&
    location.pathname !== '/login'
  ) {
    return <Navigate to='/login' replace={true} />;
  }

  return (
    <Routes>
      <Route path='/' element={loginStatus === 'true' ? <App /> : null}>
        <Route path='/' element={<Navigate to='/product' replace={true} />} />
        <Route path='order' element={<OrderManagementPage />} />
        <Route path='product' element={<ProductPage />} />
        <Route path='category' element={<CategoryPage />} />
        <Route path='account' element={<AccountPage />} />
        <Route path='*' element={<ErrorPage />} />
      </Route>

      <Route path='/register' element={<RegisterPage />} />
      <Route path='/login' element={<LoginPage />} />
    </Routes>
  );
};

export default Routers;
