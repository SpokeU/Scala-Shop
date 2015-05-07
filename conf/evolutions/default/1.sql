# --- !Ups

CREATE TABLE brands (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    image VARCHAR(500) DEFAULT 'defaultBrandImage'
);

CREATE TABLE items (
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
    FOREIGN KEY (item_id) REFERENCES items(id)
);

CREATE TABLE items_characteristics (
	item_id INT,
    characteristic_id INT,
    FOREIGN KEY (item_id) REFERENCES items(id),
    FOREIGN KEY (characteristic_id) REFERENCES characteristics(id)
);

# --- !Downs

DROP TABLE brands;
DROP TABLE items_characteristics;
DROP TABLE item_images;
DROP TABLE characteristics;
DROP TABLE categories;
DROP TABLE items;


