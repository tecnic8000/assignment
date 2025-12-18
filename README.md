# assignment
proof of concept submission

[url-client](https://localhost:5173/)
[url-admin](https://localhost:8080/admin)

# Solution Summary
- User can create order.
- User can view order history.
- Admin can view all orders, products, users.
- Admin can delete a product
- 

- JWT is stored in HttpOnly Cookies, transfer via HTTPS as required by common browsers

# docker

!

# manual start-up


# API ENDPOINTS
## USER
`/api/user/signup`
`/api/user/login`
`/api/user/validate` -- used only by backend
`/api/user/logout`
## PRODUCT
`/api/product/create`
`/api/product/view`
`/api/product/delete`
## ORDER
`/api/order/create`
`/api/order/view`
`/api/order/viewall`


# frontend
stack: zod, axios, zustand, mkcert, reactRouterDOM, tailwindcss
!


# backend
- create
!-THYMELEAF
!-Bruno & curl
!-reconfirm JWT lifespans
!-DEDUCT STOCK
!-write tests
!-redis to session admin
!-redis pwd is not set, recheck application.properties

# database
feature: local SSL

!

# Unimplemented Features
- Refresh JWT rotation