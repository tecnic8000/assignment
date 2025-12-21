import { Link, useLocation, useNavigate } from 'react-router-dom'
import { useUserStore } from '../service/store-user'
import { logout } from '../service/backend'

export default function Header() {
     const { role, authenticated, username, clearUser } = useUserStore()
     const location = useLocation().pathname
     const navigate = useNavigate()


     async function handleLogout() {
          try {
               const res = await logout()
               console.log(res)
               clearUser()
               navigate("/")
          } catch (err) { console.log(err) }
     }

     return (
          <div className={`text-black space-x-5 py-3 ${role === "admin" ? "bg-orange-300" : "bg-white"}`}>

               {(location !== "/order" && username !== null) && <Link to="/order">{username}---[VIEW HISTORY]</Link>}

               {(location !== "/") && <Link to="/">[VIEW PRODUCTS]</Link>}


               {(location !== "/signup" && !authenticated) && <Link to="/signup">[SIGNUP]</Link>}

               {(location !== "/login" && !authenticated) && <Link to="/login">[LOGIN]</Link>}

               {(location !== "/admin" && !authenticated) && <Link to="/admin">[ADMIN]</Link>}

               {authenticated && <button className='text-white' onClick={handleLogout}>LOG OUT</button>}

          </div>
     )
}
