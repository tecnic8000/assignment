import { useEffect, useState, type FormEvent } from "react"
import { useNavigate } from "react-router-dom"
import { login } from "../service/backend"
import { useUserStore } from "../service/store-user"

export default function Login() {
     const [username, setUsername] = useState("")
     const [password, setPassword] = useState("")
     const setUser = useUserStore(state => state.setUser)
     const navigate = useNavigate()
     const {authenticated} = useUserStore();

     useEffect(()=>{
          if (authenticated) navigate("/")
     },[authenticated, navigate])

     async function handleSubmit(e: FormEvent<HTMLFormElement>) {
          e.preventDefault()
          try {
               const res = await login(username, password)
               console.log(res)
               setUser(res.username, res.role)               
               navigate("/")
          } catch (err) { throw ("handleSubmit crashed" + err) }
     }
     return (
          <>
               <form onSubmit={handleSubmit}>
                    <div>LOGIN</div>
                    <input required id="usrnm" placeholder='USERNAME' value={username} onChange={(e) => setUsername(e.target.value)}></input>
                    <br />
                    <input required id="pwd" placeholder='PASSWORD' type="password" value={password} onChange={(e) => setPassword(e.target.value)}></input>
                    <br />
                    <button type='submit'>LOG IN</button>
               </form>
          </>
     )
}
