import { useEffect, useState, type FormEvent } from "react"
import { signup } from "../service/backend"
import { useNavigate } from "react-router-dom"
import { useUserStore } from "../service/store-user"

export default function Signup() {
     const [username, setUsername] = useState("")
     const [password, setPassword] = useState("")
     const navigate = useNavigate()
     const {authenticated} = useUserStore()

     useEffect(() => {
          if (authenticated) navigate("/")
     }, [authenticated, navigate])


     async function handleSubmit(e: FormEvent<HTMLFormElement>) {
          e.preventDefault()
          try {
               const res = await signup(username, password)
               console.log(res)
               navigate("/login")
          } catch (err) { throw ("handleSubmit crashed" + err) }
     }

     return (
          <>
               <form onSubmit={handleSubmit}>
                    <div>SIGN UP</div>
                    <input required name="uname" placeholder='USERNAME' value={username} onChange={(e) => setUsername(e.target.value)}></input>
                    <br />
                    <input required id="pwd" type="password" placeholder='PASSWORD' value={password} onChange={(e) => setPassword(e.target.value)}></input>
                    <br />
                    <button type="submit">SIGN UP</button>
               </form>
          </>
     )
}
