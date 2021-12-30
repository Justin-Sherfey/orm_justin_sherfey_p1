# Justin Sherfey Custom ORM

### About

A custom lightweight ORM to abstract away use of SQL statements in Java using annotations

## Local Install using Maven

**groupId** - com.revature <br>
**artifactId** - justin_sherfey_p1 <br>
**version** - 2.0 <br>

## Getting Started

Be sure to configure database connection in ConnectionService.java to have valid credentials to the correct database

Use the annotations below to to build the database models to be persisted

`@PK`  creates the primary key column <br>
      arguments (boolean serial) -> whether primary key will be serial or not <br> <br> 
`@Column`  creates a column  <br>
      arguments (boolean notNull, boolean Unique) -> whether column will have notNull and unique constrains <br> <br>
      
Use the library methods below to persist object models to the database

| Method          | Description            |
| -----------     | ----------------       |
| `void create(object.class)`    | creates a table if one does not exist in the database yet     |
| `object read(object.class, primarykey)` | Reads from the database using primary key and returns the object wanted |
| `void update(object)` | Updates an object in a table, if does not exist inserts into |
| `delete(object)` | Deletes the item requested identified by primary key |

Sample Web Implementation using servlets and endpoints, Accessed with Postman
https://github.com/Justin-Sherfey/webapp_justin_sherfey_p1/tree/master/src/main/java/com/revature
