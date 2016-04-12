Given a messenger where 
	valid server is inf.ug.edu.pl and invalid server is inf.ug.edu.eu
	valid message is somelongmessage and invalid message is ab

When server is valid and message is valid
Then checking connection should return 0
And sending message should return code status 0 or 1

When server is invalid and message is valid
Then checking connection should return 1
And sending message should return code status 1

When server is valid and message is invalid
Then sending message should return code status 2
