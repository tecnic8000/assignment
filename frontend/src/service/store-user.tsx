import { create } from 'zustand'


export type UserState = {
     username: string | null
     role: string | null
     authenticated: boolean
     setUser: (username: string, role:string) => void
     clearUser:() => void
}

export const useUserStore = create<UserState>((set) => ({
     username: null,
     role: null,
     authenticated: false,

     setUser: (username: string, role: string) => set({
          username,
          role,
          authenticated: true
     }),

     clearUser: () => set({ username: null, role: null, authenticated: false })

}))