JFLAGS = -g
JC = javac

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	BTree.java \
	Node.java \
	IntrnlNode.java \
	Leaf.java \
	KeyValue.java \
	Pair.java\
	bplustree.java



default: classes

classes: $(CLASSES:.java=.class)

run: $(MAIN).class
	$(JVM) $(MAIN)

clean:
	$(RM) *.class
