// @ts-nocheck
import { Link } from 'react-router-dom';
import IconRules from '../../assets/icon/IconForm/IconRules';
import logo from '../../assets/icon/logoBlueOC.png';
import Translation from '../Translation';

const RegisterPage = () => {
  return (
    <>
      <div className='bg-login '>
        <div className=' container-small flex flex-col justify-center items-center h-full'>
          <div>
            <div className='form w-full bg-gradient-to-b from-indigo-500 rounded-2xl shadow-2xl pt-9 pr-9 pl-9 pb-7 '>
              <div className=' flex justify-center items-center pb-9 ml-[135px] mr-[135px]'>
                <div className='bg-white p-8 rounded-full'>
                  <img src={logo} alt='' className='h-[90px] w-[90px]' />
                </div>
              </div>
              <form className='flex flex-col items-center'>
                <div className=' flex justify-center items-center pb-4 '>
                  <h2 className='text-[20px] font-bold text-white	'>
                    registration
                  </h2>
                </div>
                <div className='flex flex-col w-[400px] relative'>
                  <label className='text-[#2F3F73] font-semibold text-lg'>
                    email
                  </label>
                  <input
                    placeholder='email'
                    type='text'
                    name='email'
                    className={`w-full mb-[10px] mt-[10px] h-[45px] outline-0 p-[10px] rounded-md`}
                  />
                </div>

                <div className='flex flex-col w-[400px]'>
                  <label className='text-[#2F3F73] font-semibold text-lg'>
                    'user-name'
                  </label>
                  <input
                    placeholder='user-name'
                    type='text'
                    name='username'
                    className={`w-full mb-[10px] mt-[10px] h-[45px] outline-0 p-[10px] rounded-md`}
                  />
                </div>
                <div className='flex flex-col w-[400px]'>
                  <div className='text-[#2F3F73] font-semibold text-lg flex items-center'>
                    'password'
                  </div>
                  <div className='relative'>
                    <input
                      placeholder='password'
                      type='password'
                      name='password'
                      autoComplete='new-password'
                      className={`w-full mb-[10px] mt-[10px] h-[45px] outline-0 border-2 p-[10px] rounded-md `}
                    />
                  </div>
                </div>
                <div className='flex flex-col w-[400px]'>
                  <label className='text-[#2F3F73] font-semibold text-lg'>
                    'confirm-password'
                  </label>

                  <div className='relative'>
                    <input
                      placeholder='confirm-password'
                      type='password'
                      name='confirmPassword'
                      autoComplete='new-password1'
                      className={`w-full mb-[10px] mt-[10px] h-[45px] outline-0 border-2 p-[10px] rounded-md `}
                    />
                  </div>
                </div>
                <div className='pt-[5px]'>
                  <button className='relative inline-flex  items-center justify-center p-0.5 mb-2 mr-2 overflow-hidden text-sm font-medium text-gray-900 rounded-lg group bg-gradient-to-br from-cyan-500 to-blue-500 group-hover:from-cyan-500 group-hover:to-blue-500 hover:text-white dark:text-white '>
                    <span className='relative px-[70px] py-[15px] transition-all ease-in duration-75 bg-white dark:bg-[#2F3F73] rounded-md group-hover:bg-opacity-0'>
                      'register'
                    </span>
                  </button>
                </div>
                <h5 className='text-[16px] pt-[1.5rem] mx-auto  '>
                  'already-have-an-account'
                  <span className='ml-[5px]'>
                    <Link className='text-[#2F3F73] font-semibold' to='/login'>
                      'login'
                    </Link>
                  </span>
                </h5>
              </form>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default RegisterPage;
