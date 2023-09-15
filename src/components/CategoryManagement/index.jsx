// @ts-nocheck
import { Box, Button, Modal } from '@mui/material';
import React, { useState } from 'react';
import CategoryList from './CategoryList';
import { useDispatch } from 'react-redux';
import CategoryForm from './CategoryForm';

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 600,
  bgcolor: 'background.paper',
  borderRadius: '5px',
};

const categoryList = [
  {
    categoryId: 1,
    categoryName: 'Chair',
  },
  {
    categoryId: 2,
    categoryName: 'Chair',
  },
];

function CategoryManagement() {
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);
  const dispatch = useDispatch();
  const handleCreateCategory = () => {
    handleOpen();
  };
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
