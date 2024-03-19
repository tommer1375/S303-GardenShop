db.createCollection('products', {
  validator: {
    $jsonSchema: {
      bsonType: 'object',
      title: 'products',
      required: ['type'],
      properties: {
        type: {
          enum: ["Tree", "Flower", "Decoration"],
          description: 'Must be either Tree, Flower or Decoration.',
        },
        height: {
          bsonType: 'string'
        },
        color: {
          bsonType: 'string'
        },
        material: {
          enum: ["Wood", "Plastic"]
        },
        price: {
          bsonType: 'double'
        }
      }
    }
  }
});