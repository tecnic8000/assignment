import { RouterProvider, createBrowserRouter } from 'react-router-dom'
import './style/App.css'
import { Layout } from './components/Layout'
import Login from './components/Login'
import Product from './components/Product'
import Order from './components/Order'
import Signup from './components/Signup'
import Checkout from './components/Checkout'
import Admin from './components/Admin'

const router = createBrowserRouter([
  {
    element: <Layout/>,
    errorElement: <div>404 PAGE..</div>,
    children: [
      {
        path: '/',
        element: <Product/>
      },
      {
        path: '/order',
        element: <Order/>
      },
      {
        path: '/signup',
        element: <Signup/>
      },
      {
        path: '/login',
        element: <Login/>
      },
      {
        path: '/admin',
        element: <Admin/>
      },
      {
        path: '/checkout',
        element: <Checkout/>
      }
    ]
  },
])




function App() {

  return (
    <RouterProvider router={router}/>
  )
}

export default App
