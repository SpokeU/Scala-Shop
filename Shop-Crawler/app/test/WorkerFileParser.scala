/*package test

import akka.actor.ActorRef
import akka.actor.Actor
import scala.collection.mutable.Map
import akka.actor.Props
import akka.actor.ActorSystem
import java.util.UUID

case class JobRequest(requestId: String, data: Any)
case class JobResponse(requestId: String, status: String)

class AkkaManager extends Actor {

  val workers = context.actorOf(Props[AkkaWorker])

  val requestIdToSender: Map[String, ActorRef] = Map()
  val totalJobs: Map[String, Long] = Map().withDefaultValue(0)
  val processedJobs: Map[String, Long] = Map().withDefaultValue(0)

  def receive = {
    case JobRequest(requestId, data) => {
      val fileLines = scala.io.Source.fromFile(data.toString()).getLines().toList
      requestIdToSender(requestId) = sender
      sendJobToWorkers(requestId, fileLines)
    }
    case JobResponse(reqestId, status) => {
      processedJobs(reqestId) += 1
      if (totalJobs(reqestId) == processedJobs(reqestId)) {
        jobDone(reqestId)
      }
    }
  }

  def sendJobToWorkers(requestId: String, data: Seq[Any]) = {
    totalJobs(requestId) += data.size
    data.map { workers ! JobRequest(requestId, _) }
  }

  def jobDone(reqestId: String) = {
    println(s" RequestId : $reqestId DONE")
    requestIdToSender(reqestId) ! JobResponse(reqestId, "Success")
  }

}

class AkkaWorker extends Actor {

  def receive = {
    case JobRequest(jobId, data) => {
      val words = data.toString().split(" ")
      println(s"$jobId : Words in Line ${words.size}")
      sender ! JobResponse(jobId, "SUCCESS")
    }
  }

}

object Example {
  def main(args: Array[String]): Unit = {
    val fileLocation: String = "E:\\akkaTestWordsFile.txt"
    val requestId = UUID.randomUUID().toString()

    val system = ActorSystem("FileParserWorkerSystem")
    val akkaManager = system.actorOf(Props[AkkaManager])

    akkaManager ! JobRequest(requestId, fileLocation)
  }
}*/