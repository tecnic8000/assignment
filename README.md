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
`/v1/user/signup` -
`/v1/user/login` -
`/v1/user/validate` -- used only by backend
`/v1/user/logout` -
## PRODUCT
`/v1/product/create` -
`/v1/product/view` -
`/v1/product/delete/{id}` -
## ORDER
`/v1/order/create` -
`/v1/order/view` - 
`/v1/order/viewall` -  


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