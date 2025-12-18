import React from 'react'
import { Link } from 'react-router-dom'

export default function Header () {
     return (
          <div className='bg-white text-black space-x-5 '>
               <Link to="/login">[VIEW HISTORY]</Link>
               <Link to="/">[VIEW PRODUCT]</Link>
               <Link to="/signup">[SIGNUP]</Link>
               <Link to="/login">[LOGIN]</Link>
          </div>
     )
}
