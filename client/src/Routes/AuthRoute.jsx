// @ts-nocheck
import { useSelector } from 'react-redux';
import { Navigate, useLocation } from 'react-router-dom';

const AuthRoute = ({ children, routeType }) => {
  const { token } = useSelector((state) => state.userManagement);
  const { isLoadingPage } = useSelector((state) => state.loading);
  const location = useLocation();

  if (isLoadingPage) {
    return <LoadingPage />;
  }

  if (routeType === 'private' && !token) {
    return <Navigate to='/login' replace />;
  }

  if (
    routeType === 'public' &&
    token &&
    ['/login', '/register'].includes(location.pathname)
  ) {
    return <Navigate to='/' replace />;
  }

  return children;
};

export default AuthRoute;
