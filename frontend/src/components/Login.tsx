import { useEffect, useState, type FormEvent } from "react"
import { useNavigate } from "react-router-dom"
import { login } from "../service/backend"

export default function Login() {
     const [username, setUsername] = useState("")
     const [password, setPassword] = useState("")
     const [isLoginOK, setIsLoginOK] = useState(false)
     const navigate = useNavigate()
     useEffect(() => {
          if (isLoginOK) navigate("/")
     }, [isLoginOK, navigate])
     async function handleSubmit(e: FormEvent<HTMLFormElement>) {
          e.preventDefault()
          try {
               const res = await login(username, password)
               console.log(res)
               setIsLoginOK(true)
          } catch (err) { throw ("handleSubmit crashed" + err) }
     }
     return (
          <>
               <form onSubmit={handleSubmit}>
                    <div>LOGIN</div>
                    <input required placeholder='USERNAME' value={username} onChange={(e) => setUsername(e.target.value)}></input>
                    <br />
                    <input required placeholder='PASSWORD' type="password" value={password} onChange={(e) => setPassword(e.target.value)}></input>
                    <br />
                    <button type='submit'>LOG IN</button>
               </form>
          </>
     )
}
