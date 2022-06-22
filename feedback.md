# Migrations

* Typically, migration names should be prefixed with digits to denote the order
  * For example, create_pokemon_table.xml should be named **001_create_pokemon_table.xml** instead

* Using <text> when <varchar> fits better
  * text column types should be reserved for larger amounts of data

* It's discouraged from using regular ints as ids, as with larger applications can run out of ints. Instead, opt for big ints or UUIDs