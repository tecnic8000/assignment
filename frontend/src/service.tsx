import axios from "axios"
const API = "https://localhost:8022"

export interface Order {
  orderDetail: string[],
  customerId: string | null,
  paymentStatus: "pending" | "paid",
  paymentTotal: number,
}
// PRODUCT
export async function getProducts() {
     try {
          const res = await axios.get(`${API}/api/product/view`)
          return res.data
     } catch (err) { throw ("ERR --01 " + err) }
}
// USER
export async function signup(username: string, password: string) {
     try {
          const res = await axios.post(
               `${API}/api/user/signup`,
               { username: username, password: password, role: "customer1" },
          )
          return res.data
     } catch (err) { throw ("ERR --02" + err) }
}

export async function login(username: string, password: string) {
     try {
          const res = await axios.post(
               `${API}/api/user/login`,
               { username: username, password: password }
          )
          console.log('user logged in --', res.data, res.data)
          return res.data
     } catch (err) { throw ("ERR --03" + err) }
}

export async function validate() {
     try {
          const res = await axios.post(
               `${API}/api/user/validate`,
               {},
               { withCredentials: true }
          )
          console.log('jwt validated and renewed')
          return res.data
     } catch (err) { throw ("jwt invalidated --01" + err) }
}

export async function logout() {
     try {
          const res = await axios.post(
               `${API}/api/user/logout`,
               {},
               { withCredentials: true }
          )
          console.log('user logged out')
          return res.data
     } catch (err) { throw ("ERR--04"+ err) }
}
// ORDER
export async function createNewOrder(newOrder: Order) {
  try {
    const { orderDetail, paymentTotal } = newOrder
    const res = await axios.post(
      `${API}/api/order/create`,
      { orderDetail, paymentTotal, paymentStatus:"pending" },
      { withCredentials: true })
    console.log("status createNewOrder: ", res.status, res.data)
  } catch (err) {
    console.log(err)
  }
}

export async function viewOrder() {
  try {
    const res = await axios.post(
      `${API}/api/order/view`,
      {},
      { withCredentials: true })
    return res.data
  } catch (err) { console.log("viewHistory1 failed--01", err) }
}



// ping
export async function ping() {
     try {
          const res = await axios.get(`${API}/api/ping`)
          return res.data
     } catch (err) {
          console.log("be4 ping failed --", err)
     }
}