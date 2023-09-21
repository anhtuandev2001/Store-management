// @ts-nocheck
import { Link } from 'react-router-dom';
import logo from '../../assets/icon/logoBlueOC.png';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import { useDispatch, useSelector } from 'react-redux';
import { createUser } from '../../store/slices/ScheduleManagementSlice/productReduce';
import { useEffect, useState } from 'react';
import { LoadingButton } from '@mui/lab';
import { clearStatus } from '../../store/slices/ScheduleManagementSlice/productManagementSlice';
import { toast } from 'react-toastify';

const RegisterPage = () => {
  const initialValues = {
    email: '',
    username: '',
    password: '',
    confirmPassword: '',
  };

  const validationSchema = Yup.object().shape({
    email: Yup.string()
      .email('Invalid email address')
      .required('Email is required'),
    username: Yup.string().required('Username is required'),
    password: Yup.string()
      .min(6, 'Password must be at least 6 characters')
      .required('Password is required'),
    confirmPassword: Yup.string()
      .oneOf([Yup.ref('password'), null], 'Passwords must match')
      .required('Confirm Password is required'),
  });

  const dispatch = useDispatch();
  const { status } = useSelector((state) => state.productManagement);
  const [isLoading, setIsLoading] = useState(false);

  const handleSubmit = (values, { setSubmitting }) => {
    const data = {
      name: values.username || values.Username,
      email: values.email,
      password: values.password,
    };
    dispatch(createUser(data));
    setSubmitting(false);
    setIsLoading(true);
  };

  useEffect(() => {
    if (status.createUser === 'success' || status.createUser === 'error') {
      setIsLoading(false);
    }
    if (status.createUser === 'success') {
      dispatch(clearStatus());
      setTimeout(() => {
        window.location.href = '/login';
      }, 1000);
    }
  }, [status]);

  return (
    <>
      <div className='bg-login '>
        <div className='container-small flex flex-col justify-center items-center h-full'>
          <div>
            <div className='form w-full bg-gradient-to-b from-indigo-500 rounded-2xl shadow-2xl pt-9 pr-9 pl-9 pb-7 '>
              <div className=' flex justify-center items-center pb-9 ml-[135px] mr-[135px]'>
                <div className='bg-white p-8 rounded-full'>
                  <img src={logo} alt='' className='h-[90px] w-[90px]' />
                </div>
              </div>
              <Formik
                initialValues={initialValues}
                validationSchema={validationSchema}
                onSubmit={handleSubmit}
              >
                {({ isSubmitting }) => (
                  <Form className='flex flex-col items-center'>
                    <div className=' flex justify-center items-center pb-4 '>
                      <h2 className='text-[20px] font-bold text-white	'>
                        Registration
                      </h2>
                    </div>
                    <div className='flex flex-col w-[400px] relative'>
                      <label className='text-[#2F3F73] font-semibold text-lg'>
                        Email
                      </label>
                      <Field
                        type='text'
                        name='email'
                        placeholder='Email'
                        className={`w-full mb-[10px] mt-[10px] h-[45px] outline-0 p-[10px] rounded-md`}
                      />
                      <ErrorMessage
                        name='email'
                        component='div'
                        className='text-red-500'
                      />
                    </div>

                    <div className='flex flex-col w-[400px]'>
                      <label className='text-[#2F3F73] font-semibold text-lg'>
                        Username
                      </label>
                      <Field
                        type='text'
                        name='username'
                        placeholder='User name'
                        className={`w-full mb-[10px] mt-[10px] h-[45px] outline-0 p-[10px] rounded-md`}
                      />
                      <ErrorMessage
                        name='username'
                        component='div'
                        className='text-red-500'
                      />
                    </div>

                    <div className='flex flex-col w-[400px]'>
                      <label className='text-[#2F3F73] font-semibold text-lg'>
                        Password
                      </label>
                      <Field
                        type='password'
                        name='password'
                        placeholder='Password'
                        autoComplete='new-password'
                        className={`w-full mb-[10px] mt-[10px] h-[45px] outline-0 border-2 p-[10px] rounded-md`}
                      />
                      <ErrorMessage
                        name='password'
                        component='div'
                        className='text-red-500'
                      />
                    </div>

                    <div className='flex flex-col w-[400px]'>
                      <label className='text-[#2F3F73] font-semibold text-lg'>
                        Confirm Password
                      </label>
                      <Field
                        type='password'
                        name='confirmPassword'
                        placeholder='Confirm password'
                        autoComplete='new-password1'
                        className={`w-full mb-[10px] mt-[10px] h-[45px] outline-0 border-2 p-[10px] rounded-md`}
                      />
                      <ErrorMessage
                        name='confirmPassword'
                        component='div'
                        className='text-red-500'
                      />
                    </div>

                    <div className='pt-[5px]'>
                      <LoadingButton
                        size='small'
                        type='submit'
                        loading={isLoading}
                        disabled={isSubmitting}
                        variant='contained'
                      >
                        <span> Register</span>
                      </LoadingButton>
                    </div>

                    <h5 className='text-[16px] pt-[1.5rem] mx-auto  '>
                      Already have an account
                      <span className='ml-[5px]'>
                        <Link
                          className='text-[#2F3F73] font-semibold'
                          to='/login'
                        >
                          Login
                        </Link>
                      </span>
                    </h5>
                  </Form>
                )}
              </Formik>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default RegisterPage;
