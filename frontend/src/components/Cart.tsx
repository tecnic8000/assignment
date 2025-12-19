import { createNewOrder } from '../service/backend';
import { useCartStore } from '../service/store-cart';

export default function Cart() {

     const { cart, addItem, substractItem, removeItem, getSubtotalPerItem, getTotalItems, getTotalPayment, clearCart } = useCartStore();

     async function submitOrder() {
          console.log("submitting order ...")
          try {
               const res = await createNewOrder({
                    username: "unknown",
                    orderItems: cart,
                    orderDetail: "example",
                    orderStatus:"pending",
                    paymentTotal: getTotalPayment()
               })
               console.log(res)
          } catch (err) {
               console.log("Submission crashed --", err)
          }
     }

     return (
          <div className="bg-green-950 p-3 mt-4">
               <div className='py-5 space-x-4'>
                    <button onClick={() => clearCart()}>RESET</button>

                    [TOTAL: {getTotalItems()} ITEM(s)]
                    ---
                    [PAYMENT: {getTotalPayment()}]

                    <button onClick={() => submitOrder()}>SAVE ORDER TO DB</button>
               </div>

               <table className=''>
                    <thead className='[&>tr>th]:px-5'>
                         <tr>
                              <th>ITEM</th>
                              <th>QUANTITY</th>
                              <th>SUBTOTAL</th>
                         </tr>
                    </thead>
                    <tbody className='[&>tr>td]:py-1'>
                         {cart.map((item, index) => {
                              return (
                                   <tr key={index}>
                                        <td className='text-left'>{item.itemName}</td>
                                        <td>{item.quantity}</td>
                                        <td>{getSubtotalPerItem(item)}</td>
                                        <td className='space-x-3'>
                                             <button onClick={() => addItem(item)}>+</button>
                                             <button onClick={() => substractItem(item)}>-</button>
                                             <button onClick={() => removeItem(item)}>remove</button>
                                        </td>
                                   </tr>
                              )
                         })}
                    </tbody>
               </table>
          </div>
     )
}
