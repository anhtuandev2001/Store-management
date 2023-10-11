// @ts-nocheck
import { Tooltip } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';

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
  '& .MuiDataGrid-cell': {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
};

const AccountList = ({ accounts }) => {
  const columns = [
    { field: 'id', headerName: 'ID', width: 220 },
    {
      field: 'name',
      headerName: 'Name',
      width: 130,
    },
    {
      field: 'email',
      headerName: 'Email',
      width: 220,
      renderCell: (params) => (
        <Tooltip title={params.value}>
          <span>{params.value}</span>
        </Tooltip>
      ),
    },
    {
      field: 'defaultShippingId',
      headerName: 'Default ShippingId',
      width: 220,
    },
    {
      field: 'favoritesList',
      headerName: 'Favorite List',
      width: 220,
    },
  ];

  const rows = (accounts || []).map((item) => ({
    id: item._id,
    name: item.name,
    email: item.email,
    defaultPaymentId: item.defaultPaymentId,
    defaultShippingId: item.default_shipping_id,
    favoritesList: item.favoritesList,
    cardList: item.cardList,
  }));

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
    </div>
  );
};

export default AccountList;
