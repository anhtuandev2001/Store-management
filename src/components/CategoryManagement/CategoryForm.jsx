// @ts-nocheck
import { LoadingButton } from '@mui/lab';
import { Button } from '@mui/material';
import { ErrorMessage, Field, Form, Formik } from 'formik';
import * as Yup from 'yup';
import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { createCategory } from '../../store/slices/ScheduleManagementSlice/productReduce';

const validationSchema = Yup.object().shape({
  categoryName: Yup.string().required('Product name is required'),
});

function CategoryForm({ action, category, onClose }) {
  const dispatch = useDispatch();
  const { status } = useSelector((state) => state.productManagement);
  const initialValues = {
    categoryName: category ? category.categoryName : '',
  };
  const [isLoading, setIsLoading] = useState(false);

  const handleSubmit = (value) => {
    console.log(value);
    setIsLoading(true);
    dispatch(createCategory(value));
  };

  useEffect(() => {
    if (
      status.createCategory === 'success' ||
      status.createCategory === 'error'
    ) {
      setIsLoading(false);
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
                Category Name:
              </label>
              <Field
                type='text'
                id='categoryName'
                name='categoryName'
                autoComplete='new-password'
                className='mt-1 p-2 border border-[#C4C4C4] rounded w-full outline-none'
              />
              <ErrorMessage
                name='categoryName'
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

export default CategoryForm;
