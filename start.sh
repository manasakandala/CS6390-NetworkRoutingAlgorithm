bash cleanup.sh
javac Node.java
javac Controller.java
java Controller 50 &
java Node 0 50 3 "Message from 0" &
java Node 1 50 -1 &
java Node 2 50 -1 &
java Node 3 50 -1 &
java Node 4 50 -1 &
java Node 5 50 -1 &