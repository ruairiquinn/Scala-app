import models.Task
import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import play.api.test._
import play.api.test.Helpers._
import java.util.Date
import anorm._
import play.api.test.FakeApplication
import play.api.Play.current
import play.api.db.DB

class ToDoSpec extends Specification {

  "Adding one ToDo item" should {
    "result in one todo item being persisted" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())){
        val todo1 = "First ToDo"
        Task.create(todo1)
        Task.all().size must beEqualTo(1)
        Task.all()(0).label must beEqualTo(todo1)
      }
    }
  }

  "Adding two ToDo items" should {
    "result in two todo item being persisted" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())){
        val todo1 = "First ToDo"
        val todo2 = "Second ToDo"
        Task.create(todo1)

        Task.all().size must beEqualTo(1)
        Task.create(todo2)
        Task.all().size must beEqualTo(2)

        Task.all()(0).label must beEqualTo(todo1)
        Task.all()(1).label must beEqualTo(todo2)
      }
    }
  }

  "Adding two ToDo items and removing the 1st" should {
    "result in the second todo being the only remaining one" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())){
        val todo1 = "First ToDo"
        val todo2 = "Second ToDo"
        Task.create(todo1)
        Task.create(todo2)
        Task.all().size must beEqualTo(2)

        Task.delete(1)
        Task.all().size must beEqualTo(1)
        Task.all()(0).label must beEqualTo(todo2)
      }
    }
  }
}