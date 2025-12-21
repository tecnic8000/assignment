-- DROP database IF EXISTS demo1intvw;
-- CREATE database IF NOT EXISTS demo1intvw;
-- \c demo1intvw
CREATE SCHEMA IF NOT EXISTS demo;

-- CREATE TABLES
-- DROP TABLE IF EXISTS schema_cbe4dev.users;
CREATE TABLE demo.users (
   id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
   username TEXT UNIQUE,
   password TEXT,
   role TEXT DEFAULT 'customer', -- customer|admin
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- metadata
);

CREATE TABLE demo.products (
   id SERIAL PRIMARY KEY,
   product_name TEXT,
   product_desc TEXT DEFAULT NULL,
   product_price DECIMAL(9,0),
   product_stock SMALLINT, -- -32768 to 32767
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- metadata
);

CREATE TABLE demo.orders (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	order_items JSONB, --  key-value array obj
   order_status TEXT DEFAULT 'pending', --pending | confirmed | shipping | delivered | completed | canceled | refunded | exchanged | returned
	payment_total DECIMAL(9,0), -- 999,999,999 vnd
   order_detail TEXT ,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   customer_id UUID,
   CONSTRAINT fk_order_customer FOREIGN KEY (customer_id) REFERENCES demo.users(id)
);

