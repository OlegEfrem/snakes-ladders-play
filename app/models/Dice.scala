package models

import scala.util.Random

object Dice {
  def roll: Int = Random.nextInt(6) + 1
}
