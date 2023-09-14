
import axios from "axios";

export const loginAccount = async (data) => {
    return axios.post('https://furniturev2-1.onrender.com/api/user/signup', data)
}

export const registerAccount = async (data) => {
    return axios.post('https://furniturev2-1.onrender.com/api/user/login', data)
}