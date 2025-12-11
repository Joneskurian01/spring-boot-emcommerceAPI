CREATE TABLE carts(
    id BINARY(16) NOT NULL UNIQUE DEFAULT (uuid_to_bin(UUID())) PRIMARY KEY,
    date_created DATE DEFAULT (curdate())
);

CREATE TABLE cart_items(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cart_id BINARY(16) NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,

    CONSTRAINT FK_product_id_CartItem
    FOREIGN KEY (product_id) REFERENCES Products (id)
    ON DELETE CASCADE,

    CONSTRAINT FK_cart_id_CartItem
    FOREIGN KEY (cart_id) REFERENCES Carts (id)
    ON DELETE CASCADE,

    CONSTRAINT cart_product_unique_cart_items
    UNIQUE (cart_id,product_id)
);
--if you create a foreign key constraint an index is automatically created.
--    CREATE INDEX FK_cart_id_CartItem (cartId),
--    CREATE INDEX FK_product_id_CartItem (productId)
