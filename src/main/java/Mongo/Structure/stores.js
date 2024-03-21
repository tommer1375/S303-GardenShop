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
            required: ['product_id', 'type', 'price', 'quantity', 'quality'],
            properties: {
              product_id: {
                bsonType: 'objectId'
              },
              type: {
                enum: ["Tree", "Flower", "Decoration"],
                description: 'Must be either Tree, Flower or Decoration.',
              },
              price: {
                bsonType: 'double'
              },
              quantity: {
                bsonType: 'int'
              },
              quality: {
                bsonType: 'string'
              }
            }
          }
        },
        current_value: {
          bsonType: 'double'
        }
      }
    }
  }
});