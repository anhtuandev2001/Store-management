import React from 'react';

const ProductList = ({ products }) => {
  return (
    <div>
      <h2 className='text-2xl font-bold mb-4 text-[#42526e]'>Product List</h2>
      <ul>
        {products.map((product) => (
          <li key={product.id} className='flex justify-between space-x-4 pt-5 pb-5 border-b-2'>
            <div className='flex gap-5'>
              <img
                src={product.imageSrc}
                alt={product.name}
                className='w-[200px] h-[200px] rounded '
              />
              <div className='flex flex-col gap-2'>
                <strong className='text-lg text-[#606060]'>
                  {product.name}
                </strong>
                <span>
                  ${product.price} USD
                </span>
                <span>Quantity: {product.quantity}</span>
              </div>
            </div>
            <div className='flex flex-col w-[300px]'>
              <span>Delete</span>
              <span>Edit</span>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ProductList;
