// @ts-nocheck
import { Box, Button, Modal } from '@mui/material';
import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { handleLoading } from '../../store/slices/loadingSlice';
import {
  getAllCategory,
  getAllProduct,
} from '../../store/slices/productManagementSlice/productReduce';
import ProductForm from './ProductForm';
import ProductList from './ProductList';
import CategoryList from '../Category/CategoryList';

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
  const [openModal, setOpenModal] = useState(false);
  const [anchorEl, setAnchorEl] = useState(null);

  useEffect(() => {
    dispatch(handleLoading(true));
    dispatch(getAllProduct());
    dispatch(getAllCategory());
  }, []);
  useEffect(() => {
    if (
      status.productInfo === 'success' ||
      status.productInfo === 'error' ||
      status.categoryList === 'success' ||
      status.categoryList === 'error'
    )
      dispatch(handleLoading(false));
  }, [productList, categoryList]);

  const handleOpenModal = () => setOpenModal(true);
  const handleCloseModal = () => setOpenModal(false);

  const openMenu = Boolean(anchorEl);
  const handleClickMenu = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleCloseMenu = () => {
    setAnchorEl(null);
  };

  return (
    <div>
      <div className='flex justify-between items-center full-w py-4 text-[#6B778C]'>
        <span>Product Manager</span>
        <div className='flex gap-2'>
          <Button variant='contained' id='basic-button' onClick={handleClickMenu}>
            Category
          </Button>
          <Button variant='contained' onClick={handleOpenModal}>
            Add Product
          </Button>
        </div>
      </div>
      <ProductList products={productList} categoryList = {categoryList}/>
      <CategoryList
        categoryList={categoryList}
        onOpenMenu={openMenu}
        onHandleCloseMenu={handleCloseMenu}
        anchorEl={anchorEl}
      />
      <Modal
        open={openModal}
        onClose={handleCloseModal}
        aria-labelledby='modal-modal-title'
        aria-describedby='modal-modal-description'
      >
        <Box sx={style}>
          <ProductForm
            onClose={handleCloseModal}
            action='create'
            categoryList={categoryList}
          />
        </Box>
      </Modal>
    </div>
  );
}

export default Product;
