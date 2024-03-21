-- Mostrar el stock de un producto de una tienda
SELECT p.idproducts, p.type, p.price, p.height, p.color, p.material, s.quantity
FROM stock s
JOIN products p ON s.idproduct = p.idproducts
WHERE s.idstore = (SELECT idstores FROM stores WHERE name = 'Tienda Uno');


-- Importe total de ventas de una tienda
SELECT SUM(p.total) AS total_sales
FROM purchases p
JOIN tickets t ON p.idtickets = t.idtickets
JOIN stores s ON t.idstore = s.idstores
WHERE s.name = 'Tienda Uno';

-- Valor de los productos de una tienda
SELECT SUM(p.price * s.quantity) AS total_value
FROM stock s
JOIN products p ON s.idproduct = p.idproducts
JOIN stores st ON s.idstore = st.idstores
WHERE st.name = 'Tienda Dos';

-- Mostrar listado de todas la ventas
SELECT t.idtickets, s.name AS store_name, p.type, p.price, p.height, p.color, p.material, pu.quantity, pu.total
FROM purchases pu
JOIN tickets t ON pu.idtickets = t.idtickets
JOIN products p ON pu.idproduct = p.idproducts
JOIN stores s ON t.idstore = s.idstores;
