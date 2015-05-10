# --- !Ups

CREATE TABLE brands (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    image VARCHAR(500) DEFAULT 'defaultBrandImage'
);

CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10 , 2 ),
    description VARCHAR(1000),
    brand_id INT,
    category_id INT,
    FOREIGN KEY (brand_id)
        REFERENCES brands (id)
);

CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    image VARCHAR(500) DEFAULT 'defaultCategoryImage',
    parent_id INT,
    FOREIGN KEY (parent_id)
        REFERENCES categories (id)
);

CREATE TABLE characteristics (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    value VARCHAR(255) NOT NULL
);

CREATE TABLE item_images (
	id SERIAL PRIMARY KEY,
    image VARCHAR(500) DEFAULT 'defaultCategoryImage',
    item_id INT NOT NULL,
    FOREIGN KEY (item_id) REFERENCES products(id)
);

CREATE TABLE products_characteristics (
	item_id INT,
    characteristic_id INT,
    FOREIGN KEY (item_id) REFERENCES products(id),
    FOREIGN KEY (characteristic_id) REFERENCES characteristics(id)
);

# --- !Downs

DROP TABLE categories;
DROP TABLE item_images;
DROP TABLE products_characteristics;
DROP TABLE products;
DROP TABLE brands;
DROP TABLE characteristics;

