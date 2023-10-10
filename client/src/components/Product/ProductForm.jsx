// @ts-nocheck
import { Autocomplete, Button, TextField } from '@mui/material';
import { ErrorMessage, Field, Form, Formik } from 'formik';
import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import * as Yup from 'yup';
import {
  createProduct,
  getAllProduct,
  updateProduct,
} from '../../store/slices/productManagementSlice/productReduce';

import { LoadingButton } from '@mui/lab';
import { clearStatus } from '../../store/slices/productManagementSlice/productManagementSlice';
import { handleLoading } from '../../store/slices/loadingSlice';

const colorOptions = [
  { label: 'Red', value: 'red' },
  { label: 'Green', value: 'green' },
  { label: 'Black', value: 'black' },
  { label: 'Blue', value: 'blue' },
  { label: 'Yellow', value: 'yellow' },
  { label: 'Purple', value: 'purple' },
  { label: 'Orange', value: 'orange' },
  { label: 'Pink', value: 'pink' },
  { label: 'Brown', value: 'brown' },
  { label: 'Gray', value: 'gray' },
  { label: 'White', value: 'white' },
  { label: 'Cyan', value: 'cyan' },
  { label: 'Magenta', value: 'magenta' },
  { label: 'Lime', value: 'lime' },
  { label: 'Teal', value: 'teal' },
  { label: 'Indigo', value: 'indigo' },
  { label: 'Slate', value: 'slate' },
  { label: 'Lavender', value: 'lavender' },
  { label: 'Maroon', value: 'maroon' },
  { label: 'Olive', value: 'olive' },
  { label: 'Other', value: 'other' },
];

const validationSchema = Yup.object().shape({
  name: Yup.string().required('Product name is required'),
  price: Yup.string().required('Price is required'),
  quantity: Yup.string().required('Quantity is required'),
  description: Yup.string().required('Description is required'),
});

function ProductForm({ onClose, product, action, categoryList }) {
  const initialValues = {
    name: product ? product.name : '',
    price: product ? product.price : '',
    description: product ? product.description : '',
    quantity: product ? product.quantity : '',
  };

  const categoryOptions = (categoryList || []).map((item) => ({
    label: item?.name,
    value: item?._id,
  }));

  const [selectedColors, setSelectedColors] = useState(
    product && categoryList != []
      ? (colorOptions || []).filter((item) => product.colorsList == item.value)
      : []
  );
  const [selectedCategory, setSelectedCategory] = useState(
    product && categoryList != []
      ? (categoryOptions || []).filter(
          (item) => product.categoryId == item.value
        )[0]
      : null
  );
  const [inputImg, setInputImg] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const dispatch = useDispatch();
  const { status } = useSelector((state) => state.productManagement);

  const handleSubmit = async (values) => {
    const currentTime = new Date();
    const formattedDate = currentTime.toISOString().split('T')[0];
    values.createAt = formattedDate;

    const uniqueValuesArray = [
      ...new Set(selectedColors.map((color) => color.value)),
    ];

    let resultString;
    if (uniqueValuesArray.length > 1) {
      resultString = uniqueValuesArray.join(',');
    } else {
      resultString = uniqueValuesArray.toString();
    }
    values.colorsList = resultString;
    values.categoryId = selectedCategory.value;

    values.imagesList = inputImg;
    console.log(values);
    setIsLoading(true);
    if (action === 'create') {
      dispatch(createProduct(values));
    } else {
      values = { ...values, productId: product.productId };
      dispatch(updateProduct(values));
    }
  };

  useEffect(() => {
    if (
      status.createProduct === 'success' ||
      status.createProduct === 'error' ||
      status.updateProduct === 'success' ||
      status.updateProduct === 'error'
    ) {
      setIsLoading(false);
    }
    if (
      status.createProduct === 'success' ||
      status.updateProduct === 'success'
    ) {
      dispatch(clearStatus());
      dispatch(handleLoading(true));
      dispatch(getAllProduct());
      onClose();
    }
  }, [status]);

  const uniqueColorOptions = Array.from(
    new Set(colorOptions.map((color) => color.value))
  ).map((value) => {
    return colorOptions.find((color) => color.value === value);
  });

  const handleChangeInputImg = (event) => {
    if (event) {
      setInputImg(event.target.files[0]);
    }
  };

  return (
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
              Product Name:
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
            <label htmlFor='price' className='block text-sm font-medium'>
              Price:
            </label>
            <Field
              type='text'
              id='price'
              name='price'
              className='mt-1 p-2 border border-[#C4C4C4] rounded w-full outline-none'
            />
            <ErrorMessage
              name='price'
              component='div'
              className='text-red-500 text-sm'
            />
          </div>
          <div className='mb-4'>
            <label htmlFor='quantity' className='block text-sm font-medium'>
              Quantity:
            </label>
            <Field
              type='text'
              id='quantity'
              name='quantity'
              className='mt-1 p-2 border border-[#C4C4C4] rounded w-full outline-none'
            />
            <ErrorMessage
              name='quantity'
              component='div'
              className='text-red-500 text-sm'
            />
          </div>
          <div className='mb-4'>
            <label htmlFor='description' className='block text-sm font-medium'>
              Description:
            </label>
            <Field
              as='textarea'
              id='description'
              name='description'
              className='mt-1 p-2 border border-[#C4C4C4] rounded w-full outline-none'
            />
            <ErrorMessage
              name='description'
              component='div'
              className='text-red-500 text-sm'
            />
          </div>
          <div className='mb-4'>
            <Autocomplete
              id='categoryId'
              options={categoryOptions}
              getOptionLabel={(option) => option.label}
              value={selectedCategory}
              isOptionEqualToValue={(option, value) =>
                option.value === value.value
              }
              onChange={(_, newValue) => {
                setSelectedCategory(newValue);
              }}
              renderInput={(params) => (
                <TextField
                  {...params}
                  label='Category'
                  variant='outlined'
                  fullWidth
                />
              )}
            />
            <ErrorMessage
              name='categoryId'
              component='div'
              className='text-red-500 text-sm'
            />
          </div>
          <div className='mb-4'>
            <Autocomplete
              multiple
              id='colorsList'
              options={uniqueColorOptions}
              getOptionLabel={(option) => option.label}
              value={selectedColors}
              isOptionEqualToValue={(option, value) =>
                option.value === value.value
              }
              onChange={(_, newValue) => {
                setSelectedColors(newValue);
              }}
              renderInput={(params) => (
                <TextField
                  {...params}
                  label='Colors'
                  variant='outlined'
                  fullWidth
                />
              )}
            />
            <ErrorMessage
              name='colorsList'
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

export default ProductForm;
