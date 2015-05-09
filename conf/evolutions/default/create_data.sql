# --- !Ups


INSERT INTO brands (name, image) VALUES ('ASUS', 'http://sidenxab.ru/images/Asus_logo.jpg');
INSERT INTO brands (name, image) VALUES ('SAMSUNG', 'http://www.kiddskids.com/wp-content/uploads/2012/05/samsung-logo.jpeg');
INSERT INTO brands (name, image) VALUES ('HP', 'http://www.kiddskids.com/wp-content/uploads/2012/05/samsung-logo.jpeg');


INSERT INTO categories(id,parent_id,name,image) VALUES(1,null,'Electronics',null);
	INSERT INTO categories(id,parent_id,name,image) VALUES(2,1,'TV',null);
	INSERT INTO categories(id,parent_id,name,image) VALUES(3,1,'Computers',null);
		INSERT INTO categories(id,parent_id,name,image) VALUES(4,3,'Pads',null);
		INSERT INTO categories(id,parent_id,name,image) VALUES(5,3,'Notebooks',null);
	INSERT INTO categories(id,parent_id,name,image) VALUES(6,1,'Phones',null);
INSERT INTO categories(id,parent_id,name,image) VALUES(7,null,'Sports',null);
	INSERT INTO categories(id,parent_id,name,image) VALUES(8,7,'Hunting & Fishing',null);


INSERT INTO characteristics (name, value) VALUES('Resolution', '1366x768');
INSERT INTO characteristics (name, value) VALUES('Diagonal', '42');
INSERT INTO characteristics (name, value) VALUES('Martix', 'HDA+P');


INSERT INTO items (name, price, description, brand_id, category_id) VALUES('Samsung SMART-TV',5000,'This is totaly cool tv', 2, 2);
INSERT INTO items (name, price, description, brand_id, category_id) VALUES('HP-Compaq CQ-538745',2000,'Cool notebook', 3, 5);
