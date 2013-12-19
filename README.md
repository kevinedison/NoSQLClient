NoSQLClient
===========

a no sql implement with swing, first worked for redis.

Note: while using, you should rename the file name to nosqlClient.jar(e.g.  nosqlClient-1.3.2-2.jar --> nosqlClient.jar)

V1.3.3:
      1. Repair add and remove row function at the Edit pop window 
      2. Fix the history drop box changed, the connection not changed bug
      3. Function enhancement for edit table row value changed click the save button the data not updated, make it               update while click the save button

Note: while using, you should
      1. copy the jar file to your nosql direction, remove the old version
      2. then rename the file name to nosqlClient.jar(e.g.  nosqlClient-1.3.3-1.jar --> nosqlClient.jar)
      
V1.3.2:
      1. Add table filter for table set, list, hash at main frame;(nosqlClient-1.3.2-1)
      2. Upgrade the version of client, and add the version to the constant class;(nosqlClient-1.3.2-2)
      3. Release the redis connection to the pools after use the conn;(nosqlClient-1.3.2-3)

V1.3.1:
      1. Add copy button at main frame;
      2. Make the sub jtable column can be sorted.

V1.3.0:
      1. Enhansment the resize the compoment while the widows size is changed.
      2. Add the db index select combox and auto fill the password while change the history.
      3. Change the db to sqlite, remove the derby db, upgrade step need to remove the all the files except jre                  start.bat and the nosqlClient.jar
         just keep the file: nosqlClient.jar, start.bat a nd the jre.
          note: first will have NUllPointException, the later use will not occure.

V1.2.1:
      1. Add redis operation result pop message.
      2. PopUp the delete config window.

V1.2.0:
      1.Add the add window and edit window;

V1.1:
      1.Add the table name and table type at the right main panel while select the item at left tree.
      2.Add add, del andm edit button at search window.
