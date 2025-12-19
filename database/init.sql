-- Insert 3 users (1 admin, 2 customers)
INSERT INTO demo.users (id, username, password, role) VALUES
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'admin_user', 'hashed_password_1', 'admin'),
('e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', 'sarah_jones', 'hashed_password_5', 'customer'),
('f5eebc99-9c0b-4ef8-bb6d-6bb9bd380a16', 'david_brown', 'hashed_password_6', 'customer');

-- Insert 10 products
INSERT INTO demo.products (id, product_name, product_desc, product_price, product_stock) VALUES
(1, 'Wireless Mouse', 'Ergonomic wireless mouse with 2.4GHz connectivity and adjustable DPI', 25000, 150),
(2, 'USB-C Cable', '6ft braided USB-C charging cable, fast charging compatible', 12000, 300),
(3, 'Desk Lamp', 'LED desk lamp with 3 brightness levels and flexible neck', 35000, 75),
(4, 'Notebook Set', 'Pack of 3 lined notebooks, 100 pages each, A5 size', 15000, 200),
(5, 'Water Bottle', 'Stainless steel insulated water bottle, 32oz capacity', 28000, 120),
(6, 'Phone Stand', 'Adjustable aluminum phone stand for desk use', 18000, 180),
(7, 'Bluetooth Speaker', 'Portable waterproof speaker with 12-hour battery life', 45000, 90),
(8, 'Mechanical Pencil', 'Professional 0.5mm mechanical pencil with metal body', 8000, 250),
(9, 'Laptop Sleeve', '13-inch padded laptop sleeve with front pocket', 22000, 100),
(10, 'Desk Organizer', 'Bamboo desk organizer with multiple compartments', 32000, 65);

-- Insert 10 orders

INSERT INTO demo.orders (id, order_items, order_status, payment_total, customer_id, order_detail) VALUES
('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', '[{"itemid":1, "quantity":2, "subtotal":50000}, {"itemid":3, "quantity":1, "subtotal":35000}]'::jsonb, 'delivered', 85000, 'b1eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'Express shipping requested'),
('a2eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', '[{"itemid":2, "quantity":3, "subtotal":36000}, {"itemid":5, "quantity":2, "subtotal":56000}, {"itemid":8, "quantity":5, "subtotal":40000}]'::jsonb, 'completed', 132000, 'b1eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'Standard delivery'),
('a3eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', '[{"itemid":7, "quantity":1, "subtotal":45000}, {"itemid":9, "quantity":2, "subtotal":44000}]'::jsonb, 'shipping', 89000, 'b1eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'Gift wrap requested'),
('a4eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', '[{"itemid":4, "quantity":4, "subtotal":60000}, {"itemid":6, "quantity":3, "subtotal":54000}, {"itemid":10, "quantity":1, "subtotal":32000}]'::jsonb, 'confirmed', 146000, 'b1eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'Leave at door'),
('a5eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', '[{"itemid":1, "quantity":5, "subtotal":125000}, {"itemid":2, "quantity":10, "subtotal":120000}]'::jsonb, 'pending', 245000, 'b1eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'Waiting for payment confirmation'),
('a6eebc99-9c0b-4ef8-bb6d-6bb9bd380a16', '[{"itemid":3, "quantity":2, "subtotal":70000}, {"itemid":7, "quantity":1, "subtotal":45000}]'::jsonb, 'canceled', 115000, 'b1eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'Customer requested cancellation'),
('a7eebc99-9c0b-4ef8-bb6d-6bb9bd380a17', '[{"itemid":9, "quantity":3, "subtotal":66000}, {"itemid":5, "quantity":1, "subtotal":28000}, {"itemid":4, "quantity":2, "subtotal":30000}]'::jsonb, 'delivered', 124000, 'b1eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'Signature required'),
('a8eebc99-9c0b-4ef8-bb6d-6bb9bd380a18', '[{"itemid":6, "quantity":4, "subtotal":72000}, {"itemid":8, "quantity":6, "subtotal":48000}]'::jsonb, 'returned', 120000, 'b1eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'Product defect reported'),
('a9eebc99-9c0b-4ef8-bb6d-6bb9bd380a19', '[{"itemid":10, "quantity":5, "subtotal":160000}, {"itemid":1, "quantity":3, "subtotal":75000}, {"itemid":7, "quantity":2, "subtotal":90000}]'::jsonb, 'completed', 325000, 'b1eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'Bulk order for office supplies'),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', '[{"itemid":2, "quantity":8, "subtotal":96000}, {"itemid":3, "quantity":1, "subtotal":35000}, {"itemid":6, "quantity":2, "subtotal":36000}]'::jsonb, 'refunded', 167000, 'b1eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'Item not as described');