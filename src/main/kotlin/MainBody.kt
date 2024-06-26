import java.lang.Exception

fun mainBody(command: MyCommand): String {
    val userId : String = command.userId
    println("Executing the main logic.....")

    logger.debug("This is an Debug Message!")
    logger.info("This is an Info Message!")
    logger.warn("This is an Warn Message!")
    logger.error("This is an Error Message!")
    logger.fatal("This is an Fatal Message!")
    logger.trace("This is an Trace Message!")
    try{
        logger.trace("Entering the Try block")
        println(100/0)
    }
    catch(e : Exception){
        logger.error("Encountered the exception ${e.message}")
    }

    return "${userList[userId]?.name} is authorized to this code, finished main block"
}