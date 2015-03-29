package akkatest

import akka.actor.Actor
import akka.actor.Props
import akka.routing.RoundRobinPool
import akka.actor.ActorSystem
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration.DurationInt
import java.util.UUID
import akka.actor.OneForOneStrategy
import akka.dispatch.sysmsg.Resume
import akka.actor.SupervisorStrategy._

object SimpleMain {
  def main(args: Array[String]): Unit = {
    val requestUUD = UUID.randomUUID().toString()

    val fileLocation: String = "D:\\akkaTestWordsFile.txt"
    val system = ActorSystem("FileParserSystem")
    val fileParser = system.actorOf(Props[FileParser])
    fileParser ! ParseFile(requestUUD, fileLocation)

    /*val system = ActorSystem("CrawlerSystem"
    val simpleActor = system.actorOf(Props[ShopParserActor], name = "parserActor") /*.withRouter(RoundRobinPool(nrOfInstances = 10))*/
    simpleActor ! StartParse(DESHEVSHE)
    simpleActor ! Broadcast(Message("Hello, Akka!"))
    simpleActor ! Broadcast(PoisonPill)
    simpleActor ! Message("Boy, that was some tasty arsenic!")*/
  }
}

case class ParseFile(requestId: String, fileLocation: String)
case class ParseFileResponse(requestId: String, totalWordsCount: Int)

case class ParseLine(requestId: String, line: String)
case class ParseLineResponse(requestId: String, wordsCount: Int)

case class ParseWord(requestId: String, word: String)
case class ParseWordResponse(requestId: String, count: Int)

/**
 * Load file and send each line to separate actor to get words count
 */
class FileParser extends Actor {

  val lineParser = context.actorOf(Props[LineParser])
  val parseJobStatus: scala.collection.mutable.Map[String, Boolean] = scala.collection.mutable.Map()
  import scala.collection.mutable._

  var totalLines: Map[String, Long] = Map()
  var processedLines: Map[String, Long] = Map()
  var totalWords = 0

  def receive = {
    case ParseFile(requestId, file) => {
      val fileLines = scala.io.Source.fromFile(file).getLines().toList
      totalLines += requestId -> fileLines.size
      processedLines += requestId -> 0
      println("Total lines : " + totalLines)
      fileLines.foreach { line =>
        lineParser ! ParseLine(requestId, line)
      }
    }
    case ParseLineResponse(requestId, wordsCount) => {
      processedLines(requestId) += 1
      totalWords += wordsCount
      println(s"Got response. Lines left ${totalLines(requestId) - processedLines(requestId)}")
      if (totalLines(requestId) == processedLines(requestId)) {
        println(s"Job Done. for requestId $requestId. Total words: $totalWords")
      }
    }
    case _ => println("Unknown shit reveived")
  }

}

/**
 * Receives parse line message and returs count of words in that line
 */
class LineParser extends Actor {

  val wordParser = context.actorOf(Props[WordsParser])
  implicit val timeout = Timeout(5 seconds)

  var totalWords = 0
  var processedWords = 0

  def receive = {
    case ParseLine(requestId, line) => {
      val words = line.split(" ")
      totalWords += words.size
      sender ! ParseLineResponse(requestId, totalWords)
      //performSomeShit
      //val result = words.map { wordParser ? ParseWord(requestId, _) }
    }
    case ParseWordResponse(requestId, charSize) => {
      processedWords += 1
      if (totalWords == processedWords) {

      }
    }
  }
}

class WordsParser extends Actor {
  def receive = {
    case ParseWord(requestId, word) => sender ! ParseWordResponse(requestId, word.size)
    case _                          =>
  }
}