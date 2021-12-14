package com.example.medicationreminder

import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

object ZipFileManager {

    const val ZIP_FILE_EXTENSION = ".zip"

    private const val BUFFER_SIZE = 2048
    private const val END_OF_STREAM = -1
    private const val BUFFER_OFFSET = 0

    private fun zipRecursively(outStream: ZipOutputStream, inFile: File, parentDir: String) {
        val buffer = ByteArray(BUFFER_SIZE)

        inFile.listFiles()?.forEach { file ->
            if (file.isDirectory) {
                val entry = ZipEntry(file.name + File.separator)
                entry.time = file.lastModified()
                entry.size = file.length()

                outStream.putNextEntry(entry)

                zipRecursively(outStream, file, file.name)
            }
            else {
                if (!file.name.contains(ZIP_FILE_EXTENSION)) {
                    file.inputStream().use { inputStream ->
                        inputStream.buffered(BUFFER_SIZE). use { bufferedStream ->
                            val path = parentDir + File.separator + file.name
                            val entry = ZipEntry(path)
                            entry.time = file.lastModified()
                            entry.size = file.length()
                            outStream.putNextEntry(entry)
                            var byteCount = 0
                            while (byteCount != END_OF_STREAM) {
                                byteCount = bufferedStream.read(buffer)

                                if (byteCount != END_OF_STREAM) {
                                    outStream.write(buffer, BUFFER_OFFSET, byteCount)
                                }
                            }
                            outStream.closeEntry()
                        }
                    }
                }
                else {
                    outStream.closeEntry()
                }
            }
        }
    }


    fun streamFolderToZip(folder: File, zipAsStream: OutputStream) {

        ZipOutputStream(zipAsStream.buffered(BUFFER_SIZE)).use { outStream ->
            zipRecursively(outStream, folder, "")
        }
    }

    fun streamZipToFolder(zipAsStream: InputStream, folder: File) {
        ZipInputStream(zipAsStream.buffered(BUFFER_SIZE)).use { inStream ->
            var entry: ZipEntry? = inStream.nextEntry

            val buffer = ByteArray(BUFFER_SIZE)

            if (!folder.exists()) {
                folder.mkdir()
            }
            while (entry != null) {
                if (entry.isDirectory) {
                    val file = File(folder.path + File.separator + entry.name)

                    if (!file.exists()) {
                        file.mkdirs()
                    }
                }
                else {
                    val file = File(folder.path + File.separator + entry.name)
                    val bufferedStream = file.outputStream().buffered(BUFFER_SIZE)

                    var byteCount = inStream.read(buffer)
                    while (byteCount != END_OF_STREAM) {
                        bufferedStream.write(buffer, BUFFER_OFFSET, byteCount)
                        byteCount = inStream.read(buffer)
                    }
                    bufferedStream.close()
                }

                inStream.closeEntry()
                entry = inStream.nextEntry
            }
        }
    }

}