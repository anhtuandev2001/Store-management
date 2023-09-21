// @ts-nocheck
import { Box } from '@mui/material';
import Button from '@mui/material/Button';
import Modal from '@mui/material/Modal';
import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import {
  getAllAccount,
  getAllOrder,
} from '../../store/slices/ScheduleManagementSlice/productReduce';
import { handleLoading } from '../../store/slices/loadingSlice';
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

  useEffect(() => {
    dispatch(getAllOrder());
    dispatch(handleLoading(true));
  }, []);

  useEffect(() => {
    if (status.orderList === 'success' || status.orderList === 'error')
      dispatch(handleLoading(false));
  }, [orderList]);

  return (
    <div>
      <div className='flex justify-between items-center full-w py-4 text-[#6B778C]'>
        <span>Order Manager</span>
      </div>
      <OrderList orderList={orderList} />
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

export default OrderManagement;
