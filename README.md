NoSQLClient
===========

A client for NoSql implemented with swing, first worked for redis.

Note: while using, you should rename the jar to nosqlClient.jar(e.g.  nosqlClient-1.3.3.jar --> nosqlClient.jar)

V1.3.3:

      1. Fix: add and remove row function at the Edit pop window 
      2. Fix: update the history drop box while changed, the connection not changed bug
      3. Enhancement: for edit table: row value changed, click the save button 
         the data not updated, make it update while click the save button

Note: while using, you should

      1. Copy the jar file to your nosql direction, remove the old version
      2. Rename the file name to nosqlClient.jar(e.g.  nosqlClient-1.3.3-1.jar --> nosqlClient.jar)
      
V1.3.2:

      1. New: Add table filter for table set, list, hash at main frame;(nosqlClient-1.3.2-1)
      2. Enhansment: Upgrade client version, add version to constant class;(nosqlClient-1.3.2-2)
      3. Fix: release the redis connection to the pools after use the conn;(nosqlClient-1.3.2-3)

V1.3.1:

      1. New: add copy button at main frame;
      2. Fix: make the sub jtable column can be sorted.

V1.3.0:

      1. Enhansment: the resize the compoment while the widows size is changed.
      2. New: add the db index select combox and auto fill the password while change the history.
      3. Enhansment: change the db to sqlite, remove the derby db, 
         upgrade step: 
            remove all the files except thress file or dir: jre start.bat;
            Upgrade the jar nosqlClient.jar;
            then run start.bat to start the client, it will replace the db while started.
     note: first will have NUllPointException, the later use will not occure.

V1.2.1:

      1. New: add redis operation result pop message.
      2. New: popup the delete config window.

V1.2.0:

      1.New: add the add window and edit window;

V1.1:

      1.New: add name and type for table at the right main panel when select the item at left tree.
      2.New: add, del and edit button at search window.
