// @ts-nocheck
import React, { useEffect, useState } from 'react';
import { Tooltip, Button, Modal, Box } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import { AiFillEdit, AiOutlineDelete } from 'react-icons/ai';
import { BiDetail } from 'react-icons/bi';
import { useDispatch, useSelector } from 'react-redux';
import { v4 as uuidv4 } from 'uuid';

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  bgcolor: 'background.paper',
  boxShadow: 24,
  borderRadius: '5px',
  p: 4,
  width: 700,
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
  const [total, setTotal] = useState();
  const { status } = useSelector((state) => state.productManagement);

  const dispatch = useDispatch();
  const handleDelete = (order) => {};
  const handleEdit = (order) => {};
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

  const columns = [
    { field: 'id', headerName: 'ID', width: 70 },
    {
      field: 'orderDate',
      headerName: 'orderDate',
      width: 130,
    },
    {
      field: 'status',
      headerName: 'status',
      width: 160,
    },
    {
      field: 'userId',
      headerName: 'userId',
      width: 130,
    },
    {
      field: 'action',
      headerName: 'Action',
      width: 250,
      renderCell: (params) => (
        <div className='flex gap-2'>
          <Button variant='outlined' onClick={() => handleDelete(params.row)}>
            <AiOutlineDelete size={24} />
          </Button>
          <Button variant='contained' onClick={() => handleEdit(params.row)}>
            <AiFillEdit size={24} />
          </Button>
          <Button variant='contained' onClick={() => handleView(params.row)}>
            <BiDetail size={24} />
          </Button>
        </div>
      ),
    },
  ];

  const rows = (orderList || []).map((item) => ({
    id: item.orderId,
    orderDate: item.orderDate,
    status:
      item.status == 0
        ? 'Confirming'
        : item.status == 1
        ? 'Processing'
        : item.status == 2
        ? 'Deliveried'
        : 'Cancel',
    userId: item.userId,
    orderDetail: item.orderDetail,
  }));

  console.log(orderView);

  const [open, setOpen] = React.useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const handleImage = (image) => {
    if (image) {
      const imageUrlWithoutBrackets = image.replace(/\[|\]/g, '');
      return imageUrlWithoutBrackets;
    }
  };

  return (
    <div className='overflow-scroll'>
      <h2 className='text-2xl font-bold mb-4 text-[#42526e]'>
        Account Manager
      </h2>
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
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby='modal-modal-title'
        aria-describedby='modal-modal-description'
      >
        <Box sx={style}>
          {action === 'view' ? (
            <div className='text-[#42526e]'>
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
            ''
          )}
        </Box>
      </Modal>
    </div>
  );
};

export default OrderList;
