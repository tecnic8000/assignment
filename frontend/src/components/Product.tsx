import { useEffect, useState } from "react";
import { getProducts } from "../service";



export default function Product() {
     const [loading, setLoading] = useState(true);

     useEffect(() => {
          let cancelled = false;
          async function getMenu() {
               try {
                    const data = await getProducts();
                    if (cancelled) return;
                    // setMenu(data);
                    // if (!pageSize && data?.result?.length > 0 && data?.next) {
                    //      setPageSize(data.results.length)
                    // }
                    console.log(data)
               } catch (error) {
                    if (!cancelled) console.error('Error fetching menu:', error);
               } finally {
                    if (!cancelled) setLoading(false);
               }
          }
          getMenu();
          return () => { cancelled = true };
     }, []);


     return (
          <div>Product</div>
     )
}
