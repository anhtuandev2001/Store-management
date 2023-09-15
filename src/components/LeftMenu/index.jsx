// @ts-nocheck
import { useEffect, useRef } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useLocation } from 'react-router-dom';
import { v4 as uuidv4 } from 'uuid';
import { handleLeftMenu } from '../../store/slices/leftMenuSlice';
import menuItems from './MenuItems';

function LeftMenu() {
  const leftMenuRef = useRef(null);
  const location = useLocation();
  const { showMenu } = useSelector((state) => state?.leftMenu);
  const dispatch = useDispatch();

  useEffect(() => {
    window.addEventListener('resize', windowResize);
    windowResize();
    return () => {
      window.removeEventListener('resize', windowResize);
    };
  }, []);

  const windowResize = () => {
    dispatch(handleLeftMenu(window.innerWidth > 768));
  };

  return (
    <div>
      <menu
        ref={leftMenuRef}
        className={`pl-4 pt-6 md:pt-[80px] transition-[width] md:top-0 left-0 z-[40] duration-300 group fixed md:sticky top-[56px]  bg-Neutral10-0 ${
          showMenu ? 'w-60' : 'w-2'
        } min-h-[100vh] border-r`}
      >
        <div className='whitespace-nowrap overflow-hidden'>
          <ul className=' mr-4'>
            {menuItems.map((item) => (
              <li key={uuidv4()}>
                <Link
                  to={item.to}
                  className={`flex px-[10px] rounded-[3px] gap-4 items-center ${
                    location.pathname === item.to
                      ? 'text-[#0052CC] bg-NeutralAlpha-0'
                      : 'text-[#42526E]'
                  }`}
                >
                  <span>{item.icon}</span>
                  <span className='leading-10 font-medium'>{item.title}</span>
                </Link>
              </li>
            ))}
          </ul>
        </div>
      </menu>
    </div>
  );
}

export default LeftMenu;
