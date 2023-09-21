import React from 'react';
import { Navigate } from 'react-router-dom';

const WithAuthentication = (WrappedComponent) => {
  return (props) => {
    const loginStatus = localStorage.getItem('login');

    if (loginStatus === 'true') {
      return <WrappedComponent {...props} />;
    } else {
      return <Navigate to='/login' replace={true} />;
    }
  };
};

export default WithAuthentication;
