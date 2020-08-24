import org.slf4j.Logger
import org.slf4j.LoggerFactory

object LogsGenerator {
  def main(args: Array[String]): Unit = {
    println("txt")
    val logger: Logger = LoggerFactory.getLogger(LogsGenerator.getClass)
    logger.info("This is an info message")
    logger.error("This is an error message")
    logger.debug("Here is a debug message")  }
}
