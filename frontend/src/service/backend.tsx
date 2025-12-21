import axios from "axios"
import type { Product } from "./store-cart"
export const API = "https://localhost:8011"

export type Order = {
     username: string,
     orderItems: OrderItem[],
     orderDetail: string,
     orderStatus: "pending" | "paid",
     paymentTotal: number,
}

export type OrderItem = {
     itemid: number,
     quantity: number,
     subtotal: number
}

// PRODUCT
export async function getProducts() {
     try {
          const res = await axios.get(`${API}/api/product/view`)
          return res.data
     } catch (err) { throw ("ERR --01 " + err) }
}
export async function createProduct(newProduct: Product) {
     try {
          const res = await axios.post(
               `${API}/api/product/create`,
               newProduct,
               { withCredentials: true })
          console.log("new product created", res.status, res.data)
     } catch (err) { console.log("createProduct failed --07", err) }
}
export async function deleteProduct(id: number) {
     try {
          const res = await axios.delete(
               `${API}/api/product/delete/${id}`,
               { withCredentials: true },)
          console.log("product deleted", res.status, res.data)
     } catch (err) { console.log("ERR delete --05", err) }
}
// USER
export async function signup(username: string, password: string) {
     try {
          const res = await axios.post(
               `${API}/api/user/signup`,
               { username: username, password: password, role: "customer" },
          )
          return res.data
     } catch (err) { throw ("ERR signup--02" + err) }
}

export async function login(username: string, password: string) {
     try {
          const res = await axios.post(
               `${API}/api/user/login`,
               { username: username, password: password },
               { withCredentials: true }
          )
          console.log('user logged in --', res.data)
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
     } catch (err) { throw ("ERR--04" + err) }
}
// ORDER
export async function createNewOrder(newOrder: Order) {
     try {
          const res = await axios.post(
               `${API}/api/order/create`,
               newOrder,
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