import { Link } from 'react-router-dom'

export default function Login() {
     return (
          <>
               <form>
                    <div>LOGIN</div>
                    <input placeholder='USERNAME'></input>
                    <br />
                    <input placeholder='PASSWORD'></input>
                    <br />
                    <button type='submit'>LOG IN</button>
               </form>
          </>
     )
}
