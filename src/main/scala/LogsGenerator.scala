import org.slf4j.Logger
import org.slf4j.LoggerFactory

object LogsGenerator {
  def main(args: Array[String]): Unit = {
    val logger: Logger = LoggerFactory.getLogger(LogsGenerator.getClass)

    //ERROR > WARN > INFO > DEBUG > TRACE
    for (messageNum <- 1 to 10) {
      logger.trace(s"Trace Message $messageNum")
      logger.debug(s"Debug Message $messageNum")
      logger.info(s"Info Message $messageNum")
      logger.warn(s"Warn Message $messageNum")
      logger.error(s"Error Message $messageNum")
      Thread.sleep(1000) // wait for 1000 millisecond
    }
  }
}
