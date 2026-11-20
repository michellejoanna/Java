# Task A4 – What Should Be Serialized?

Persist stable domain data: hostel name, room number, resident register number, resident name and phone number. Mark password credentials, live database connections, sockets and runtime sessions transient. Recreate runtime resources after loading.

Serializing Hostel traverses its rooms list and each room's residents list. Every reachable object must be serializable unless its reference is transient. Sensitive secrets should not be stored in the serialized graph.

Keep an explicit serialVersionUID. When phoneNumber is added, older compatible serialized streams normally supply null for the new reference field. Use readObject, migration code or versioned DTOs to supply defaults. An incompatible serialVersionUID can cause InvalidClassException.

CSV/JSON is human-readable and easier to migrate across languages, but may be larger and needs parsing. Java serialization preserves object graphs compactly but couples data to Java class definitions.

## Run
Compile and run HostelSerializationDemo.java.