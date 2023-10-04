// @ts-nocheck
import { Box, Button, Modal } from '@mui/material';
import React, { useEffect, useState } from 'react';
import CategoryList from './CategoryList';
import { useDispatch, useSelector } from 'react-redux';
import CategoryForm from './CategoryForm';
import { handleLoading } from '../../store/slices/loadingSlice';
import { getAllCategory } from '../../store/slices/productManagementSlice/productReduce';

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 600,
  bgcolor: 'background.paper',
  borderRadius: '5px',
};

function CategoryManagement() {
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);
  const dispatch = useDispatch();
  const { status } = useSelector((state) => state.productManagement);
  const { categoryList } = useSelector((state) => state.productManagement);
  const handleCreateCategory = () => {
    handleOpen();
  };

  useEffect(() => {
    dispatch(getAllCategory());
    dispatch(handleLoading(true));
  }, []);

  useEffect(() => {
    if (status.categoryList === 'success' || status.categoryList === 'error')
      dispatch(handleLoading(false));
  }, [categoryList]);

  return (
    <div>
      <div className='flex justify-between items-center full-w py-4 text-[#6B778C]'>
        <span>Category Manager</span>
        <Button onClick={handleCreateCategory} variant='contained'>
          Add Category
        </Button>
      </div>
      <CategoryList categoryList={categoryList} />
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby='modal-modal-title'
        aria-describedby='modal-modal-description'
      >
        <Box sx={style}>
          <CategoryForm onClose={handleClose} action='create' />
        </Box>
      </Modal>
    </div>
  );
}

export default CategoryManagement;
