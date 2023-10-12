// @ts-nocheck
import { Box } from '@mui/material';
import Modal from '@mui/material/Modal';
import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { handleLoading } from '../../store/slices/loadingSlice';
import {
  getAllCart,
  getAllOrder,
  getAllProduct,
} from '../../store/slices/productManagementSlice/productReduce';
import {
  getAllAccount,
  getAllAddress,
} from '../../store/slices/userManagementSlice/userReduce';
import OrderList from './OrderList';

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  width: 600,
  transform: 'translate(-50%, -50%)',
  bgcolor: 'background.paper',
  borderRadius: '3px',
  boxShadow: 24,
};

function OrderManagement() {
  const dispatch = useDispatch();
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const { orderList } = useSelector((state) => state.productManagement);
  const { status } = useSelector((state) => state.productManagement);
  const { statusUser } = useSelector((state) => state.userManagement);

  useEffect(() => {
    dispatch(getAllAccount());
    dispatch(getAllAddress());
    dispatch(getAllOrder());
    dispatch(getAllCart());
    dispatch(getAllProduct());
    dispatch(handleLoading(true));
  }, []);

  console.log(status);

  useEffect(() => {
    if (
      (status.orderList == 'success' || status.orderList == 'error') &&
      (status.productInfo == 'success' || status.productInfo == 'error') &&
      (statusUser.accountList == 'success' ||
        statusUser.accountList == 'error') &&
      (statusUser.addressList == 'success' ||
        statusUser.addressList == 'error') &&
      (status.cartList == 'success' || status.cartList == 'error')
    )
      dispatch(handleLoading(false));
  }, [status, statusUser]);

  return (
    <div>
      <div className='flex justify-between items-center full-w py-4 text-[#6B778C]'>
        <span>Order Manager</span>
      </div>
      <OrderList orderList={orderList} />
      <Modal
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

export default OrderManagement;
