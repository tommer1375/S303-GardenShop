db.createCollection('tickets', {
  validator: {
    $jsonSchema: {
      bsonType: 'object',
      title: 'tickets',
      required: ['store_id', 'total'],
      properties: {
        store_id: {
          bsonType: 'objectId'
        },
        products: {
          bsonType: 'array',
          items: {
            title: 'object',
            required: ['product_id', 'quantity', 'total'],
            properties: {
              product_id: {
                bsonType: 'objectId'
              },
              quantity: {
                bsonType: 'int'
              },
              total: {
                bsonType: 'double'
              }
            }
          }
        },
        total: {
          bsonType: 'double'
        }
      }
    }
  }
});