import Exception from "../exceptions/Exception.js"
import { Cart, Order, Product } from "../models/index.js"

const getAllOrders = async () => {
    const allOrder = await Order.find()
    return allOrder
}
const getOrderByUserId = async (userId) => {
    const order = await Order.find({ userId })
    if (!order) {
        throw new Exception('Cannot find Order with id ' + userId)
    }

    const reversedOrder = order.reverse();

    return reversedOrder;
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
            const product = await Product.findById(cart.productId);
            const quantityDeleted = product.quantity - cart.quantity;
            if (quantityDeleted < 0) {
                throw new Exception('Out of stock in stock');
            }
            product.quantity = quantityDeleted ? quantityDeleted : product.quantity
            await product.save();
        }

        return order;
    } catch (exception) {
        if (exception.errors) {
            throw new Error('Input error', exception.errors);
        }
        throw exception; // Ném ngoại lệ gốc nếu có lỗi khác
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
    status,
}) => {
    const order = await Order.findById(id)
    debugger
    order.status = status ?? order.status
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