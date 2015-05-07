package util

trait BaseDao[T] {

  def all: List[T]

  def create(entry: T): Option[T]

  def find(id: Long): T

  def update(entry: T): Boolean

  def delete(id: Long): Boolean

}
