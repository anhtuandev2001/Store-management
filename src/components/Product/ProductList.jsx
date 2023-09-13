// @ts-nocheck
import { Button } from '@mui/material';
import React from 'react';
import { useDispatch } from 'react-redux';
import { deleteProduct } from '../../store/slices/ScheduleManagementSlice/productReduce';

const ProductList = ({ products }) => {
  const dispatch = useDispatch()
  const handleDelete = (product)=>{
    console.log(product);
    dispatch(deleteProduct(product))
  }
  return (
    <div>
      <h2 className='text-2xl font-bold mb-4 text-[#42526e]'>Product List</h2>
      <ul>
        {products.map((product) => (
          <li
            key={product.productId}
            className='flex justify-between space-x-4 pt-5 pb-5 border-b-2'
          >
            <div className='flex gap-5'>
              <img
                src={product.imagesList}
                alt={product.name}
                className='w-[200px] h-[200px] rounded '
              />
              <div className='flex flex-col gap-2'>
                <strong className='text-lg text-[#606060]'>
                  {product.name}
                </strong>
                <span>{product.categoryId}</span>
                <span>${product.price} USD</span>
                <span>Quantity: {product.quantity}</span>
                <span className='uppercase'>
                  {product.colorsList ? product.colorsList : 'No color'}
                </span>
                <span>{product.description}</span>
              </div>
            </div>
            <div className='flex flex-col w-[300px]'>
              <Button onClick={() => handleDelete(product)}>Delete</Button>
              <span>Edit</span>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ProductList;
