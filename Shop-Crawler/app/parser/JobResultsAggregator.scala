package parser

import scala.collection.mutable.Map

object JobResultsAggregator {

  private val totalCategories = Map[Int, Int]().withDefaultValue(0)
  private val processedCategories = Map[Int, Int]().withDefaultValue(0)

  private val totalProducts = Map[Int, Int]().withDefaultValue(0)
  private val processedProducts = Map[Int, Int]().withDefaultValue(0)

  def addToTotalCategories(implicit jobId: Int) {
    totalCategories(jobId) += 1
  }

  def addToProcessedCategories(implicit jobId: Int) {
    processedCategories(jobId) += 1
  }

  def addToTotalProducts(implicit jobId: Int) {
    totalProducts(jobId) += 1
  }

  def addToProcessedProducts(implicit jobId: Int) {
    processedProducts(jobId) += 1
  }

  def cleanJobResults(implicit jobId: Int) {
    totalCategories(jobId) = 0
    processedCategories(jobId) = 0
    totalProducts(jobId) = 0
    processedProducts(jobId) = 0
  }
  
  def getTotalCategories(implicit jobId:Int) = {
    totalCategories(jobId)
  }
  
  def getTotalProducts(implicit jobId:Int) = {
    totalProducts(jobId)
  }
  
  def getProcessedCategories(implicit jobId:Int) = {
    processedCategories(jobId)
  }
  
  def getProcessedProducts(implicit jobId:Int) = {
    processedProducts(jobId)
  }

}