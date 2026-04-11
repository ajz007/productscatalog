INSERT IGNORE INTO categories (name) VALUES
('ELECTRONICS'),
('JEWELLERY'),
('MENS_CLOTHING'),
('WOMEN_CLOTHING');

INSERT INTO products (title, price, category_id, description, image, stock_quantity)
SELECT
    'Noise Cancelling Headphones',
    149.99,
    c.id,
    'Wireless over-ear headphones with active noise cancellation.',
    'https://images.unsplash.com/photo-1505740420928-5e560c06d30e',
    25
FROM categories c
WHERE c.name = 'ELECTRONICS'
  AND NOT EXISTS (
      SELECT 1 FROM products p WHERE p.title = 'Noise Cancelling Headphones'
  );

INSERT INTO products (title, price, category_id, description, image, stock_quantity)
SELECT
    'Smart Fitness Watch',
    89.50,
    c.id,
    'Fitness watch with heart-rate tracking and sleep insights.',
    'https://images.unsplash.com/photo-1523275335684-37898b6baf30',
    40
FROM categories c
WHERE c.name = 'ELECTRONICS'
  AND NOT EXISTS (
      SELECT 1 FROM products p WHERE p.title = 'Smart Fitness Watch'
  );

INSERT INTO products (title, price, category_id, description, image, stock_quantity)
SELECT
    'Sterling Silver Ring',
    59.00,
    c.id,
    'Minimal sterling silver ring for everyday wear.',
    'https://images.unsplash.com/photo-1617038220319-276d3cfab638',
    18
FROM categories c
WHERE c.name = 'JEWELLERY'
  AND NOT EXISTS (
      SELECT 1 FROM products p WHERE p.title = 'Sterling Silver Ring'
  );

INSERT INTO products (title, price, category_id, description, image, stock_quantity)
SELECT
    'Classic Denim Jacket',
    72.00,
    c.id,
    'Mid-weight denim jacket with a timeless regular fit.',
    'https://images.unsplash.com/photo-1542272604-787c3835535d',
    30
FROM categories c
WHERE c.name = 'MENS_CLOTHING'
  AND NOT EXISTS (
      SELECT 1 FROM products p WHERE p.title = 'Classic Denim Jacket'
  );

INSERT INTO products (title, price, category_id, description, image, stock_quantity)
SELECT
    'Everyday Linen Dress',
    68.75,
    c.id,
    'Breathable linen dress designed for all-day comfort.',
    'https://images.unsplash.com/photo-1496747611176-843222e1e57c',
    22
FROM categories c
WHERE c.name = 'WOMEN_CLOTHING'
  AND NOT EXISTS (
      SELECT 1 FROM products p WHERE p.title = 'Everyday Linen Dress'
  );

INSERT INTO products (title, price, category_id, description, image, stock_quantity)
SELECT
    'Leather Crossbody Bag',
    110.00,
    c.id,
    'Structured crossbody bag with adjustable strap and secure zip closure.',
    'https://images.unsplash.com/photo-1548036328-c9fa89d128fa',
    15
FROM categories c
WHERE c.name = 'WOMEN_CLOTHING'
  AND NOT EXISTS (
      SELECT 1 FROM products p WHERE p.title = 'Leather Crossbody Bag'
  );
