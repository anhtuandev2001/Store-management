// @ts-nocheck
import { LoadingButton } from '@mui/lab';
import { Box, Button, Modal, Tooltip } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';

import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { clearStatus } from '../../store/slices/ScheduleManagementSlice/productManagementSlice';
import { handleLoading } from '../../store/slices/loadingSlice';
import { AiOutlineDelete } from 'react-icons/ai';
import { AiFillEdit } from 'react-icons/ai';
import CategoryForm from './CategoryForm';

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

function CategoryList({ categoryList }) {
  const [isLoading, setIsLoading] = useState(false);
  const { status } = useSelector((state) => state.productManagement);
  const [itemCategory, setItemCategory] = useState();

  const dispatch = useDispatch();
  const handleDelete = (category) => {
    handleOpen();
    setAction('delete');
    setItemCategory(category);
  };

  const handleEdit = (category) => {
    handleOpen();
    setAction('edit');
    setItemCategory(category);
  };

  console.log(itemCategory);

  const [open, setOpen] = React.useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);
  const [action, setAction] = useState();

  const handleSubmitDelete = () => {
    setIsLoading(true);
  };

  const columns = [
    { field: 'id', headerName: 'ID', width: 70, renderCell: renderTooltipCell },
    {
      field: 'name',
      headerName: 'Name',
      width: 200,
      key: 'name',
      renderCell: renderTooltipCell,
    },
    {
      field: 'action',
      headerName: 'Action',
      width: 200,
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

  function renderTooltipCell(params) {
    return (
      <Tooltip title={params.value || ''}>
        <span>{params.value}</span>
      </Tooltip>
    );
  }

  const rows = (categoryList || []).map((item) => ({
    id: item.categoryId,
    name: item.categoryName,
  }));

  return (
    <div className='overflow-scroll'>
      <h2 className='text-2xl font-bold mb-4 text-[#42526e]'>Product List</h2>
      <div style={{ height: '600px', width: 600, marginInline: 'auto' }}>
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
              <h2 className='text-xl whitespace-nowrap font-bold text-[#42526e]'>
                Are you sure you want to delete the Category?
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
              <CategoryForm
                category={itemCategory}
                onClose={handleClose}
                action='edit'
              />
            </div>
          )}
        </Box>
      </Modal>
    </div>
  );
}

export default CategoryList;
