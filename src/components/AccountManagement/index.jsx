// @ts-nocheck
import { Box } from '@mui/material';
import Button from '@mui/material/Button';
import Modal from '@mui/material/Modal';
import { useEffect, useState } from 'react';
import AddAccountForm from './AccountForm';
import AccountList from './AccountList';
import { useDispatch, useSelector } from 'react-redux';
import { getAllAccount } from '../../store/slices/ScheduleManagementSlice/productReduce';

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

function AccountManagement() {
  const dispatch = useDispatch();
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const { accountList } = useSelector((state) => state.productManagement);

  useEffect(() => {
    dispatch(getAllAccount());
  }, []);

  console.log(accountList);

  const handleAddAccount = (values) => {
    console.log('Thêm tài khoản:', values);
  };
  return (
    <div>
      <div className='flex justify-between items-center full-w py-4 text-[#6B778C]'>
        <span>Account Manager</span>
        <Button variant='contained' onClick={handleOpen}>
          Add Account
        </Button>
      </div>
      <AccountList  />
      <Modal
        // @ts-ignore
        open={open}
        onClose={handleClose}
        aria-labelledby='modal-modal-title'
        aria-describedby='modal-modal-description'
      >
        <Box sx={style}>
          <AddAccountForm onSubmit={handleAddAccount} />
        </Box>
      </Modal>
    </div>
  );
}

export default AccountManagement;
