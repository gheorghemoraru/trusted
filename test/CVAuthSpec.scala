import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._


/**
 * Test the implementation of the Crowd Valley Auth object
 */
@RunWith(classOf[JUnitRunner])
class CVAuthSpec extends Specification {

    val targetLen = 16;
    
    var apiKey = "testapikey-001"
    var apiSecret = "ap1s3cr3t"
    var username = "test@test.com"
    var password = "testtest"
    var network = "testnetwork"
    var apiBase = "https://sandbox.crowdvalley.com/v1"
    var apiBasicUsername = ""
    var apiBasicPassword = ""
    
    val targetPass = "FRVCB0cGAUcA"

    "CV Auth" should {

        "encrypt passwords" in {
            var encrypted = util.cvauth.CVAuth.encrypt(apiSecret, password, targetLen)
            
            new sun.misc.BASE64Encoder().encode(encrypted.getBytes) must
                        equalTo(targetPass)
          
        }
        
        "create tokens" in {
            
            var encrypted = util.cvauth.CVAuth.encrypt(apiSecret, password, targetLen)
            
            var token = util.cvauth.CVAuth.createToken(apiKey, apiSecret, username, encrypted)
            
            println("[Token]=" + token)
            
            token must startWith("AuthToken")
        }

  }
}
