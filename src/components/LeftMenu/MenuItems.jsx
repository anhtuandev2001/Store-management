import {BiLogoProductHunt} from 'react-icons/bi'
import {BiSolidUserAccount} from 'react-icons/bi'
import {FaRegMoneyBillAlt} from 'react-icons/fa'

const menuItems = [
	{
		to: "/product",
		icon: <BiLogoProductHunt />,
		title: "Product Management",
	},
	{
		to: "/bill",
		icon: <FaRegMoneyBillAlt />,
		title: "Bill Management",
	},
	{
		to: "/account",
		icon: <BiSolidUserAccount />,
		title: "Account Management",
	},
];

export default menuItems;
