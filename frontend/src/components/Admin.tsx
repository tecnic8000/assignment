import axios from "axios";
import { useEffect, useState, type FormEvent } from "react";
import { API } from "../service/backend";
import { useNavigate } from "react-router-dom";

export default function Admin() {
     const [password, setPassword] = useState("")
     const [isAdminloginOK, setIsAdminloginOK] = useState(false)
     const navigate = useNavigate()

     useEffect(() => {
          if (isAdminloginOK) navigate("/")
     }, [isAdminloginOK, navigate])


     async function handleSubmit(e: FormEvent<HTMLFormElement>) {
          e.preventDefault()
          try {
               const res = await axios.post(`${API}/api/user/loginadmin`, password)
               console.log("admin logged in -- ", res.status, res.data)
               setIsAdminloginOK(true)
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
