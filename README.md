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
!-submit zustand state
!-reconfirm JWT lifespans
- need to set maximum quantity according to current stock
-currency format

# backend
!-SSL crashed
!
!-Bruno & curl
!-write tests
!-redis pwd is not set, recheck application.properties

# database
feature: local SSL
ManyToMany; food tags drink,food, cheese, chocolate, cream
!- orderItem is separate TABLE OrderDetail

# Unimplemented Features
- Refresh JWT rotation
- Pagination for  product, order