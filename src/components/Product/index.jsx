import { Box, Button, Modal } from '@mui/material';
import ProductList from './ProductList';
import { useState } from 'react';

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 400,
  bgcolor: 'background.paper',
  borderRadius: '3px',
  boxShadow: 24,
  p: 4,
};

export function Product() {
  const products = [
    {
      id: 1,
      name: 'Sản phẩm 1',
      price: 10,
      quantity: 20,
      imageSrc: 'src/assets/image/photo.png',
    },
    {
      id: 2,
      name: 'Sản phẩm 2',
      price: 20,
      quantity: 20,
      imageSrc: '/path/to/image2.jpg',
    },
    {
      id: 3,
      name: 'Sản phẩm 3',
      price: 30,
      quantity: 20,
      imageSrc: '/path/to/image3.jpg',
    },
    {
      id: 4,
      name: 'Sản phẩm 3',
      price: 30,
      quantity: 20,
      imageSrc: '/path/to/image3.jpg',
    },
    {
      id: 5,
      name: 'Sản phẩm 3',
      price: 30,
      quantity: 20,
      imageSrc: '/path/to/image3.jpg',
    },
    // Thêm dữ liệu sản phẩm và đường dẫn hình ảnh ở đây
  ];
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
      <ProductList products={products} />
      <Modal
        // @ts-ignore
        open={open}
        onClose={handleClose}
        aria-labelledby='modal-modal-title'
        aria-describedby='modal-modal-description'
      >
        <Box sx={style}></Box>
      </Modal>
    </div>
  );
}

export default Product;
