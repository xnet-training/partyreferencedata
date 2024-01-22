

```sh
curl -X 'POST' \
  'https://api.dev.crossnetcorp.com.pe/partyreferencedata/api/v1/party' \
  -H 'accept: application/json' \
  -H 'Authorization: Bearer khjljklj' \
  -H 'Content-Type: application/json' \
  -d '{
  "party": {
    "fullName": [
      { "function": "NOMBRE","name": "JOSE" },
      { "function": "APMATERNO", "name": "CONTRERAS" },
      { "function": "APMATERNO", "name": "LOPEZ" }
    ],
    "birthDate": "1978-01-21",
    "document": [
      {
        "id": "CE",
        "number": "000196717"
      }
    ],
    "addresses": [
      {
        "address": "CALLE JORGE BASADRE",
        "number": "2123",
        "geoLocation": {
          "countryCode": "PE",
          "state": "LIMA",
          "district": "LIMA",
          "county": "SAN MIGUEL"
        }
      }
    ]
  }
}'
```
