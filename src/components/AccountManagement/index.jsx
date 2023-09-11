import { Box } from '@mui/material';
import Button from '@mui/material/Button';
import Modal from '@mui/material/Modal';
import { useState } from 'react';
import AddAccountForm from './AccountForm';
import AccountList from './AccountList';

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
  const accounts = [
    { id: 1, name: 'Người dùng 1', email: 'user1@example.com', role: 'user' },
    { id: 2, name: 'Người dùng 2', email: 'user2@example.com', role: 'admin' },
  ];
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const handleAddAccount = (values) => {
    console.log('Thêm tài khoản:', values);
  };
  return (
    <div>
      <div className='flex justify-between items-center full-w py-4 text-[#6B778C]'>
        <span>Account Manager</span>
        <Button variant='contained' onClick={handleOpen}>Add Account</Button>
      </div>
      <AccountList accounts={accounts} />
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
