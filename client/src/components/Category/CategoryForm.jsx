// @ts-nocheck
import { Button } from '@mui/material';
import { ErrorMessage, Field, Form, Formik } from 'formik';
import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import * as Yup from 'yup';
import {
  createCategory,
  getAllCategory,
  updateCategory,
} from '../../store/slices/productManagementSlice/productReduce';

import { LoadingButton } from '@mui/lab';
import { handleLoading } from '../../store/slices/loadingSlice';
import { clearStatus } from '../../store/slices/productManagementSlice/productManagementSlice';

const validationSchema = Yup.object().shape({
  name: Yup.string().required('Category name is required'),
});

function CategoryForm({ onClose, category, action }) {
  const initialValues = {
    name: category ? category.name : '',
  };

  const [inputImg, setInputImg] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const dispatch = useDispatch();
  const { status } = useSelector((state) => state.productManagement);


  const handleSubmit = async (values) => {
    values.imagesList = inputImg;
    setIsLoading(true)

    if (action === 'create') {
      dispatch(createCategory(values));
    } else {
      values = { ...values, id: category._id };
      dispatch(updateCategory(values));
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
    if (
      status.createCategory === 'success' ||
      status.updateCategory === 'success'
    ) {
      dispatch(clearStatus());
      dispatch(handleLoading(true));
      dispatch(getAllCategory());
      onClose();
    }
  }, [status]);

  const handleChangeInputImg = (event) => {
    if (event) {
      setInputImg(event.target.files[0]);
    }
  };

  return (
    <div className='mx-auto p-6 shadow-lg text-[#42526e]'>
      <h1 className='text-2xl font-semibold mb-4'>Category</h1>
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
            <label htmlFor='imagesList' className='block text-sm font-medium'>
              Image:
            </label>
            <input
              type='file'
              name='imagesList'
              accept='image/*'
              onChange={handleChangeInputImg}
              className='mt-1 p-2 border border-[#C4C4C4] rounded w-full outline-none'
            />
            <ErrorMessage
              name='imagesList'
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
  );
}

export default CategoryForm;
