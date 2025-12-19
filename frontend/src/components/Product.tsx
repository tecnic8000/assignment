import { useEffect, useState, type FormEvent } from "react";
import { createProduct, deleteProduct, getProducts } from "../service/backend";
import { useCartStore } from '../service/store-cart';

import type { Product } from "../service/store-cart"
import { useUserStore } from "../service/store-user";

export default function Product() {
     const [loading, setLoading] = useState(true)
     const [menu, setMenu] = useState<Product[]>([])
     const { addItem } = useCartStore()
     const { role, username, authenticated } = useUserStore()


     const [productName, setProductName] = useState("")
     const [productDesc, setProductDesc] = useState("")
     const [productPrice, setProductPrice] = useState(0)
     const [productStock, setProductStock] = useState(0)

     console.log(username)
     useEffect(() => {
          let cancelled = false;
          async function getMenu() {
               try {
                    const res = await getProducts();
                    if (!cancelled) {
                         setMenu(res.message);
                         setLoading(false);
                    }
               } catch (err) {
                    if (!cancelled) {
                         setLoading(false);
                         console.log("err- getMenu", err)
                    }
               }
          }
          getMenu();
          return () => { cancelled = true };
     }, []);

     async function handleDelete(productid: number) {
          try {
               const res = await deleteProduct(productid)
               console.log("product deleted", res)
          } catch (err) { console.log(err) }
     }

     async function handleCreate(e: FormEvent<HTMLFormElement>) {
          e.preventDefault()
          try {
               const res = await createProduct({
                    id: 0, productName, productDesc, productPrice, productStock
               })
               console.log(res)
          } catch (err) { console.log(err) }
     }

     return (
          <>
               {loading && <>loading</>}
               <table className="">
                    <thead>
                         <tr className="text-left">
                              <th className="w-55">PRODUCT</th>
                              <th className="w-55">Desc</th>
                              <th className="w-25">Price</th>
                         </tr>
                    </thead>
                    <tbody className="[&>tr>td]:py-2">
                         {menu.map((item, index) => {



                              return (
                                   <tr key={index} className="text-left" >

                                        <td >{item.productName}</td>
                                        <td>{item.productDesc}</td>
                                        <td>{item.productPrice}</td>
                                        {authenticated &&
                                             <tr>
                                                  <button onClick={() => addItem({ id: item.id, itemName: item.productName, quantity: 1, unitprice: item.productPrice })}>
                                                       ADD TO CART
                                                  </button>
                                             </tr>}
                                        {(role == "admin") &&
                                             <tr>
                                                  <button onClick={() => handleDelete(item.id)}>
                                                       DELETE
                                                  </button>
                                             </tr>}
                                   </tr>)
                         })}
                    </tbody>
               </table>

               {(role == "admin") &&
                    <form onSubmit={handleCreate} className="p-5 bg-blue-900 space-y-2">
                         <input required placeholder="PRODUCT NAME" value={productName} onChange={(e) => setProductName(e.target.value)} /><br />
                         <input required placeholder="PRODUCT DESCRIPTION" value={productDesc} onChange={(e) => setProductDesc(e.target.value)} /><br />
                         <input required placeholder="PRODUCT PRICE" value={productPrice} onChange={(e) => setProductPrice(Number(e.target.value))} /><br />
                         <input required placeholder="PRODUCT STOCK" value={productStock} onChange={(e) => setProductStock(Number(e.target.value))} /><br />
                         <button type="submit">CREATE NEW PRODUCT</button>
                    </form>
               }
          </>
     )
}
