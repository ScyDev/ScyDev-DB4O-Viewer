/*
* Released 10.12.2010 by ScyDev.ch
* under the New BSD license.
*/

SETUP
-----

      !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
      !!! IF YOU DON'T DO THESE 3 STEPS, NOTHING WILL WORK !!!
      !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

- drop the DB4O JAR that your server is currently running on, into the lib directory
- adjust the BAT file to include that JAR in the class path
- and also include the path to your classes that you store in the DB

- after connecting, wait patiently until all the classes in the DB are scanned


FEATURES
--------

- connect to any running DB4O server
- filter the displayed classlist by package path (like "scythe.dev.") to only see the classes that are important to your application
- open detail view of an object by double-clicking it in the result table
- browse object in any depth, references are resolved on the fly
--> !! simply drag & drop field values between two or more objects !!
- refresh, edit, save, delete any object


POWERED BY
----------

- Jigloo GUI Editor
- SQL for DB4o
  - http://www.datanucleus.org/products/accessplatform_1_0/db4o/sql4o.html
- DB4O


