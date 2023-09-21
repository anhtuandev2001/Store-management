// @ts-nocheck
import {
  Navigate,
  Route,
  Routes,
  useLocation,
  useNavigate,
} from 'react-router-dom';

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
import { useEffect, useState } from 'react';

const Routers = () => {
  const loginStatus = localStorage.getItem('login');
  const location = useLocation();
  const navigate = useNavigate();
  const [previousPath, setPreviousPath] = useState(null);

  // Lưu lại đường dẫn trước đó khi trạng thái là false và không phải trang register hoặc login.
  useEffect(() => {
    if (
      loginStatus === 'false' &&
      location.pathname !== '/register' &&
      location.pathname !== '/login'
    ) {
      setPreviousPath(location.pathname);
    }
  }, [loginStatus, location]);

  // Hàm này sẽ được gọi khi người dùng đăng nhập thành công.
  const handleLoginSuccess = () => {
    // Kiểm tra nếu trạng thái là true và trước đó đã lưu lại đường dẫn.
    if (loginStatus === 'true' && previousPath) {
      navigate(previousPath); // Chuyển hướng lại đường dẫn trước đó.
    } else {
      navigate('/product'); // Chuyển hướng đến trang product mặc định.
    }
  };

  // Kiểm tra nếu đã đăng nhập (loginStatus === 'true') thì không cho truy cập trang đăng nhập (/login).
  if (loginStatus === 'true' && location.pathname === '/login') {
    navigate('/product'); // Chuyển hướng người dùng trở lại trang product.
  }

  // Kiểm tra nếu trạng thái là false và không phải trang register hoặc login, chuyển hướng đến trang login.
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
      <Route
        path='/login'
        element={<LoginPage onLoginSuccess={handleLoginSuccess} />}
      />
    </Routes>
  );
};

export default Routers;
