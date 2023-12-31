import {BiCategoryAlt, BiLogoProductHunt} from 'react-icons/bi'
import {BiSolidUserAccount} from 'react-icons/bi'
import {FaRegMoneyBillAlt} from 'react-icons/fa'

const menuItems = [
	{
		to: "/product",
		icon: <BiLogoProductHunt />,
		title: "Product Management",
	},
	{
		to: "/statistical",
		icon: <BiCategoryAlt />,
		title: "Statistical Chart",
	},
	{
		to: "/order",
		icon: <FaRegMoneyBillAlt />,
		title: "Order Management",
	},
	{
		to: "/account",
		icon: <BiSolidUserAccount />,
		title: "Account Management",
	},
];

export default menuItems;
