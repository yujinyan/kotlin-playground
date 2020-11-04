import com.mongodb.client.model.UpdateOptions
import org.litote.kmongo.*

class Shop(
  val meta: Meta
)

class Meta(
  val status: Int
)

fun getShop(): Shop = TODO()

fun main() {

  Shop::meta / Meta::status gt 1
  Meta::status gt 1
//  Meta::status gt "1"

  val client = KMongo.createClient() //get com.mongodb.MongoClient new instance
  val database = client.getDatabase("test") //normal java driver usage
  val col = database.getCollection<Shop>() //KMongo extension method
//here the name of the collection by convention is "jedi"
//you can use getCollection<Jedi>("otherjedi") if the collection name is different

  val shop = getShop()



  col.updateOne(shop, UpdateOptions().bypassDocumentValidation(true) )
}