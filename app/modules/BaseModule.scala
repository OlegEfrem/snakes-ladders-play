package modules

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import play.api.ApplicationLoader
import service.SnakesLaddersService
import service.dao.mongo.{ GameInstanceMongoDao, GameSetupMongoDao, PlayerMongoDao }
import service.dao.{ GameInstanceDao, GameSetupDao, PlayerDao }
import service.impl.DefaultSnakesLaddersService

/**
 * The base Guice module.
 */
class BaseModule extends AbstractModule with ScalaModule {

  /**
   * Configures the module.
   */
  def configure(): Unit = {
    ApplicationLoader
    bind[PlayerDao].to[PlayerMongoDao]
    bind[GameSetupDao].to[GameSetupMongoDao]
    bind[GameInstanceDao].to[GameInstanceMongoDao]
    bind[SnakesLaddersService].to[DefaultSnakesLaddersService]
  }

}
