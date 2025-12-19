import { useEffect, useState, type FormEvent } from "react"
import { signup } from "../service/backend"
import { useNavigate } from "react-router-dom"

export default function Signup() {
     const [username, setUsername] = useState("")
     const [password, setPassword] = useState("")
     const [isSignupOK, setIsSignupOK] = useState(false)
     const navigate= useNavigate()
     useEffect(() => {
          if (isSignupOK) navigate("/login")
     }, [isSignupOK,navigate])
     async function handleSubmit(e: FormEvent<HTMLFormElement>) {
          e.preventDefault()
          try {
               const res = await signup(username, password)
               console.log(res)
               setIsSignupOK(true)
          } catch (err) { throw ("handleSubmit crashed" + err) }
     }

     return (
          <>
               <form onSubmit={handleSubmit}>
                    <div>SIGN UP</div>
                    <input required placeholder='USERNAME' value={username} onChange={(e) => setUsername(e.target.value)}></input>
                    <br />
                    <input required type="password" placeholder='PASSWORD' value={password} onChange={(e) => setPassword(e.target.value)}></input>
                    <br />
                    <button type="submit">SIGN UP</button>
               </form>
          </>
     )
}
