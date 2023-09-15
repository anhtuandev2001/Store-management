import { Navigate, Route, Routes } from 'react-router-dom';

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
  return (
    <Routes>
      <Route path='/' element={<App />}>
        <Route path='/' element={<Navigate to='/product' replace={true} />} />
        <Route path='order' element={<OrderManagementPage />} />
        <Route path='product' element={<ProductPage />} />
        <Route path='category' element={<CategoryPage />} />
        <Route path='account' element={<AccountPage />} />
        <Route path='*' element={<ErrorPage />} />
      </Route>

      <Route path='/login' element={<LoginPage />} />

      <Route path='/register' element={<RegisterPage />} />
    </Routes>
  );
};

export default Routers;
