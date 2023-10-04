import Exception from "../exceptions/Exception.js"
import { Cart } from "../models/index.js"

const getAllCarts = async () => {
    const allCart = await Cart.find()
    return allCart
}
const getCartByUserId = async (userId) => {
    const cart = await Cart.find({userId})
    if (!cart) {
        throw new Exception('Cannot find cart with id ' + cartId)
    }
    return cart
}

const insertCart = async ({
    productId,
    quantity,
    color,
    userId,
}) => {
    try {
        const existingCart = await Cart.findOne({ productId, color, userId });
        if (existingCart) {
            const newQuantity = Number(quantity);
            const existingQuantity = Number(existingCart.quantity);
            existingCart.quantity = existingQuantity + newQuantity;
            await existingCart.save();
            return existingCart;
        } else {
            const cart = await Cart.create({
                productId,
                userId,
                quantity,
                color,
            });
            return cart;
        }
    } catch (exception) {
        if (!!exception.errors) {
            throw new Exception('Input error', exception.errors);
        }
    }
}

const deleteCart = async (cartId) => {
    try {
        const cart = await Cart.findByIdAndDelete(cartId);
        if (!cart) {
            throw new Error('cart not found');
        }
        return {
            message: 'Cart deleted successfully',
            data: null,
        };
    } catch (exception) {
        throw new Error('Error deleting cart: ' + exception.message);
    }
};

const updateCart = async ({
    productId,
    userId,
    quantity,
    color,
}) => {
    const cart = await Cart.findById(id)
    debugger
    cart.productId = productId ?? cart.productId
    cart.quantity = quantity ?? cart.quantity
    cart.color = color ?? cart.color
    cart.userId = userId ?? cart.userId
    await cart.save()
    return cart
}

export default {
    getAllCarts,
    insertCart,
    getCartByUserId,
    deleteCart,
    updateCart,
}