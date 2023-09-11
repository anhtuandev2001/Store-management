import React from 'react';
import { TextField, Button } from '@mui/material';
import { useFormik } from 'formik';
import * as Yup from 'yup';

const validationSchema = Yup.object({
  name: Yup.string().required('Vui lòng nhập tên tài khoản'),
  email: Yup.string()
    .email('Email không hợp lệ')
    .required('Vui lòng nhập email'),
  password: Yup.string().required('Vui lòng nhập mật khẩu'),
  role: Yup.string().required('Vui lòng nhập vai trò'),
});

const AddAccountForm = ({ onSubmit }) => {
  const formik = useFormik({
    initialValues: {
      name: '',
      email: '',
      password: '', // Thêm trường password
      role: '', // Thêm trường role
    },
    validationSchema: validationSchema,
    onSubmit: (values) => {
      onSubmit(values);
      formik.resetForm();

    },
  });

  return (
    <form onSubmit={formik.handleSubmit}>
      <TextField
        fullWidth
        id='name'
        name='name'
        label='name'
        variant='outlined'
        margin='normal'
        value={formik.values.name}
        onChange={formik.handleChange}
        error={formik.touched.name && Boolean(formik.errors.name)}
        helperText={formik.touched.name && formik.errors.name}
      />
      <TextField
        fullWidth
        id='email'
        name='email'
        label='Email'
        variant='outlined'
        margin='normal'
        autoComplete="new-password"
        value={formik.values.email}
        onChange={formik.handleChange}
        error={formik.touched.email && Boolean(formik.errors.email)}
        helperText={formik.touched.email && formik.errors.email}
      />
      <TextField
        fullWidth
        id='password'
        name='password'
        label='Mật khẩu'
        type='password'
        variant='outlined'
        autoComplete="new-password"
        margin='normal'
        value={formik.values.password}
        onChange={formik.handleChange}
        error={formik.touched.password && Boolean(formik.errors.password)}
        helperText={formik.touched.password && formik.errors.password}
      />

      <TextField
        fullWidth
        id='role'
        name='role'
        label='Vai trò'
        variant='outlined'
        margin='normal'
        value={formik.values.role}
        onChange={formik.handleChange}
        error={formik.touched.role && Boolean(formik.errors.role)}
        helperText={formik.touched.role && formik.errors.role}
      />

      <Button type='submit' variant='contained' color='primary'>
        Thêm tài khoản
      </Button>
    </form>
  );
};

export default AddAccountForm;
