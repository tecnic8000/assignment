import axios from "axios";
import { useEffect, useState, type FormEvent } from "react";
import { API } from "../service/backend";
import { useNavigate } from "react-router-dom";
import { useUserStore } from "../service/store-user";

export default function Admin() {
     const [password, setPassword] = useState("")
     const { setUser, authenticated } = useUserStore()
     const navigate = useNavigate()


     useEffect(() => {
          if (authenticated) navigate("/")
     }, [authenticated, navigate])

     async function handleSubmit(e: FormEvent<HTMLFormElement>) {
          e.preventDefault()
          try {
               const res = await axios.post(
                    `${API}/api/user/loginadmin`,
                    { password: password },
                    { withCredentials: true })
               console.log("admin logged in -- ", res.status, res.data)
               setUser(res.data.username,res.data.role)
               navigate("/")
          } catch (err) { console.log("adminLogin crashed --", err) }
     }
     return (
          <>
               <form className="h-40" onSubmit={handleSubmit}>
                    <input required placeholder="ADMIN PASSWORD"
                         type="password"
                         value={password}
                         onChange={(e) => setPassword(e.target.value)} />
                    <br />
                    <button type="submit">LOGIN AS ADMIN</button>
               </form>
          </>
     )
}
