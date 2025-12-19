import { create } from 'zustand'

export type Product = {
     id: number,
     productName: string,
     productDesc: string,
     productPrice: number,
     productStock: number
}

export type CartItem = {
     id: number,
     itemName: string,
     quantity: number,
     unitprice: number
}



type CartState = {
     cart: CartItem[]
     addItem: (item:CartItem) => void
     substractItem: (item:CartItem) => void
     removeItem:(item:CartItem) => void
     clearCart: () => void
     // getItemQuantity:(item:CartItem) => number
     getSubtotalPerItem:(item:CartItem)=> number
     getTotalItems:()=>number 
     getTotalPayment:()=>number
}

export const useCartStore = create<CartState>((set, get) => ({
     cart: [],

     addItem: (item: CartItem) => set((state) => {
          const existingItem = state.cart.find((i: CartItem) => i.id === item.id);
          if (existingItem) {
               return {
                    cart: state.cart.map((i: CartItem) =>
                         i.id === item.id ? { ...i, quantity: i.quantity + 1 } : i
                    ),
               };
          }
          return {
               cart: [...state.cart, { ...item, quantity: 1 }],
          };
     }),

     substractItem: (item: CartItem) => set((state) => {
          const existingItem = state.cart.find((i: CartItem) => i.id === item.id);
          if (existingItem && existingItem.quantity > 1) {
               return {
                    cart: state.cart.map((i: CartItem) =>
                         i.id === item.id ? { ...i, quantity: i.quantity - 1 } : i
                    ),
               };
          }
          return {
               cart: state.cart.filter((i: CartItem) => i.id !== item.id),
          };
     }),

     removeItem: (item: CartItem) => set((state) => ({
          cart: state.cart.filter((i: CartItem) => i.id !== item.id),
     })),

     clearCart: () => set({ cart: [] }),

     // getItemQuantity: (item: CartItem) => {
     //      const state = get();
     //      const thisItem = state.cart.find((i:CartItem) => i.id === item.id);
     //      return thisItem ? thisItem.quantity : 0;
     // },

     getTotalItems: () => {
          const state = get();
          return state.cart.reduce((total: number, item: CartItem) => total + item.quantity, 0);
     },

     getSubtotalPerItem: (item: CartItem) => {
          const state = get()
          const thisItem = state.cart.find((i:CartItem) => i.id === item.id)
          return thisItem ? (thisItem.unitprice * thisItem.quantity) : 0
     },

     getTotalPayment: () => {
          const state = get();
          return state.cart.reduce((total: number, item: CartItem) => total + (item.unitprice * item.quantity), 0);
     },

}));

