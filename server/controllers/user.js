import {
  validationResult
} from 'express-validator';
import { EventEmitter } from 'node:events';
import HttpStatusCode from '../exceptions/HttpStatusCode.js';
import {
  userRepository
} from '../repositories/index.js';

const myEvent = new EventEmitter()
myEvent.on('event.register.user', (params) => {
  console.log(`They talked about : ${JSON.stringify(params)}`)
})

const login = async (req, res) => {
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    return res.status(HttpStatusCode.BAD_REQUEST).json({
      errors: errors.array()
    });
  }
  const { email, password } = req.body;
  try {
    let existingUser = await userRepository.login({ email, password })
    res.status(HttpStatusCode.OK).json({
      message: 'Login user successfully',
      data: existingUser
    })
  } catch (exception) {
    res.status(HttpStatusCode.INTERNAL_SERVER_ERROR).json({
      message: exception.toString(),
    })
  }
}
const register = async (req, res) => {
  const {
    name,
    email,
    password,
    phoneNumber,
    address
  } = req.body

  myEvent.emit('event.register.user', { email, phoneNumber })
  try {
    debugger
    const user = await userRepository.register({
      name,
      email,
      password,
      phoneNumber,
      address
    })
    res.status(HttpStatusCode.INSERT_OK).json({
      message: 'Register user successfully',
      data: user
    })
  } catch (exception) {
    debugger
    res.status(HttpStatusCode.INTERNAL_SERVER_ERROR).json({
      message: exception.toString(),
    })
  }

}
const getDetailUser = async (req, res) => {

}

async function updateAddressUser(req, res) {
  try {
      debugger
      const user = await userRepository.updateAddressUser(req.body)
      res.status(HttpStatusCode.OK).json({
          message: 'Update user successfully',
          data: user,
      })
  } catch (exception) {
      res.status(HttpStatusCode.INTERNAL_SERVER_ERROR).json({
          message: exception.message,
      })
  }
}

export default {
  login,
  register,
  getDetailUser,
  updateAddressUser
}