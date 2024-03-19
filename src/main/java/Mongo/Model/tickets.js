db.createCollection('tickets', {
  validator: {
    $jsonSchema: {
      bsonType: 'object',
      title: 'tickets',
      required: ['store_id'],
      properties: {
        store_id: {
          bsonType: 'objectId'
        },
        products: {
          bsonType: 'array',
          items: {
            title: 'object',
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
        }
      }
    }
  }
});