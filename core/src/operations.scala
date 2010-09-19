package sbinary;

object Operations {
  import java.io._
  import JavaIO._

  def format[T: Format]                         = implicitly[Format[T]]
  def read[T: Reads](in : Input)                = implicitly[Reads[T]] reads in
  def write[T: Writes](out : Output, value : T) = implicitly[Writes[T]].writes(out, value)

  /**
   * Get the serialized value of this class as a byte array.
   */
  def toByteArray[T: Writes](t : T): Array[Byte] = {
    val target = new ByteArrayOutputStream()
    implicitly[Writes[T]].writes(target, t)
    target.toByteArray()
  }
 
  /**
   * Read a value from the byte array. Anything past the end of the value will be
   * ignored.
   */ 
  def fromByteArray[T: Reads](array : Array[Byte]) = read[T](new ByteArrayInputStream(array))

  /** 
   * Convenience method for writing binary data to a file.
   */
  def toFile[T: Writes](t : T)(file : File) = {
    val out = new BufferedOutputStream(new FileOutputStream(file))
    
    try out.write(toByteArray(t))
    finally out.close()
  }

  /** 
   * Convenience method for reading binary data from a file.
   */
  def fromFile[T: Reads](file : File) = {
    val in = new BufferedInputStream(new FileInputStream(file))

    try read[T](in)
    finally in.close()
  }
}
