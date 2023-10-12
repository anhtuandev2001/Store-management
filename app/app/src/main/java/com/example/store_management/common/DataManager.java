package com.example.store_management.common;

import com.example.store_management.address.Address;
import com.example.store_management.address.AddressInsertRequest;
import com.example.store_management.address.AddressUpdateRequest;
import com.example.store_management.user.UserAddressRequest;
import com.example.store_management.user.UserData;
import com.example.store_management.login.LoginRequest;
import com.example.store_management.order.Order;
import com.example.store_management.order.OrderInsertRequest;
import com.example.store_management.product.Product;
import com.example.store_management.cart.Cart;
import com.example.store_management.cart.CartInsertRequest;
import com.example.store_management.cart.CartRequest;
import com.example.store_management.category.Category;
import com.example.store_management.user.UserFavoriteRequest;

import java.util.List;

public class DataManager {
    private static DataManager instance;

    // User data
    private UserData userData;

    // Product-related data
    private List<Product> productList;

    private String productFilterCategoryId = "";
    private String productFilterSearchInput = "";
    private List<Category> categoryList;

    // Cart-related data
    private List<Cart> cartList;
    private double cartTotalPrice;
    private int cartQuantity;
    private String cartId;

    // Order-related data
    private List<Order> orderList;
    private CartRequest cartRequest;
    private CartInsertRequest cartInsertRequest;
    private OrderInsertRequest orderInsertRequest;
    private LoginRequest loginRequest;
    private UserAddressRequest userAddressRequest;
    private String token;
    private String positionProductBack = "product";

    public String getPositionProductBack() {
        return positionProductBack;
    }

    public void setPositionProductBack(String positionProductBack) {
        this.positionProductBack = positionProductBack;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserFavoriteRequest getUserFavoriteRequest() {
        return userFavoriteRequest;
    }

    public void setUserFavoriteRequest(UserFavoriteRequest userFavoriteRequest) {
        this.userFavoriteRequest = userFavoriteRequest;
    }

    private UserFavoriteRequest userFavoriteRequest;
    private AddressInsertRequest addressInsertRequest;

    private String addressId;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public AddressUpdateRequest getAddressUpdateRequest() {
        return addressUpdateRequest;
    }

    public void setAddressUpdateRequest(AddressUpdateRequest addressUpdateRequest) {
        this.addressUpdateRequest = addressUpdateRequest;
    }

    private AddressUpdateRequest addressUpdateRequest;

    public AddressInsertRequest getAddressInsertRequest() {
        return addressInsertRequest;
    }

    public void setAddressInsertRequest(AddressInsertRequest addressInsertRequest) {
        this.addressInsertRequest = addressInsertRequest;
    }

    private int checkedPosition;
    private List<Address> addressList;
    private boolean isCheckout = false;

    public boolean isCheckout() {
        return isCheckout;
    }

    public void setCheckout(boolean checkout) {
        isCheckout = checkout;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public UserAddressRequest getUserAddressRequest() {
        return userAddressRequest;
    }

    public void setUserAddressRequest(UserAddressRequest userAddressRequest) {
        this.userAddressRequest = userAddressRequest;
    }

    public int getCheckedPosition() {
        return checkedPosition;
    }

    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
    }

    private DataManager() {
        // Private constructor to prevent external instantiation.
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    // Getter and setter methods for each data field

    // User data
    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    // Product-related data
    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    // Cart-related data
    public List<Cart> getCartList() {
        return cartList;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }

    public double getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(double cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public int getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(int cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    // Order-related data
    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public CartRequest getCartRequest() {
        return cartRequest;
    }

    public void setCartRequest(CartRequest cartRequest) {
        this.cartRequest = cartRequest;
    }

    public CartInsertRequest getCartInsertRequest() {
        return cartInsertRequest;
    }

    public void setCartInsertRequest(CartInsertRequest cartInsertRequest) {
        this.cartInsertRequest = cartInsertRequest;
    }

    public OrderInsertRequest getOrderInsertRequest() {
        return orderInsertRequest;
    }

    public void setOrderInsertRequest(OrderInsertRequest orderInsertRequest) {
        this.orderInsertRequest = orderInsertRequest;
    }

    public LoginRequest getLoginRequest() {
        return loginRequest;
    }

    public void setLoginRequest(LoginRequest loginRequest) {
        this.loginRequest = loginRequest;
    }

    public String getProductFilterCategoryId() {
        return productFilterCategoryId;
    }

    public void setProductFilterCategoryId(String productFilterCategoryId) {
        this.productFilterCategoryId = productFilterCategoryId;
    }

    public String getProductFilterSearchInput() {
        return productFilterSearchInput;
    }

    public void setProductFilterSearchInput(String productFilterSearchInput) {
        this.productFilterSearchInput = productFilterSearchInput;
    }

    public void clearAllData() {
        // Xóa tất cả dữ liệu của ứng dụng ở đây
        userData = null;
        productList = null;
        categoryList = null;
        cartList = null;
        cartTotalPrice = 0;
        cartQuantity = 0;
        cartId = null;
        orderList = null;
        cartRequest = null;
        cartInsertRequest = null;
        orderInsertRequest = null;
        loginRequest = null;
        userAddressRequest = null;
        checkedPosition = 0;
        addressList = null;
        isCheckout = false;
    }
}
