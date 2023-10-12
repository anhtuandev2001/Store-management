// @ts-nocheck
import React, { useEffect, useState } from 'react';
import { Tooltip, Button, Modal, Box, Menu, MenuItem } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import { AiFillEdit, AiOutlineDelete } from 'react-icons/ai';
import { BiDetail } from 'react-icons/bi';
import { useDispatch, useSelector } from 'react-redux';
import { v4 as uuidv4 } from 'uuid';
import {
  changeStatusOrder,
  getAllOrder,
} from '../../store/slices/productManagementSlice/productReduce';
import { LoadingButton } from '@mui/lab';
import { clearStatusUser } from '../../store/slices/userManagementSlice/userManagementSlice';
import { handleLoading } from '../../store/slices/loadingSlice';
import { getAllAccount } from '../../store/slices/userManagementSlice/userReduce';

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  bgcolor: 'background.paper',
  boxShadow: 24,
  borderRadius: '5px',
  p: 4,
  overflow: 'scroll',
};

const dataGridClass = {
  '& .MuiDataGrid-columnHeaderTitleContainer': {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  '& .MuiDataGrid-cell--withRenderer': {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  '& .MuiDataGrid-cell': {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
};

const OrderList = ({ orderList }) => {
  const [isLoading, setIsLoading] = useState(false);
  const [action, setAction] = useState();
  const [orderView, setOrderView] = useState();
  const [orderItem, setOrderItem] = useState();
  const [dataChange, setDataChange] = useState();
  const [total, setTotal] = useState();
  const { status } = useSelector((state) => state.productManagement);
  const { accountList } = useSelector((state) => state.userManagement);
  const dispatch = useDispatch();

  useEffect(() => {}, []);

  const [open, setOpen] = React.useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const handleImage = (image) => {
    if (image) {
      const imageUrlWithoutBrackets = image.replace(/\[|\]/g, '');
      return imageUrlWithoutBrackets;
    }
  };
  const handleView = (order) => {
    setAction('view');
    handleOpen();
    const orderView = JSON.parse(order.orderDetail);
    const totalPrice = orderView.reduce(
      (accumulator, currentValue) => accumulator + currentValue.price,
      0
    );
    setTotal(totalPrice);
    setOrderView(orderView);
  };

  console.log(accountList);

  const findNameById = (userIdToFind) => {
    for (const item of accountList) {
      if (item._id === userIdToFind) {
        return item.email;
      }
    }
    return null;
  };

  const [anchorEl, setAnchorEl] = React.useState(null);
  const openMenu = Boolean(anchorEl);
  const handleClickMenu = (event, params) => {
    setOrderItem(params);
    setAnchorEl(event.currentTarget);
  };
  const handleCloseMenu = (value) => {
    if (typeof value == 'number') {
      setAction('change');
      const data = { status: value, orderId: orderItem?.id };
      setDataChange(data);
      handleOpen();
    }
    setAnchorEl(null);
  };

  const handleSubmitDelete = () => {
    setIsLoading(true);
    dispatch(changeStatusOrder(dataChange));
  };

  useEffect(() => {
    if (
      status.changeStatusOrder === 'success' ||
      status.changeStatusOrder === 'error'
    ) {
      setIsLoading(false);
    }
    if (status.changeStatusOrder === 'success') {
      dispatch(getAllOrder());
      dispatch(clearStatus());
      dispatch(handleLoading(true));
      handleClose();
    }
  }, [status]);

  const columns = [
    { field: 'id', headerName: 'ID', width: 220 },
    {
      field: 'orderDate',
      headerName: 'Order Date',
      width: 160,
    },
    {
      field: 'status',
      headerName: 'Status',
      width: 160,
    },
    {
      field: 'userId',
      headerName: 'User',
      width: 220,
    },
    {
      field: 'action',
      headerName: 'Action',
      width: 250,
      renderCell: (params) => (
        <div className='flex gap-2'>
          <Tooltip title='Change Status'>
            <Button
              variant='contained'
              id='basic-button'
              aria-controls={open ? 'basic-menu' : undefined}
              aria-haspopup='true'
              aria-expanded={open ? 'true' : undefined}
              onClick={(event) => handleClickMenu(event, params.row)}
            >
              <AiFillEdit size={24} />
            </Button>
          </Tooltip>
          <Tooltip title='View Order'>
            <Button variant='contained' onClick={() => handleView(params.row)}>
              <BiDetail size={24} />
            </Button>
          </Tooltip>
        </div>
      ),
    },
  ];

  const rows =
    orderList &&
    orderList.map((item) => ({
      id: item._id,
      orderDate: item.orderDate.split('T')[0],
      status:
        item.status == 0
          ? 'Processing'
          : item.status == 1
          ? 'Deliveried'
          : 'Cancel',
      userId: findNameById(item.userId),
      orderDetail: item.orderDetail,
    }));

  return (
    <div className='overflow-scroll'>
      <h2 className='text-2xl font-bold mb-4 text-[#42526e]'>Order Manager</h2>
      <div style={{ height: '600px' }}>
        <DataGrid
          rows={rows}
          columns={columns}
          rowHeight={100}
          sx={dataGridClass}
          initialState={{
            pagination: {
              paginationModel: { page: 0, pageSize: 5 },
            },
          }}
          pageSizeOptions={[5, 10]}
        />
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
        <MenuItem onClick={() => handleCloseMenu(0)}>Confirming</MenuItem>
        <MenuItem onClick={() => handleCloseMenu(1)}>Processing</MenuItem>
        <MenuItem onClick={() => handleCloseMenu(2)}>Deliveried</MenuItem>
        <MenuItem onClick={() => handleCloseMenu(3)}>Cancel</MenuItem>
      </Menu>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby='modal-modal-title'
        aria-describedby='modal-modal-description'
      >
        <Box sx={style}>
          {action === 'view' ? (
            <div className='text-[#42526e] w-[700px]'>
              <h2 className='text-xl mb-4 text-center'>View Order</h2>
              <div className='flex h-[420px] '>
                <div className='border-r-2 pr-4 pb-4 overflow-scroll flex-1'>
                  {(orderView || []).map((item) => (
                    <div key={uuidv4()} className='flex gap-4 pb-3'>
                      <img
                        src={handleImage(item.image)}
                        alt={item.name}
                        className='w-[200px] rounded-sm h-[200px] object-cover'
                      />
                      <div className='flex flex-col gap-2'>
                        <span className='font-bold text-lg'>{item.name}</span>
                        <span>${item.price}USD</span>
                        <span>Color: {item.color}</span>
                        <span>Quantity: {item.quantity}</span>
                      </div>
                    </div>
                  ))}
                </div>
                <div className='w-[200px] text-xl text-black text-center'>
                  Total: ${total}USD
                </div>
              </div>
            </div>
          ) : (
            <div className='p-5'>
              <h2 className='text-xl font-bold text-[#42526e]'>
                Are you sure you want change status to{' '}
                {dataChange?.status == 0
                  ? 'Confirming'
                  : dataChange?.status == 1
                  ? 'Processing'
                  : dataChange?.status == 2
                  ? 'Deliveried'
                  : 'Cancel'}
              </h2>
              <div className='flex justify-between pt-5'>
                <Button onClick={handleClose} sx={{ color: 'red' }}>
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

export default OrderList;
