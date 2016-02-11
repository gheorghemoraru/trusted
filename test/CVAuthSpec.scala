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
    
    var apiKey = "trusted-001"
    var apiSecret = "uY1B1Uh9G2"
    var username = "lucas.carbonaro@gmail.com"
    var password = "iTzpFohxuL"
    var network = "trusted"
    var apiBase = "https://api.sandbox.crowdvalley.com/v1"
    var apiBasicUsername = ""
    var apiBasicPassword = ""
    
    val targetPass = "HA1LMnc6AEEyfg=="

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
