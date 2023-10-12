// @ts-nocheck
import { LoadingButton } from '@mui/lab';
import { Box, Button, Menu, MenuItem, Modal, Tooltip } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import React, { useEffect, useState } from 'react';
import { AiFillEdit } from 'react-icons/ai';
import { BiDetail } from 'react-icons/bi';
import { useDispatch, useSelector } from 'react-redux';
import { v4 as uuidv4 } from 'uuid';
import { handleLoading } from '../../store/slices/loadingSlice';
import { clearStatus } from '../../store/slices/productManagementSlice/productManagementSlice';
import {
  changeStatusOrder,
  getAllOrder,
} from '../../store/slices/productManagementSlice/productReduce';

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

const statusListDefault = [
  { value: 0, name: 'Confirming' },
  { value: 1, name: 'Processing' },
  { value: 2, name: 'Delivered' },
  { value: 3, name: 'Cancel' },
];

const OrderList = ({ orderList }) => {
  const [isLoading, setIsLoading] = useState(false);
  const [action, setAction] = useState();
  const [orderView, setOrderView] = useState();
  const [orderItem, setOrderItem] = useState();
  const [dataChange, setDataChange] = useState();
  const [total, setTotal] = useState();
  const { status } = useSelector((state) => state.productManagement);
  const { accountList } = useSelector((state) => state.userManagement);
  const { addressList } = useSelector((state) => state.userManagement);
  const { cartList } = useSelector((state) => state.productManagement);
  const { productList } = useSelector((state) => state.productManagement);
  const [statusList, setStatusList] = useState(statusListDefault);
  const dispatch = useDispatch();

  const [open, setOpen] = React.useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  console.log(cartList);
  console.log(productList);

  const handleView = (order) => {
    setAction('view');
    handleOpen();
  };

  const findNameById = (userIdToFind) => {
    for (const item of accountList) {
      if (item._id === userIdToFind) {
        return item.email;
      }
    }
    return null;
  };

  const findAddressById = (id) => {
    for (const item of addressList) {
      if (item._id === id) {
        return item.address;
      }
    }
    return null;
  };

  const findPhoneNumberById = (id) => {
    for (const item of addressList) {
      if (item._id === id) {
        return item.phoneNumber;
      }
    }
    return null;
  };

  const [anchorEl, setAnchorEl] = React.useState(null);
  const openMenu = Boolean(anchorEl);
  const handleClickMenu = (event, params) => {
    const newStatusList = statusListDefault.filter(
      (item) => item.name != params.status
    );
    setStatusList(newStatusList);
    setOrderItem(params);
    setAnchorEl(event.currentTarget);
  };
  const handleCloseMenu = (value) => {
    if (typeof value == 'number') {
      setAction('change');
      const data = { status: value, id: orderItem?.id };
      setDataChange(data);
      handleOpen();
    }
    setAnchorEl(null);
  };

  const handleSubmitDelete = () => {
    setIsLoading(true);
    dispatch(changeStatusOrder(dataChange));
    dispatch(handleLoading(true));
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
      field: 'quantity',
      headerName: 'Quantity',
      width: 120,
    },
    {
      field: 'total',
      headerName: 'Total',
      width: 120,
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
      field: 'address',
      headerName: 'Address',
      width: 150,
    },
    {
      field: 'phoneNumber',
      headerName: 'Phone Number',
      width: 150,
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
          ? 'Confirming'
          : item.status == 1
          ? 'Processing'
          : item.status == 2
          ? 'Delivered'
          : 'Cancel',
      userId: findNameById(item.userId),
      orderDetail: item.orderDetail,
      quantity: item.quantity,
      total: item.total,
      address: findAddressById(item.addressId),
      phoneNumber: findPhoneNumberById(item.addressId),
    }));

  const rowsDetail =
    orderList &&
    orderList.map((item) => ({
      id: item._id,
      orderDate: item.orderDate.split('T')[0],
      status:
        item.status == 0
          ? 'Confirming'
          : item.status == 1
          ? 'Processing'
          : item.status == 2
          ? 'Delivered'
          : 'Cancel',
      userId: findNameById(item.userId),
      orderDetail: item.orderDetail,
      quantity: item.quantity,
      total: item.total,
      address: findAddressById(item.addressId),
      phoneNumber: findPhoneNumberById(item.addressId),
    }));

  const columnsDetail = [
    { field: 'id', headerName: 'ID', width: 220 },
    {
      field: 'name',
      headerName: 'Name',
      width: 160,
    },
    {
      field: 'quantity',
      headerName: 'Quantity',
      width: 120,
    },
    {
      field: 'price',
      headerName: 'Price',
      width: 120,
    },
    {
      field: 'total',
      headerName: 'Total',
      width: 120,
    },
  ];

  const OrderDetail = () => {
    return (
      <div className='text-[#42526e] w-[700px]'>
        <h2 className='text-xl mb-4 text-center'>View Order</h2>
        <div>Bills code:</div>
        <div>
          <span>name</span>
          <span>phoneNumber</span>
        </div>
        <div>
          <span>address</span>
          <span>date</span>
        </div>
        <div className='flex h-[420px] '>
          <div className='pr-4 pb-4 overflow-scroll flex-1'>
            <DataGrid
              rows={rowsDetail}
              columns={columnsDetail}
              rowHeight={100}
              sx={dataGridClass}
            />
          </div>
        </div>
      </div>
    );
  };

  const ChangeStatus = () => {
    return (
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
    );
  };

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
        {statusList &&
          statusList.map(({ value, name }) => (
            <MenuItem onClick={() => handleCloseMenu(value)}>{name}</MenuItem>
          ))}
      </Menu>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby='modal-modal-title'
        aria-describedby='modal-modal-description'
      >
        <Box sx={style}>
          {action === 'view' ? <OrderDetail /> : <ChangeStatus />}
        </Box>
      </Modal>
    </div>
  );
};

export default OrderList;
