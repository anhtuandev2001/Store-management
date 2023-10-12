// @ts-nocheck
import { Box, Button, Menu, MenuItem, Modal } from '@mui/material';
import { v4 as uuidv4 } from 'uuid';
import { AiFillEdit, AiOutlineDelete } from 'react-icons/ai';
import { useEffect, useState } from 'react';
import CategoryForm from './CategoryForm';
import { useDispatch, useSelector } from 'react-redux';
import {
  deleteCategory,
  getAllCategory,
} from '../../store/slices/productManagementSlice/productReduce';
import { clearStatus } from '../../store/slices/productManagementSlice/productManagementSlice';
import { handleLoading } from '../../store/slices/loadingSlice';
import { LoadingButton } from '@mui/lab';
import { IoAddSharp } from 'react-icons/io5';

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 600,
  bgcolor: 'background.paper',
  borderRadius: '5px',
};

const CategoryList = ({
  categoryList,
  onOpenMenu,
  onHandleCloseMenu,
  anchorEl,
}) => {
  const [openModal, setOpenModal] = useState(false);
  const [modalType, setModalType] = useState();
  const [categoryItem, setCategoryItem] = useState();
  const [isLoading, setIsLoading] = useState(false);
  const [action, setAction] = useState(false);
  const { status } = useSelector((state) => state.productManagement);

  const handleOpenModal = () => setOpenModal(true);
  const handleCloseModal = () => setOpenModal(false);

  const dispatch = useDispatch();

  const handleEditCategory = (item) => {
    setAction('update');
    onHandleCloseMenu();
    setCategoryItem(item);
    setModalType('update');
    handleOpenModal();
  };

  const handleDeleteCategory = (item) => {
    onHandleCloseMenu();
    setCategoryItem(item);
    setModalType('delete');
    handleOpenModal();
  };

  const handleSubmitDelete = () => {
    handleCloseModal();
    setIsLoading(true);
    dispatch(deleteCategory(categoryItem._id));
  };

  useEffect(() => {
    if (
      status.deleteCategory === 'success' ||
      status.deleteCategory === 'error'
    ) {
      setIsLoading(false);
    }

    if (status.deleteCategory === 'success') {
      dispatch(clearStatus());
      dispatch(handleLoading(true));
      dispatch(getAllCategory());
    }
  }, [status]);

  const handleAddCategory = () => {
    setAction('create');
    onHandleCloseMenu();
    setCategoryItem(null);
    setModalType('create');
    handleOpenModal();
  };
  return (
    <div>
      <Menu
        id='basic-menu'
        anchorEl={anchorEl}
        open={onOpenMenu}
        onClose={onHandleCloseMenu}
        MenuListProps={{
          'aria-labelledby': 'basic-button',
        }}
      >
        <MenuItem onClick={handleAddCategory} className='flex gap-4'>
          <IoAddSharp />
          Add New Category
        </MenuItem>
        {(categoryList || []).map((item) => (
          <MenuItem key={uuidv4()}>
            <div className='w-full flex gap-4 justify-between'>
              <img
                src={item.image}
                alt={item.name}
                className='h-[20px] w-[20px]'
              />
              <span>{item.name}</span>
              <div className='flex gap-2'>
                <Button
                  className='min-w-[20px]'
                  sx={{
                    minWidth: 'unset',
                  }}
                  onClick={() => handleDeleteCategory(item)}
                >
                  <AiOutlineDelete />
                </Button>
                <Button
                  className='min-w-[20px]'
                  sx={{
                    minWidth: 'unset',
                  }}
                  onClick={() => handleEditCategory(item)}
                >
                  <AiFillEdit />
                </Button>
              </div>
            </div>
          </MenuItem>
        ))}
      </Menu>
      <Modal
        open={openModal}
        onClose={handleCloseModal}
        aria-labelledby='modal-modal-title'
        aria-describedby='modal-modal-description'
      >
        <Box sx={style}>
          {modalType == 'update' || modalType == 'create' ? (
            <CategoryForm
              onClose={handleCloseModal}
              category={categoryItem}
              action={action}
            />
          ) : (
            <div className='p-5'>
              <h2 className='text-xl font-bold text-[#42526e]'>
                Are you sure you want to delete the category?
              </h2>
              <div className='flex justify-between pt-5'>
                <Button onClick={handleCloseModal} sx={{ color: 'red' }}>
                  Cancel
                </Button>
                <LoadingButton
                  size='small'
                  onClick={handleSubmitDelete}
                  loading={isLoading}
                  variant='contained'
                >
                  <span>Yes</span>
                </LoadingButton>
              </div>
            </div>
          )}
        </Box>
      </Modal>
    </div>
  );
};

export default CategoryList;
