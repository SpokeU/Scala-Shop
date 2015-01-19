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



#Example data

#--------BRANDS----------
INSERT INTO brands (name, image_link) VALUES ('ASUS', 'http://sidenxab.ru/images/Asus_logo.jpg');
INSERT INTO brands (name, image_link) VALUES ('SAMSUNG', 'http://www.kiddskids.com/wp-content/uploads/2012/05/samsung-logo.jpeg');
INSERT INTO brands (name, image_link) VALUES ('HP', 'http://www.kiddskids.com/wp-content/uploads/2012/05/samsung-logo.jpeg');

#--------CATEGORIES--------
INSERT INTO categories(id,parent_id,name,image_link) VALUES(1,null,'Electronics',null);
	INSERT INTO categories(id,parent_id,name,image_link) VALUES(2,1,'TV',null);
	INSERT INTO categories(id,parent_id,name,image_link) VALUES(3,1,'Computers',null);
		INSERT INTO categories(id,parent_id,name,image_link) VALUES(4,3,'Pads',null);
		INSERT INTO categories(id,parent_id,name,image_link) VALUES(5,3,'Notebooks',null);
	INSERT INTO categories(id,parent_id,name,image_link) VALUES(6,1,'Phones',null);
INSERT INTO categories(id,parent_id,name,image_link) VALUES(7,null,'Sports',null);
	INSERT INTO categories(id,parent_id,name,image_link) VALUES(8,7,'Hunting & Fishing',null);

#----------CHARACTERISTICS---------
INSERT INTO characteristics (name, value) VALUES('Resolution', '1366x768');
INSERT INTO characteristics (name, value) VALUES('Diagonal', '42');
INSERT INTO characteristics (name, value) VALUES('Martix', 'HDA+P');

#--------ITEMS-----------
INSERT INTO items (name, price, description, brand_id, category_id) VALUES('Samsung SMART-TV',5000,'This is totaly cool tv', 2, 2);
INSERT INTO items (name, price, description, brand_id, category_id) VALUES('HP-Compaq CQ-538745',2000,'Cool notebook', 3, 5);