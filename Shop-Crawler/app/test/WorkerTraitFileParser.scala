package test

import java.util.UUID

import scala.collection.mutable.Map
import scala.concurrent.ExecutionContext.Implicits.global

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props

trait Job { val jobId: String }
case class JobRequest(jobId: String, data: Any)
case class JobResponse(override val jobId: String, data: Option[Any] = None) extends Job
case class JobErrorResponse(override val jobId: String, errorMessage: String) extends Job

trait ManagerActor extends Actor with ActorLogging {

  val workers: ActorRef

  val jobIdToSender: Map[String, ActorRef] = Map()
  val totalJobs: Map[String, Long] = Map().withDefaultValue(0)
  val processedJobs: Map[String, Long] = Map().withDefaultValue(0)

  val additionalMapping: Map[String, String] = Map()

  def receive = {
    case request: JobRequest   => processJobRequest(request)
    case response: JobResponse => processJobResponse(response)
    case _                     =>
  }

  def processJobRequest(request: JobRequest) = {
    val jobId = request.jobId
    jobIdToSender(jobId) = sender
    try {
      val jobsForWorkers = receiveJob(request)
      sendJobToWorkers(jobId, jobsForWorkers)
    } catch {
      case e: Exception =>
        log.error(e, "Error processing job")
        sender ! JobErrorResponse(jobId, e.getMessage)
    }
  }

  protected def processJobResponse(response: JobResponse) = {
    val jobId = response.jobId
    processedJobs(response.jobId) += 1
    workerResponse(response)
    if (totalJobs(jobId) == processedJobs(jobId)) {
      jobDone(jobId)
    }
  }

  /**
   * Receive job and perform logic against data.
   * To send job to workers it should return sequence of data which will be passed to workers.
   * If Nil or Seq[Unit] will be returned .
   */
  def receiveJob(request: JobRequest): Seq[Any]

  /**
   * Processes response from worker that job was done.
   */
  def workerResponse(response: JobResponse)

  /**
   * Callback that called when all workers for particular jobId done their jobs
   */
  protected def jobDone(jobId: String) = {
    log.info(s" JobId : $jobId DONE")
  }

  private def sendJobToWorkers(jobId: String, data: Seq[Any]): Unit = {
    data match {
      case Nil         => log.info("Nil")
      case (Unit) :: _ => log.info("Unit list")
      case data: Seq[Any] => {
        if (totalJobs.contains(jobId)) {
          val newJobId = UUID.randomUUID().toString()
          jobIdToSender(newJobId) = sender
          totalJobs(newJobId) += data.size
          data.map(workers ! JobRequest(newJobId, _))
        } else {
          totalJobs(jobId) += data.size // FIX? same jobId.Should be different?
          data.map(workers ! JobRequest(jobId, _))
        }
      }
    }
  }

}

trait WorkerActor extends Actor with ActorLogging {
  def receive = {
    case request: JobRequest => {
      val result = processJob(request)
      sender ! JobResponse(request.jobId, Some(result))
    }
  }

  def processJob(jobRequest: JobRequest): Any
}

// Implementation

class AkkaManager extends ManagerActor {

  val workers: ActorRef = context.actorOf(Props[LinesWorker]) //def actorProps:Props[A<ActorWorker]

  val resultsAggregation: Map[String, Int] = Map().withDefaultValue(0)

  def receiveJob(request: JobRequest): Seq[Any] = {
    scala.io.Source.fromFile(request.data.toString()).getLines().toList
  }

  def workerResponse(response: JobResponse): Unit = {
    response.data match {
      case Some(x: Int) =>
        println(s"Line reponse $x")
        println(totalJobs)
        println(processedJobs)
        resultsAggregation(response.jobId) += x
      case x => println(x)
    }
  }

  override def jobDone(jobId: String) = {
    println("TOTAL CHARACTERS SIZE IS " + resultsAggregation(jobId))
  }

}

class LinesWorker extends ManagerActor {

  val workers: ActorRef = context.actorOf(Props[WordsWorker])

  val jobToWords: Map[String, Int] = Map().withDefaultValue(0)

  def receiveJob(request: JobRequest): Seq[Any] = {
    val lines = request.data.toString.split(" ")
    println(s"Got line with ${lines.size}")
    lines
  }

  def workerResponse(response: JobResponse): Unit = {
    response.data match {
      case Some(x: Int) =>
        jobToWords(response.jobId) += x
      case _ => println("uNDEAD")
    }
  }

  override def jobDone(jobId: String) = {
    println(s"LinesWorker: jobId $jobId done. Total words for request is ${jobToWords(jobId)}")
    jobIdToSender(jobId) ! JobResponse(jobId, Some(jobToWords(jobId)))
  }

}

class WordsWorker extends WorkerActor {

  def processJob(jobRequest: JobRequest): Any = {
    val word = jobRequest.data.toString
    println(s"Got word $word . Size: ${word.size} letters")
    word.size
  }

}

object Example {
  def main(args: Array[String]): Unit = {
    val fileLocation: String = "E:\\akkaTestWordsFile.txt"
    val requestId = UUID.randomUUID().toString()

    val system = ActorSystem("FileParserWorkerSystem")
    val akkaManager = system.actorOf(Props[AkkaManager], name = "FileParserManager")

    //akkaManager ! JobRequest(requestId, fileLocation)

    val x = true
    x match {
      case true  => println("true")
      case false => println("false")
    }

    /*val jobId = parser.parseShop(DESHEVSHE)(DESHEVSHE.toString)
    while (!parser.jobDone(jobId)) {
      Thread.sleep(10000)
      println("Running")
    }
    println("Done")*/
  }
}