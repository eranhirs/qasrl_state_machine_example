package qasrl_state_machine_example.models

trait InputExample {
  def getSentence: String

  def getPredicateIdx: Integer

  def getVerbForm: String

  def getQuestion: String

  def getSentenceTokens: Vector[String] = this.getSentence.split(" ").toVector
}
