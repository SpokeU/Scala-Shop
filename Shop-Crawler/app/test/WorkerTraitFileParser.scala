package test

import akka.actor.ActorRef
import akka.actor.Actor
import scala.collection.mutable.Map
import akka.actor.Props
import akka.actor.ActorSystem
import java.util.UUID
import akka.actor.ActorLogging

trait Job { val jobId: String }
case class JobRequest(jobId: String, data: Any)
case class JobResponse(override val jobId: String, data: Option[Any] = None) extends Job
case class JobErrorResponse(override val jobId: String, errorMessage: String) extends Job

trait ManagerActor extends Actor with ActorLogging {

  val workers: ActorRef

  val requestIdToSender: Map[String, ActorRef] = Map()
  val totalJobs: Map[String, Long] = Map().withDefaultValue(0)
  val processedJobs: Map[String, Long] = Map().withDefaultValue(0)

  def receive = {
    case request: JobRequest => {
      val jobId = request.jobId
      requestIdToSender(jobId) = sender
      try {
        val jobsForWorkers = processJobRequest(request)
        sendJobToWorkers(jobId, jobsForWorkers)
      } catch {
        case e: Exception =>
          log.error(e, "Error processing job")
          sender ! JobErrorResponse(jobId, e.getMessage)
      }
    }
    case response: JobResponse => {
      val jobId = response.jobId
      processedJobs(response.jobId) += 1
      processJobResponse(response)
      if (totalJobs(jobId) == processedJobs(jobId)) {
        jobDone(jobId)
      }
    }
    case _ =>
  }

  /**
   * Processes job request performing some logic against data.
   * To send job to workers it should return sequence of data which will be passed to workers.
   * If Nil or Seq[Unit] will be returned.
   */
  def processJobRequest(request: JobRequest): Seq[Any]

  /**
   * Processes response from worker that job was done.
   */
  protected def processJobResponse(response: JobResponse) = {}

  /**
   * Callback that called when all job for particular jobId was done
   */
  protected def jobDone(jobId: String) = {
    log.info(s" JobId : $jobId DONE")
    requestIdToSender(jobId) ! JobResponse(jobId)
  }

  private def sendJobToWorkers(jobId: String, data: Seq[Any]): Unit = {
    data match {
      case Nil         => println("Nil")
      case (Unit) :: _ => println("Unit")
      case data: Seq[Any] => {
        totalJobs(jobId) += data.size
        data.map(workers ! JobRequest(jobId, _))
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

  def processJobRequest(request: JobRequest): Seq[Any] = {
    scala.io.Source.fromFile(request.data.toString()).getLines().toList
  }

}

class LinesWorker extends ManagerActor {

  val workers: ActorRef = context.actorOf(Props[WordsWorker])

  def processJobRequest(request: JobRequest): Seq[Any] = {
    val lines = request.data.toString.split(" ")
    log.info(s"Got line with ${lines.size}")
    lines
  }

}

class WordsWorker extends WorkerActor {

  def processJob(jobRequest: JobRequest): Any = {
    val word = jobRequest.data.toString
    log.info(s"Got word with ${word.size}")
    jobRequest.data.toString.size
  }

}

object Example {
  def main(args: Array[String]): Unit = {
    val fileLocation: String = "E:\\akkaTestWordsFile.txt"
    val requestId = UUID.randomUUID().toString()

    val system = ActorSystem("FileParserWorkerSystem")
    val akkaManager = system.actorOf(Props[AkkaManager], name = "FileParserManager")

    akkaManager ! JobRequest(requestId, fileLocation)
  }
}