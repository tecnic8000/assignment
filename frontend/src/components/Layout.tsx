import { Outlet } from 'react-router-dom'
import Header from './Header'
import Cart from './Cart'
import { useUserStore } from '../service/store-user'


export const Layout = () => {
     const { username } = useUserStore()
     return (
          <>
               <Header />

               <div className="bg-gray-700 text-white h-auto w-200 p-10">
                    <Outlet />
               </div>


               {(username !== null) && <Cart />}
          </>
     )
}
