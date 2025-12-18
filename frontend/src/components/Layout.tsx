import { Outlet } from 'react-router-dom'
import Header from './Header'
import Cart from './Cart'


export const Layout = () => {

     return (
          <>
               <Header/>

               <div className="bg-gray-900 text-white h-120 w-140 p-10">
                    <Outlet />
               </div>

               <Cart/>
          </>
     )
}
