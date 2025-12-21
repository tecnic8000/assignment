import { useEffect, useState } from "react"
import { viewAllOrders, viewOrder } from "../service/backend"
import { useUserStore } from "../service/store-user"
import { useNavigate } from "react-router-dom"

type RetrievedOrder = {
  orderItems: {
    id: string,
    itemname: string,
    quantitu: number,
    subtotal: number
  }[],
  orderStatus: string,
  paymentTotal: number
}

export default function Order() {
  const navigate = useNavigate()
  const { role } = useUserStore()
  const [orders, setOrders] = useState<RetrievedOrder[]>([])

  useEffect(() => {
    const getHistory = async () => {
      try {
        const res = await viewOrder()
        setOrders(res.message)
      } catch (err) { console.log(err) }
    }
    async function getHistoryAdmin() {
      try {
        const res = await viewAllOrders()
        setOrders(res.message)
      } catch (err) { console.log(err) }
    }
    if (role == null) {
      navigate("/")
    } else if (role == "admin") {
      getHistoryAdmin()
    } else {
      getHistory()
    }
  }, [role, navigate])

  console.log(orders)
  return (
    <div>

      {orders.map((item, index) => (
        <div key={index} className="text-left" >
          <div className="flex flex-row items-center border-2 m-2 p-1 w-fit">
            <div>
              {item.orderItems.map((item, index) => (<div key={index} className="m-1 bg-blue-900">{item.itemname}</div>))}
            </div>
            <div> {item.orderStatus} --- {item.paymentTotal}
            </div>
          </div>
        </div>
      ))}

    </div>
  )
}
