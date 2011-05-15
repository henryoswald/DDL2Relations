Converts it to a set of relations text file for database documentation. 

takes 1 argument of the path to the file you want to convert. 
Only tested with Oracle express 11 DDL exporting only Table information


1) Export DDL from Oracle Express 11, only selecting Table information
2) save information to txt/sql etc file
3) run the program passing it the location of your file (e.g. pass it "oracle.sql"
	if the file is in the application root folder)
4) a file called relations.txt will be written to the application root folder