package parser

import pageobject.ProductInfoPage
import JobResultsAggregator._

object ProductParser {

  def parse(product: ProductInfoPage)(implicit jobId: Int) = {
    addToTotalProducts
    saveProduct(product)
    addToProcessedProducts
  }

  private def saveProduct(product: ProductInfoPage) = {
    println(s"Saving product ${product.name}")
  }

}