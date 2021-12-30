# Justin Sherfey Custom ORM

### About

A custom lightweight ORM to abstract away use of SQL statements in Java using annotations

## Local Install using Maven

**groupId** - com.revature <br>
**artifactId** - justin_sherfey_p1 <br>
**version** - 2.0 <br>

## Getting Started

Use the annotations below to to build the database models to be persisted

`@PK`  creates the primary key column <br>
      arguments (boolean serial) -> whether primary key will be serial or not <br> <br> <br>
`@Column`  creates a column  <br>
      arguments (boolean notNull, boolean Unique) -> whether column will have notNull and unique constrains <br> <br> <br>
      
Use the library methods below to persist object models to the database

| Method          | Description            |
| -----------     | ----------------       |
| `void create(object.class)`    | creates a table if one does not exist in the database yet     |


`object read(object.class, primarykey)` - Reads from the database using primary key and returns the object wanted <br><br>

`void update(object)` - Updates an object in a table, if does not exist inserts into <br><br>

`delete(object)` - Deletes the item requested identified by primary key <br><br>
