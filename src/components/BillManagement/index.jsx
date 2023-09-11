
const BillManagement = () => {
  const bill = {
    customerName: 'Người mua hàng',
    date: '2023-09-15',
    items: [
      { id: 1, productName: 'Sản phẩm 1', quantity: 2, price: 10 },
      { id: 2, productName: 'Sản phẩm 2', quantity: 1, price: 20 },
      { id: 3, productName: 'Sản phẩm 3', quantity: 3, price: 15 },
    ],
  };
  return (
    <div className='bg-white'>
      <div className='flex justify-between h-[68.5px] items-center py-4 full-w text-[#6B778C]'>
        <span>Bill Manager</span>
      </div>
      <div>
        <h2 className='text-2xl font-bold mb-4'>Hóa đơn</h2>
        <p className='mb-2'>Tên khách hàng: {bill.customerName}</p>
        <p className='mb-2'>Ngày mua: {bill.date}</p>
        <h3 className='text-xl font-semibold mb-2'>Chi tiết hóa đơn:</h3>
        <ul>
          {bill.items.map((item) => (
            <li key={item.id} className='mb-2'>
              {item.productName} - Số lượng: {item.quantity} - Giá: $
              {item.price} USD
            </li>
          ))}
        </ul>
        <p className='text-lg font-semibold mt-4'>
          Tổng cộng: ${calculateTotal(bill.items)} USD
        </p>
      </div>
    </div>
  );
};

const calculateTotal = (items) => {
  return items.reduce((total, item) => total + item.price * item.quantity, 0);
};

export default BillManagement;
