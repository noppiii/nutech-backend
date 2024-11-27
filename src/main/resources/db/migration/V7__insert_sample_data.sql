DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM users WHERE email = 'novi@gmail.com') THEN
        INSERT INTO users (email, name, password, social_code, role, created_at, modified_at)
        VALUES ('novi@gmail.com', 'Novianto', '{bcrypt}$2a$10$7cznJ/ZaY5Tb0KYcLQZhfuNobXnmP2pJ3RGZKX2hFQZBa1RPtA2Za', 'NORMAL', 'USER', NOW(), NOW());
END IF;
END $$;

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM products WHERE name = 'Product 1') THEN
        INSERT INTO products (name, quantity, price, created_at, modified_at)
        VALUES ('Product 1', 100, 50000, NOW(), NOW());
END IF;
END $$;

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM wallets WHERE user_id = 1) THEN
        INSERT INTO wallets (user_id, balance, created_at, modified_at)
        VALUES (1, 0.00, NOW(), NOW());
END IF;
END $$;

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM transactions WHERE invoice_number = 'TOPUP12345') THEN
        INSERT INTO transactions (user_id, invoice_number, name, amount, type, payment_method, created_at, modified_at)
        VALUES (1, 'TOPUP12345', 'Top Up 1', 200.00, 'TOP_UP', 'DANA', NOW(), NOW());
END IF;
END $$;

UPDATE wallets
SET balance = balance + 200.00
WHERE user_id = 1;

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM transactions WHERE invoice_number = 'PURCHASE12345') THEN
        INSERT INTO transactions (user_id, invoice_number, name, amount, type, payment_method, created_at, modified_at)
        VALUES (1, 'PURCHASE12345', 'Purchase 1', 100.00, 'PURCHASE', 'SHOPEEPAY', NOW(), NOW());
END IF;
END $$;

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM purchases WHERE transaction_id = 2 AND product_id = 1) THEN
        INSERT INTO purchases (transaction_id, product_id, quantity, created_at, modified_at)
        VALUES (2, 1, 2, NOW(), NOW());
END IF;
END $$;

UPDATE wallets
SET balance = balance - 100.00
WHERE user_id = 1;
