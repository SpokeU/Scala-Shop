package test

import akka.actor.Props
import akka.util.Timeout
import akka.actor.Actor
import scala.concurrent.duration.DurationInt
import scala.collection.mutable.Map

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

  var totalWords: Map[String, Long] = Map()
  var processedWords: Map[String, Long] = Map()

  def receive = {
    case ParseLine(requestId, line) => {
      val words = line.split(" ")
      totalWords += requestId -> words.size
      processedWords += requestId -> 0
      //performSomeShit
      words.map { wordParser ! ParseWord(requestId, _) }
    }
    case ParseWordResponse(requestId, charSize) => {
      processedWords(requestId) += 1
      if (totalWords == processedWords) {
        println(s"$requestId is done")
        ParseLineResponse(requestId, processedWords(requestId).toInt)
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