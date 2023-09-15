// @ts-nocheck
import React, { useEffect, useState } from 'react';
import { Tooltip, Button, Modal, Box } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import { AiFillEdit, AiOutlineDelete } from 'react-icons/ai';
import { useDispatch, useSelector } from 'react-redux';
import { getAllAccount } from '../../store/slices/ScheduleManagementSlice/productReduce';

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  bgcolor: 'background.paper',
  boxShadow: 24,
  borderRadius: '5px',
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
};

const AccountList = ({ accounts }) => {
  const [isLoading, setIsLoading] = useState(false);
  const [action, setAction] = useState();
  const { status } = useSelector((state) => state.productManagement);

  const dispatch = useDispatch();
  const handleDelete = (product) => {};
  const handleEdit = (product) => {};

  const columns = [
    { field: 'id', headerName: 'ID', width: 70, renderCell: renderTooltipCell },
    {
      field: 'name',
      headerName: 'Name',
      width: 130,
      renderCell: renderTooltipCell,
    },
    {
      field: 'email',
      headerName: 'email',
      width: 130,
      renderCell: renderTooltipCell,
    },
    {
      field: 'defaultPaymentId',
      headerName: 'defaultPaymentId',
      width: 130,
      renderCell: renderTooltipCell,
    },
    {
      field: 'defaultShippingId',
      headerName: 'defaultShippingId',
      width: 130,
      renderCell: renderTooltipCell,
    },
    {
      field: 'favouriteList',
      headerName: 'favouriteList',
      width: 130,
      renderCell: renderTooltipCell,
    },
    {
      field: 'cardList',
      headerName: 'cardList',
      width: 130,
      renderCell: renderTooltipCell,
    },
    {
      field: 'password',
      headerName: 'password',
      width: 130,
      renderCell: renderTooltipCell,
    },

    {
      headerName: 'Action',
      width: 160,
      renderCell: (params) => (
        <div className='flex gap-2'>
          <Button variant='outlined' onClick={() => handleDelete(params.row)}>
            <AiOutlineDelete size={24} />
          </Button>
          <Button variant='contained' onClick={() => handleEdit(params.row)}>
            <AiFillEdit size={24} />
          </Button>
        </div>
      ),
    },
  ];

  // Hàm renderCell tùy chỉnh để bọc nội dung trong Tooltip
  function renderTooltipCell(params) {
    return <Tooltip title={params.value || ''}>{params.value}</Tooltip>;
  }

  const rows = (accounts || []).map((item) => ({
    id: item.userId,
    name: item.name,
    email: item.email,
    defaultPaymentId: item.defaultPaymentId,
    defaultShippingId: item.defaultShippingId,
    favouriteList: item.favouriteList,
    cardList: item.cardList,
    password: item.password,
  }));

  const [open, setOpen] = React.useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  return (
    <div className='overflow-scroll'>
      <h2 className='text-2xl font-bold mb-4 text-[#42526e]'>Product List</h2>
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
          {action === 'delete' ? (
            <div className='p-5'>
              <h2 className='text-xl font-bold text-[#42526e]'>
                Are you sure you want to delete the product?
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
          ) : (
            <div className='w-[600px]'></div>
          )}
        </Box>
      </Modal>
    </div>
  );
};

export default AccountList;
