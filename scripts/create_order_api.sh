curl -i -X POST \
   -H "Content-Type:application/json" \
   -d '{"description":"some", "total":1.5}' \
 'http://localhost:8080/orders'