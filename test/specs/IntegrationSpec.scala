package specs

import helpers.DbCleanup
import org.scalatest.WordSpec

trait IntegrationSpec extends WordSpec with BaseSpec with DbCleanup
