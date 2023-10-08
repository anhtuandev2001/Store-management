import Exception from "../exceptions/Exception.js"
import { Cart, Order } from "../models/index.js"

const getAllOrders = async () => {
    const allOrder = await Order.find()
    return allOrder
}
const getOrderByUserId = async (userId) => {
    const order = await Order.find({ userId })
    if (!order) {
        throw new Exception('Cannot find Order with id ' + userId)
    }
    return order
}

const insertOrder = async ({
    orderDate,
    quantity,
    total,
    status,
    userId,
    cartList,
    addressId,
}) => {
    try {
        debugger
        const order = await Order.create({
            orderDate,
            quantity,
            total,
            status,
            userId,
            cartList,
            addressId,
        });
        const newCartList = cartList.split(',');
        for (const cartId of newCartList) {
            const cart = await Cart.findByIdAndDelete(cartId);
        }

        return order;
    } catch (exception) {
        if (!!exception.errors) {
            throw new Exception('Input error', exception.errors);
        }
    }
}

const deleteOrder = async (OrderId) => {
    try {
        const order = await Order.findByIdAndDelete(OrderId);
        if (!order) {
            throw new Error('Order not found');
        }
        return {
            message: 'Order deleted successfully',
            data: null,
        };
    } catch (exception) {
        throw new Error('Error deleting Order: ' + exception.message);
    }
};

const updateOrder = async ({
    id,
    quantity,
}) => {
    const order = await Order.findById(id)
    debugger
    order.quantity = quantity ?? Order.quantity
    await order.save()
    return order
}

export default {
    getAllOrders,
    insertOrder,
    getOrderByUserId,
    deleteOrder,
    updateOrder,
}