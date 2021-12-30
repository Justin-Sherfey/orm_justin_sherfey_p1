# Justin Sherfey Custom ORM

### About

A custom lightweight ORM to abstract away use of SQL statements in Java using annotations

### Getting Started

Use the annotations below to to build the database models to be persisted

`@PK    `  creates the primary key column <br>
      arguments (boolean serial) -> whether primary key will be serial or not <br> <br> <br>
`@Column`  creates a column  <br>
      arguments (boolean notNull, boolean Unique) -> whether column will have notNull and unique constrains <br> <br> <br>

