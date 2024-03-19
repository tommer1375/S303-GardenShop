db.createCollection('stores', {
  validator: {
    $jsonSchema: {
      bsonType: 'object',
      title: 'stores',
      required: ['name', 'stock'],
      properties: {
        name: {
          bsonType: 'string'
        },
        stock: {
          bsonType: 'array',
          items: {
            title: 'object',
            required: ['product_id', 'quantity'],
            properties: {
              product_id: {
                bsonType: 'objectId'
              },
              quantity: {
                bsonType: 'int'
              }
            }
          }
        }
      }
    }
  }
});