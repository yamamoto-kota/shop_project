CREATE TABLE UserForm (
    USERNAME VARCHAR(255) NOT NULL PRIMARY KEY,
    ADDRESS VARCHAR(255),
    TOTALPRICE number(255),
    PURCHASEDATE varchar(255)
);

CREATE TABLE AllGoods(
    ID number(255) NOT NULL PRIMARY KEY,
    NAME VARCHAR(255),
    GENRE varchar(255),
    PRICE number(255),
    IMG varchar(255),
    PURCHASEFLAG number(255),
    QUANTITY number(255)
);

INSERT INTO UserForm (USERNAME, ADDRESS) VALUES ('KOTA', 'FUKUSHIMA');
INSERT INTO AllGoods (ID, NAME, genre, price, quantity) VALUES (1, 'bread', 'eat', 100, 0);
