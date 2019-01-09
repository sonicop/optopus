UPDATE products SET name = REPLACE(TRIM(BOTH '"' FROM name),'""','"') WHERE name LIKE '\"%' AND name LIKE '%\"';
UPDATE products SET name = '"OHM" Symbol' WHERE sku = 'WHI030';
UPDATE products SET name = '"LOVE"' WHERE sku = 'WHR003';
UPDATE products SET name = '"LOVE"' WHERE sku = 'WHR003SG';
UPDATE products SET name = 'Chinese Letter "Love"' WHERE sku = 'WHR024';
UPDATE products SET name = '"Love"' WHERE sku = 'WHD094';