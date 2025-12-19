import { create } from 'zustand'


export type UserState = {
     username: string | null
     role: string | null
     authenticated: boolean
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