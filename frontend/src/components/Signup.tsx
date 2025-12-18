import React from 'react'
import { Link } from 'react-router-dom'

export default function Signup() {
     return (
          <>
               <form>
                    <div>SIGN UP</div>
                    <input placeholder='USERNAME'></input>
                    <br/>
                    <input placeholder='PASSWORD'></input>
                    <br/>
                    <button>SIGN UP</button>
               </form>
          </>
     )
}
