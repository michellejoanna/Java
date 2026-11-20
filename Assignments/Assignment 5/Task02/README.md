# Task A2 – Don't Read What You Don't Need

A 50 MiB file contains 52,428,800 bytes. Calling InputStream.read() once per byte can require roughly 52.4 million Java read calls. The operating system may buffer internally, but Java method-call overhead remains.

Wrap the stream in BufferedInputStream (for example, 64 KiB). The scan remains O(n), but buffer refills happen in blocks. A character log can use BufferedReader.readLine().

Buffering helps little for tiny files, in-memory data or CPU-bound work. Files.readAllBytes takes O(n) time and O(n) memory; a 50 MiB file needs at least about 50 MiB plus overhead. Streaming uses O(buffer size) auxiliary memory.

BufferingBenchmark generates 1, 10 and 50 MB files and times byte-at-a-time, buffered and whole-file strategies. Record your own measured values.

## Run
Compile and run BufferingBenchmark.java.