// @ts-nocheck
import { Box, Button, Modal } from '@mui/material';
import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import {
  getAllCategory,
  getAllProduct,
} from '../../store/slices/productManagementSlice/productReduce';
import { handleLoading } from '../../store/slices/loadingSlice';
import ProductForm from './ProductForm';
import ProductList from './ProductList';

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

  console.log(categoryList);

  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);
  return (
    <div>
      <div className='flex justify-between items-center full-w py-4 text-[#6B778C]'>
        <span>Product Manager</span>
        <Button variant='contained' onClick={handleOpen}>
          Add Product
        </Button>
      </div>
      <ProductList products={productList} categoryList={categoryList}/>
      <Modal
        // @ts-ignore
        open={open}
        onClose={handleClose}
        aria-labelledby='modal-modal-title'
        aria-describedby='modal-modal-description'
      >
        <Box sx={style}>
          <ProductForm
            onClose={handleClose}
            action='create'
            categoryList={categoryList}
          />
        </Box>
      </Modal>
    </div>
  );
}

export default Product;
