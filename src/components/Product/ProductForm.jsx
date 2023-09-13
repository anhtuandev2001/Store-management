// @ts-nocheck
import { Autocomplete, Button, TextField } from '@mui/material';
import { ErrorMessage, Field, Form, Formik } from 'formik';
import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import * as Yup from 'yup';
import { createProduct } from '../../store/slices/ScheduleManagementSlice/productReduce';
import { LoadingButton } from '@mui/lab';

const initialValues = {
  name: '',
  price: 0.0,
  description: '',
  categoryId: 0,
  colorsList: '',
  imagesList: {},
  createdAt: '',
};

const validationSchema = Yup.object().shape({
  name: Yup.string().required('Product name is required'),
  price: Yup.number().required('Price is required'),
  description: Yup.string().required('Description is required'),
  categoryId: Yup.number().required('Category is required'),
});

function ProductForm() {
  const [selectedColors, setSelectedColors] = useState([]);
  const [inputImg, setInputImg] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const dispatch = useDispatch();
  const { status } = useSelector((state) => state.productManagement);
  const handleSubmit = (values) => {
    const currentTime = new Date();
    values.createdAt = currentTime;
    const uniqueValuesArray = [
      ...new Set(selectedColors.map((color) => color.value)),
    ];
    let resultString;
    if (uniqueValuesArray.length > 1) {
      resultString = uniqueValuesArray.join(', ');
    } else {
      resultString = uniqueValuesArray.toString();
    }
    values.colorsList = resultString;
    values.imagesList = inputImg;
    console.log(values);
    dispatch(createProduct(values));
    setIsLoading(true);
  };

  useEffect(() => {
    if (
      status.createProduct === 'success' ||
      status.createProduct === 'error'
    ) {
      setIsLoading(false);
    }
  }, [status]);

  console.log(selectedColors);

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

  const uniqueColorOptions = Array.from(
    new Set(colorOptions.map((color) => color.value))
  ).map((value) => {
    return colorOptions.find((color) => color.value === value);
  });

  const handleChangeInputImg = (event) => {
    if (event) {
      setInputImg(event.target.files[0]);
      console.log(inputImg);
    }
  };

  return (
    <div className='mx-auto p-6 shadow-lg'>
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
              type='number'
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
            <label htmlFor='categoryId' className='block text-sm font-medium'>
              Category:
            </label>
            <Field
              type='number'
              id='categoryId'
              name='categoryId'
              className='mt-1 p-2 border border-[#C4C4C4] rounded w-full outline-none'
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
              onChange={(_, newValue) => {
                setSelectedColors(newValue);
              }}
              isOptionEqualToValue={(option, value) => option.value === value}
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

          <LoadingButton
            size='small'
            type='submit'
            loading={isLoading}
            variant='contained'
          >
            <span>Submit</span>
          </LoadingButton>
        </Form>
      </Formik>
    </div>
  );
}

export default ProductForm;
