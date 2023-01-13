CREATE TABLE DEPT (
    NAME VARCHAR(255) NOT NULL
);

INSERT INTO DEPT(NAME) VALUES ('Marketing');
INSERT INTO DEPT(NAME) VALUES ('Sales');
INSERT INTO DEPT(NAME) VALUES ('Development');

CREATE TABLE USERLIST (
    USERID VARCHAR(255) PRIMARY KEY,
    USERNAME VARCHAR(255) NOT NULL,
    ADDRESS VARCHAR(255) NOT NULL,
    MAIL VARCHAR(255) NOT NULL,
    MONEY INT NOT NULL
);

CREATE TABLE  ALLITEM(
    ITEMID INT NOT NULL PRIMARY KEY,
    ITEMNAME VARCHAR(255) NOT NULL,
    GENRE VARCHAR(255) NOT NULL,
    PRICE INT NOT NULL,
    IMG VARCHAR(255) NOT NULL
);

CREATE TABLE  CARTITEM(
    CARTITEMID INT AUTO_INCREMENT,
    ITEMID INT NOT NULL,
    ITEMNAME VARCHAR(255) NOT NULL,
    GENRE VARCHAR(255) NOT NULL,
    PRICE INT NOT NULL,
    IMG VARCHAR(255) NOT NULL,
    QUANTITY INT NOT NULL,
    USERID VARCHAR(255) NOT NULL
);

CREATE TABLE  SLIP(
    SLIPID INT NOT NULL PRIMARY KEY,
    USERID VARCHAR(255) NOT NULL,
    ALLITEMNAME VARCHAR(255) NOT NULL,
    PURCHASEPRICE INT NOT NULL,
    PURCHASEDATE VARCHAR(255) NOT NULL
);

INSERT INTO ALLITEM(ITEMID,ITEMNAME,GENRE,PRICE,IMG) VALUES (1,'bread','food',100,'pan');
INSERT INTO ALLITEM(ITEMID,ITEMNAME,GENRE,PRICE,IMG) VALUES (2,'apple','food',200,'ringo');
INSERT INTO ALLITEM(ITEMID,ITEMNAME,GENRE,PRICE,IMG) VALUES (3,'meat','food',300,'hana');


INSERT INTO USERLIST(USERID,USERNAME,ADDRESS,MAIL,MONEY) VALUES ('lk2889=','test','Fukushima','aaa@bb.com',1500);
INSERT INTO USERLIST(USERID,USERNAME,ADDRESS,MAIL,MONEY) VALUES ('lk2889','kota','Fukushima','aaa@bb.com',1500);
INSERT INTO USERLIST(USERID,USERNAME,ADDRESS,MAIL,MONEY) VALUES ('lk2890','taro','Tokyo','zzz@bb.com',100);


