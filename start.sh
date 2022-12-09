bash cleanup.sh
javac Node.java
javac Controller.java
java Controller 50 &
java Node 0 50 3 "ßMessage from 0" &
java Node 1 50 -1 &
java Node 2 50 -1 &
java Node 3 50 -1 &
java Node 4 50 -1 &

#0 -> 3
#1 -> 0, 3
#2 -> 1
#3 -> 2, 4
#4 -> 0