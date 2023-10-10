// @ts-nocheck
import { Box, Button, Modal } from '@mui/material';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { handleLoading } from '../../store/slices/loadingSlice';
import { AiFillEdit, AiOutlineDelete } from 'react-icons/ai';
import { v4 as uuidv4 } from 'uuid';
import {
  getAllCategory,
  getAllProduct,
} from '../../store/slices/productManagementSlice/productReduce';
import ProductForm from './ProductForm';
import ProductList from './ProductList';
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

export function Product() {
  const dispatch = useDispatch();
  const { productList } = useSelector((state) => state.productManagement);
  const { status } = useSelector((state) => state.productManagement);
  const { categoryList } = useSelector((state) => state.productManagement);
  const [modalType, setModalType] = useState();
  const [categoryItem, setCategoryItem] = useState();


  useEffect(() => {
    dispatch(handleLoading(true));
    dispatch(getAllProduct());
    dispatch(getAllCategory());
  }, []);
  useEffect(() => {
    if (
      status.productInfo === 'success' ||
      (status.productInfo === 'error' && status.categoryList === 'success') ||
      status.categoryList === 'error'
    )
      dispatch(handleLoading(false));
  }, [productList]);

  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const [anchorEl, setAnchorEl] = useState(null);
  const openMenu = Boolean(anchorEl);
  const handleClickMenu = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleCloseMenu = () => {
    handleCategory();
    setAnchorEl(null);
  };

  const handleAddProduct = () => {
    setModalType('product');
    handleOpen();
  };

  const handleCategory = () => {
    setModalType('category');
    handleOpen();
  };

  const handleEditCategory = (category) =>{
    console.log(category);
    handleCategory();
    handleCloseMenu();
    setCategoryItem(category)
  }

  console.log(categoryItem);

  return (
    <div>
      <div className='flex justify-between items-center full-w py-4 text-[#6B778C]'>
        <span>Product Manager</span>
        <div className='flex gap-2'>
          <Button
            id='basic-button'
            aria-controls={open ? 'basic-menu' : undefined}
            aria-haspopup='true'
            variant='contained'
            aria-expanded={open ? 'true' : undefined}
            onClick={handleClickMenu}
          >
            Category
          </Button>
          <Button variant='contained' onClick={handleAddProduct}>
            Add Product
          </Button>
        </div>
        <Menu
          id='basic-menu'
          anchorEl={anchorEl}
          open={openMenu}
          onClose={handleCloseMenu}
          MenuListProps={{
            'aria-labelledby': 'basic-button',
          }}
        >
          {(categoryList || []).map((item) => (
            <MenuItem key={uuidv4()}>
              <div className='w-full flex gap-4 justify-between'>
                <span>{item.name}</span>
                <div className='flex gap-2'>
                  <Button
                    className='min-w-[20px]'
                    sx={{
                      minWidth: 'unset',
                    }}
                    onClick={() => handleEditCategory(item)}
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
      </div>
      <ProductList products={productList} categoryList={categoryList} />
      <Modal
        // @ts-ignore
        open={open}
        onClose={handleClose}
        aria-labelledby='modal-modal-title'
        aria-describedby='modal-modal-description'
      >
        <Box sx={style}>
          {modalType == 'product' ? (
            <ProductForm
              onClose={handleClose}
              action='create'
              categoryList={categoryList}
            />
          ) : (
            <CategoryForm onClose={handleClose} category={categoryItem} action='update'/>
          )}
        </Box>
      </Modal>
    </div>
  );
}

export default Product;
