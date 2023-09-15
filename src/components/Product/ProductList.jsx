// @ts-nocheck
import { LoadingButton } from '@mui/lab';
import { Box, Button, Modal, Tooltip } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';

import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { clearStatus } from '../../store/slices/ScheduleManagementSlice/productManagementSlice';
import {
  deleteProduct,
  getAllProduct,
} from '../../store/slices/ScheduleManagementSlice/productReduce';
import { handleLoading } from '../../store/slices/loadingSlice';
import ProductForm from './ProductForm';
import { AiOutlineDelete } from 'react-icons/ai';
import { AiFillEdit } from 'react-icons/ai';

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

const ProductList = ({ products }) => {
  const [itemProduct, setItemProduct] = useState();
  const [isLoading, setIsLoading] = useState(false);
  const [action, setAction] = useState();
  const { status } = useSelector((state) => state.productManagement);

  const dispatch = useDispatch();
  const handleDelete = (product) => {
    product = {
      ...product,
      productId: product.id,
    };
    setItemProduct(product);
    setAction('delete');
    handleOpen();
  };

  const handleEdit = (product) => {
    setItemProduct(product);
    setAction('edit');
    handleOpen();
  };

  const [open, setOpen] = React.useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const handleSubmitDelete = () => {
    setIsLoading(true);
    dispatch(deleteProduct(itemProduct));
  };

  useEffect(() => {
    if (
      status.deleteProduct === 'success' ||
      status.deleteProduct === 'error'
    ) {
      dispatch(clearStatus());
      setIsLoading(false);
      dispatch(handleLoading(true));
      dispatch(getAllProduct());
      handleClose();
    }
  }, [status]);

  const columns = [
    { field: 'id', headerName: 'ID', width: 70, renderCell: renderTooltipCell },
    {
      field: 'name',
      headerName: 'Name',
      width: 130,
      renderCell: renderTooltipCell,
    },
    {
      field: 'description',
      headerName: 'Description',
      width: 160,
      renderCell: renderTooltipCell,
    },
    {
      field: 'colorList',
      headerName: 'ColorList',
      width: 120,
      renderCell: renderTooltipCell,
    },
    {
      field: 'price',
      headerName: 'Price',
      type: 'number',
      width: 140,
      renderCell: renderTooltipCell,
    },
    {
      field: 'quantity',
      headerName: 'Quantity',
      type: 'number',
      width: 140,
      renderCell: renderTooltipCell,
    },
    {
      field: 'categoryId',
      headerName: 'CategoryId',
      type: 'number',
      width: 140,
      renderCell: renderTooltipCell,
    },
    {
      field: 'createdAt',
      headerName: 'CreatedAt',
      width: 150,
      renderCell: renderTooltipCell,
    },
    {
      field: 'imagesList',
      headerName: 'Images',
      width: 160,
      renderCell: (params) => (
        <img
          src={params.value}
          alt='Product'
          style={{ width: '100px', height: '100px' }}
        />
      ),
    },
    {
      field: 'action',
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
    return <Tooltip title={params.value.toString() || ''}>{params.value}</Tooltip>;
  }

  const rows = (products || []).map((item) => ({
    id: item.productId,
    name: item.name,
    description: item.description,
    quantity: item.quantity,
    price: item.price,
    categoryId: item.categoryId,
    createdAt: item.createdAt.split('T')[0],
    colorList: item.colorsList,
    imagesList: `https://furniturev2-1.onrender.com/api/products/images/${item.imagesList}`,
  }));

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
            <div className='w-[600px]'>
              <ProductForm
                product={itemProduct}
                onClose={handleClose}
                action='edit'
              />
            </div>
          )}
        </Box>
      </Modal>
    </div>
  );
};

export default ProductList;
