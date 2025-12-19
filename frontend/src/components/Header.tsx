import { Link, useLocation } from 'react-router-dom'
import { useUserStore } from '../service/store-user'

export default function Header() {
     const { role, authenticated, username } = useUserStore()
     const location = useLocation().pathname


     return (
          <div className= {`text-black space-x-5 py-3 ${role === "admin" ? "bg-orange-500" : "bg-white"}`}>
               {(username !== null) && <Link to="/login">{username}---[VIEW HISTORY]</Link>} 

               {(location !== "/") && <Link to="/">[VIEW PRODUCTS]</Link>}

               
               {(location !== "/signup") && <Link to="/signup">[SIGNUP]</Link>}

              {(location !== "/login") && <Link to="/login">[LOGIN]</Link>} 
               
               {(location !== "/admin") && <Link to="/admin">[ADMIN]</Link>}

               {authenticated && <button>LOG OUT</button>}
          </div>
     )
}
