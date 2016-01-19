package util.cvauth

import javax.inject.Inject
import play.api.libs.ws.WSClient
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.Future

class CVAuthRestClient(ws: WSClient) {
    
    val targetLen = 16;
    var apiKey = "trusted-001"
    var apiSecret = "uY1B1Uh9G2"
    var username = "lucas.carbonaro@gmail.com"
    var password = "iTzpFohxuL"
    var network = "trusted"
    var apiBase = "https://api.sandbox.crowdvalley.com/v1"
    var apiBasicUsername = ""
    var apiBasicPassword = ""
    
    var encrypted = util.cvauth.CVAuth.encrypt(apiSecret, password, targetLen)
            
    var token = util.cvauth.CVAuth.createToken(apiKey, apiSecret, username, encrypted)
            
    
    def isAlive():Future[String] = {
        ws.url(apiBase+"/"+network).withHeaders("Accept" -> "application/json").get().map { response =>
            response.body
        }
    }
    
    def getOfferings():Future[String] = {
        
        println("TOKEN:" + token)
        
        ws.url(apiBase+"/"+network+"/offerings?page=0&limit=10&orderby=sort_number&sort=asc")
            .withHeaders("Accept" -> "application/json",
                "cv-auth" -> token)
            .get().map { response =>
                response.body
        }
    }
}