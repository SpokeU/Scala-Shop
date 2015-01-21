# --- !Ups

CREATE TABLE brands (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    image_link VARCHAR(500) DEFAULT 'defaultBrandImage',
    PRIMARY KEY(id)
);

CREATE TABLE items (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10 , 2 ),
    description VARCHAR(1000),
    brand_id INT,
    category_id INT,
    PRIMARY KEY (id),
    FOREIGN KEY (brand_id)
        REFERENCES brands (id)
);

CREATE TABLE categories (
    id INT NOT NULL AUTO_INCREMENT,
    parent_id INT,
    name VARCHAR(255) NOT NULL,
    image_link VARCHAR(500) DEFAULT 'defaultCategoryImage',
    PRIMARY KEY (id),
    FOREIGN KEY (parent_id)
        REFERENCES categories (id)
);

CREATE TABLE characteristics (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    value VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE item_images (
	id INT NOT NULL AUTO_INCREMENT,
    image_link VARCHAR(500) DEFAULT 'defaultCategoryImage',
    item_id INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (item_id) REFERENCES items(id)
);

CREATE TABLE items_characteristics (
	item_id INT,
    characteristic_id INT,
    PRIMARY KEY(item_id,characteristic_id),
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


