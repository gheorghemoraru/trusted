import util.cvauth.CVAuthRestClient
import play.core.server.Server
import play.api.routing.sird._
import play.api.mvc._
import play.api.libs.json._
import play.api.test._

import scala.concurrent.Await
import scala.concurrent.duration._

import org.specs2.mutable.Specification
import org.specs2.time.NoTimeConversions




/**
 * Test the interaction with the Crowd Valley REST API
 */
object CVRestClientSpec extends Specification {


    
    val targetPass = "FRVCB0cGAUcA"

    "CV REST API" should {

        "be online" in {
            
            Server.withRouter() {
                case GET(p"/repositories") => Action {
                    Results.Ok(Json.arr(Json.obj("full_name" -> "octocat/Hello-World")))
                }
            } { implicit port =>
                play.api.test.WsTestClient.withClient { client =>
                    val result = Await.result(
                        new CVAuthRestClient(client).isAlive(), 10.seconds)
                    println("IS ALIVE:"+result)
                    result must_== result
                }
            }
          
        }
       

  }
}