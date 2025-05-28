CREATE TABLE products (
                          product_id SERIAL PRIMARY KEY,
                          product_name VARCHAR(255),
                          user_id INTEGER REFERENCES users(user_id),
                          main_category_id INTEGER REFERENCES main_category(main_category_id),
                          product_price DOUBLE PRECISION,
                          product_status VARCHAR(50),
                          description TEXT,
                          location TEXT,
                          condition VARCHAR(50),
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE users (
                       user_id SERIAL PRIMARY KEY,
                       user_name VARCHAR(50),
                       first_name VARCHAR(50),
                       last_name VARCHAR(50),
                       email VARCHAR(100) UNIQUE,
                       password VARCHAR(255),
                       gender VARCHAR(10),
                       phone_number VARCHAR(20),
                       profile_image TEXT,
                       address TEXT,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE product_images (
                                id SERIAL PRIMARY KEY,
                                product_id INTEGER REFERENCES products(product_id),
                                url TEXT
);
CREATE TABLE product_history (
                                 id SERIAL PRIMARY KEY,
                                 product_id INTEGER REFERENCES products(product_id),
                                 message TEXT,
                                 updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE main_category (
                               main_category_id SERIAL PRIMARY KEY,
                               name VARCHAR(100)
);
CREATE TABLE favorites (
                           id SERIAL PRIMARY KEY,
                           user_id INTEGER REFERENCES users(user_id),
                           product_id INTEGER REFERENCES products(product_id)
);
CREATE TABLE ratings (
                         rating_id SERIAL PRIMARY KEY,
                         rated_user_id INTEGER REFERENCES users(user_id),
                         rating_user_id INTEGER REFERENCES users(user_id),
                         score INTEGER,
                         comment TEXT
);
CREATE TABLE discounts (
                           discount_id SERIAL PRIMARY KEY,
                           product_id INTEGER REFERENCES products(product_id),
                           discount_percent DOUBLE PRECISION,
                           expired_at TIMESTAMP
);
CREATE TABLE notifications (
                               id SERIAL PRIMARY KEY,
                               user_id INTEGER REFERENCES users(user_id),
                               content TEXT,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE otp_number (
                            id SERIAL PRIMARY KEY,
                            user_id INTEGER REFERENCES users(user_id),
                            otp_code VARCHAR(6),
                            expires_at TIMESTAMP,
                            is_used BOOLEAN DEFAULT FALSE
);
CREATE TABLE contact_info (
                              id SERIAL PRIMARY KEY,
                              user_id INTEGER REFERENCES users(user_id),
                              telegram VARCHAR(255),
                              facebook VARCHAR(255),
                              other_contact TEXT
);
TRUNCATE TABLE products CASCADE;
TRUNCATE TABLE product_images, products;
DROP TABLE products CASCADE;
TRUNCATE TABLE product_images, products RESTART IDENTITY CASCADE;
drop table main_category;
drop table product_images;
INSERT INTO users (user_name, first_name, last_name, email, password)
VALUES ('testuser', 'Test', 'User', 'test@example.com', 'hashedpassword');
INSERT INTO main_category(name) values ('Fashion'),
                                       ('Accessory'),
                                       ('Sport'),
                                       ('Beauty'),
                                       ('Book');