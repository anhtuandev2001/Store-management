// @ts-nocheck
import { LoadingButton } from '@mui/lab';
import { Button } from '@mui/material';
import { ErrorMessage, Field, Form, Formik } from 'formik';
import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import * as Yup from 'yup';
import { handleLoading } from '../../store/slices/loadingSlice';
import { clearStatus } from '../../store/slices/userManagementSlice/userManagementSlice';
import {
  createUser,
  getAllAccount,
} from '../../store/slices/userManagementSlice/userReduce';

const validationSchema = Yup.object().shape({
  name: Yup.string().required('Product name is required'),
  email: Yup.string().required('Product name is required'),
  password: Yup.string().required('Product name is required'),
});

function AccountForm({ action, account, onClose }) {
  const dispatch = useDispatch();
  const { status } = useSelector((state) => state.productManagement);
  const initialValues = {
    name: account ? account.name : '',
    email: account ? account.email : '',
    password: account ? account.password : '',
  };
  const [isLoading, setIsLoading] = useState(false);

  const handleSubmit = (value) => {
    setIsLoading(true);
    dispatch(createUser(value));
  };

  useEffect(() => {
    if (status.createUser === 'success' || status.createUser === 'error') {
      setIsLoading(false);
    }
    if (status.createUser === 'success') {
      dispatch(getAllAccount());
      dispatch(handleLoading(true));
      dispatch(clearStatus());
      onClose();
    }
  }, [status]);
  return (
    <div>
      <div className='mx-auto p-6 shadow-lg text-[#42526e]'>
        <h1 className='text-2xl font-semibold mb-4'>Create Product</h1>
        <Formik
          initialValues={initialValues}
          validationSchema={validationSchema}
          onSubmit={handleSubmit}
        >
          <Form>
            <div className='mb-4'>
              <label htmlFor='name' className='block text-sm font-medium'>
                Name:
              </label>
              <Field
                type='text'
                id='name'
                name='name'
                autoComplete='new-password'
                className='mt-1 p-2 border border-[#C4C4C4] rounded w-full outline-none'
              />
              <ErrorMessage
                name='name'
                component='div'
                className='text-red-500 text-sm'
              />
            </div>
            <div className='mb-4'>
              <label htmlFor='name' className='block text-sm font-medium'>
                Email:
              </label>
              <Field
                type='text'
                id='email'
                name='email'
                autoComplete='new-password'
                className='mt-1 p-2 border border-[#C4C4C4] rounded w-full outline-none'
              />
              <ErrorMessage
                name='email'
                component='div'
                className='text-red-500 text-sm'
              />
            </div>
            <div className='mb-4'>
              <label htmlFor='name' className='block text-sm font-medium'>
                Password:
              </label>
              <Field
                type='text'
                id='password'
                name='password'
                autoComplete='new-password'
                className='mt-1 p-2 border border-[#C4C4C4] rounded w-full outline-none'
              />
              <ErrorMessage
                name='password'
                component='div'
                className='text-red-500 text-sm'
              />
            </div>

            <div className='flex justify-between'>
              <Button onClick={onClose}>Discard</Button>
              <LoadingButton
                size='small'
                type='submit'
                loading={isLoading}
                variant='contained'
              >
                <span>{action === 'create' ? 'Submit' : 'Update'}</span>
              </LoadingButton>
            </div>
          </Form>
        </Formik>
      </div>
    </div>
  );
}

export default AccountForm;
