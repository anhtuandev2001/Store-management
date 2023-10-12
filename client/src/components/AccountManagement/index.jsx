// @ts-nocheck
import { Box } from '@mui/material';
import Button from '@mui/material/Button';
import Modal from '@mui/material/Modal';
import { useEffect, useState } from 'react';
import AccountList from './AccountList';
import { useDispatch, useSelector } from 'react-redux';
import {
  getAllAccount,
  getAllAddress,
} from '../../store/slices/userManagementSlice/userReduce';
import { handleLoading } from '../../store/slices/loadingSlice';
import AccountForm from './AccountForm';

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

function AccountManagement() {
  const dispatch = useDispatch();
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const { accountList } = useSelector((state) => state.userManagement);
  const { statusUser } = useSelector((state) => state.userManagement);
  const { addressList } = useSelector((state) => state.userManagement);

  useEffect(() => {
    dispatch(getAllAccount());
    dispatch(getAllAddress());
    dispatch(handleLoading(true));
  }, []);

  useEffect(() => {
    if (
      (statusUser.accountList === 'success' ||
        statusUser.accountList === 'error') &&
      (statusUser.addressList === 'success' ||
        statusUser.addressList === 'error')
    )
      dispatch(handleLoading(false));
  }, [accountList, addressList]);

  return (
    <div>
      <div className='flex justify-between items-center full-w py-4 text-[#6B778C]'>
        <span>Account Manager</span>
        <Button variant='contained' onClick={handleOpen}>
          Add Account
        </Button>
      </div>
      <AccountList accounts={accountList} addressList={addressList} />
      <Modal
        // @ts-ignore
        open={open}
        onClose={handleClose}
        aria-labelledby='modal-modal-title'
        aria-describedby='modal-modal-description'
      >
        <Box sx={style}>
          <AccountForm action='create' onClose={handleClose} />
        </Box>
      </Modal>
    </div>
  );
}

export default AccountManagement;
