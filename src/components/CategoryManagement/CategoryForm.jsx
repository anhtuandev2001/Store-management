// @ts-nocheck
import { LoadingButton } from '@mui/lab';
import { Button } from '@mui/material';
import { ErrorMessage, Field, Form, Formik } from 'formik';
import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import * as Yup from 'yup';
import { clearStatus } from '../../store/slices/ScheduleManagementSlice/productManagementSlice';
import {
  createCategory,
  getAllCategory,
  updateCategory,
} from '../../store/slices/ScheduleManagementSlice/productReduce';
import { handleLoading } from '../../store/slices/loadingSlice';

const validationSchema = Yup.object().shape({
  categoryName: Yup.string().required('Product name is required'),
});

function CategoryForm({ action, category, onClose }) {
  const dispatch = useDispatch();
  const { status } = useSelector((state) => state.productManagement);
  const initialValues = {
    categoryName: category ? category.name : '',
  };
  const [isLoading, setIsLoading] = useState(false);

  const handleSubmit = (value) => {
    setIsLoading(true);
    if (action === 'create') {
      dispatch(createCategory(value));
    } else {
      value.categoryId = category.id;
      dispatch(updateCategory(value));
    }
  };

  useEffect(() => {
    if (
      status.createCategory === 'success' ||
      status.createCategory === 'error' ||
      status.updateCategory === 'success' ||
      status.updateCategory === 'error'
    ) {
      setIsLoading(false);
    }
  });

  useEffect(() => {
    if (
      status.createCategory === 'success' ||
      status.updateCategory === 'success'
    ) {
      dispatch(getAllCategory());
      dispatch(clearStatus());
      dispatch(handleLoading(true));
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
