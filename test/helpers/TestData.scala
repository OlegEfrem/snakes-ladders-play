package helpers

import java.time.LocalDate

import scala.util.Random

trait TestData {

  def randomEmail: String = s"$randomString@$randomString.com"

  def randomString: String = Random.alphanumeric.take(10).mkString("")

  def randomDateOfBirth: LocalDate = LocalDate.now.minusYears(Random.nextInt(50))

}
